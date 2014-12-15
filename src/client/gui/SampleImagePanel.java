package client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SampleImagePanel extends JPanel
{
	private BufferedImage image;
	private int width;
	private int height;
	public JButton button;
	
	public SampleImagePanel(URL url)
	{
		super();
		
		this.setLayout(new BorderLayout());
		
		try
		{
			//get the image
			InputStream input = url.openStream();
			image = ImageIO.read(input);
			
			this.setPreferredSize(new Dimension(image.getWidth()/2, image.getHeight()/2));
			width = image.getWidth() / 2;
			height = image.getHeight() / 2;

		}
		catch(Exception e)
		{
			
		}	
		JPanel south = new JPanel();
		south.setLayout(new BoxLayout(south, BoxLayout.PAGE_AXIS));
		
		//add in the button
		button = new JButton("Ok");
		button.setAlignmentX(CENTER_ALIGNMENT);
		south.add(button);
		this.add(south, BorderLayout.SOUTH);
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image, 0, 0, width, height, null);	
	}
}
