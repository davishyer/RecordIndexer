package client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;

import shared.communication.*;
import shared.model.Project;
import client.facade.BatchState;
import client.facade.ClientFacade;
import client.gui.image.ImageComponent;

@SuppressWarnings("serial")
public class IndexingFrame extends JFrame
{
	private IndexingButtonPanel buttons;
	private JSplitPane topAndBottom;
	private ImageComponent imageComponent;
	private JSplitPane leftAndRight;
	private HelpPanel helpPanel;
	private EntryPanel entryPanel;
	private JMenuBar menuBar;
	private ClientFacade facade;
	private ArrayList<Project> projects;
	private BatchState batchState;
	private JMenu menu;
	
	public IndexingFrame(client.gui.Frames frame, ClientFacade fac) 
	{
		super();
		
		//TO-DO bring back the previous users batch state
		batchState = new BatchState();
		
		facade = fac;
		
		setTitle("Record Indexer");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));		
		this.setLayout(new BorderLayout());
		
		// Set the location of the window on the desktop
		this.setLocation(470, 320);

		// Set the window's size
		this.setSize(800, 300);
		
		//add in menuBar
		addMenuBar();
		
		//initialize buttons
		buttons = new IndexingButtonPanel();
		if(batchState.getBatch() == null)
		{
			buttons.resetEnabled(false);
		}
		this.add(buttons, BorderLayout.NORTH);
		
		//grab new components
		imageComponent = new ImageComponent(batchState);
		batchState.setImageComponent(imageComponent);
		helpPanel = new HelpPanel(batchState);
		batchState.setFieldPanel(helpPanel.getFieldPanel());
		batchState.setImageNav(helpPanel.getImageNav());
		entryPanel = new EntryPanel(batchState);
		batchState.setTableModel(entryPanel.getTable());
		batchState.setEntryPanel(entryPanel);
		batchState.setFacade(fac);
		
		//create bottom split pane
		leftAndRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		leftAndRight.setOneTouchExpandable(true);
		leftAndRight.setDividerLocation(0.5);
		leftAndRight.setResizeWeight(0.5);
		leftAndRight.setLeftComponent(entryPanel);
		leftAndRight.setRightComponent(helpPanel);
		
		//create top split pane
		topAndBottom = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		topAndBottom.setOneTouchExpandable(true);
		topAndBottom.setDividerLocation(0.5);
		topAndBottom.setResizeWeight(0.8);
		topAndBottom.setTopComponent(imageComponent);
		topAndBottom.setBottomComponent(leftAndRight);
		this.add(topAndBottom, BorderLayout.CENTER);
		
		setButtonListeners();
		
		this.setMinimumSize(new Dimension(800, 400));
		this.setSize(new Dimension(2000, 1000));
	}
	
	private void addMenuBar()
	{
		menu = new JMenu("File");
		JMenuItem item1 = new JMenuItem("Download Batch");
		item1.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//download a batch
				GetProjectsResult result = facade.getProjects(); 
				projects = result.getProjects();
				
				JPanel panel = new JPanel();
				generateDownloadBatchPanel(panel);
				
				JDialog downloadBatch = new JDialog();
				downloadBatch.add(panel);
				downloadBatch.setTitle("Download Batch");
				downloadBatch.setSize(new Dimension(600, 100));
				downloadBatch.setResizable(false);
				downloadBatch.setLocationRelativeTo(null);
				downloadBatch.setModal(true);
				downloadBatch.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				
				downloadBatchListeners(panel, downloadBatch);
				
				downloadBatch.setVisible(true);
			}
		});
		menu.add(item1);
		JMenuItem item2 = new JMenuItem("Log Out");
		item2.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//save batch state
				Frames.swap();
			}
		});
		menu.add(item2);
		JMenuItem item3 = new JMenuItem("Exit");
		item3.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//save batch state
				Frames.closeAll();
			}
		});
		menu.add(item3);
		menuBar = new JMenuBar();
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}
	
	private void setButtonListeners()
	{
		buttons._zoomIn.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{	
				imageComponent.zoomInPressed();
			}
		});
		buttons._zoomOut.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{	
				imageComponent.zoomOutPressed();
			}
		});
		buttons._invert.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{	
				imageComponent.invertImage();
			}
		});
		buttons._toggleHighlights.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{	
				imageComponent.toggleHighlight();
			}
		});
		buttons._save.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{	
				//TODO implement the save
			}
		});
		buttons._submit.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{	
				String values = batchState.submit();
				int batchID = batchState.getBatch().getID();
				SubmitBatchResult result = facade.submitBatch(values, batchID);
				if(result.isSuccess())
				{
					batchSubmitted();
					Frames.submit();
				}
			}
		});
	}
	
	private void generateDownloadBatchPanel(JPanel panel)
	{
		//creates the entire frame for downloading a batch
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 2;
		
		JLabel projects = new JLabel("Projects: ");
		panel.add(projects, c);
		
		c.gridx = 3;
		c.gridwidth = 3;
		
		JComboBox<String> projectsBox = new JComboBox<String>();
		populateProjects(projectsBox);
		panel.add(projectsBox, c);
		
		c.gridx = 6;
		c.gridwidth = 3;
		
		JButton sample = new JButton("View Sample Image");
		sample.setName("view");
		panel.add(sample, c);
		
		c.gridy = 2;
		c.gridx = 4;
		c.gridwidth = 2;
		
		JButton download = new JButton("Download Batch");
		download.setName("download");
		panel.add(download, c);
		
		c.gridx = 6;
		c.gridwidth = 1;
		
		JButton cancel = new JButton("Cancel");
		cancel.setName("cancel");
		panel.add(cancel, c);
	}
	
	private void populateProjects(JComboBox<String> box)
	{
		for(Project p : projects)
		{
			box.addItem(p.getProjInfo().getName());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void downloadBatchListeners(JPanel panel, final JDialog downloadBatch)
	{
		JComboBox<String> checkCombo = new JComboBox<String>();
		for(Component c : panel.getComponents())
		{
			if(c.getClass() == checkCombo.getClass())
			{
				checkCombo = (JComboBox<String>)c;
			}
		}
		final JComboBox<String> combo = checkCombo;
		JButton button = new JButton();
		for(final Component c : panel.getComponents())
		{
			if(c.getClass() == button.getClass())
			{
				button = (JButton)c;
				if(button.getName() == "cancel")
				{
					button.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							downloadBatch.dispose();
						}
					});
				}
				else if(button.getName() == "view")
				{
					button.addActionListener(new ActionListener() 
					{		
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							//bring up a sample image for the project
							int projNum = combo.getSelectedIndex();
							projNum++;
							
							GetSampleImageResult sampleImage = facade.getSampleImage(projNum);
							
							URL url = sampleImage.getURL();
							SampleImagePanel imagePanel = new SampleImagePanel(url);
							final JDialog sampleFrame = new JDialog();
							
							sampleFrame.add(imagePanel);
							
							imagePanel.button.addActionListener(new ActionListener()
							{	
								@Override
								public void actionPerformed(ActionEvent e) 
								{
									sampleFrame.dispose();
								}
							});
							
							sampleFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							sampleFrame.setModal(true);
							sampleFrame.pack();
							sampleFrame.setResizable(false);
							sampleFrame.setLocationRelativeTo(null);
							sampleFrame.setVisible(true);
						}
					});
				}
				else if(button.getName() == "download")
				{
					button.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							int projNum = combo.getSelectedIndex();
							projNum++;
							
							//download a batch and update all batchstate listeners
							DownloadBatchResult result = facade.downloadBatch(projNum);
							batchState.setResult(result);
							downloadBatch.dispose();
							buttons.resetEnabled(true);
							
							//don't allow the user to download another batch
							batchGiven();
							IndexingFrame.this.leftAndRight.repaint(); 
						}
					});
				}
			}
		}
	}
	private void batchGiven()
	{
		menu.getItem(0).setEnabled(false);
	}

	private void batchSubmitted()
	{
		menu.getItem(0).setEnabled(true);
	}
}
