import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Client client;
	JLabel icon;
	JTextField idInput;
	JPasswordField password;
	JButton login, register;
	boolean noID = false;
	
	public LoginPage(Client client0) {
		this.client = client0;
		this.setLayout(new GridLayout(2, 1));
		this.setSize(300, 300);
		this.setLocation((UIDisplay.WIDTH - 300) / 2, (UIDisplay.HEIGHT - 300) / 3);
		//icon
		icon = new JLabel();
		icon.setIcon(new ImageIcon(client.getLastUserIcon().
				getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		iconPanel.add(icon);
		//id
		idInput = new JTextField(client.getLastUserID(), 15);
		JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		idPanel.add(new JLabel("UserID:"));
		idPanel.add(idInput);
		idPanel.setOpaque(false);
		idInput.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				icon.setIcon(Client.getDefaultUserIcon());
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				icon.setIcon(Client.getDefaultUserIcon());
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				icon.setIcon(Client.getDefaultUserIcon());
			}
		});
		//pass
		password = new JPasswordField(15);
		JPanel passPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		passPanel.add(new JLabel("Password:"));
		passPanel.add(password);
		passPanel.setOpaque(false);
		password.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER)
					(login.getListeners(ActionListener.class))[0].actionPerformed(null);
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
		});
		//button
		login = new JButton("Login");
		register = new JButton("Register");
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		buttonPanel.add(login);
		buttonPanel.add(register);
		buttonPanel.setOpaque(false);
		login.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int ret = 4;
				String info = "";
				if (String.valueOf(password.getPassword()).equals(""))
					ret = 2;
				if (idInput.getText().equals("") || idInput.getText().indexOf(' ') != -1)
				{
					info = "ID cannot contain spaces or be empty!";
					ret = 3;
				}
				if (ret == 4)
					ret = client.tryLogin(idInput.getText(), String.valueOf(password.getPassword()));
				switch (ret)
				{
				case 0:info = "Server Not Found!";break;
				case 1:info = "No Such ID!";break;
				case 2:info = "Incorrect Password!";break;
				}
				if (ret != -1)
				{
					JOptionPane.showMessageDialog(client.ui.mainFrame, info, "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				client.ui.setPage(new MainPage(client));
			}
			
		});
		register.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				client.ui.setPage(client.ui.registerPage = new RegisterPage(client));
			}
			
		});
		//join together
		this.add(iconPanel);
		JPanel infoPanel = new JPanel(new GridLayout(3, 1));
		infoPanel.add(idPanel);
		infoPanel.add(passPanel);
		infoPanel.add(buttonPanel);
		this.add(infoPanel);
		this.setOpaque(false);
		iconPanel.setOpaque(false);
		infoPanel.setOpaque(false);
	}
}
