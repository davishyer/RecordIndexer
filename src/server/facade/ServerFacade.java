package server.facade;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.*;

import server.database.*;
import shared.communication.*;
import shared.model.*;
import server.*;

public class ServerFacade 
{

	public static void initialize() throws ServerException 
	{		
		try 
		{
			Database.initialize();		
		}
		catch (DatabaseException e) 
		{
			throw new ServerException(e.getMessage(), e);
		}		
	}
	
	public static ValidateUserResult validateUser(ValidateUserCredentials creds)
	{
		ValidateUserResult result = new ValidateUserResult();
		Database db = new Database();
		try 
		{
			db.startTransaction();		
			Credentials credentials = new Credentials(creds.getUsername(), creds.getPassword());
			User user = db.getUserDAO().getUser(credentials);
			if(user == null)
			{
				result.setOutput(false);
			}
			else
			{
				if(user.getCurrentBatch() != 0)
				{
					result.setBatch(true);
				}
				result.setOutput(true);
				result.setFirstName(user.getUserInfo().getFirstName());
				result.setLastName(user.getUserInfo().getLastName());
				result.setRecordNum(user.getRecordCount());
			}
		} 
		catch (DatabaseException e) 
		{
			e.printStackTrace();
		}
		db.endTransaction(true);
		return result;
	}
	
	public static GetProjectsResult getProjects(GetProjectsParameters params)
	{
		GetProjectsResult result = new GetProjectsResult();
		Database db = new Database();								
		try 
		{
			db.startTransaction();	
			result.setProjects(db.getProjectDAO().getAll());
		} 
		catch (DatabaseException e) 
		{
			e.printStackTrace();
		}
		db.endTransaction(true);
		return result;
	}
	
	public static GetFieldsResult getFields(GetFieldsParameters params)
	{
		GetFieldsResult result = new GetFieldsResult();
		Database db = new Database();								
		try 
		{
			db.startTransaction();		
			result.setFields(db.getFieldDAO().getAll());
			result.setValidUser(true);
		} 
		catch (DatabaseException e) 
		{
			e.printStackTrace();
		}
		db.endTransaction(true);
		return result;
	}
	
