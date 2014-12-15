package shared.communication;

public class SubmitBatchParameters 
{
	private String name;
	private String password;
	private int batchID;
	private String fieldValues;
	
	public SubmitBatchParameters()
	{

	}
	
	public SubmitBatchParameters(String name, String password, int batchID, String fieldValues)
	{
		this.name = name;
		this.password = password;
		this.batchID = batchID;
		this.fieldValues = fieldValues;
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

	public int getBatchID() 
	{
		return batchID;
	}

	public void setBatchID(int batchID) 
	{
		this.batchID = batchID;
	}

	public String getFieldValues() 
	{
		return fieldValues;
	}

	public void setFieldValues(String fieldValues)
	{
		this.fieldValues = fieldValues;
	}
}
