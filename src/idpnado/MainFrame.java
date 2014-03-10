package idpnado;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import common.File;
import common.User;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5058979263127835567L;
	private JPanel contentPane;
	
	private JList<User> usersList;
	private JList<File> filesList;
	private DownloadsJTabel downloadsTable;
	
	private DefaultListModel<User> usersModel;
	private DefaultListModel<File> filesModel;
	private DownloadsTableModel dtm;
	/**
	 * Create the frame.
	 */
	public MainFrame() {
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
		filesList = new JList<File>(filesModel);
		
		filesList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        
		        if (evt.getClickCount() == 2) {
		            int index = filesList.locationToIndex(evt.getPoint());
		            
		        	System.out.println("Selecte value is "  + index  + "dim lista" + filesList.getModel().getSize());
					System.out.println( usersList.getSelectedIndex());
					String filename = "lala";
					 filename = filesList.getModel().getElementAt(index).filename;
					String source = usersList.getSelectedValue().name;
					String destination = "me";
					String state = TransferState.Receiving.name();
					int progress = 70;
					
					
					dtm.addRow( new Object[]{source, destination,filename,progress,state} );
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
	
	public void addUser(User user)
	{
		if(!usersModel.contains(user))
			usersModel.addElement(user);
	}
	
	public void removeUser(User user)
	{
		usersModel.removeElement(user);
	}
	
	private void usersInit() {

		
		usersModel = new DefaultListModel<>();
		usersList = new JList<User>(usersModel);
		usersList.addListSelectionListener( new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				filesModel.clear();
				System.out.println("INTRU AICI");
				ArrayList<File> files = usersList.getSelectedValue().getFiles();
				
				for ( File f : files)
				{
					filesModel.addElement(f);
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

		User u1 = new User("user1");
		u1.files.add(new File("fis de la 1"));
		u1.files.add(new File("fis2 de la 1"));
		
		usersModel.addElement( u1 );
		
		User u2 = new User("user2");
		u2.files.add( new File("fis de la 2"));
		u2.files.add( new File("fis2 de la 2"));
		
		usersModel.addElement( u2);
		contentPane.add(usersList, gbc_usersList);
	}

	private void downloadsInit() {

		dtm = new DownloadsTableModel();
		downloadsTable = new DownloadsJTabel( dtm);
		
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.gridwidth = 3;
		gbc_table.insets = new Insets(0, 0, 0, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 2;
		gbc_table.gridy = 3;
		
		JScrollPane tableContainer = new JScrollPane(downloadsTable);
		
		contentPane.add( tableContainer, gbc_table);
	}

}
