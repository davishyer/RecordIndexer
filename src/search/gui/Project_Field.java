package search.gui;

import java.util.ArrayList;

import shared.model.*;

public class Project_Field 
{
	private ArrayList<Field> fields;
	private Project project;
	
	public Project_Field(ArrayList<Field> fields, Project project)
	{
		this.fields = fields;
		this.project = project;
	}

	public ArrayList<Field> getFields() 
	{
		return fields;
	}

	public void setFields(ArrayList<Field> fields) 
	{
		this.fields = fields;
	}

	public Project getProject()
	{
		return project;
	}

	public void setProject(Project project) 
	{
		this.project = project;
	}
	
}
