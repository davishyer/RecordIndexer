package client.gui;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import client.facade.BatchState;
import client.gui.image.ImageNavigator;

@SuppressWarnings("serial")
public class HelpPanel extends JTabbedPane
{
	private BatchState batchState;
	private FieldPanel field;
	private ImageNavigator imageNav;
	
	public HelpPanel(BatchState batchS)
	{
		//initializes the bottom right component of the indexing window
		this.batchState = batchS;
		field = new FieldPanel(this.batchState);
		imageNav = new ImageNavigator(this.batchState);
		JScrollPane scroll = new JScrollPane(field);
		this.add("Field Help", scroll);
		this.add("Image Navigator", imageNav);
	}

	public FieldPanel getFieldPanel() 
	{
		return field;
	}

	public void setFieldPanel(FieldPanel field)
	{
		this.field = field;
		repaint();
	}

	public ImageNavigator getImageNav() 
	{
		return imageNav;
	}

	public void setImageNav(ImageNavigator imageNav)
	{
		this.imageNav = imageNav;
	}
}
