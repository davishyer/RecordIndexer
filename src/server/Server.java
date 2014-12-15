package server;

import java.io.*;
import java.net.*;
import java.util.logging.*;

import server.facade.ServerFacade;

import com.sun.net.httpserver.*;

public class Server 
{

	private static int SERVER_PORT_NUMBER;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	private static Logger logger;
	
	static 
	{
		try 
		{
			initLog();
		}
		catch (IOException e) 
		{
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	private static void initLog() throws IOException 
	{
		
		Level logLevel = Level.FINE;
		
		logger = Logger.getLogger("recordIndexer"); 
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}

	private HttpServer server;
	
	public Server() 
	{
		return;
	}
	
	private void run() 
	{		
		logger.info("Initializing Model");
		
		try 
		{
			ServerFacade.initialize();		
		}
		catch (ServerException e) 
		{
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		
		logger.info("Initializing HTTP Server");
		
		try 
		{
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) 
		{
			logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}

		server.setExecutor(null); // use the default executor
		
		//contexts
		server.createContext("/ValidateUser", validateUserHandler);
		server.createContext("/GetProjects", getProjectsHandler);
		server.createContext("/GetFields", getFieldsHandler);
		server.createContext("/GetSampleImage", getSampleImageHandler);
		server.createContext("/Search", searchHandler);
		server.createContext("/DownloadBatch", downloadBatchHandler);
		server.createContext("/SubmitBatch", submitBatchHandler);
		server.createContext("/Records", downloadFileHandler);
		
		logger.info("Starting HTTP Server");

		server.start();
	}

	//Handler objects
	private ValidateUserHandler validateUserHandler = new ValidateUserHandler();
	private GetProjectsHandler getProjectsHandler = new GetProjectsHandler();
	private GetFieldsHandler getFieldsHandler = new GetFieldsHandler();
	private GetSampleImageHandler getSampleImageHandler = new GetSampleImageHandler();
	private SearchHandler searchHandler = new SearchHandler();
	private DownloadBatchHandler downloadBatchHandler = new DownloadBatchHandler();
	private SubmitBatchHandler submitBatchHandler = new SubmitBatchHandler();
	private DownloadFileHandler downloadFileHandler = new DownloadFileHandler();

	public static void main(String[] args) 
	{
		//check for given port number
		if(args == null)
		{
			SERVER_PORT_NUMBER = 50080;
		}
		else if(args.length > 0)
		{
			SERVER_PORT_NUMBER = Integer.parseInt(args[0]);
		}
		else
		{
			SERVER_PORT_NUMBER = 50080;
		}
		new Server().run();
	}

}