import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class MainPage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Client client;
	Timer timer;

	public MainPage(Client cli) {
		client = cli;
		int num = client.personNumberTalkedToLocally();
		//MainScreen
		JPanel mainScreen = new JPanel();
		mainScreen.setLayout(new BoxLayout(mainScreen, BoxLayout.Y_AXIS));
		JScrollPane screen = new JScrollPane(mainScreen);
		screen.setPreferredSize(new Dimension(UIDisplay.WIDTH, UIDisplay.HEIGHT - 150));
		screen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		for (int i = 0; i < num; ++i)
		{
			JPanel display = new JPanel(new BorderLayout());
			display.setPreferredSize(new Dimension(UIDisplay.WIDTH - 30, (UIDisplay.HEIGHT - 150) / 6));
			final User curTalked = client.getUserTalked(i);
			JLabel image = new JLabel(new ImageIcon(client.getUserIcon(curTalked).
					getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
			display.add("West", image);
			String userName = client.getUserName(curTalked);
			String lastSentence = client.getConversation(client.getCurUser().id, curTalked.id);
			if (lastSentence.startsWith("0"))
				lastSentence = "Me : " + lastSentence.substring(1);
			else
				lastSentence = userName + " : " + lastSentence.substring(1);
			JLabel nameLabel = new JLabel(userName);
			nameLabel.setFont(new Font("Dialog", 1, 15));
			JPanel centerPanel = new JPanel(new GridLayout(2, 1));
			centerPanel.add(nameLabel);
			centerPanel.add(new JLabel(lastSentence));
			display.add("Center", centerPanel);
			mainScreen.add(display);
			display.addMouseListener(new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent arg0) {
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
		for (int i = 0; i < 6 - num; ++i)
		{
			JPanel display = new JPanel(new BorderLayout());
			display.setPreferredSize(new Dimension(UIDisplay.WIDTH - 30, (UIDisplay.HEIGHT - 150) / 6));
			mainScreen.add(display);
		}
		//Buttons
		MenuButton menu = new MenuButton(client, 0);
		//join together
		this.add("Center", screen);
		this.add("South", menu);
		this.setVisible(true);
		this.setSize(UIDisplay.WIDTH, UIDisplay.HEIGHT);
		//timer
		timer = new Timer(1000, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				client.reflush();
			}
		});
		timer.start();
	}

}
