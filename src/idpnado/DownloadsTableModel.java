/**
 * 
 */
package idpnado;

import javax.swing.table.DefaultTableModel;

/**
 * @author vlad
 *
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
	
	@Override
	public boolean isCellEditable(int row, int column) {
		
		return false;
	}
}
