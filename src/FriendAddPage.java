import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class FriendAddPage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Client client;
	JTextField inputID;
	JButton checkAndAdd, back, seeRequest;
	JLabel title;

	public FriendAddPage(Client cli) {
		client = cli;
		//search label
		title = new JLabel("Search Friends");
		title.setFont(new Font("Dialog", 1, 20));
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		titlePanel.add(title);
		//input
		JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		inputPanel.add(new JLabel("Search ID: "));
		inputID = new JTextField(15);
		inputPanel.add(inputID);
		//button
		JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
		checkAndAdd = new JButton("Check Profile");
		back = new JButton("Back");
		seeRequest = new JButton("See Request");
		buttonPanel.add(checkAndAdd);
		buttonPanel.add(back);
		buttonPanel.add(seeRequest);
		checkAndAdd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (inputID.getText().equals(""))
				{
					JOptionPane.showMessageDialog(client.ui.mainFrame, "ID cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int ret = client.findUserID(inputID.getText());
				String info = "Request sent. Please wait for reply.";
				switch (ret)
				{
				case 0:info = "Server Not Found!";break;
				case 1:info = "Such ID does not exist!";break;
				}
				if (ret != -1)
				{
					JOptionPane.showMessageDialog(client.ui.mainFrame, info, "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{
					//client.sendFriendRequestTo(inputID.getText());
					client.ui.push(client.ui.userPage = new UserPage(client, inputID.getText()));
					//JOptionPane.showMessageDialog(client.ui.mainFrame, info, "info", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
			
		});
		back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				client.ui.pop();
			}
			
		});
		seeRequest.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				client.ui.push(client.ui.seeRequestPage = new SeeRequestPage(client));
			}
			
		});
		//join together
		this.setLayout(new GridLayout(3, 1));
		this.add(titlePanel);
		this.add(inputPanel);
		this.add(buttonPanel);
		this.setSize(350, 200);
		this.setLocation((UIDisplay.WIDTH - 350) / 2, (UIDisplay.HEIGHT - 200) / 3);
		this.setVisible(true);
	}

}
