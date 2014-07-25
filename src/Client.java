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
	ArrayList<String> friends, records;
	final static String ipAddress = //"127.0.0.1";
									"166.111.227.148";
	
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
		//System.out.println("@Client.java, line 46 : " + curUser.id);
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
		String path = System.getProperty("user.dir") + "/database/" + curUser.id + ".jpg";
		//System.out.println(path);
		if (new File(path).exists())
		{
			//System.out.println("here");
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

	public String getConversation(int order) {
		//returns the last order-th sentence that User me and friend had talked
		//if the required sentence is spoken by me, add a zero in the front, else add a one
		//it is already stored in records
		return this.records.get(order);
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

	public int getNumberOfFriends() {
		this.friends = new ArrayList<String>();
		try {
			this.communicator.friend(friends, curUser.id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(friends);
		ids = friends.toArray(new String[friends.size()]);
		return friends.size();
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

	public User getUserById(String userID) {
		User user = new User();
		try {
			this.communicator.getUser(userID, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public boolean isFriend(String userID) {
		int n = this.getNumberOfFriends();
		for (int i = 0; i < n; ++i)
			if (ids[i].equals(userID))
				return true;
		return false;
	}

	public void deleteFriend(String userID) {
		try {
			this.communicator.deleteFriend(curUser.id, userID);
			//System.out.println("ok");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteAllRecordWith(String userID) {
		// TODO Auto-generated method stub
		
	}

	public int getNumberOfMessages(String userID) {
		records = new ArrayList<String>();
		if (!new File("database" + File.separator + curUser.id + File.separator + userID + ".txt").exists())
			return 0;
		try {
			FileReader fw = new FileReader("database" + File.separator + curUser.id + File.separator + userID + ".txt");
			String buffer;
			BufferedReader in = new BufferedReader(fw);
			while ((buffer = in.readLine()) != null)
				records.add(buffer);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return records.size();
	}

	public void logout() {
		try {
			this.LastUserID = curUser.id;
			//System.out.println(this.LastUserID);
			communicator.logout(curUser.id);
			communicator.savePic("database/" + curUser.id + ".jpg", "database/record/LastUser.jpg");
			OutputStreamWriter tmp = new OutputStreamWriter(new FileOutputStream("database/record/LastUser.txt"));
			tmp.write(this.LastUserID + "\n");
			tmp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String userID, String text) {
		try {
			this.communicator.sendMessage(curUser.id, userID, text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		addMessage(curUser.id, userID, text, true);
		//addMessage(userID, curUser.id, text, false);
	}

	private void addMessage(String aID, String bID, String text, boolean aToB) {
		new File("database" + File.separator + aID).mkdirs();
		try {
			FileWriter fw = new FileWriter("database" + File.separator + aID + File.separator + bID + ".txt", true);
			fw.write((aToB ? 0 : 1) + text + "\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getConversation(User aID, User bID) {
		String ans = "0";
		if (!new File("database" + File.separator + aID + File.separator + bID + ".txt").exists())
			return ans;
		try {
			FileReader fw = new FileReader("database" + File.separator + aID + File.separator + bID + ".txt");
			String buffer;
			BufferedReader in = new BufferedReader(fw);
			while ((buffer = in.readLine()) != null)
				ans = buffer.intern();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ans;
	}
}
