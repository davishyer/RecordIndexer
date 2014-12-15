package client.facade;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import client.facade.SpellCorrector.NoSimilarWordFoundException;
import client.gui.*;
import client.gui.image.ImageComponent;
import client.gui.image.ImageNavigator;
import client.gui.table.*;
import shared.communication.*;
import shared.model.*;


public class BatchState 
{
	private Cell currentCell;
	private String[][] indexedData;
	private BufferedImage image;
	private Project project;
	private ArrayList<Field> fields;
	private DownloadBatchResult result;
	private String imagePath;
	private ImageComponent imageComponent;
	private FieldPanel fieldPanel;
	private EntryPanel entryPanel;
	private ImageNavigator imageNav;
	private Table table;
	private Batch batch;
	private String url;
	private boolean[][] misspelled;
	private ArrayList<Corrector> dictionaries;
	private ClientFacade facade;
	
	public BatchState()
	{
		currentCell = new Cell();
		indexedData = null;
		image = null;
		result = null;
		project = null;
		fields = null;
		imagePath = "";
		imageComponent = null;
		batch = null;
		url = null;
		fieldPanel = null;
		table = null;
		dictionaries = null;
		misspelled = null;
		facade = null;
		imageNav = null;
	}

	public Cell getCurrentCell()
	{
		return currentCell;
	}

	public void setCurrentCell(Cell currentCell) 
	{
		this.currentCell = currentCell;
	}

	public String[][] getIndexedData() 
	{
		return indexedData;
	}

	public void setIndexedData(String[][] indexedData) 
	{
		this.indexedData = indexedData;
	}

	public BufferedImage getImage() 
	{
		return image;
	}

	public void setImage(BufferedImage image)
	{
		this.image = image;
	}

	public Project getProject()
	{
		return project;
	}

	public void setProject(Project project) 
	{
		this.project = project;
	}

	public ArrayList<Field> getFields() 
	{
		return fields;
	}

	public void setFields(ArrayList<Field> fields)
	{
		this.fields = fields;
	}

	public DownloadBatchResult getResult() 
	{
		return result;
	}

