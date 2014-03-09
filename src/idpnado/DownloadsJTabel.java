package idpnado;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class DownloadsJTabel extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6713852804203019793L;

	private ProgressCellRenderer pcr;
	
	
	public DownloadsJTabel() {
		
	}

	public DownloadsJTabel(TableModel dm) {
		super(dm);
		pcr = new ProgressCellRenderer();
		
	//	 getColumn("Progress").setCellRenderer(new ProgressCellRenderer());
	}

	public DownloadsJTabel(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		
	}

	public DownloadsJTabel(int numRows, int numColumns) {
		super(numRows, numColumns);
		
	}

	public DownloadsJTabel(Vector rowData, Vector columnNames) {
		super(rowData, columnNames);
		
	}

	public DownloadsJTabel(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		
	}

	public DownloadsJTabel(TableModel dm, TableColumnModel cm,
			ListSelectionModel sm) {
		super(dm, cm, sm);
		
	}

	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		
		if ( column == 3)
		{
			return pcr;
		}
		return super.getCellRenderer(row, column);
	}
}
