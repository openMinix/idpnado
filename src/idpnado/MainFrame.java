package idpnado;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sun.org.mozilla.javascript.internal.annotations.JSConstructor;
import common.File;
import common.User;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5058979263127835567L;
	private JPanel contentPane;
	private JTable downloadsTable;
	private JList<File> filesList;
	private JList<User> usersList;

	private DefaultListModel<User> usersModel;
	private DefaultListModel<File> filesModel;

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

		filesInit();
		usersInit();
		downloadsInit();
	}

	private void filesInit() {

		filesModel = new DefaultListModel<File>();
		filesList = new JList(filesModel);
		GridBagConstraints gbc_filesList = new GridBagConstraints();
		gbc_filesList.gridheight = 2;
		gbc_filesList.insets = new Insets(0, 0, 5, 5);
		gbc_filesList.fill = GridBagConstraints.BOTH;
		gbc_filesList.gridx = 2;
		gbc_filesList.gridy = 1;
		gbc_filesList.gridwidth = 3;
		contentPane.add(filesList, gbc_filesList);
	}

	private void usersInit() {

		usersModel = new DefaultListModel<>();
		usersList = new JList<User>(usersModel);
		usersList.addListSelectionListener( new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				filesModel.clear();
				
				ArrayList<File> files = usersList.getSelectedValue().getFiles();
				
				for ( File f : files)
				{
					filesModel.addElement(f);
				}
				filesList = new JList<File>();
				
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
		
		usersModel.addElement( u1 );
		
		User u2 = new User("user2");
		u2.files.add( new File("fis de la 2"));
		
		usersModel.addElement( u2);
		contentPane.add(usersList, gbc_usersList);
	}

	private void downloadsInit() {

		downloadsTable = new JTable( new DownloadsTableModel());
		
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
