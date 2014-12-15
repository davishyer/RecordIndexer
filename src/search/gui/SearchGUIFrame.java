package search.gui;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import client.communication.*;
import shared.communication.*;
import shared.model.*;
import static servertester.views.Constants.*;

@SuppressWarnings("serial")
public class SearchGUIFrame extends JFrame 
{
	private final SearchGUIFrame frame = this;
	private CredentialsPanel _credentialsPanel;
	private ProjectsPanel _projectsPanel;
	private SearchPanel _searchPanel;
	private ImagesPanel _imagesPanel;
	private final String host = "localhost";
	private final String port = "50080";
	private String URL;
	private ClientCommunicator cc;
	
	public SearchGUIFrame() 
	{
		setTitle("Search GUI");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));		
		
		addCredentialsPanel();
		
		// Set the location of the window on the desktop
		this.setLocation(400, 300);

		// Set the window's size
		this.setSize(1000, 80);
		this.setMinimumSize(this.getSize());
	}
	
	private void addCredentialsPanel()
	{
		add(Box.createRigidArea(DOUBLE_VSPACE));
		
		_credentialsPanel = new CredentialsPanel();
		_credentialsPanel.setHost(host);
		_credentialsPanel.setPort(port);
		add(_credentialsPanel);
		_credentialsPanel._executeButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{	
				cc = new ClientCommunicator(_credentialsPanel.getPort(), _credentialsPanel.getHost());
				ValidateUserCredentials creds = new ValidateUserCredentials(_credentialsPanel.getUser(), _credentialsPanel.getPassword());
				ValidateUserResult result = cc.validateUser(creds);
				if(result.isOutput())
				{
					URL = "http://" + _credentialsPanel.getHost() + ":" + _credentialsPanel.getPort() + "/";
					_credentialsPanel._hostTextField.setEnabled(false);
					_credentialsPanel._passwordTextField.setEnabled(false);
					_credentialsPanel._portTextField.setEnabled(false);
					_credentialsPanel._userTextField.setEnabled(false);
					_credentialsPanel._executeButton.setVisible(false);
					addSearchPanel(creds.getUsername(), creds.getPassword());
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "Invalid Username or Password");
				}
			}
		});

		add(Box.createRigidArea(SINGLE_VSPACE));
	}
	
	private void addSearchPanel(final String username, final String password)
	{
		ArrayList<Project_Field> body = new ArrayList<Project_Field>();
		GetProjectsParameters params = new GetProjectsParameters(username, password);
		GetProjectsResult result = cc.getProjects(params);
		for(Project p : result.getProjects())
		{
			GetFieldsParameters fParams = new GetFieldsParameters(username, password, p.getProjInfo().getID());
			GetFieldsResult fResult = cc.getFields(fParams);
			body.add(new Project_Field(fResult.getFields(), p));
		}
		add(Box.createRigidArea(DOUBLE_VSPACE));
	
		_projectsPanel = new ProjectsPanel(body);
		add(_projectsPanel);
		add(Box.createRigidArea(DOUBLE_VSPACE));
		_searchPanel = new SearchPanel();
		add(_searchPanel);
		add(Box.createRigidArea(SINGLE_VSPACE));
		_searchPanel._searchButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(_searchPanel.getValues().length() > 0)
				{
					performSearch(username, password);
				}
				else
				{
					_imagesPanel._imageComboBox.removeAllItems();
				}
			}
		});
		addImagesPanel();
		this.setSize(getPreferredSize());
		this.setMinimumSize(getPreferredSize());
		this.pack();
	}
	
	private void performSearch(String username, String password)
	{
		String values = _searchPanel.getValues();
		List<String> holder = Arrays.asList(values.split(",",-1));
		int tempFieldID = 0;
		ArrayList<Integer> fieldList = new ArrayList<Integer>();
		for(Component c : _projectsPanel.getComponents())
		{
			JCheckBox j = new JCheckBox();
			if(c.getClass() == j.getClass())
			{
				++tempFieldID;
				j = (JCheckBox) c;
				if(j.isSelected())
				{
					fieldList.add(tempFieldID);
				}
			}
		}
		if(fieldList.size() == 0)
		{
			_imagesPanel._imageComboBox.removeAllItems();
			return;
		}
		ArrayList<String> searchList = new ArrayList<String>();
		for(String s : holder)
		{
			s = s.toUpperCase();
			if(!searchList.contains(s))
			{
				searchList.add(s); 
			}
		}
		SearchParameters params = new SearchParameters(username, password, fieldList, searchList);
		SearchResult result = cc.search(params);
		updateImages(result);
	}
	
	private void addImagesPanel()
	{
		_imagesPanel = new ImagesPanel();
		add(_imagesPanel);
		_imagesPanel._viewButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				InputStream input;
				try 
				{
					input = new URL((String)URL + _imagesPanel._imageComboBox.getSelectedItem()).openStream();
					BufferedImage image = ImageIO.read(input);
					JFrame f = new JFrame();
					f.setTitle((String)URL + _imagesPanel._imageComboBox.getSelectedItem());
					ImageIcon icon = new ImageIcon(image);
					JPanel j = new JPanel();
					j.add(new JLabel(icon));
					j.revalidate();
					f.add(new JScrollPane(j));
					f.pack();f.setVisible(true);
					pack();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		this.setSize(getPreferredSize());
		this.setMinimumSize(getPreferredSize());
		this.pack();
	}
	
	@SuppressWarnings("unchecked")
	private void updateImages(SearchResult result)
	{
		ArrayList<String> uniqueLinks = new ArrayList<String>();
		_imagesPanel._imageComboBox.removeAllItems();
		for(String s : result.getLinks())
		{
			if(!uniqueLinks.contains(s))
			{
				_imagesPanel._imageComboBox.addItem(s);
				uniqueLinks.add(s);
			}
		}
		_imagesPanel.setSize(getPreferredSize());
	}
}
