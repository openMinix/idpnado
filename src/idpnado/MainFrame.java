package idpnado;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mediator.Mediator;
import common.File;
import common.User;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5058979263127835567L;
	private JPanel contentPane;

	private JList<String> usersList;
	private JList<String> filesList;
	private DownloadsJTable downloadsTable;

	private DefaultListModel<String> usersModel;
	private DefaultListModel<String> filesModel;
	private DownloadsTableModel dtm;
	
	private Mediator mediator;
	

	/**
	 * Create the frame.
	 */
	public MainFrame(Mediator mediator) {
		
		this.mediator = mediator;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 500);

		contentPane = new JPanel();

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 53, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 98, 109, 59, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 1.0,
				Double.MIN_VALUE, 1.0 };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 3.0, 1.0,
				Double.MIN_VALUE };

		contentPane.setLayout(gbl_contentPane);

		usersInit();
		filesInit();

		downloadsInit();
	}

	private void filesInit() {

		filesModel = new DefaultListModel<>();
		filesList = new JList<String>(filesModel);

		filesList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {

				if (evt.getClickCount() == 2) {
//					int index = filesList.locationToIndex(evt.getPoint());
					int index = filesList.getSelectedIndex();
					
					if(index == -1)
						return;

					System.out.println("Selecte value is " + index
							+ "dim lista" + filesList.getModel().getSize());
					System.out.println(usersList.getSelectedIndex());
					
					String filename = filesList.getModel().getElementAt(index);
					String source = usersList.getSelectedValue();
					String destination = "me";
					String state = TransferState.Receiving.name();
					int progress = 70;

					dtm.addRow(new Object[] { source, destination, filename,
							progress, state });
				}
			}
		});


		GridBagConstraints gbc_filesList = new GridBagConstraints();
		gbc_filesList.gridheight = 2;
		gbc_filesList.insets = new Insets(0, 0, 5, 5);
		gbc_filesList.fill = GridBagConstraints.BOTH;
		gbc_filesList.gridx = 2;
		gbc_filesList.gridy = 1;
		gbc_filesList.gridwidth = 3;
		contentPane.add(filesList, gbc_filesList);

	}
	
	public void addFileToUser(String userName, String fileName)
	{
		int elementIndex = usersModel.indexOf(userName);
		if(elementIndex == -1)
			return;
		
		//add file to current view if necessary
		int selectedIndex = usersList.getSelectedIndex();
		if(selectedIndex == -1 || !usersModel.get(selectedIndex).equals(userName))
			return;
		
		filesModel.addElement(fileName);
	}
	
	public void removeFileFromUser(String userName, String fileName)
	{
		int elementIndex = usersModel.indexOf(userName);
		if(elementIndex == -1)
			return;

		//remove file from current view if necessary		
		int selectedIndex = usersList.getSelectedIndex();
		if(selectedIndex == -1 || !usersModel.get(selectedIndex).equals(userName))
			return;
		
		filesModel.addElement(fileName);		
	}
	
	public void addUser(String userName)
	{
		if(!usersModel.contains(userName))
			usersModel.addElement(userName);
	}
	
	public void removeUser(String userName)
	{
		usersModel.removeElement(userName);
	}
	
	private void usersInit() {

		usersModel = new DefaultListModel<>();
		usersList = new JList<String>(usersModel);
		usersList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				filesModel.clear();
				System.out.println("INTRU AICI");
				try
				{
					int index = usersList.getSelectedIndex();
					if(index == -1)
						return;
					
					ArrayList<String> fileNames;
					
					if(index == 0)
						fileNames = mediator.getMyFiles();
					else
						fileNames = mediator.getFiles(usersList.getSelectedValue());
					

					for (String fileName : fileNames)
					{
						filesModel.addElement(fileName);
					}
				}
				catch(NullPointerException ex)
				{
					
				}

			}
		});
		GridBagConstraints gbc_usersList = new GridBagConstraints();
		gbc_usersList.gridheight = 3;
		gbc_usersList.insets = new Insets(0, 0, 0, 5);
		gbc_usersList.fill = GridBagConstraints.BOTH;
		gbc_usersList.gridx = 5;
		gbc_usersList.gridy = 1;
		gbc_usersList.gridwidth = 2;
		
		usersModel.addElement(mediator.getMyName());
		contentPane.add(usersList, gbc_usersList);		
		

//		User u1 = new User("user1");
//		u1.files.add(new File("fis de la 1"));
//		u1.files.add(new File("fis2 de la 1"));
//
//		usersModel.addElement(u1);
//
//		User u2 = new User("user2");
//		u2.files.add(new File("fis de la 2"));
//		u2.files.add(new File("fis2 de la 2"));
//
//		usersModel.addElement(u2);
//		contentPane.add(usersList, gbc_usersList);
	}

	private void downloadsInit() {

		dtm = new DownloadsTableModel();
		downloadsTable = new DownloadsJTable(dtm);

		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.gridwidth = 3;
		gbc_table.insets = new Insets(0, 0, 0, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 2;
		gbc_table.gridy = 3;

		JScrollPane tableContainer = new JScrollPane(downloadsTable);

		contentPane.add(tableContainer, gbc_table);
	}

}
