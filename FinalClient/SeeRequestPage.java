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
		this.setOpaque(false);
		client = cli;
		this.setLayout(new BorderLayout());
		//list
		int num = client.getNumberOfRequest();
		JPanel mainScreen = new JPanel(new GridLayout(num < 6 ? 6 : num, 1));
		mainScreen.setOpaque(false);
		JScrollPane screen = new JScrollPane(mainScreen);
		screen.setPreferredSize(new Dimension(UIDisplay.WIDTH, UIDisplay.HEIGHT - 200));
		screen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		screen.getViewport().setOpaque(false);
		screen.setOpaque(false);
		//System.out.println(num);
		for (int i = 0; i < num; ++i)
		{
			//System.out.println("in showing page");
			JPanel display = new JPanel(new BorderLayout());
			display.setOpaque(false);
			display.setSize(new Dimension(UIDisplay.WIDTH, (UIDisplay.HEIGHT - 150) / 6));
			curRequest = client.getUserOfRequest(i);
			JLabel image = new JLabel(new ImageIcon(client.getUserIcon(curRequest.id).
					getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
			image.setBounds(0, 0, 100, 100);
			//System.out.println(image.getWidth() + " " + image.getHeight());
			display.add("West", image);
			JLabel userName = new JLabel(client.getUserName(curRequest));
			userName.setFont(new Font("Dialog", 1, 20));
			display.add("Center", userName);
			JPanel buttons = new JPanel(new GridLayout(3, 1));
			buttons.setOpaque(false);
			final JButton accept = new JButton("Accept"), decline = new JButton("Decline"), 
					profile = new JButton("Profile");
			buttons.add(accept);
			buttons.add(decline);
			buttons.add(profile);
			display.add("East", buttons);
			mainScreen.add(display);
			final int order = i;
			accept.addActionListener(new ActionListener(){
				int number = order;
				@Override
				public void actionPerformed(ActionEvent e) {
					client.acceptRequestWith(client.requestids[number]);
					accept.setEnabled(false);
					decline.setEnabled(false);
				}
				
			});
			decline.addActionListener(new ActionListener(){
				int number = order;
				@Override
				public void actionPerformed(ActionEvent e) {
					client.declineRequestWith(client.requestids[number]);
					accept.setEnabled(false);
					decline.setEnabled(false);
				}
				
			});
			profile.addActionListener(new ActionListener(){
				int number = order;
				@Override
				public void actionPerformed(ActionEvent e) {
					client.ui.push(client.ui.userPage = new UserPage(client, client.requestids[number], false));
				}
				
			});
		}
		for (int i = 0; i < 6 - num; ++i)
		{
			JPanel display = new JPanel(new BorderLayout());
			display.setOpaque(false);
			display.setSize(new Dimension(UIDisplay.WIDTH, (UIDisplay.HEIGHT - 150) / 6));
			mainScreen.add(display);
		}
		//back
		back = new JButton("Back");
		JPanel backPanel = new JPanel(new FlowLayout());
		backPanel.setOpaque(false);
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
