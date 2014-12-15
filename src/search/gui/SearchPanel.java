package search.gui;

import static servertester.views.Constants.DOUBLE_HSPACE;
import static servertester.views.Constants.SINGLE_HSPACE;
import static servertester.views.Constants.TRIPLE_HSPACE;

import javax.swing.*;

@SuppressWarnings("serial")
public class SearchPanel extends JPanel
{
	public JTextField _valuesTextField;
	public JButton _searchButton;
	
	public SearchPanel()
	{
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		add(Box.createRigidArea(DOUBLE_HSPACE));
		add(new JLabel("VALUES:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		_valuesTextField = new JTextField(50);
		_valuesTextField.setMinimumSize(_valuesTextField.getPreferredSize());
		_valuesTextField.setMaximumSize(_valuesTextField.getPreferredSize());
		add(_valuesTextField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		_searchButton = new JButton("SEARCH");
		add(_searchButton);	
		add(Box.createRigidArea(DOUBLE_HSPACE));
		
		this.setMinimumSize(getPreferredSize());
		this.setMaximumSize(getPreferredSize());
	}
	public String getValues()
	{
		return _valuesTextField.getText();
	}
}


