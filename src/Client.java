import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.*;


public class Client {
	UIDisplay ui;
	Communicator communicator;
	User curUser = new User();
	String LastUserID;
	HashSet<String> set;
	String ids[];
	final static String ipAddress = "166.111.227.148";
	
	public static void main(String args[]) throws Exception
	{
		Client client = new Client();
		client.go();
	}

	private void go() throws Exception {
		BufferedReader tmp = new BufferedReader(new InputStreamReader(new FileInputStream("database/record/LastUser.txt")));
		this.LastUserID = tmp.readLine();
		ui = new UIDisplay(this);
		communicator = new Communicator();
		ui.init();
		tmp.close();
	}

	public ImageIcon getLastUserIcon() {
		return new ImageIcon("database/record/LastUser.jpg");
	}

	public String getLastUserID() {
		return this.LastUserID;
	}

	public static ImageIcon getDefaultUserIcon() {
		return new ImageIcon(new ImageIcon("resources/icons/default.png").
				getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
	}

	public int tryLogin(String userID, String password) {
		//0 for ServerNotFound, 1 for IDNotFound, 2 for IncorrectPassword, -1 for succeed
		int ret = 0;
		try {
			ret = this.communicator.login(userID, password);
			if (ret == -1)
				this.communicator.getUser(userID, curUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("@Client.java, line 46 : " + curUser.id);
		return ret;
	}

	public int createNewAccount(String id, String name, String password, String imagePath) {
		//0 for ServerNotFound, 1 for ID exists, -1 for succeed
		int ret = -1;
		try {
			ret = this.communicator.create(id, name, password, imagePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ret == -1)
			curUser = new User(id, name, password, "image");
		return ret;
	}

	public int personNumberTalkedToLocally() {
		// TODO Auto-generated method stub
		return 20;
	}

	public User getUserTalked(int order) {
		// TODO Auto-generated method stub
		//get the last order-th user that talked with current user
		return new User();
	}

	public ImageIcon getUserIcon(User curUser) {
		String path = "database/" + curUser.id + ".jpg";
		if (new File(path).exists())
		{
			System.out.println(path);
			return new ImageIcon(path);
		}
		else
			try {
				if (this.communicator.getUser(curUser.id, curUser) == -1)
					return new ImageIcon(path);
				else
					return Client.getDefaultUserIcon();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return Client.getDefaultUserIcon();
	}

	public String getUserName(User curUser) {
		return curUser.name;
	}

	public User getCurUser() {
		// TODO Auto-generated method stub
		return curUser;
	}

	public String getConversation(User me, User friend, int order) {
		// TODO Auto-generated method stub
		//returns the last order-th sentence that User me and friend had talked
		//if the required sentence is spoken by me, add a zero in the front, else add a one
		return "1I am speaking";
	}

	public String getPasswordOfUser(User curUser) {
		return curUser.password;
	}

	public int editAccount(String id, String name, String password, String imagePath) {
		//0 for ServerNotFound, -1 for succeed
		int ret = -1;
		try {
			ret = this.communicator.edit(id, name, password, imagePath);
			if (ret == -1)
				this.communicator.getUser(id, curUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public String getUserID(User curUser) {
		return curUser.id;
	}

	public int getNumberOfFriends() {
		// TODO Auto-generated method stub
		return 10;
	}

	public int findUserID(String userID) {
		//0 for ServerNotFound, 1 for NoSuchID, -1 for succeed
		int ret = 0;
		try {
			ret = this.communicator.idExists(userID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public void sendFriendRequestTo(String userID) {
		try {
			this.communicator.sendRequest(this.curUser.id, userID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getNumberOfRequest() {
		set = new HashSet<String>();
		try {
			this.communicator.request(set, curUser.id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ids = set.toArray(new String[set.size()]);
		return set.size();
	}

	public User getUserOfRequest(int i) {
		User user = new User();
		try {
			this.communicator.getUser(ids[i], user);
			//System.out.println(user.id + " " + user.name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	public void acceptRequestWith(String userID) {
		try {
			this.communicator.acceptRequest(userID, curUser.id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public User getKthFriend(int i) {
		// TODO Auto-generated method stub
		return new User();
	}

	public User getUserById(String userID) {
		// TODO Auto-generated method stub
		User user = new User();
		try {
			this.communicator.getUser(userID, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public boolean isFriend(String userID) {
		// TODO Auto-generated method stub
		return false;
	}

	public void deleteFriend(String userID) {
		// TODO Auto-generated method stub
		
	}

	public void deleteAllRecordWith(String userID) {
		// TODO Auto-generated method stub
		
	}

	public int getNumberOfMessages(String userID) {
		// TODO Auto-generated method stub
		return 20;
	}

	public void logout() {
		try {
			this.LastUserID = curUser.id;
			System.out.println(this.LastUserID);
			communicator.logout(curUser.id);
			communicator.savePic("database/" + curUser.id + ".jpg", "database/record/LastUser.jpg");
			OutputStreamWriter tmp = new OutputStreamWriter(new FileOutputStream("database/record/LastUser.txt"));
			tmp.write(this.LastUserID + "\n");
			tmp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
