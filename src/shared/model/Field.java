package shared.model;

public class Field 
{
	private String title;
	private int ID;
	private String helpPath;
	private String knownPath;
	private int number;
	private int projectID;
	private int width;
	private int xCoord;
	
	public Field()
	{
		knownPath = "";
	}
	
	public Field(String title, String help, String known, int num, int projID, int width, int x)
	{
		this.title = title;
		this.helpPath = help;
		this.knownPath = known;
		this.number = num;
		this.projectID = projID;
		this.width = width;
		this.xCoord = x;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String s)
	{
		title = s;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public void setID(int i)
	{
		ID = i;
	}
	
	public String getHelp()
	{
		return helpPath;
	}
	
	public void setHelp(String s)
	{
		helpPath = s;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public void setNumber(int i)
	{
		number = i;
	}
	
	public int getX()
	{
		return xCoord;
	}
	
	public void setX(int i)
	{
		xCoord = i;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setWidth(int i)
	{
		width = i;
	}
	
	public int getProjectID()
	{
		return projectID;
	}
	
	public void setProjectID(int i)
	{
		projectID = i;
	}

	public String getKnownPath() 
	{
		return knownPath;
	}

	public void setKnownPath(String knownPath) 
	{
		this.knownPath = knownPath;
	}
	
}
