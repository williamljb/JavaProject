import java.awt.*;

import javax.swing.*;


public class DiscoverPage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Client client;

	public DiscoverPage(Client cli) {
		client = cli;
		//main
		JLabel pending = new JLabel("Pending...");
		pending.setFont(new Font("Dialog", 1, 30));
		pending.setPreferredSize(new Dimension(UIDisplay.WIDTH, UIDisplay.HEIGHT - 200));
		pending.setHorizontalAlignment(JLabel.CENTER);;
		//Buttons
		MenuButton menu = new MenuButton(client, 2);
		//personal info
		PersonalInfo info = new PersonalInfo(client);
		//join together
		this.add("North", info);
		this.add("Center", pending);
		this.add("South", menu);
		this.setVisible(true);
		this.setSize(UIDisplay.WIDTH, UIDisplay.HEIGHT);
		this.setOpaque(false);
	}

}
