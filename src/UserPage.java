import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class UserPage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Client client;
	JButton send, operate, back;

	public UserPage(Client cli, final String userID, final boolean shouldBack) {
		client = cli;
		//info
		JPanel display = new JPanel(new BorderLayout());
		display.setPreferredSize(new Dimension(UIDisplay.WIDTH, (UIDisplay.HEIGHT - 150) / 6));
		final User curTalked = client.getUserById(userID);
		JLabel image = new JLabel(new ImageIcon(client.getUserIcon(curTalked).
				getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		display.add("West", image);
		String userName = client.getUserName(curTalked);
		JLabel nameLabel = new JLabel(userName);
		nameLabel.setFont(new Font("Dialog", 1, 15));
		JPanel centerPanel = new JPanel(new GridLayout(2, 1));
		centerPanel.add(nameLabel);
		centerPanel.add(new JLabel(userID));
		display.add("Center", centerPanel);
		//buttons
		send = new JButton("Send Message");
		back = new JButton("back");
		operate = new JButton(client.isFriend(userID) ? "Delete Friend" : "Send Request");
		JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
		buttonPanel.add(send);
		buttonPanel.add(operate);
		buttonPanel.add(back);
		back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				client.ui.pop();
			}
			
		});
		if (!client.isFriend(userID))
			send.setEnabled(false);
		send.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (shouldBack)
					client.ui.pop();
				else
					client.ui.push(client.ui.talkPage = new TalkPage(client, userID, -2));
			}
			
		});
		operate.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (client.isFriend(userID))
				{
					int ret = JOptionPane.showConfirmDialog(client.ui.mainFrame, 
							"Are you sure to delete this friend?", "Confirm", 
							JOptionPane.YES_NO_OPTION);
					if (ret == JOptionPane.NO_OPTION)
						return;
					client.deleteFriend(userID);
					System.out.println("ok");
					client.ui.pop();
					client.ui.stack[client.ui.top - 1].repaint();
				}
				else
				{
					client.sendFriendRequestTo(userID);
					JOptionPane.showMessageDialog(client.ui.mainFrame, "Request sent. Please wait for reply",
							"info", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
		});
		//join together
		this.setLayout(new GridLayout(2, 1));
		this.add(display);
		this.add(buttonPanel);
		this.setSize(350, 200);
		this.setLocation((UIDisplay.WIDTH - 350) / 2, (UIDisplay.HEIGHT - 300) / 2);
		this.setVisible(true);
	}

}
