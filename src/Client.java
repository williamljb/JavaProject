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
	ArrayList<String> friends, records, talkingQueue;
	String[] requestids;
	static String ipAddress;//"127.0.0.1";
									//"166.111.227.148";
	final static String SEP = File.separator;
	HashSet<String> unreadMessage;
	
	public static void main(String args[]) throws Exception
	{
		BufferedReader tmp = new BufferedReader(new InputStreamReader(new FileInputStream("database"+SEP+"record"+SEP+"config.txt")));
		ipAddress = tmp.readLine();
		tmp.close();
		Client client = new Client();
		client.go();
	}

	private void go() throws Exception {
		unreadMessage = new HashSet<String>();
		try {
		BufferedReader tmp = new BufferedReader(new InputStreamReader(new FileInputStream("database"+SEP+"record"+SEP+"LastUser.txt")));
		this.LastUserID = tmp.readLine();
		tmp.close();
		} catch (FileNotFoundException e) {
			this.LastUserID = "";
		}
		ui = new UIDisplay(this);
		communicator = new Communicator();
		ui.init();
	}

	public ImageIcon getLastUserIcon() {
		if (new File("database"+SEP+"record"+SEP+"LastUser.jpg").exists())
			return new ImageIcon("database"+SEP+"record"+SEP+"LastUser.jpg");
		else
			return Client.getDefaultUserIcon();
	}

	public String getLastUserID() {
		return this.LastUserID;
	}

	public static ImageIcon getDefaultUserIcon() {
		return new ImageIcon(new ImageIcon("resources"+SEP+"icons"+SEP+"default.png").
				getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
	}

	public int tryLogin(String userID, String password) {
		//0 for ServerNotFound, 1 for IDNotFound, 2 for IncorrectPassword, -1 for succeed
		int ret = 0;
		try {
			ret = this.communicator.login(userID, password);
			if (ret == -1)
			{
				this.communicator.getUser(userID, curUser);
				if (!new File("database" + SEP + curUser.id).exists())
					new File("database" + SEP + curUser.id).mkdirs();
				for (File f : new File("database" + SEP + curUser.id).listFiles())
					if (f.isFile())
					{
						String name = f.getName();
						if (name.substring(name.indexOf(".") + 1).equals("last"))
							continue;
						name = name.substring(0, name.indexOf("."));
						if (this.lastUnRead(name))
						{
							System.out.println("clinet 75 " + name);
							this.unreadMessage.add(name);
						}
					}
			}
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
		int num = this.getNumberOfFriends(), ans = 0;
		this.talkingQueue = new ArrayList<String>();
		for (String s : this.friends)
			this.talkingQueue.add(s.intern());
		for (int i = num - 1; i >= 0; --i)
			if (haveTalked(curUser.id, this.talkingQueue.get(i)))
			{
				String s = this.talkingQueue.get(i).intern();
				this.talkingQueue.remove(i);
				this.talkingQueue.add(s);
				++ans;
			}
		return ans;
	}

	boolean haveTalked(String aID, String bID) {
		return new File("database" + File.separator + aID + File.separator + bID + ".txt").exists();
	}

	public User getUserTalked(int order) {
		//get the last order-th user that talked with current user
		User user = new User();
		try {
			this.communicator.getUser(this.talkingQueue.get(this.talkingQueue.size() - 1 - order), user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public ImageIcon getUserIcon(String id) {
		String path = "database"+SEP + id + ".jpg";
		//System.out.println(path);
		if (new File(path).exists())
		{
			//System.out.println("here");
			return new ImageIcon(path);
		}
		else return Client.getDefaultUserIcon();
	}

	public String getUserName(User curUser) {
		return curUser.name;
	}

	public User getCurUser() {
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
		requestids = set.toArray(new String[set.size()]);
		return set.size();
	}

	public User getUserOfRequest(int i) {
		User user = new User();
		try {
			this.communicator.getUser(requestids[i], user);
			//System.out.println(user.id + " " + user.name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public void acceptRequestWith(String userID) {
		try {
			this.communicator.acceptRequest(userID, curUser.id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void declineRequestWith(String userID) {
		try {
			this.communicator.declineRequest(userID, curUser.id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public User getKthFriend(int i) {
		User user = new User();
		try {
			this.communicator.getUser(ids[i], user);
			//System.out.println(user.id + " " + user.name);
		} catch (Exception e) {
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
		File del = new File("database" + File.separator + curUser.id + File.separator + userID + ".txt");
		del.delete();
	}

	public int getNumberOfMessages(String userID) {
		records = new ArrayList<String>();
		new File("database" + File.separator + curUser.id).mkdirs();
		try {
			if (!new File("database" + File.separator + curUser.id + File.separator + userID + ".txt").exists())
				return 0;
			FileReader fw = new FileReader("database" + File.separator + curUser.id + File.separator + userID + ".txt");
			String buffer;
			BufferedReader in = new BufferedReader(fw);
			while ((buffer = in.readLine()) != null)
			{
				//System.out.println(buffer);
				records.add(buffer);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return records.size();
	}

	public void logout() {
		try {
			this.unreadMessage.removeAll(unreadMessage);
			this.LastUserID = curUser.id;
			//System.out.println(this.LastUserID);
			communicator.logout(curUser.id);
			Communicator.savePic("database"+SEP + curUser.id + ".jpg", "database"+SEP+"record"+SEP+"LastUser.jpg");
			OutputStreamWriter tmp = new OutputStreamWriter(new FileOutputStream("database"+SEP+"record"+SEP+"LastUser.txt"));
			tmp.write(this.LastUserID + "\n");
			tmp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String userID, String text) {
		Calendar cal = new GregorianCalendar();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int miniute = cal.get(Calendar.MINUTE);
		String add = String.valueOf(miniute); if (miniute < 10) add = "0" + add;
		text = ":" + add + "]" + text;
		add = String.valueOf(hour); if (hour < 10) add = "0" + add;
		text = " " + add + text;
		int tmp = cal.get(Calendar.DATE); add = String.valueOf(tmp); if(tmp < 10) add = "0" + add;
		text = "-" + add + text;
		tmp = cal.get(Calendar.MONTH); add = String.valueOf(tmp); if(tmp < 10) add = "0" + add;
		text = "-" + add + text;
		tmp = cal.get(Calendar.YEAR); add = String.valueOf(tmp);
		text = "[" + add + text;
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

	public String getConversation(String aID, String bID) {
		String ans = "1";
		if (!new File("database" + File.separator + aID + File.separator + bID + ".txt").exists())
			return ans;
		try {
			//System.out.println("ok");
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

	public int getLastRead(String userID, int last, int reflush) {
		String ans = "-1";
		try {
			if (reflush == -2 && new File("database" + File.separator + curUser.id + File.separator + userID + ".last").exists())
			{
				FileReader fr = new FileReader("database" + File.separator + curUser.id + File.separator + userID + ".last");
				BufferedReader in = new BufferedReader(fr);
				ans = in.readLine();
				in.close();
			}
			{
				new File("database" + File.separator + curUser.id).mkdirs();
				FileWriter fw = new FileWriter("database" + File.separator + curUser.id + File.separator + userID + ".last");
				fw.write(last - 1 + "\n");
				fw.close();
			}
			if (reflush != -2)
				return reflush;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.parseInt(ans);
	}

	public boolean getUnread(String userID) {
		String ret;
		try {
			ret = this.communicator.getUnread(curUser.id, userID);
			if (ret.equals(""))
				return false;
			//System.out.println(ret);
			new File("database" + File.separator + curUser.id).mkdirs();
			FileWriter fw = new FileWriter("database" + File.separator + curUser.id + File.separator + userID + ".txt", true);
			for (String s : ret.split("\n"))
				fw.write(s + "\n");
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void reflush() {
		boolean flag = false;
		this.getNumberOfFriends();
		for (String s : this.friends)
			if (this.getUnread(s))
			{
				flag = true;
				this.unreadMessage.add(s);
				if (ui.top > 0 && ui.stack[ui.top - 1] instanceof TalkPage)
					this.unreadMessage.remove(((TalkPage)ui.stack[ui.top - 1]).ID);
			}
		if (ui.top > 0 && ui.stack[ui.top - 1] instanceof FriendPage)
		{
			ui.setPage(ui.friendPage = new FriendPage(this));
		}
		if (ui.top > 0 && ui.stack[ui.top - 1] instanceof DiscoverPage)
		{
			ui.setPage(ui.discoverPage = new DiscoverPage(this));
		}
		if (ui.top > 0 && ui.stack[ui.top - 1] instanceof UserPage)
		{
			//System.out.println("hihi");
			ui.pop();
			ui.push(ui.userPage = new UserPage(this, ((UserPage)ui.stack[ui.top]).userID, ((UserPage)ui.stack[ui.top]).shouldBack));
		}
		if (ui.top > 0 && ui.stack[ui.top - 1] instanceof MainPage)
		{
			//System.out.println("hihi");
			ui.setPage(new MainPage(this));
		}
		if (flag)
		{
			if (ui.top > 0 && ui.stack[ui.top - 1] instanceof TalkPage)
			{
				//System.out.println("hoho");
				ui.pop();
				ui.push(ui.talkPage = new TalkPage(this, ((TalkPage)ui.stack[ui.top]).ID, ((TalkPage)ui.stack[ui.top]).lastRead));
			}
		}
	}

	public void download(String userID) {
		String ret;
		try {
			ret = this.communicator.download(curUser.id, userID);
			new File("database" + File.separator + curUser.id).mkdirs();
			FileWriter fw = new FileWriter("database" + File.separator + curUser.id + File.separator + userID + ".txt");
			if (ret.equals(""))
			{
				fw.close();
				return;
			}
			for (String s : ret.split("\n"))
				fw.write(s + "\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean lastUnRead(String userID) {
		try {
			FileReader fl = new FileReader("database" + File.separator + curUser.id + File.separator + userID + ".last");
			FileReader fr = new FileReader("database" + File.separator + curUser.id + File.separator + userID + ".txt");
			BufferedReader in = new BufferedReader(fl);
			String ans = in.readLine();
			int last = Integer.parseInt(ans);
			in.close();
			in = new BufferedReader(fr);
			while ((ans = in.readLine()) != null)
				--last;
			in.close();
			//System.out.println("client 444 : " + last);
			return last != -1;
		} catch (FileNotFoundException e) {
			return true;
		} catch (IOException e) {
			return true;
		}
	}

	public boolean hasUnread() {
		return this.unreadMessage.size() != 0;
	}

	public boolean hasRequest() {
		return this.getNumberOfRequest() != 0;
	}

}
