package client.gui.table;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

import client.facade.BatchState;
import client.facade.Cell;
import client.facade.SuggestionWindow;
import client.gui.EntryPanel;
import client.gui.Listener;

@SuppressWarnings("serial")
public class Table extends JPanel implements Listener
{

	private static BatchState batchState;
	private TableModel tableModel;
	private JTable table;
	private static EntryPanel entryPanel;
	private CellRenderer renderer;
	
	@SuppressWarnings("static-access")
	public Table(BatchState batchState, EntryPanel entryPanel) throws HeadlessException 
	{
		this.batchState = batchState;
		this.entryPanel = entryPanel;
		this.renderer = new CellRenderer(this.batchState);
		
		tableModel = new TableModel(batchState);
		
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionBackground(new Color(244, 255, 41, 95));
		
		TableColumnModel columnModel = table.getColumnModel();
		for (int i = 0; i < tableModel.getColumnCount(); ++i) 
		{
			TableColumn column = columnModel.getColumn(i);
			column.setPreferredWidth(150);
		}	
		
		JPanel rootPanel = new JPanel(new BorderLayout());
		rootPanel.add(table.getTableHeader(), BorderLayout.NORTH);
		rootPanel.add(table, BorderLayout.CENTER);
		
		this.add(rootPanel);
	}
	
	public static void main(String[] args) 
	{
    	
        EventQueue.invokeLater(new Runnable()
        {
           public void run()
           {
        	   Table frame = new Table(batchState, entryPanel);
               frame.setVisible(true);
           }
        });
	}

	@Override
	public void setCell()
	{
		Cell cell = batchState.getCurrentCell();
		table.changeSelection(cell.getRecordNum(), cell.getFieldNum(), false, false);
	}

	@Override
	public void setText(int x, int y, String text) 
	{
		tableModel.fromTable = true;
		tableModel.setValueAt(text, y, x + 1);
	}
	
    public Color getTableCellBackground(JTable table, int row, int col) 
    {
        TableCellRenderer renderer = table.getCellRenderer(row, col);
        Component component = table.prepareRenderer(renderer, row, col);    
        return component.getBackground();
    }

	@SuppressWarnings("static-access")
	@Override
	public void updateBatch(BatchState batchS) 
	{
		this.batchState = batchS;
		this.removeAll();
		tableModel = new TableModel(batchState);
		
		table = new JTable(tableModel);
		
		table.addMouseListener(new MouseListener() 
		{
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e){}
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				final int row = table.rowAtPoint(e.getPoint());
				final int column = table.columnAtPoint(e.getPoint());
				Color c = getTableCellBackground(table, row, column);
				if(e.getButton() == 3 && c.getRGB() == Color.RED.getRGB())
				{
					//brings up the drop down menu
					final JPopupMenu suggestion = new JPopupMenu();
					JMenuItem menu = new JMenuItem("see suggestions");
					menu.addActionListener(new ActionListener() 
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							//creates the new suggestion window
							String cellText = (String)tableModel.getValueAt(row, column);
							ArrayList<String> list = batchState.getSuggestions(
									cellText, column - 1);
							SuggestionWindow suggestionWindow = new SuggestionWindow(list, column, row, batchState);

							suggestionWindow.setSize(new Dimension(300, 200));
							suggestionWindow.setResizable(false);
							suggestionWindow.setLocationRelativeTo(null);
							suggestionWindow.setVisible(true);
						}
					});
					suggestion.add(menu);
					suggestion.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		table.setSelectionMode(JTable.WHEN_FOCUSED);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		
		TableColumnModel columnModel = table.getColumnModel();
		for (int i = 0; i < tableModel.getColumnCount(); ++i) 
		{
			TableColumn column = columnModel.getColumn(i);
			column.setPreferredWidth(50);
			if(i == 0)
			{
				column.setMaxWidth(30);
				column.setMinWidth(30);
			}
		}	
		for (int i = 1; i < tableModel.getColumnCount(); ++i) 
		{
			TableColumn column = columnModel.getColumn(i);
			column.setCellRenderer(renderer);
		}	
		
		JPanel rootPanel = new JPanel(new BorderLayout());
		rootPanel.add(table.getTableHeader(), BorderLayout.NORTH);
		rootPanel.add(table, BorderLayout.CENTER);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(rootPanel);
		this.setVisible(true);
		entryPanel.setTable(this);
		repaint();
	}

}