package idpnado;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JList;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTable;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5058979263127835567L;
	private JPanel contentPane;
	private JTable table;
	private JList filesList;
	private JList usersList;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 500);
		
		contentPane = new JPanel();
		
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 53, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE, 1.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		
		contentPane.setLayout(gbl_contentPane);
		
		
		filesList = new JList();
		GridBagConstraints gbc_filesList = new GridBagConstraints();
		gbc_filesList.gridheight = 2;
		gbc_filesList.insets = new Insets(0, 0, 5, 5);
		gbc_filesList.fill = GridBagConstraints.BOTH;
		gbc_filesList.gridx = 2;
		gbc_filesList.gridy = 1;
		gbc_filesList.gridwidth= 3;
		contentPane.add(filesList, gbc_filesList);
		
		usersList = new JList();
		GridBagConstraints gbc_usersList = new GridBagConstraints();
		gbc_usersList.gridheight = 3;
		gbc_usersList.insets = new Insets(0, 0, 0, 5);
		gbc_usersList.fill = GridBagConstraints.BOTH;
		gbc_usersList.gridx = 5;
		gbc_usersList.gridy = 1;
		gbc_usersList.gridwidth = 2;
		
		contentPane.add(usersList, gbc_usersList);
		
		table = new JTable();
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.gridwidth = 3;
		gbc_table.insets = new Insets(0, 0, 0, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 2;
		gbc_table.gridy = 3;
		contentPane.add(table, gbc_table);
	}

}
