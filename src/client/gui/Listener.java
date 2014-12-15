package client.gui;

import client.facade.BatchState;

public interface Listener
{
	//basic interface for anything listening to the batch state
	public void setCell();
	public void setText(int x, int y, String text);
	public void updateBatch(BatchState batchS);
}
