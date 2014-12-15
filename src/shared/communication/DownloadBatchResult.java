package shared.communication;

import java.util.ArrayList;

import shared.model.*;

public class DownloadBatchResult 
{
	private Batch batch;
	private Project project;
	private ArrayList<Field> fields;
	private int numberOfFields;
	private String url;
	
	
	private boolean validUser;
	
	public DownloadBatchResult()
	{
		validUser = false;
	}
	
	public DownloadBatchResult(Batch batch, Project project, ArrayList<Field> fields, int numberOfFields)
	{
		this.batch = batch;
		this.project = project;
		this.fields = fields;
		this.numberOfFields = numberOfFields;
	}

	public Batch getBatch() 
	{
		return batch;
	}

	public void setBatch(Batch batch) 
	{
		this.batch = batch;
	}

	public Project getProject() 
	{
		return project;
	}

	public void setProject(Project project) 
	{
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

	public int getNumberOfFields() 
	{
		return numberOfFields;
	}

	public void setNumberOfFields(int numberOfFields) 
	{
		this.numberOfFields = numberOfFields;
	}

	public boolean isValidUser() 
	{
		return validUser;
	}

	public void setValidUser(boolean validUser) 
	{
		this.validUser = validUser;
	}
	
	public String getUrl() 
	{
		return url;
	}

	public void setUrl(String url) 
	{
		this.url = url;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if(validUser)
		{
			sb.append(this.batch.getID() + "\n");
			sb.append(this.project.getProjInfo().getID() + "\n");
			sb.append(this.url + "/" + this.batch.getFilePath() + "\n");
			sb.append(this.project.getFirstY() + "\n");
			sb.append(this.project.getRecordHeight() + "\n");
			sb.append(this.project.getRecordsPerBatch() + "\n");
			sb.append(this.numberOfFields + "\n");
			int i = 1;
			for(Field f : fields)
			{
				sb.append(f.getID() + "\n");
				sb.append(i + "\n");
				sb.append(f.getTitle() + "\n");
				sb.append(this.url + "/" + f.getHelp() + "\n");
				sb.append(f.getX() + "\n");
				sb.append(f.getWidth() + "\n");
				if(f.getKnownPath().length() > 0)
				{
					sb.append(this.url + "/" + f.getKnownPath() + "\n");
				}
				i++;
			}
		}
		else
		{
			sb.append("FAILED\n");
		}
		return sb.toString();
	}
}
