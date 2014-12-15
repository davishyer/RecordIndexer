package shared.communication;

import java.util.ArrayList;

import shared.model.Field;

public class GetFieldsResult 
{
	private ArrayList<Field> fields;
	private boolean validUser;
	
	public GetFieldsResult()
	{
		validUser = false;
	}

	public ArrayList<Field> getFields() 
	{
		return fields;
	}

	public void setFields(ArrayList<Field> fields) 
	{
		this.fields = fields;
	}
	
	public boolean isValidUser() 
	{
		return validUser;
	}

	public void setValidUser(boolean validUser) 
	{
		this.validUser = validUser;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if(validUser)
		{
			for(Field f : fields)
			{
				sb.append(f.getProjectID() + "\n");
				sb.append(f.getID() + "\n");
				sb.append(f.getTitle() + "\n");
			}
		}
		else
		{
			sb.append("FAILED\n");
		}
		return sb.toString();
	}
}
