package client.gui.table;


import javax.swing.table.*;

import client.facade.BatchState;


@SuppressWarnings("serial")
public class TableModel extends AbstractTableModel
{
	private BatchState batchState;
	private String[][] entries;
	public boolean fromTable;

	public TableModel(BatchState batchState) 
	{
		this.batchState = batchState;
		entries = batchState.getIndexedData();
		fromTable = false;
	}

	@Override
	public int getColumnCount() 
	{
		if(batchState.getFields() == null)
		{
			return 0;
		}
		return batchState.getFields().size() + 1;
	}

	@Override
	public String getColumnName(int column)
	{
		//sets the column names to the field names
		String result = null;
		if(getColumnCount() > column & 0 <= column)
		{
			if(column != 0)
			{
				result = batchState.getFields().get(column - 1).getTitle();
			}
			else
			{
				result = "Num";
			}
		}

		return result;
	}

	@Override
	public int getRowCount() 
	{
		if(batchState.getProject() == null)
		{
			return 0;
		}
		return batchState.getProject().getRecordsPerBatch();
	}

	@Override
	public boolean isCellEditable(int row, int column) 
	{
		if(column == 0)
		{
			return false;
		}
		return true;
	}

	@Override
	public Object getValueAt(int row, int column) 
	{
		Object result = null;
		if(row >= 0 && row < getRowCount() && column >= 1
				&& column < getColumnCount()) 
		{
			result = entries[column - 1][row];
		}
		else if(column == 0)
		{
			result = Integer.toString(row + 1);
		}
		return result;
	}

	@Override
	public void setValueAt(Object value, int row, int column) 
	{
		if(row >= 0 && row < getRowCount() && column >= 0
				&& column < getColumnCount()) 
		{
			entries[column - 1][row] = (String) value;
			if(!fromTable)
			{
				batchState.addText(column - 1, row, (String)value);
			}
			fromTable = false;
			this.fireTableCellUpdated(row, column);
		} 
		else 
		{
			throw new IndexOutOfBoundsException();
		}
	}
}