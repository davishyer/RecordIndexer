package shared.model;

public class Record 
{
	private int ID;
	private int RecNum;
	private int batchID;
	private String data;
	private int fieldID;
	
	public Record()
	{
		
	}
	
	public Record(int rn, int bID, String d, int fID)
	{
		RecNum = rn;
		batchID = bID;
		data = d;
		fieldID = fID;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public void setID(int i)
	{
		ID = i;
	}
	
	public int getRecordNumber()
	{
		return RecNum;
	}
	
	public void setRecordNumber(int i)
	{
		RecNum = i;
	}
	
	public int getBatchID()
	{
		return batchID;
	}
	
	public void setBatchID(int i)
	{
		batchID = i;
	}

	public String getData() 
	{
		return data;
	}

	public void setData(String data) 
	{
		this.data = data;
	}

	public int getFieldID() 
	{
		return fieldID;
	}

	public void setFieldID(int fieldID) 
	{
		this.fieldID = fieldID;
	}
}
