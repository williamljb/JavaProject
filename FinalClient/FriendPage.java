import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.*;


public class FriendPage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Client client;
	final static String SEP = File.separator;
	
	public FriendPage(Client cli) {
		client = cli;
		this.setOpaque(false);
		//friend list
		int num = client.getNumberOfFriends();
		JPanel listPanel = new JPanel(new GridLayout(num + 1 < 5 ? 5 : num + 1, 1));
		listPanel.setOpaque(false);
		JScrollPane screen = new JScrollPane(listPanel);
		screen.setPreferredSize(new Dimension(UIDisplay.WIDTH, UIDisplay.HEIGHT - 200));
		screen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		screen.getViewport().setOpaque(false);
		screen.setOpaque(false);
		for (int i = -1; i < num; ++i)
		{
			JPanel display = new JPanel(new BorderLayout());
			display.setOpaque(false);
			display.setSize(UIDisplay.WIDTH, (UIDisplay.HEIGHT - 200) / 5);
			if (i == -1)
			{
				JLabel image = new JLabel(new ImageIcon(new ImageIcon("resources"+SEP+"icons"+SEP+"assist.png").getImage()
						.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
				display.add("West", image);
				JLabel name = new JLabel("Friend Adding Assistant");
				name.setFont(new Font("Dialog", 1, 20));
				display.add("Center", name);
				display.addMouseListener(new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent arg0) {
						client.ui.push(client.ui.friendAddPage = new FriendAddPage(client));
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {}

					@Override
					public void mouseExited(MouseEvent arg0) {}

					@Override
					public void mousePressed(MouseEvent arg0) {}

					@Override
					public void mouseReleased(MouseEvent arg0) {}
				});
			}
			else
			{
				final User curFriend = client.getKthFriend(i);
				JLabel image = new JLabel(new ImageIcon(client.getUserIcon(curFriend.id).getImage()
						.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
				display.add("West", image);
				JLabel name = new JLabel(client.getUserName(curFriend));
				name.setFont(new Font("Dialog", 1, 20));
				display.add("Center", name);
				display.addMouseListener(new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent arg0) {
						client.ui.push(client.ui.userPage = new UserPage(client, client.getUserID(curFriend), false));
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {}

					@Override
					public void mouseExited(MouseEvent arg0) {}

					@Override
					public void mousePressed(MouseEvent arg0) {}

					@Override
					public void mouseReleased(MouseEvent arg0) {}
				});
			}
			listPanel.add(display);
		}
		for (int i = 0; i < 5 - num - 1; ++i)
		{
			JPanel display = new JPanel(new BorderLayout());
			display.setOpaque(false);
			display.setSize(new Dimension(UIDisplay.WIDTH, (UIDisplay.HEIGHT - 200) / 6));
			listPanel.add(display);
		}
		//Buttons
		MenuButton menu = new MenuButton(client, 1);
		//personal info
		PersonalInfo info = new PersonalInfo(client);
		//join together
		this.add("North", info);
		this.add("Center", screen);
		this.add("South", menu);
		this.setVisible(true);
		this.setSize(UIDisplay.WIDTH, UIDisplay.HEIGHT);
	}

}