	public static SearchResult search(SearchParameters params)
	{
		SearchResult result = new SearchResult();
		Database db = new Database();
		ArrayList<Record> records = new ArrayList<Record>();
		ArrayList<String> links = new ArrayList<String>();
		try
		{
			db.startTransaction();
			for(int i : params.getFieldID())
			{
				for(String s : params.getSearch())
				{
					records.addAll(db.getRecordDAO().search(i, s));
				}
			}
			for(Record r : records)
			{
				links.add(db.getBatchDAO().getBatch(r.getBatchID()).getFilePath());
			}
			result.setLinks(links);
			result.setRecords(records);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		db.endTransaction(true);
		return result;
	}
	
	private static Batch grabBatch(ArrayList<Batch> batches, DownloadBatchParameters params, Batch batch, Database db)
	{
		for(Batch b : batches)
		{
			if((b.getProjectID() == params.getProjectID())
					& b.getState() == 0)
			{
				batch = b;
				batch.setState(-1);
				db.getBatchDAO().update(batch);
				Credentials creds = new Credentials(params.getName(), params.getPassword());
				User user = db.getUserDAO().getUser(creds);
				user.setCurrentBatch(batch.getID());
				db.getUserDAO().update(user);
				break;
			}
		}
		return batch;
	}
	
	public static DownloadBatchResult downloadBatch(DownloadBatchParameters params)
	{
		DownloadBatchResult result = new DownloadBatchResult();
		ArrayList<Field> fields = new ArrayList<Field>();
		Batch batch = null;
		Project project = null;
		Database db = new Database();
		try
		{
			db.startTransaction();
			ArrayList<Batch> batches = db.getBatchDAO().getAll();
			batch = grabBatch(batches, params, batch, db);
			if(batch != null)
			{
				project = db.getProjectDAO().getProject(params.getProjectID());
				ArrayList<Field> holder = db.getFieldDAO().getAll();
				for(Field f : holder)
				{
					if(f.getProjectID() == params.getProjectID())
					{
						fields.add(f);
					}
				}
				result = new DownloadBatchResult(batch, project, fields, fields.size());
			}
			else
			{
				db.endTransaction(false);
				return result;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		db.endTransaction(true);
		return result;
	}
	
	public static GetSampleImageResult getSampleImage(GetSampleImageParameters params)
	{
		GetSampleImageResult result = new GetSampleImageResult();
		Database db = new Database();								
		try 
		{
			db.startTransaction();	
			ArrayList<Batch> batches = db.getBatchDAO().getAll();
			int i = 0;
			while(batches.get(i).getProjectID() != params.getProjectID())
			{
				i++;
				if(i == batches.size())
				{
					break;
				}
			}
			if(i < batches.size())
			{
				String sample = batches.get(i).getFilePath();
				result.setLink(sample);
				result.setValidUser(true);
				db.endTransaction(true);
			}
			else
			{
				result.setValidUser(false);
				db.endTransaction(false);
			}
		} 
		catch (DatabaseException e) 
		{
			db.endTransaction(false);
			e.printStackTrace();
		} 
		return result;
	}
	
	private static ArrayList<Integer> getFieldIDs(Batch batch, Database db)
	{
		ArrayList<Integer> fields = new ArrayList<Integer>();
		ArrayList<Field> holder = db.getFieldDAO().getAll();
		for(Field f : holder)
		{
			if(f.getProjectID() == batch.getProjectID())
			{
				fields.add(f.getID());
			}
		}
		return fields;
	}
	
	private static void addRecords(String input, Project project, Batch batch, Database db, ArrayList<Integer> fields)
	{
		List<String> rows = Arrays.asList(input.split(";",-1));
		int row = 1;
		for(String s : rows)
		{
			int i = 0;
			List<String> values = Arrays.asList(s.split(",",-1));
			for(String str : values)
			{
				str = str.toUpperCase();
				Record record = new Record(row, batch.getID(), str, fields.get(i));
				db.getRecordDAO().add(record);												
				i++;
			}
			row++;
		}
		batch.setState(1);
		db.getBatchDAO().update(batch);
	}
	
	public static SubmitBatchResult submitBatch(SubmitBatchParameters params)
	{
		SubmitBatchResult result = new SubmitBatchResult();
		Database db = new Database();
		try
		{
			db.startTransaction();
			Credentials creds = new Credentials(params.getName(), params.getPassword());
			User user = db.getUserDAO().getUser(creds);
			
			if(user.getCurrentBatch() == params.getBatchID())
			{
				String input = params.getFieldValues();
				Batch batch = db.getBatchDAO().getBatch(params.getBatchID());
				Project project = db.getProjectDAO().getProject(batch.getProjectID());
				ArrayList<Integer> fields = getFieldIDs(batch, db);
				int size = fields.size();
				if(size > 0)
				{
					addRecords(input, project, batch, db, fields);
	
					user.setCurrentBatch(0);
					int count = (user.getRecordCount() + project.getRecordsPerBatch());
					user.setRecordCount(count);
					db.getUserDAO().update(user);
					result.setSuccess(true);
				}
				else
				{
					result.setSuccess(false);
					db.endTransaction(false);
					return result;
				}
			}
			else
			{
				result.setSuccess(false);
				db.endTransaction(false);
				return result;
			}
		}
		catch(Exception e)
		{
			db.endTransaction(false);
			e.printStackTrace();
		}
		db.endTransaction(true);
		return result;
	}
	
	public static DownloadFileResult downloadFile(DownloadFileParameters params)
	{
		InputStream is;
		byte[] data = null;
		try 
		{
			is = new FileInputStream(params.getUrl());
			data = IOUtils.toByteArray(is);
			is.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return new DownloadFileResult(data);
	}
}