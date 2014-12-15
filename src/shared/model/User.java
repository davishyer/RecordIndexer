package shared.model;

public class User 
{
	private int ID;
	private Credentials creds;
	private UserInfo info;
	private int recordCount;
	private int batch;
	
	public User()
	{
		
	}
	
	public User(Credentials c, UserInfo ui)
	{
		creds = c;
		info = ui;
		recordCount = 0;
		batch = 0;
		ID = -1;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public void setID(int i)
	{
		ID = i;
	}
	
	public Credentials getCreds()
	{
		return creds;
	}
	
	public void setCreds(Credentials c)
	{
		creds = c;
	}
	
	public UserInfo getUserInfo()
	{
		return info;
	}
	
	public void setUserInfo(UserInfo u)
	{
		info = u;
	}
	
	public int getRecordCount()
	{
		return recordCount;
	}
	
	public void setRecordCount(int i)
	{
		recordCount = i;
	}
	
	public int getCurrentBatch()
	{
		return batch;
	}
	
	public void setCurrentBatch(int i)
	{
		batch = i;
	}
}
