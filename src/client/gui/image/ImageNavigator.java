package client.gui.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import client.facade.BatchState;
import client.gui.Listener;
import client.gui.image.ImageComponent.DrawingShape;

@SuppressWarnings("serial")
public class ImageNavigator extends JComponent implements Listener
{
	private BatchState batchState;
	private BufferedImage image;
	private double scale;
	private int imageDx;
	private int imageDy;
	private double viewScale;
	private int viewWidth;
	private int viewHeight;
	private int currdx;
	private int currdy;
	
	public ImageNavigator(final BatchState batchState)
	{
		this.batchState = batchState;
		this.image = null;
		this.scale = 1.0;
		this.currdx = 0;
		this.currdy = 0;
		this.addMouseListener(mouseAdapter);
	}
	
	private MouseAdapter mouseAdapter = new MouseAdapter() 
	{
		@Override
		public void mouseClicked(MouseEvent e){mousePressed(e);}
		@Override
		public void mousePressed(MouseEvent e)
		{
			imageDx = e.getX() - getWidth() / 2;
			imageDx += currdx;
			imageDy = e.getY() - getHeight() / 2;
			imageDy += currdy;
			imageDx = -imageDx;
			imageDy = -imageDy;
			System.out.println("imageDx: " + imageDx + " imageDy: " + imageDy);
			repaint();
			batchState.getImageComponent().dragging(imageDx, imageDy);
		}
		@Override
		public void mouseDragged(MouseEvent e) {mousePressed(e);}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {}	
	};
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		drawBackground(g2);
		
		if(image != null)
		{
			setScale(g2);
			setImage(g2);
			setRect(g2);
		}		
	}
	
	private void drawBackground(Graphics2D g2)
	{
		g2.setColor(new Color(216, 237, 237));
		g2.fillRect(0,  0, getWidth(), getHeight());
	}
	
	private void setScale(Graphics2D g2)
	{
		g2.translate(getWidth() / 2.0, getHeight() / 2.0);
		double height = this.getHeight();
		float avgHeight = ((float)height / (float)image.getHeight());
		double width = this.getWidth();
		float avgWidth = ((float)width / (float)image.getWidth());
		scale = Math.min(avgHeight, avgWidth);
		g2.scale(scale, scale);
	}
	
	private void setImage(Graphics2D g2)
	{
		AffineTransform transform = g2.getTransform();
		g2.translate(-(image.getWidth() / 2), -(image.getHeight() / 2));
		g2.drawImage(image, 0, 0, null);
		g2.setTransform(transform);
	}
	
	private void setRect(Graphics2D g2)
	{
		float boxScale = (float) (1.0f / viewScale);
		g2.scale(boxScale, boxScale);
		g2.setColor(new Color(130, 130, 130, 95));
		int x = (int) (imageDx * viewScale + viewWidth / 2);
		int y = (int) (imageDy * viewScale + viewHeight / 2);
		g2.fillRect(-x, -y, viewWidth, viewHeight);
	}
	
	@Override
	public void setCell() {}
	@Override
	public void setText(int x, int y, String text) {}
	@Override
	public void updateBatch(BatchState batchS) 
	{
		this.batchState = batchS;
		image = this.batchState.getImage();
		this.imageDx = batchState.getImageComponent().dx;
		this.imageDy = batchState.getImageComponent().dy;
		this.viewScale = batchState.getImageComponent().scale;
		this.viewHeight = batchState.getImageComponent().getHeight();
		this.viewWidth = batchState.getImageComponent().getWidth();
		repaint();
	}
	
	public void updateRectangle(int viewHeight, int viewWidth, double viewScale, int dx, int dy)
	{
		this.viewHeight = viewHeight;
		this.viewWidth = viewWidth;
		this.viewScale = viewScale;

		this.imageDx = dx + currdx;
		this.imageDy = dy + currdy;
		
		repaint();
	}
	
	public void finishedDrag()
	{
		this.currdx = this.imageDx;
		this.currdy = this.imageDy;
	}
}
