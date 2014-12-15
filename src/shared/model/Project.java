package shared.model;

public class Project 
{
	private ProjectInfo projInfo;
	private int recordsPerBatch;
	private int firstY;
	private int recordHeight;
	
	public Project()
	{
		
	}
	
	public Project(ProjectInfo pi, int rpb, int fY, int rH)
	{
		projInfo = pi;
		recordsPerBatch = rpb;
		firstY = fY;
		recordHeight = rH;
	}

	public ProjectInfo getProjInfo() 
	{
		return projInfo;
	}

	public void setProjInfo(ProjectInfo projInfo) 
	{
		this.projInfo = projInfo;
	}

	public int getRecordsPerBatch() 
	{
		return recordsPerBatch;
	}

	public void setRecordsPerBatch(int recordsPerBatch) 
	{
		this.recordsPerBatch = recordsPerBatch;
	}

	public int getFirstY() 
	{
		return firstY;
	}

	public void setFirstY(int firstY) 
	{
		this.firstY = firstY;
	}

	public int getRecordHeight() 
	{
		return recordHeight;
	}

	public void setRecordHeight(int recordHeight) 
	{
		this.recordHeight = recordHeight;
	}
	
}
