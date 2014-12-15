package client.gui.table;

import java.awt.Color;
import java.awt.Component;


import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

import client.facade.BatchState;

@SuppressWarnings("serial")
public class CellRenderer extends JLabel implements TableCellRenderer
{
	private final Color selected = new Color(244, 255, 41, 95);
	private final Color nonSelected = Color.WHITE;
	private final Color error = Color.RED;
	private BatchState batchState;
	private Border unselectedBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
	private Border selectedBorder = BorderFactory.createLineBorder(Color.BLACK, 2);

	
	public CellRenderer(BatchState batchState)
	{
		this.setOpaque(true);
		this.batchState = batchState;
	}
	
	//custom renderer used to highlight the table accordingly
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) 
	{
		boolean[][] check = batchState.getMisspelled();
		boolean misspelled = check[column - 1][row];
		
		if(isSelected)
		{
			this.setBackground(selected);
			this.setBorder(selectedBorder);
			batchState.cellChanged(column, row);
		}
		else if(misspelled)
		{
			this.setBackground(error);
			this.setBorder(selectedBorder);
		}
		else
		{
			this.setBackground(nonSelected);
			this.setBorder(unselectedBorder);
		}

		this.setText((String)value);
		return this;
	}
}
