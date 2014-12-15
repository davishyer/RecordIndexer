package client.gui.logIn;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class PasswordPanel extends JPanel
{
	private JPasswordField _password;
	public PasswordPanel()
	{
		this.setLayout(new FlowLayout());
		
		this.add(new JLabel("Password:"));
		_password = new JPasswordField(10);
		this.add(_password);
	}
	
	public String getPassword()
	{
		return new String(_password.getPassword());
	}
	
	public void clearText()
	{
		_password.setText("");
	}
}
