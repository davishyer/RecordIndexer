package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JPanel;

import client.facade.BatchState;

@SuppressWarnings("serial")
public class FieldPanel extends JPanel implements Listener
{
	private JEditorPane htmlViewer;
	private BatchState batchState;
	private int fieldNum;
	
	public FieldPanel(BatchState batchS)
	{
		//creates the viewport for the field help
		setLayout(new BorderLayout());
		
		this.batchState = batchS;
		fieldNum = batchState.getCurrentCell().getFieldNum();
		
		htmlViewer = new JEditorPane();
		htmlViewer.setOpaque(true);
        htmlViewer.setBackground(Color.white);
        htmlViewer.setPreferredSize(new Dimension(250, 300));
        htmlViewer.setEditable(false);
        htmlViewer.setContentType("text/html");
        if(batchState.getBatch() != null)
        {
	        try 
	        {
				htmlViewer.setPage("");
			} 
	        catch (IOException e)
			{
				e.printStackTrace();
			}
        }
        this.add(htmlViewer, BorderLayout.CENTER);
	}
	
	@Override
	public void setCell() 
	{	
		//updates the view based on the current cell
		fieldNum = batchState.getCurrentCell().getFieldNum();
    	String path = batchState.getUrl() + "/" +
    			batchState.getFields().get(fieldNum - 1).getHelp();
        try 
        {
			htmlViewer.setPage(path);
		} 
        catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void setText(int x, int y, String text){}

	@Override
	public void updateBatch(BatchState batchS)
	{
		//updates the field help panel
		batchState = batchS;
		setLayout(new BorderLayout());
		
		this.batchState = batchS;
		fieldNum = batchState.getCurrentCell().getFieldNum();
		
		htmlViewer = new JEditorPane();
		htmlViewer.setOpaque(true);
        htmlViewer.setBackground(Color.white);
        htmlViewer.setPreferredSize(new Dimension(250, 300));
        htmlViewer.setEditable(false);
        htmlViewer.setContentType("text/html");
        if(batchState.getBatch() != null)
        {
        	String path = batchState.getUrl() + "/" +
        			batchState.getFields().get(fieldNum - 1).getHelp();
	        try 
	        {
				htmlViewer.setPage(path);
			} 
	        catch (IOException e)
			{
				e.printStackTrace();
			}
        }
        this.add(htmlViewer, BorderLayout.CENTER);
	}
}
