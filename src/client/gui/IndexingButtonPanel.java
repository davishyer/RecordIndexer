package client.gui;

import java.awt.FlowLayout;

import javax.swing.*;

@SuppressWarnings("serial")
public class IndexingButtonPanel extends JPanel
{
	public JButton _zoomIn;
	public JButton _zoomOut;
	public JButton _invert;
	public JButton _toggleHighlights;
	public JButton _save;
	public JButton _submit;
	
	public IndexingButtonPanel() 
	{
		//create a panel with all buttons for the indexing window
		super();
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		_zoomIn = new JButton("Zoom In");
		add(_zoomIn);
		_zoomOut = new JButton("Zoom Out");
		add(_zoomOut);
		_invert = new JButton("Invert Image");
		add(_invert);
		_toggleHighlights = new JButton("Toggle Highlights");
		add(_toggleHighlights);
		_save = new JButton("Save");
		add(_save);
		_submit = new JButton("Submit");
		add(_submit);
	}
	
	public void resetEnabled(boolean on)
	{
		_zoomIn.setEnabled(on);
		_zoomOut.setEnabled(on);
		_invert.setEnabled(on);
		_toggleHighlights.setEnabled(on);
		_save.setEnabled(on);
		_submit.setEnabled(on);
	}
}
