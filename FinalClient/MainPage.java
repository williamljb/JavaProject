import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class MainPage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Client client;
	static Timer timer;
	String lastSentence;

	public MainPage(Client cli) {
		client = cli;
		int num = client.personNumberTalkedToLocally();
		//MainScreen
		JPanel mainScreen = new JPanel();
		mainScreen.setOpaque(false);
		mainScreen.setLayout(new BoxLayout(mainScreen, BoxLayout.Y_AXIS));
		JScrollPane screen = new JScrollPane(mainScreen);
		screen.setPreferredSize(new Dimension(UIDisplay.WIDTH, UIDisplay.HEIGHT - 200));
		screen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		screen.getViewport().setOpaque(false);
		screen.setOpaque(false);
		for (int i = 0; i < num; ++i)
		{
			JPanel display = new JPanel(new BorderLayout());
			display.setOpaque(false);
			display.setPreferredSize(new Dimension(UIDisplay.WIDTH - 30, (UIDisplay.HEIGHT - 200) / 5));
			final User curTalked = client.getUserTalked(i);
			JLabel image = new JLabel(new ImageIcon(client.getUserIcon(curTalked.id).
					getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
			display.add("West", image);
			image.setOpaque(false);
			String userName = client.getUserName(curTalked);
			lastSentence = client.getConversation(client.getCurUser().id, curTalked.id);
			int tmp = TalkPage.isHashCode(lastSentence.substring(1 + 18));
			if (tmp == 1)
				lastSentence = lastSentence.substring(0, 19) + "[image]";
			else
				if (tmp == 2)
					lastSentence = lastSentence.substring(0, 19) + "[sound]";
			if (lastSentence.startsWith("0"))
				lastSentence = "Me : " + lastSentence.substring(1);
			else
				lastSentence = userName + " : " + lastSentence.substring(1);
			final boolean flag;
			if (client.lastUnRead(curTalked.id))
			{
				flag = true;
				lastSentence = "UNREAD! " + lastSentence;
			}
			else
				flag = false;
			JLabel nameLabel = new JLabel(userName);
			nameLabel.setFont(new Font("Dialog", 1, 15));
			JPanel centerPanel = new JPanel(new GridLayout(2, 1));
			centerPanel.setOpaque(false);
			centerPanel.add(nameLabel);
			nameLabel.setOpaque(false);
			JLabel lasts = new JLabel(lastSentence);
			lasts.setOpaque(false);
			centerPanel.add(lasts);
			display.add("Center", centerPanel);
			mainScreen.add(display);
			display.addMouseListener(new MouseListener(){

				boolean b = flag;
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if (b)
						client.unreadMessage.remove(curTalked.id);
					client.ui.push(client.ui.talkPage = new TalkPage(client, client.getUserID(curTalked), -2));
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
		for (int i = 0; i < 5 - num; ++i)
		{
			JPanel display = new JPanel(new BorderLayout());
			display.setOpaque(false);
			display.setPreferredSize(new Dimension(UIDisplay.WIDTH - 30, (UIDisplay.HEIGHT - 200) / 6));
			mainScreen.add(display);
		}
		//Buttons
		MenuButton menu = new MenuButton(client, 0);
		//personal info
		PersonalInfo info = new PersonalInfo(client);
		//join together
		this.add("North", info);
		this.add("Center", screen);
		this.add("South", menu);
		this.setVisible(true);
		this.setSize(UIDisplay.WIDTH, UIDisplay.HEIGHT);
		this.setOpaque(false);

		//timer
		if (timer == null)
		{
			timer = new Timer(1000, new ActionListener(){
				public void actionPerformed(ActionEvent e){
					client.reflush();
				}
			});
			timer.start();
		}
	}

}
