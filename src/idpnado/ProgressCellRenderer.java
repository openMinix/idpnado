package idpnado;

import java.awt.Component;

import javax.swing.BoundedRangeModel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ProgressCellRenderer extends JProgressBar implements
		TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5007531122610383602L;


	public ProgressCellRenderer() {
		
		setValue(40);
	}

	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		if ( value != null)
			setValue((int)value);
		else
		{
			setValue(30);
		}
		return this;
	}

}
