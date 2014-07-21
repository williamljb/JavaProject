import java.awt.*;

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

	public static ImageIcon getDefaultUserIcon() {
		// TODO Auto-generated method stub
		return new ImageIcon("resources/icons/2.png");
	}

	public int tryLogin(String userID, String password) {
		// TODO Auto-generated method stub
		//0 for ServerNotFound, 1 for IDNotFound, 2 for IncorrectPassword, -1 for succeed
		return -1;
	}

	public int createNewAccount(String id, String name, String password, ImageIcon image) {
		// TODO Auto-generated method stub
		//0 for ServerNotFound, 1 for ID exists, -1 for succeed
		return -1;
	}

	public int personNumberTalkedToLocally() {
		// TODO Auto-generated method stub
		return 20;
	}

	public User getUserTalked(int i) {
		// TODO Auto-generated method stub
		return new User();
	}

	public ImageIcon getUserIcon(User curUser) {
		// TODO Auto-generated method stub
		return getDefaultUserIcon();
	}

	public String getUserName(User curUser) {
		// TODO Auto-generated method stub
		return "Default";
	}

	public User getCurUser() {
		// TODO Auto-generated method stub
		return new User();
	}

	public String getConversation(User me, User friend, int order) {
		// TODO Auto-generated method stub
		//returns the last order-th sentence that User me and friend had talked
		//if the required sentence is spoken by me, add a zero in the front, else add a one
		return "0I am speaking";
	}
}
