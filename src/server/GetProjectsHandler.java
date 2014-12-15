package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.facade.ServerFacade;
import shared.communication.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetProjectsHandler implements HttpHandler
{
	private Logger logger = Logger.getLogger("recordindexer"); 
	
	@Override
	public void handle(HttpExchange exchange) throws IOException 
	{
		XStream x = new XStream(new DomDriver());
		GetProjectsParameters params = (GetProjectsParameters)x.fromXML(exchange.getRequestBody());
		ValidateUserCredentials user = new ValidateUserCredentials(params.getName(), params.getPassword());
		
		try 
		{
			ValidateUserResult logIn = ServerFacade.validateUser(user);
			if(logIn.isOutput())
			{
				GetProjectsResult result = ServerFacade.getProjects(params);
				result.setValidUser(true);
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				x.toXML(result, exchange.getResponseBody());
				exchange.getResponseBody().close();
			}
			else
			{
				GetProjectsResult result = new GetProjectsResult();
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
