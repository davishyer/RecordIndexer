package shared.communication;

public class GetSampleImageParameters 
{
	private String name;
	private String password;
	private int projectID;
	
	public GetSampleImageParameters()
	{
		
	}
	
	public GetSampleImageParameters(String name, String password, int projectID)
	{
		this.name = name;
		this.password = password;
		this.projectID = projectID;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	public int getProjectID() 
	{
		return projectID;
	}

	public void setProjectID(int projectID) 
	{
		this.projectID = projectID;
	}
	
	
}
