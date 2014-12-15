package search.gui;

import static servertester.views.Constants.DOUBLE_HSPACE;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import shared.model.*;

@SuppressWarnings("serial")
public class ProjectsPanel extends JPanel
{
	private GridBagConstraints c;
	
	private JLabel label;
	private JCheckBox checkBox;
	
	public ProjectsPanel(ArrayList<Project_Field> projects_fields)
	{
		super();
		
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		
		int i = 0;
		for(Project_Field pf : projects_fields)
		{
			c.gridx = i;
			c.gridy = 0;
			label = new JLabel(pf.getProject().getProjInfo().getName());
			this.add(label, c);
			int j = 1;
			for(Field f : pf.getFields())
			{
				checkBox = new JCheckBox(f.getTitle());
				c.gridx = i;
				c.gridy = j;
				this.add(checkBox, c);
				j++;
			}
			i++;
			add(Box.createRigidArea(DOUBLE_HSPACE));
		}
		this.setMinimumSize(getPreferredSize());
		this.setMaximumSize(getPreferredSize());
	}
}
