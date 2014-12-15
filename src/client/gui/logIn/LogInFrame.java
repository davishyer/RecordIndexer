package client.gui.logIn;

import static servertester.views.Constants.DOUBLE_VSPACE;
import static servertester.views.Constants.SINGLE_VSPACE;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import shared.communication.*;
import client.facade.ClientFacade;
import client.gui.Frames;

@SuppressWarnings("serial")
public class LogInFrame extends JFrame
{	
	private LogInFrame logIn = this;
	private UsernamePanel _usernamePanel;
	private PasswordPanel _passwordPanel;
	private LogInButtonPanel _buttons;
	private ClientFacade facade;
	
	public LogInFrame(client.gui.Frames contr, ClientFacade facade) 
	{
		super();
		
		this.facade = facade;
		
		setTitle("LogIn");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));		
		
		addCredentialsPanel();
		
		// Set the location of the window on the desktop
		this.setLocation(665, 400);

		// Set the window's size
		this.setSize(250, 130);
		this.setResizable(false);
	}
	
	private void addCredentialsPanel()
	{
		add(Box.createRigidArea(DOUBLE_VSPACE));
		_usernamePanel = new UsernamePanel();
		this.add(_usernamePanel);
				
		_passwordPanel = new PasswordPanel();
		this.add(_passwordPanel);
		
		_buttons = new LogInButtonPanel();
		this.add(_buttons);
		
		add(Box.createRigidArea(SINGLE_VSPACE));
		
		_buttons.LogIn.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{	
				String username = _usernamePanel.getUsername();
				String password = _passwordPanel.getPassword();
				ValidateUserResult result = facade.validateUser(username, password);
				if(result.isOutput())
				{
					String welcome = "Welcome " + result.getFirstName() + " " 
							+ result.getLastName() + ". \nYou have indexed "
							+ result.getRecordNum() + " records";
					JOptionPane.showMessageDialog(logIn, welcome);
					_usernamePanel.clearText();
					_passwordPanel.clearText();
					Frames.swap();
				}
				else
				{
					_passwordPanel.clearText();
					JOptionPane.showMessageDialog(logIn, "Invalid Username and/or Password");
				}
			}
		});
		
		_buttons.Exit.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{	
				Frames.closeAll();
			}
		});

		add(Box.createRigidArea(SINGLE_VSPACE));
	}
}
