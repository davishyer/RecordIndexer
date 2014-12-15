package shared.communication;

import java.net.URL;
import java.util.ArrayList;

import shared.model.Record;

public class SearchResult 
{
	private ArrayList<Record> records;
	private ArrayList<URL> urls;
	private ArrayList<String> links;
	private boolean validUser;
	
	public SearchResult()
	{
		validUser = false;
	}

	public ArrayList<Record> getRecords() 
	{
		return records;
	}

	public void setRecords(ArrayList<Record> records) 
	{
		this.records = records;
	}

	public ArrayList<URL> getUrls() 
	{
		return urls;
	}

	public void setUrls(ArrayList<URL> urls) 
	{
		this.urls = urls;
	}

	public boolean isValidUser() 
	{
		return validUser;
	}

	public void setValidUser(boolean validUser) 
	{
		this.validUser = validUser;
	}

	public ArrayList<String> getLinks() 
	{
		return links;
	}

	public void setLinks(ArrayList<String> links) 
	{
		this.links = links;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if(validUser & records.size() > 0)
		{
			for(int i = 0; i < records.size(); ++i)
			{
				sb.append(records.get(i).getBatchID() + "\n");
				sb.append(urls.get(i) + "\n");
				sb.append(records.get(i).getRecordNumber() + "\n");
				sb.append(records.get(i).getFieldID() + "\n");
			}
		}
		else
		{
			sb.append("FAILED\n");
		}
		return sb.toString();
	}
	
}
