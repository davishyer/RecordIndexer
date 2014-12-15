package server;

import java.io.IOException;
import java.net.HttpURLConnection;

import server.facade.ServerFacade;
import shared.communication.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SubmitBatchHandler implements HttpHandler
{
	@Override
	public void handle(HttpExchange exchange) throws IOException 
	{
		XStream x = new XStream(new DomDriver());
		SubmitBatchParameters params = (SubmitBatchParameters) x.fromXML(exchange.getRequestBody());
		ValidateUserCredentials creds = new ValidateUserCredentials(params.getName(), params.getPassword());
		
		try 
		{
			ValidateUserResult logIn = ServerFacade.validateUser(creds);
			if(logIn.isOutput())
			{
				SubmitBatchResult result = ServerFacade.submitBatch(params);
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				x.toXML(result, exchange.getResponseBody());
				exchange.getResponseBody().close();
			}
			else
			{
				SubmitBatchResult result = new SubmitBatchResult();
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				x.toXML(result, exchange.getResponseBody());
				exchange.getResponseBody().close();
			}
		}
		catch (Exception e) 
		{
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			exchange.getResponseBody().close();
		}
	}
}
