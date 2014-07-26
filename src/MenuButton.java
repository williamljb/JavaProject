import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class MenuButton extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Client client;
	JButton mainButton = new JButton(), friendButton = new JButton(), 
			discoverButton = new JButton(), myButton = new JButton();

	public MenuButton(Client cli, int i) {
		client = cli;
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(UIDisplay.WIDTH, 150));
		//main
		setButton("resources/icons/main.png", mainButton, i == 0);
		mainButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {client.ui.setPage(new MainPage(client));}
		});
		//friend
		setButton("resources/icons/friend.png", friendButton, i == 1);
		friendButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {client.ui.setPage(client.ui.friendPage = new FriendPage(client));}
		});
		//discover
		setButton("resources/icons/discover.png", discoverButton, i == 2);
		discoverButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {client.ui.setPage(client.ui.discoverPage = new DiscoverPage(client));}
		});
		//my
		setButton("resources/icons/my.png", myButton, i == 3);
		myButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {client.ui.setPage(client.ui.myPage = new MyPage(client));}
		});
		//join together
		this.add(mainButton);
		this.add(friendButton);
		this.add(discoverButton);
		this.add(myButton);
		this.setVisible(true);
	}

	private void setButton(String path, JButton button, boolean b) {
		ImageIcon image = new ImageIcon(path);
		button.setIcon(new ImageIcon(image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		if (b)
		{
			button.setBorder(BorderFactory.createLoweredBevelBorder());
			button.setEnabled(false);
		}
		else
		{
			button.setBorder(BorderFactory.createRaisedBevelBorder());
			button.setEnabled(true);
		}
	}

}
