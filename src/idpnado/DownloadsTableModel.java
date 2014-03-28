/**
 * 
 */
package idpnado;

import javax.swing.table.DefaultTableModel;

/**
 * 	Clasa DownloadsTableModel reprezinta modelul componentei
 *  grafice ce afiseaza download-urile in desfasurare
 */
public class DownloadsTableModel extends DefaultTableModel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2778786991931062097L;

	public DownloadsTableModel()
	{
		super( new String[]{"Source","Destination","Filename","Progress","State" }, 0);
		
		
	}
	
	/**
	 * Metoda isCellEditable determina daca o celula e editabila sau nu
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		
		return false;
	}
}
