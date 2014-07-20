import java.awt.Image;

import javax.swing.*;


public class Client {
	UIDisplay ui;
	
	public static void main(String args[])
	{
		Client client = new Client();
		client.go();
	}

	private void go() {
		ui = new UIDisplay(this);
		ui.init();
	}

	public ImageIcon getLastUserIcon() {
		// TODO Auto-generated method stub
		return new ImageIcon("resources/icons/1.png");
	}

	public String getLastUserID() {
		// TODO Auto-generated method stub
		return "";
	}

	public ImageIcon getDefaultUserIcon() {
		// TODO Auto-generated method stub
		return new ImageIcon("resources/icons/2.png");
	}

	public int tryLogin(String userID, String password) {
		// TODO Auto-generated method stub
		//0 for ServerNotFound, 1 for IDNotFound, 2 for IncorrectPassword, -1 for succeed
		return -1;
	}

	public int createNewAccount(String id, String name, String passwor, Icon image) {
		// TODO Auto-generated method stub
		//0 for ServerNotFound, 1 for ID exists, -1 for succeed
		return -1;
	}
}
