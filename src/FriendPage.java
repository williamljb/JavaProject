import javax.swing.*;


public class FriendPage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Client client;
	
	public FriendPage(Client cli) {
		client = cli;
		//Buttons
		MenuButton menu = new MenuButton(client, 1);
		//join together
		this.add("South", menu);
		this.setVisible(true);
		this.setSize(UIDisplay.WIDTH, UIDisplay.HEIGHT);
	}

}
