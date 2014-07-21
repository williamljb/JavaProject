import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

public class MainPage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Client client;

	public MainPage(Client cli) {
		client = cli;
		int num = client.personNumberTalkedToLocally();
		//MainScreen
		JPanel mainScreen = new JPanel(new GridLayout(num, 1));
		JScrollPane screen = new JScrollPane(mainScreen);
		screen.setPreferredSize(new Dimension(UIDisplay.WIDTH, UIDisplay.HEIGHT - 100));
		screen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		screen.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		for (int i = 0; i < num; ++i)
		{
			JPanel display = new JPanel(new BorderLayout());
			display.setSize(UIDisplay.WIDTH, (UIDisplay.HEIGHT - 100) / 6);
			User curTalked = client.getUserTalked(i);
			JLabel image = new JLabel(new ImageIcon(client.getUserIcon(curTalked).
					getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
			display.add("West", image);
			String userName = client.getUserName(curTalked);
			String lastSentence = client.getConversation(client.getCurUser(), curTalked, 0);
			JLabel nameLabel = new JLabel(userName);
			nameLabel.setFont(new Font("Dialog", 1, 15));
			JPanel centerPanel = new JPanel(new GridLayout(2, 1));
			centerPanel.add(nameLabel);
			centerPanel.add(new JLabel(lastSentence + ""));
			display.add("Center", centerPanel);
			mainScreen.add(display);
			display.addMouseListener(new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent arg0) {
					//TODO
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
		//Buttons
		//TODO
		//join together
		this.add("Center", screen);
		this.setVisible(true);
		this.setSize(UIDisplay.WIDTH, UIDisplay.HEIGHT);
	}

}
