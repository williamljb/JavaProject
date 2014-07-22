import javax.swing.*;

public class UIDisplay {
	public static final int WIDTH = 440;
	public static final int HEIGHT = 720;
	JPanel stack[] = new JPanel[10];
	int top = 0;
	int cnt = 0;
	JFrame mainFrame;
	Client client;
	LoginPage login;
	MainPage mainPage;
	RegisterPage registerPage;
	FriendPage friendPage;
	DiscoverPage discoverPage;
	MyPage myPage;
	FriendAddPage friendAddPage;
	SeeRequestPage seeRequestPage;
	UserPage userPage;
	TalkPage talkPage;
	
	public UIDisplay(Client client) {
		this.client = client;
	}

	public void init() {
		mainFrame = new JFrame();
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPage(login = new LoginPage(this.client));
	}

	void setPage(JPanel page) {
		while (top != 0)
			this.mainFrame.remove(stack[--top]);
		stack[top = 0] = page;
		top++;
		displayPage(page);
	}

	private void displayPage(JPanel page) {
		this.mainFrame.setLayout(null);
		this.mainFrame.add(page);
		this.mainFrame.setSize(WIDTH, HEIGHT);
		this.mainFrame.setVisible(true);
		this.mainFrame.repaint();
		++cnt;
		if (cnt % 20 == 0)
			System.gc();
	}

	void push(JPanel panel) {
		this.mainFrame.remove(stack[top - 1]);
		stack[top++] = panel;
		if (stack[top - 1] instanceof FriendPage)
			stack[top - 1] = new FriendPage(client);
		displayPage(panel);
	}
	void pop() {
		this.mainFrame.remove(stack[top - 1]);
		if (stack[top - 2] instanceof FriendPage)
			stack[top - 2] = new FriendPage(client);
		displayPage(stack[top - 2]);
		--top;
	}

}
