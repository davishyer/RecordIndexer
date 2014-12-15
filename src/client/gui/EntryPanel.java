package client.gui;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import client.facade.BatchState;
import client.gui.table.*;

@SuppressWarnings("serial")
public class EntryPanel extends JTabbedPane
{
	private BatchState batchState;
	
	private Table table;
	private FormEntryPanel formEntry;
	
	//holders for both forms of entry
	private JScrollPane tableHolder = new JScrollPane();
	private JScrollPane formHolder = new JScrollPane();
	
	public EntryPanel(BatchState batchState)
	{
		this.batchState = batchState;
		
		table = new Table(this.batchState, this);
		this.add("Table Entry", tableHolder);
		
		formEntry = new FormEntryPanel(this.batchState, this);
		this.add("Form Entry", formHolder);
	}
	
	public void addFormEntryListener()
	{
		this.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				formEntry.setCell();
			}
		});
	}

	public Table getTable() 
	{
		return table;
	}

	public void setTable(Table table) 
	{
		this.table = table;
		tableHolder.setViewportView(table);
	}
	
	public FormEntryPanel getFormEntry()
	{
		return formEntry;
	}

	public void setFormEntry(FormEntryPanel formEntry) 
	{
		this.formEntry = formEntry;
		formHolder.setViewportView(formEntry);
	}
}
