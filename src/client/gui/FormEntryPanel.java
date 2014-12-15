package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

import client.facade.BatchState;
import client.facade.Cell;
import client.facade.SuggestionWindow;
import shared.model.*;

@SuppressWarnings("serial")
public class FormEntryPanel extends JPanel implements Listener
{

	private BatchState batchState;
	private EntryPanel entryPanel;
	private String[][] data;

	private JList<String> recordList;
	private DefaultListModel<Integer> dlm = new DefaultListModel<>();
	private JPanel formText;
	private ArrayList<TextBox> textBoxes;

	
	
	public FormEntryPanel(BatchState batchState, EntryPanel entryPanel) 
	{
		super();
		this.batchState = batchState;
		this.entryPanel = entryPanel;
		this.setLayout(new BorderLayout());
		
		recordList = null;
		formText = null;
		textBoxes = null;
		data = null;
	}
	
	private void builder() 
	{
		//creates a new form entry when a batch is downloaded
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		ArrayList<Field> fields = batchState.getFields();
		
		for(int i = 0; i < batchState.getFields().size(); ++i) 
		{
			TextBox temp = new TextBox(i+1);
			
			textBoxes.add(temp);
			
			gbc.gridy++;
			textBoxes.get(i).fieldTitle.setText(fields.get(i).getTitle() + " :");
			formText.add(textBoxes.get(i), gbc);
			
			gbc.gridy++;
			formText.add(new JPanel(), gbc);
		}			
		for(int i = 0; i < batchState.getProject().getRecordsPerBatch(); ++i) 
		{
			dlm.addElement(i+1);
		}
		data = new String[fields.size()][batchState.getProject().getRecordsPerBatch()];
		initializeData();
	}
	
	private void initializeData()
	{
		for(int i = 0; i < batchState.getFields().size(); ++i)
		{
			for(int j = 0; j < batchState.getProject().getRecordsPerBatch(); ++j)
			{
				data[i][j] = "";
			}
		}
	}
	
	private void addRecordListListener() 
	{
		recordList.addMouseListener(new MouseListener() 
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
				batchState.cellChanged(-1,recordList.getSelectedIndex());
			}
		});
	}

	@Override
	public void setCell() 
	{
		Cell cell = batchState.getCurrentCell();
		recordList.setSelectedIndex(cell.getRecordNum());
		textBoxes.get(cell.getFieldNum()-1).text.requestFocus();
		for(int i = 0; i < batchState.getFields().size(); ++i)
		{
			String t = data[i][cell.getRecordNum()];
			textBoxes.get(i).text.setText(t);
			boolean[][] check = batchState.getMisspelled();
			if(check[i][cell.getRecordNum()])
			{
				textBoxes.get(i).text.setBackground(Color.RED);
			}
			else
			{
				textBoxes.get(i).text.setBackground(Color.WHITE);
			}
		}
	}

	@Override
	public void setText(int x, int y, String text) 
	{
		data[x][y] = text;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void updateBatch(BatchState batchState) 
	{
		this.batchState = batchState;
		
		formText = new JPanel();
		formText.setLayout(new GridBagLayout());
		textBoxes = new ArrayList<>();
		this.builder();
		recordList = new JList(dlm);
		recordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addRecordListListener();
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.add(recordList, BorderLayout.WEST);
		basePanel.add(formText, BorderLayout.CENTER);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(basePanel);
		this.setVisible(true);
		entryPanel.setFormEntry(this);
		repaint();
	}
	
	
	private class TextBox extends JPanel 
	{
		//the right half of the form entry
		protected JLabel fieldTitle;
		protected JTextField text;
		protected int fieldNumber;
		
		public TextBox(int position) 
		{
			super();
			fieldNumber = position;
			
			this.setLayout(new GridLayout(1, 2));
			fieldTitle = new JLabel();
			fieldTitle.setAlignmentX(LEFT_ALIGNMENT);
			
			text = new JTextField(20);
			
			text.addFocusListener(new FocusListener() 
			{
				
				@Override
				public void focusLost(FocusEvent e) 
				{
			    	  String entry = text.getText();
			    	  batchState.addText(fieldNumber - 1, batchState.getCurrentCell().getRecordNum(), entry);
				}
				
				@Override
				public void focusGained(FocusEvent e) 
				{
					batchState.cellChanged(fieldNumber, -1);
				}
			});
			
			text.addMouseListener(new MouseListener()
			{
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e){}
				@Override
				public void mouseEntered(MouseEvent e){}
				
				@Override
				public void mouseClicked(MouseEvent e)
				{
					//checks if the click was a right click
					if(e.getButton() == 3)
					{
						if(text.getBackground() == Color.RED)
						{
							//brings up drop down menu
							final JPopupMenu suggestion = new JPopupMenu();
							JMenuItem menu = new JMenuItem("see suggestions");
							menu.addActionListener(new ActionListener() 
							{
								@Override
								public void actionPerformed(ActionEvent e) 
								{
									//initializes the suggestion window
									ArrayList<String> list = batchState.getSuggestions(
											text.getText(), fieldNumber - 1);
									int column = fieldNumber;
									int row = recordList.getSelectedIndex();
									SuggestionWindow suggestionWindow = new SuggestionWindow(list, column, row, batchState);

									//create the window with these characteristics
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
				}
			});
						
			this.add(fieldTitle);
			this.add(text);
		}

		@SuppressWarnings("unused")
		public int getFieldNumber() 
		{
			return fieldNumber;
		}
				
	}
}