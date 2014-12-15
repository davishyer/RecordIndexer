package shared.communication;

public class SubmitBatchResult 
{
	private boolean success;
	
	public SubmitBatchResult()
	{
		success = false;
	}

	public boolean isSuccess() 
	{
		return success;
	}

	public void setSuccess(boolean success) 
	{
		this.success = success;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		if(success)
		{
			sb.append("TRUE\n");
		}
		else
		{
			sb.append("FAILED\n");
		}
		return sb.toString();
	}
}
