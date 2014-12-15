package shared.communication;

import shared.model.*;

public class ValidateUserCredentials 
{
	private String username;
	private String password;
	
	public ValidateUserCredentials(String u, String p)
	{
		username = u;
		password = p;
	}
	
	/**
	 * gets username
	 * @return -> returns the username
	 */
	public String getUsername()
	{
		return username;
	}
	
	/**
	 * sets username
	 * @param s -> new username
	 */
	public void setUsername(String s)
	{
		username = s;
	}
	
	/**
	 * gets password
	 * @return -> returns the password
	 */
	public String getPassword()
	{
		return password;
	}
	
	/**
	 * sets password
	 * @param s -> new password
	 */
	public void setPassword(String s)
	{
		password = s;
	}
	
	public User getUser()
	{
		User user = new User();
		return user;
	}
}
