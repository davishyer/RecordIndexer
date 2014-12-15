package shared.model;

public class ProjectInfo 
{
	private String name;
	private int ID;
	
	public ProjectInfo()
	{
		
	}
	
	public ProjectInfo(String s)
	{
		name = s;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String s)
	{
		name = s;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public void setID(int i)
	{
		ID = i;
	}
}
