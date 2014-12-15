package search.gui;

import static servertester.views.Constants.DOUBLE_HSPACE;
import static servertester.views.Constants.SINGLE_HSPACE;
import static servertester.views.Constants.TRIPLE_HSPACE;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import servertester.views.BasePanel;

@SuppressWarnings("serial")
public class CredentialsPanel extends BasePanel
{
	public JTextField _userTextField;
	public JPasswordField _passwordTextField;
	public JButton _executeButton;
	public JTextField _hostTextField;
	public JTextField _portTextField;
	
	public CredentialsPanel() 
	{
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		add(Box.createRigidArea(DOUBLE_HSPACE));
		add(new JLabel("HOST:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		_hostTextField = new JTextField(10);
		_hostTextField.setMinimumSize(_hostTextField.getPreferredSize());
		_hostTextField.setMaximumSize(_hostTextField.getPreferredSize());
		add(_hostTextField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		add(Box.createRigidArea(DOUBLE_HSPACE));
		add(new JLabel("PORT:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		_portTextField = new JTextField(10);
		_portTextField.setMinimumSize(_portTextField.getPreferredSize());
		_portTextField.setMaximumSize(_portTextField.getPreferredSize());
		add(_portTextField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		add(Box.createRigidArea(DOUBLE_HSPACE));
		add(new JLabel("USERNAME:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		_userTextField = new JTextField(10);
		_userTextField.setMinimumSize(_userTextField.getPreferredSize());
		add(_userTextField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		add(Box.createRigidArea(DOUBLE_HSPACE));
		add(new JLabel("PASSWORD:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		_passwordTextField = new JPasswordField(10);
		_passwordTextField.setMinimumSize(_passwordTextField.getPreferredSize());
		add(_passwordTextField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		_executeButton = new JButton("LOG IN");
		add(_executeButton);	
		add(Box.createRigidArea(DOUBLE_HSPACE));
		
		this.setMinimumSize(getPreferredSize());
		this.setMaximumSize(getPreferredSize());
	}
	
	public void setHost(String host)
	{
		_hostTextField.setText(host);
	}
	public void setPort(String port)
	{
		_portTextField.setText(port);
	}
	public String getHost()
	{
		return _hostTextField.getText();
	}
	public String getPort()
	{
		return _portTextField.getText();
	}
	public String getUser()
	{
		return _userTextField.getText();
	}
	public String getPassword()
	{
		return new String(_passwordTextField.getPassword());
	}
}
