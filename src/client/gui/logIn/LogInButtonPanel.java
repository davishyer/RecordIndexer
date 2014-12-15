package client.gui.logIn;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LogInButtonPanel extends JPanel
{
	public JButton LogIn;
	public JButton Exit;
	
	public LogInButtonPanel()
	{
		this.setLayout(new FlowLayout());
		
		LogIn = new JButton("Log In");
		LogIn.setSize(50, 10);
		this.add(LogIn);
		
		Exit = new JButton("Exit");
		Exit.setSize(50, 10);
		this.add(Exit);
	}
}
