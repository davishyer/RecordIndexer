package shared.communication;

import java.net.URL;

public class GetSampleImageResult 
{
	private URL url;
	private String link;
	
	public String getLink() 
	{
		return link;
	}

	public void setLink(String link) 
	{
		this.link = link;
	}

	private boolean validUser;
	
	public GetSampleImageResult()
	{
		validUser = false;
	}
	
	public void setURL(URL url)
	{
		this.url = url;
	}
	
	public URL getURL()
	{
		return url;
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
			sb.append(url + "\n");
		}
		else
		{
			sb.append("FAILED\n");
		}
		return sb.toString();
	}
}
