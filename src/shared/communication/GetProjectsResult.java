package shared.communication;

import java.util.ArrayList;

import shared.model.*;

public class GetProjectsResult 
{
	private ArrayList<Project> projects;
	private boolean validUser;
	
	public GetProjectsResult()
	{
		validUser = false;
	}
	
	/**
	 * gets all projects
	 * @return -> array of projectinfo if found, else return null
	 */
	public ArrayList<Project> getProjects()
	{
		return projects;
	}
	
	/**
	 * replaces projects with new projects
	 * @param projects -> array of new projects with which to replace
	 */
	public void setProjects(ArrayList<Project> projects)
	{
		this.projects = projects;
	}
	
	public boolean isValidUser() 
	{
		return validUser;
	}

	public void setValidUser(boolean validUser) 
	{
		this.validUser = validUser;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if(validUser)
		{
			for(Project p : projects)
			{
				sb.append(p.getProjInfo().getID() + "\n");
				sb.append(p.getProjInfo().getName() + "\n");
			}
		}
		else
		{
			sb.append("FAILED\n");
		}
		return sb.toString();
	}
}
