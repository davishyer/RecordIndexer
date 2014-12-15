package shared.communication;

import java.util.ArrayList;

public class SearchParameters 
{
	private String name;
	private String password;
	private ArrayList<Integer> fieldID;
	private ArrayList<String> search;
	
	public SearchParameters()
	{
		
	}
	
	public SearchParameters(String name, String password, ArrayList<Integer> fieldID, ArrayList<String> search)
	{
		this.name = name;
		this.password = password;
		this.fieldID = fieldID;
		this.search = search;
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

	public ArrayList<Integer> getFieldID() 
	{
		return fieldID;
	}

	public void setFieldID(ArrayList<Integer> fieldID) 
	{
		this.fieldID = fieldID;
	}

	public ArrayList<String> getSearch() 
	{
		return search;
	}

	public void setSearch(ArrayList<String> search)
	{
		this.search = search;
	}

	
}
