package shared.model;

public class Batch 
{
	private int ID;
	private String filePath;
	private int projectID;
	private int state;
	
	public Batch()
	{
		
	}
	
	public Batch(String fp, int pID, int s)
	{
		filePath = fp;
		projectID = pID;
		state = s;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public void setID(int i)
	{
		ID = i;
	}
	
	public String getFilePath()
	{
		return filePath;
	}
	
	public void setFilePath(String s)
	{
		filePath = s;
	}
	
	public int getProjectID()
	{
		return projectID;
	}
	
	public void setProjectID(int i)
	{
		projectID = i;
	}
	
	public int getState()
	{
		return state;
	}
	
	public void setState(int i)
	{
		state = i;
	}
}
