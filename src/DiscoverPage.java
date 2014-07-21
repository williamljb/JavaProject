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
		pending.setPreferredSize(new Dimension(UIDisplay.WIDTH, UIDisplay.HEIGHT - 150));
		pending.setHorizontalAlignment(JLabel.CENTER);;
		//Buttons
		MenuButton menu = new MenuButton(client, 2);
		//join together
		this.add("Center", pending);
		this.add("South", menu);
		this.setVisible(true);
		this.setSize(UIDisplay.WIDTH, UIDisplay.HEIGHT);
	}

}
