package shared.model;

public class UserInfo 
{
	private String first;
	private String last;
	private String email;
	
	public UserInfo()
	{
		
	}
	
	public UserInfo(String f, String l, String e)
	{
		first = f;
		last = l;
		email = e;
	}
	
	public String getFirstName()
	{
		return first;
	}
	
	public void setFirstName(String s)
	{
		first = s;
	}
	
	public String getLastName()
	{
		return last;
	}
	
	public void setLastName(String s)
	{
		last = s;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String s)
	{
		email = s;
	}
}
