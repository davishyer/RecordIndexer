package client.facade;

import shared.communication.*;
import client.communication.*;

public class ClientFacade 
{
	private ClientCommunicator cc;
	private String username;
	private String password;
	
	public ClientFacade(String host, String port)
	{
		cc = new ClientCommunicator(port, host);
	}
	
	public ValidateUserResult validateUser(String username, String password)
	{
		ValidateUserCredentials creds = new ValidateUserCredentials(username, password);
		this.username = username;
		this.password = password;
		return cc.validateUser(creds);
	}
	
	public GetProjectsResult getProjects()
	{
		GetProjectsParameters params = new GetProjectsParameters(username, password);
		return cc.getProjects(params);
	}
	
	public GetSampleImageResult getSampleImage(int projID)
	{
		GetSampleImageParameters params = new GetSampleImageParameters(username, password, projID);
		return cc.getSampleImage(params);
	}
	
	public DownloadBatchResult downloadBatch(int projID)
	{
		DownloadBatchParameters params = new DownloadBatchParameters(username, password, projID);
		return cc.downloadBatch(params);
	}
	
	public SubmitBatchResult submitBatch(String input, int batchID)
	{
		SubmitBatchParameters params = new SubmitBatchParameters(username, password, batchID, input);
		return cc.submitBatch(params);
	}
}
