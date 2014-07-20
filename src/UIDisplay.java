import javax.swing.*;

public class UIDisplay {
	public static final int WIDTH = 440;
	public static final int HEIGHT = 720;
	JFrame mainFrame;
	Client client;
	LoginPage login;
	MainPage mainPage;
	RegisterPage registerPage;
	JPanel stack[] = new JPanel[10];
	int top = 0;
	
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
		this.mainFrame.setLayout(null);
		this.mainFrame.add(page);
		this.mainFrame.setSize(WIDTH, HEIGHT);
		this.mainFrame.setVisible(true);
		this.mainFrame.repaint();
	}

	private void push(JPanel panel) {stack[top++] = panel;}
	private JPanel pop() {return stack[--top];}

}
