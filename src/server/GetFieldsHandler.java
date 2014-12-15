package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.facade.ServerFacade;
import shared.communication.*;
import shared.model.Field;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetFieldsHandler implements HttpHandler
{
	private Logger logger = Logger.getLogger("recordindexer"); 
	
	@Override
	public void handle(HttpExchange exchange) throws IOException 
	{
		XStream x = new XStream(new DomDriver());
		GetFieldsParameters params = (GetFieldsParameters)x.fromXML(exchange.getRequestBody());
		ValidateUserCredentials user = new ValidateUserCredentials(params.getName(), params.getPassword());
		int projectID = params.getProjectID();
		
		try 
		{
			ValidateUserResult logIn = ServerFacade.validateUser(user);
			if(logIn.isOutput())
			{
				GetFieldsResult result = ServerFacade.getFields(params);
				if(projectID != -1)
				{
					ArrayList<Field> fields2 = new ArrayList<Field>();
					for(Field f : result.getFields())
					{
						if(f.getProjectID() == projectID)
						{
							fields2.add(f);
						}
					}
					result.setFields(fields2);
					if(result.getFields().size() == 0)
					{
						result.setValidUser(false);
					}
				}
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				x.toXML(result, exchange.getResponseBody());
				exchange.getResponseBody().close();
			}
			else
			{
				GetFieldsResult result = new GetFieldsResult();
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				x.toXML(result, exchange.getResponseBody());
				exchange.getResponseBody().close();
			}
		}
		catch (Exception e) 
		{
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			exchange.getResponseBody().close();
		}
	}
}
