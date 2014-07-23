import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class SeeRequestPage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Client client;
	JButton back;
	User curRequest;

	public SeeRequestPage(Client cli) {
		client = cli;
		this.setLayout(new BorderLayout());
		//list
		int num = client.getNumberOfRequest();
		JPanel mainScreen = new JPanel(new GridLayout(num, 1));
		JScrollPane screen = new JScrollPane(mainScreen);
		screen.setPreferredSize(new Dimension(UIDisplay.WIDTH, UIDisplay.HEIGHT - 200));
		screen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		for (int i = 0; i < num; ++i)
		{
			JPanel display = new JPanel(new BorderLayout());
			display.setSize(UIDisplay.WIDTH, (UIDisplay.HEIGHT - 150) / 6);
			curRequest = client.getUserOfRequest(i);
			JLabel image = new JLabel(new ImageIcon(client.getUserIcon(curRequest).
					getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
			display.add("West", image);
			JLabel userName = new JLabel(client.getUserName(curRequest));
			userName.setFont(new Font("Dialog", 1, 20));
			display.add("Center", userName);
			JPanel buttons = new JPanel(new GridLayout(2, 1));
			final JButton accept = new JButton("Accept"), profile = new JButton("Profile");
			buttons.add(accept);
			buttons.add(profile);
			display.add("East", buttons);
			mainScreen.add(display);
			accept.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					client.acceptRequestWith(client.getUserID(curRequest));
					accept.setEnabled(false);
				}
				
			});
			profile.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					client.ui.push(client.ui.userPage = new UserPage(client, client.getUserID(curRequest)));
				}
				
			});
		}
		//back
		back = new JButton("Back");
		JPanel backPanel = new JPanel(new FlowLayout());
		backPanel.add(back);
		backPanel.setPreferredSize(new Dimension(200, 60));
		back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				client.ui.pop();
			}
			
		});
		//join
		this.add("Center", screen);
		this.add("South", backPanel);
		this.setVisible(true);
		this.setSize(UIDisplay.WIDTH, UIDisplay.HEIGHT);
	}

}
