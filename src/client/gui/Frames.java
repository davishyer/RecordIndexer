package client.gui;

import java.awt.*;

import client.facade.ClientFacade;
import client.gui.logIn.LogInFrame;


public class Frames
{
	private static boolean loggedIn = false;
	private static LogInFrame logIn;
	private static IndexingFrame indexing;
	private static Frames frames;
	private static ClientFacade facade;
	
	public Frames()
	{
		frames = this;
	}
	
	public static void main(String[] args) 
	{
		String host = args[0];
		String port = args[1];
		facade = new ClientFacade(host, port);
		
		/*
			In Swing, all user interface operations must occur on the �UI thread�.
			All components should be created on the UI thread.
			All method calls on UI components should happen on the UI thread.
			EventQueue.invokeLater runs the specified code on the UI thread.
			The main method for Swing programs should call EventQueue.invokeLater to create the UI.
			The main thread exits immediately after calling EventQueue.invokeLater, 
				but the UI thread keeps the program running.
		 */
		EventQueue.invokeLater(new Runnable()
		{		
			public void run() 
			{
				// Create the login window object
				logIn = new LogInFrame(frames, facade);
				logIn.setVisible(!loggedIn);
		
				// Create the indexing window object
				indexing = new IndexingFrame(frames, facade);
				indexing.setVisible(loggedIn);
			}
		});

	}
	
	public static void swap()
	{
		//used for login/logout
		loggedIn = !loggedIn;
		logIn.setVisible(!loggedIn);
		indexing.dispose();
		indexing = new IndexingFrame(frames, facade);
		indexing.setVisible(loggedIn);
	}
	
	public static void submit()
	{
		//resets the indexing frame to a null state
		indexing.dispose();
		indexing = new IndexingFrame(frames, facade);
		indexing.setVisible(loggedIn);
	}
	
	public static void closeAll()
	{
		//closes everything
		logIn.dispose();
		indexing.dispose();
	}

}