	public void setResult(DownloadBatchResult result) 
	{
		if(result.getBatch() != null)
		{
			//initialize all variables based on the new batch
			this.result = result;
			this.batch = result.getBatch();
			this.project = result.getProject();
			this.fields = this.result.getFields();
			url = this.result.getUrl();
			this.imagePath = url;
			this.imagePath += "/" + this.result.getBatch().getFilePath();
			
			
			initializeDictionaries();
			
			URL url;
			try
			{
				url = new URL(imagePath);
				InputStream input = url.openStream();
				image = ImageIO.read(input); 
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			indexedData = new String[this.fields.size()][this.project.getRecordsPerBatch()];
			initializeMisspelled();
			
			imageComponent.updateBatch(this);
			imageNav.updateBatch(this);
			fieldPanel.updateBatch(this);
			entryPanel.getTable().updateBatch(this);
			entryPanel.getFormEntry().updateBatch(this);
			entryPanel.getTable().repaint();
		}
	}
	
	private void initializeMisspelled()
	{
		//misspelled array should start with all false
		misspelled = new boolean[this.fields.size()][this.project.getRecordsPerBatch()];
		for(int i = 0; i < this.fields.size(); ++i)
		{
			for(int j = 0; j < this.project.getRecordsPerBatch(); ++j)
			{
				misspelled[i][j] = false;
			}
		}
	}
	
	private void initializeDictionaries()
	{
		//initialize correctors for quality check
		dictionaries = new ArrayList<Corrector>();
		for(Field f : this.fields)
		{
			String fileSuffix = f.getKnownPath();
			Corrector temp = new Corrector();
			if(!fileSuffix.equals(""))
			{
				String file = this.url + "/" + fileSuffix;
				
				try 
				{
					URL url = new URL(file);
					InputStream input = url.openStream();
					String fileContents = IOUtils.toString(input);
					temp.useDictionary(fileContents);
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			
			dictionaries.add(temp);
		}
	}

	public String getImagePath() 
	{
		return imagePath;
	}

	public void setImagePath(String imagePath)
	{
		this.imagePath = imagePath;
	}

	public ImageComponent getImageComponent() 
	{
		return imageComponent;
	}

	public void setImageComponent(ImageComponent imageComponent) 
	{
		this.imageComponent = imageComponent;
	}

	public Batch getBatch() 
	{
		return batch;
	}

	public void setBatch(Batch batch) 
	{
		this.batch = batch;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url) 
	{
		this.url = url;
	}
	
	public void cellChanged(int x, int y)
	{
		//change the currently selected cell and update all listeners
		if(x != -1)
			currentCell.setFieldNum(x);
		if(y != -1)
			currentCell.setRecordNum(y);
		fieldPanel.setCell();
		imageComponent.setCell();
		entryPanel.getFormEntry().setCell();
		table.setCell();
	}
	
	public void addText(int x, int y, String text)
	{
		//store the text entered and update all listeners
		indexedData[x][y] = text;
		table.setText(x, y, text);
		entryPanel.getFormEntry().setText(x, y, text);
		Corrector c = dictionaries.get(x);
		if(!text.equals(""))
		{
			//if the field is a string
			if(c.implemented)
			{
				ArrayList<String> results = new ArrayList<String>();
				try 
				{
					results = c.suggestSimilarWord(text);
				} 
				catch (NoSimilarWordFoundException e) 
				{
					e.printStackTrace();
				}
				if(!results.contains(text))
				{
					misspelled[x][y] = true;
				}
				else
				{
					misspelled[x][y] = false;
				}
			}
			//the field is a number
			else
			{
				if(!text.matches("[0-9]+"))
				{
					misspelled[x][y] = true;
				}
				else
				{
					misspelled[x][y] = false;
				}
			}
		}
		else
		{
			misspelled[x][y] = false;
		}
	}
	
	public String submit()
	{
		//return data array in the correct form for submission
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < this.project.getRecordsPerBatch(); i++)
		{
			for(int j = 0; j < this.fields.size(); j++)
			{
				if(indexedData[j][i] != null)
				{
					sb.append(indexedData[j][i]);
				}
				if(j != this.fields.size() - 1)
				{
					sb.append(",");
				}
			}
			if(i != this.project.getRecordsPerBatch() - 1)
			{
				sb.append(";");
			}
		}
		return sb.toString();
	}

	public FieldPanel getFieldPanel() 
	{
		return fieldPanel;
	}

	public void setFieldPanel(FieldPanel fieldPanel)
	{
		this.fieldPanel = fieldPanel;
	}

	public Table getTableModel() 
	{
		return table;
	}

	public void setTableModel(Table tableModel) 
	{
		this.table = tableModel;
	}

	public EntryPanel getEntryPanel() 
	{
		return entryPanel;
	}

	public void setEntryPanel(EntryPanel entryPanel) 
	{
		this.entryPanel = entryPanel;
	}

	public boolean[][] getMisspelled()
	{
		return misspelled;
	}

	public void setMisspelled(boolean[][] misspelled) 
	{
		this.misspelled = misspelled;
	}

	public ArrayList<Corrector> getDictionaries()
	{
		return dictionaries;
	}

	public void setDictionaries(ArrayList<Corrector> dictionaries) 
	{
		this.dictionaries = dictionaries;
	}
	
	public ArrayList<String> getSuggestions(String text, int field)
	{
		//based on a word in a specific field, get the suggestions
		Corrector c = dictionaries.get(field);
		//if the field is a string
		ArrayList<String> results = new ArrayList<String>();
		if(!text.equals(""))
		{
			if(c.implemented)
			{
				
				try 
				{
					results = c.suggestSimilarWord(text);
				} 
				catch (NoSimilarWordFoundException e) 
				{
					e.printStackTrace();
				}
			}
		}
		return results;
	}

	public ClientFacade getFacade() 
	{
		return facade;
	}

	public void setFacade(ClientFacade facade) 
	{
		this.facade = facade;
	}

	public ImageNavigator getImageNav() 
	{
		return imageNav;
	}

	public void setImageNav(ImageNavigator imageNav) 
	{
		this.imageNav = imageNav;
	}
	
	public void updateNav(int viewHeight, int viewWidth, double viewScale, int dx, int dy)
	{
		imageNav.updateRectangle(viewHeight, viewWidth, viewScale, dx, dy);
	}
}
