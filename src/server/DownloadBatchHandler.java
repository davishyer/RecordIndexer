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

public class DownloadBatchHandler implements HttpHandler
{

	private Logger logger = Logger.getLogger("recordindexer"); 
	
	@Override
	public void handle(HttpExchange exchange) throws IOException 
	{
		XStream x = new XStream(new DomDriver());
		DownloadBatchParameters params = (DownloadBatchParameters) x.fromXML(exchange.getRequestBody());
		ValidateUserCredentials creds = new ValidateUserCredentials(params.getName(), params.getPassword());
		
		try 
		{
			ValidateUserResult logIn = ServerFacade.validateUser(creds);
			if(logIn.isOutput() & !logIn.isBatch())
			{
				DownloadBatchResult result = ServerFacade.downloadBatch(params);
				result.setValidUser(true);
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				x.toXML(result, exchange.getResponseBody());
				exchange.getResponseBody().close();
			}
			else
			{
				DownloadBatchResult result = new DownloadBatchResult();
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
