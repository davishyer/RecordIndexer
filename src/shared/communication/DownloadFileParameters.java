package shared.communication;

public class DownloadFileParameters 
{
	private String url;

	public DownloadFileParameters(String url) 
	{
		this.url = url;
	}


	public String getUrl() 
	{
		return url;
	}


	public void setUrl(String url) 
	{
		this.url = url;
	}
}
