package server;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import server.facade.ServerFacade;
import shared.communication.DownloadFileParameters;
import shared.communication.DownloadFileResult;

public class DownloadFileHandler implements HttpHandler
{
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger("DownloadFileHandler");
	@Override
	public void handle(HttpExchange exchange)
	{
		//logger.info("DownloadFile Handler");
		String url;
		try 
		{
			url = new File("").getAbsolutePath() + exchange.getRequestURI().getPath();
			DownloadFileResult result = null;
			DownloadFileParameters params= new DownloadFileParameters(url);
			result = ServerFacade.downloadFile(params);
			OutputStream response = exchange.getResponseBody();
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			response.write(result.getFileBytes());
			response.close();
		} 
		catch (Exception e)
		{
			return;
		}
	}
}
