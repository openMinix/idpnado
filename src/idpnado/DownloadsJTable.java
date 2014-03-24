package idpnado;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class DownloadsJTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6713852804203019793L;

//	private ProgressCellRenderer pcr;
	private ArrayList<ProgressCellRenderer> progressCellRenderers;
	
	
	public DownloadsJTable()
	{
		
	}

	public DownloadsJTable(TableModel dm) {
		super(dm);
//		pcr = new ProgressCellRenderer();
		
	//	 getColumn("Progress").setCellRenderer(new ProgressCellRenderer());
		progressCellRenderers = new ArrayList<>();
	}
	

	public DownloadsJTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		
	}

	public DownloadsJTable(int numRows, int numColumns) {
		super(numRows, numColumns);
		
	}

	public DownloadsJTable(Vector rowData, Vector columnNames) {
		super(rowData, columnNames);
		
	}

	public DownloadsJTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		
	}

	public DownloadsJTable(TableModel dm, TableColumnModel cm,
			ListSelectionModel sm) {
		super(dm, cm, sm);
		
	}
	
	

	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		
		if ( column == 3)
		{
			if(progressCellRenderers.size() < row + 1)
			{
				for(int i = progressCellRenderers.size(); i < row + 1; i++)
					progressCellRenderers.add(new ProgressCellRenderer());
			}
			return progressCellRenderers.get(row);
		}
		return super.getCellRenderer(row, column);
	}
}
