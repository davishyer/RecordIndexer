package shared.communication;

public class DownloadFileResult 
{
	private byte[] fileBytes;
	/**
	 * Bytes returned by file referenced by URL
	 * @param fileBytes
	 */
	public DownloadFileResult(byte[] fileBytes)
	{
		this.fileBytes = fileBytes;
	}

	/**
	 * @return the fileBytes
	 */
	public byte[] getFileBytes() 
	{
		return fileBytes;
	}

	/**
	 * @param fileBytes the fileBytes to set
	 */
	public void setFileBytes(byte[] fileBytes)
	{
		this.fileBytes = fileBytes;
	}
}
