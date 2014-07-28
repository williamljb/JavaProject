import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.*;


public class MyPage extends JPanel {
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

	public MyPage(Client cli) {
		client = cli;
		//register label
		title = new JLabel("Edit Personal Info");
		title.setFont(new Font("Dialog", 1, 20));
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		titlePanel.add(title);
		//id field
		id = new JTextField(client.getUserID(client.getCurUser()), 15);
		JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		idPanel.add(new JLabel("User ID:"));
		idPanel.add(id);
		id.setEditable(false);
		//name field
		name = new JTextField(client.getUserName(client.getCurUser()), 15);
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
		image = new JLabel(new ImageIcon(client.getUserIcon(client.getCurUser().id).
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
			
		});;
		//register
		register = new JButton("Confirm");
		register.setPreferredSize(new Dimension(100, 20));
		register.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String info = "";
				if (name.getText().equals(""))
				{
					info = "Name should not be empty!";
					JOptionPane.showMessageDialog(client.ui.mainFrame, info, "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!String.valueOf(password.getPassword()).equals(String.valueOf(passConfirm.getPassword())))
				{
					info = "Passwords are not the same!";
					JOptionPane.showMessageDialog(client.ui.mainFrame, info, "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (String.valueOf(password.getPassword()).equals(""))
					password.setText(client.getPasswordOfUser(client.getCurUser()));
				int ret = client.editAccount(id.getText(), name.getText(), 
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
				JOptionPane.showMessageDialog(client.ui.mainFrame, "Succeed!", "Info", JOptionPane.INFORMATION_MESSAGE);
				client.ui.setPage(new MainPage(client));
			}
			
		});
		JButton logout = new JButton("Logout");
		JPanel buttonPane = new JPanel(new GridLayout(2, 1));
		buttonPane.add(register);
		buttonPane.add(logout);
		logout.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				client.logout();
				client.ui.setPage(client.ui.login = new LoginPage(client));
			}
			
		});
		//Buttons
		MenuButton menu = new MenuButton(client, 3);
		//join together
		JPanel infoPanel = new JPanel(new GridLayout(5, 1));
		infoPanel.add(titlePanel);
		infoPanel.add(idPanel);
		infoPanel.add(namePanel);
		infoPanel.add(passPanel);
		infoPanel.add(conPanel);
		JPanel buttonPanel = new JPanel(new GridLayout(2, 1)), imagePanel = new JPanel(new FlowLayout());
		imagePanel.add(image);
		imagePanel.add(upload);
		buttonPanel.add(imagePanel);
		buttonPanel.add(buttonPane);
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add("Center", infoPanel);
		mainPanel.add("South", buttonPanel);
		mainPanel.setPreferredSize(new Dimension(350, UIDisplay.HEIGHT - 150));

		this.add("Center", mainPanel);
		this.add("South", menu);
		this.setVisible(true);
		this.setSize(new Dimension(UIDisplay.WIDTH, UIDisplay.HEIGHT));
	}

}
