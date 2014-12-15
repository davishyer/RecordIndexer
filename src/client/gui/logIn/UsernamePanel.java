package client.gui.logIn;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class UsernamePanel extends JPanel
{
	private JTextField _username;
	public UsernamePanel()
	{
		this.setLayout(new FlowLayout());
		this.add(new JLabel("Username:"));
		_username = new JTextField(10);
		this.add(_username);
	}
	
	public String getUsername()
	{
		return _username.getText();
	}
	
	public void clearText()
	{
		_username.setText("");
	}
}
