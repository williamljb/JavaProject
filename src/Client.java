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

	public User getUserTalked(int order) {
		// TODO Auto-generated method stub
		//get the last order-th user that talked with current user
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
		return "1I am speaking";
	}

	public String getPasswordOfUser(User curUser) {
		// TODO Auto-generated method stub
		return "";
	}

	public int editAccount(String id, String newName, String newPassword, ImageIcon newImage) {
		// TODO Auto-generated method stub
		//0 for ServerNotFound, 1 for ID exists, -1 for succeed
		System.out.println(newPassword);
		return -1;
	}

	public String getUserID(User curUser) {
		// TODO Auto-generated method stub
		return "heyhey";
	}

	public int getNumberOfFriends() {
		// TODO Auto-generated method stub
		return 10;
	}

	public int findUserID(String userID) {
		// TODO Auto-generated method stub
		//0 for ServerNotFound, 1 for NoSuchID, -1 for succeed
		return -1;
	}

	public void sendFriendRequestTo(String userID) {
		// TODO Auto-generated method stub
		
	}

	public int getNumberOfRequest() {
		// TODO Auto-generated method stub
		return 10;
	}

	public User getUserOfRequest(int i) {
		// TODO Auto-generated method stub
		return new User();
	}

	public void acceptRequestWith(String userID) {
		// TODO Auto-generated method stub
		
	}

	public User getKthFriend(int i) {
		// TODO Auto-generated method stub
		return new User();
	}

	public User getUserById(String userID) {
		// TODO Auto-generated method stub
		return new User();
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
}
