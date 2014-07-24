import javax.swing.*;
import javax.swing.filechooser.*;

import java.awt.*;
import java.awt.event.*;


public class RegisterPage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Client client;
	JTextField id, name;
	JPasswordField password, passConfirm;
	JButton upload, register;
	JLabel title, image;
	String imagePath = "resources/icons/2.png";

	public RegisterPage(Client cli) {
		client = cli;
		//register label
		title = new JLabel("Register");
		title.setFont(new Font("Dialog", 1, 20));
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		titlePanel.add(title);
		//id field
		id = new JTextField(15);
		JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		idPanel.add(new JLabel("User ID:"));
		idPanel.add(id);
		//name field
		name = new JTextField(15);
		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		namePanel.add(new JLabel("User Name:"));
		namePanel.add(name);
		//pass
		password = new JPasswordField(15);
		JPanel passPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		passPanel.add(new JLabel("Password:"));
		passPanel.add(password);
		//confirm
		passConfirm = new JPasswordField(15);
		JPanel conPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		conPanel.add(new JLabel("Confirm Password:"));
		conPanel.add(passConfirm);
		//upload
		upload = new JButton("Upload Image...");
		image = new JLabel(new ImageIcon(Client.getDefaultUserIcon().
				getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		upload.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Choose image");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("images", "jpg", "png", "bmp");
				fc.setFileFilter(filter);
				int ret = fc.showOpenDialog(client.ui.mainFrame);
				if (ret == JFileChooser.APPROVE_OPTION)
					image.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(imagePath = fc.getSelectedFile().getPath())
							.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
				client.ui.stack[0].repaint();
			}
			
		});
		//register
		register = new JButton("Register");
		register.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String info = "";
				if (id.getText().equals("") || id.getText().indexOf(' ') != -1)
				{
					info = "ID should not be empty or contain spaces!";
					JOptionPane.showMessageDialog(client.ui.mainFrame, info, "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (name.getText().equals("") || name.getText().indexOf(' ') != -1)
				{
					info = "Name should not be empty or contain spaces!";
					JOptionPane.showMessageDialog(client.ui.mainFrame, info, "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (String.valueOf(password.getPassword()).indexOf(' ') != -1)
				{
					info = "Password should not contain spaces!";
					JOptionPane.showMessageDialog(client.ui.mainFrame, info, "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (String.valueOf(password.getPassword()).equals(""))
				{
					info = "Password should not be empty!";
					JOptionPane.showMessageDialog(client.ui.mainFrame, info, "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!String.valueOf(password.getPassword()).equals(String.valueOf(passConfirm.getPassword())))
				{
					info = "Passwords are not the same!";
					JOptionPane.showMessageDialog(client.ui.mainFrame, info, "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int ret = client.createNewAccount(id.getText(), name.getText(), 
						String.valueOf(password.getPassword()), imagePath);
				switch (ret)
				{
				case 0:info = "Server Not Found!";break;
				case 1:info = "Such ID already exists!";break;
				}
				if (ret != -1)
				{
					JOptionPane.showMessageDialog(client.ui.mainFrame, info, "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				client.ui.setPage(client.ui.mainPage = new MainPage(client));
			}
			
		});
		//join together
		JPanel infoPanel = new JPanel(new GridLayout(5, 1));
		infoPanel.add(titlePanel);
		infoPanel.add(idPanel);
		infoPanel.add(namePanel);
		infoPanel.add(passPanel);
		infoPanel.add(conPanel);
		JPanel buttonPanel = new JPanel(new FlowLayout()), imagePanel = new JPanel(new FlowLayout());
		imagePanel.add(image);
		imagePanel.add(upload);
		buttonPanel.add(imagePanel);
		buttonPanel.add(register);
		this.setLayout(new GridLayout(2, 1));
		this.add(infoPanel);
		this.add(buttonPanel);
		this.setSize(350, 500);
		this.setLocation((UIDisplay.WIDTH - 350) / 2, (UIDisplay.HEIGHT - 300) / 3);
		this.setVisible(true);
	}

}
