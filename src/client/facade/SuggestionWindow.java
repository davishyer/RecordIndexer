package client.facade;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class SuggestionWindow extends JDialog
{
	private ArrayList<String> suggestions;
	private JPanel panel;
	private JButton use;
	private JList<String> list;
	private int column;
	private int row;
	private BatchState batchState;
	
	public SuggestionWindow(ArrayList<String> suggestions, int column, int row, BatchState batchState)
	{
		this.column = column;
		this.row = row;
		this.batchState = batchState;
		this.setLayout(new BorderLayout());
		this.suggestions = suggestions;
		
		//add in the list of suggestions
		JScrollPane suggestionBox = new JScrollPane();
		setPanel();
		suggestionBox.setViewportView(panel);
		this.add(suggestionBox, BorderLayout.CENTER);
		
		//add in buttons
		this.add(addButtons(this), BorderLayout.SOUTH);
		this.setTitle("Suggestions");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	private void setPanel()
	{
		//initializes our list of suggestions
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JPanel box = new JPanel();
		box.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		 
		for(String s : suggestions)
		{
			listModel.addElement(s);
		}
		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel.add(list);
	}
	
	private JPanel addButtons(final SuggestionWindow window)
	{
		//creates both the cancel and use suggestion buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		JButton cancel = new JButton("cancel");
		cancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				window.dispose();
			}
		});
		use = new JButton("use suggestion");
		use.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(list.isSelectionEmpty())
				{
					//if no suggestion is made, prompt the user for a selection before continuing
					JOptionPane.showMessageDialog(window, "Make a selection before continuing");
					return;
				}
				String s = list.getSelectedValue();
				batchState.addText(column - 1, row, s);
				window.dispose();
			}
		});
		buttonPanel.add(cancel, BorderLayout.WEST);
		buttonPanel.add(use, BorderLayout.EAST);
		return buttonPanel;
	}
}
