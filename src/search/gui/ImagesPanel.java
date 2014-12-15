package search.gui;

import static servertester.views.Constants.*;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.*;

@SuppressWarnings("serial")
public class ImagesPanel extends JPanel
{
	@SuppressWarnings("rawtypes")
	public JComboBox _imageComboBox;
	public JButton _viewButton;

	public ImagesPanel()
	{
		super();
		
		setLayout(new FlowLayout());
		
		_imageComboBox = new JComboBox<String>();
		add(_imageComboBox);
		add(Box.createRigidArea(DOUBLE_HSPACE));
		
		_viewButton = new JButton("VIEW");
		add(_viewButton);	
		add(Box.createRigidArea(DOUBLE_HSPACE));
		this.setPreferredSize(new Dimension(500, 50));
		this.setMinimumSize(getPreferredSize());
		this.setMaximumSize(getPreferredSize());
	}
}
