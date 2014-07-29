import java.awt.event.*;
import java.io.File;

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
		this.mainFrame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				client.logout();
			}
		});
	    //mainFrame.add(imgLabel);
	    //mainFrame.pack();
		this.setPage(login = new LoginPage(this.client));
	}

	void setPage(JPanel page) {
		while (top != 0)
		{
			this.mainFrame.remove(stack[--top]);
		}
		stack[top = 0] = page;
		top++;
		displayPage(page);
	}

	void displayPage(JPanel page) {
		this.mainFrame.setLayout(null);
		
	    ImageIcon img = new ImageIcon("resources" + File.separator + "background.jpg"); 
	    JLabel imgLabel = new JLabel(img);  
	    mainFrame.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
	    imgLabel.setBounds(0,0,img.getIconWidth(), img.getIconHeight()); 
	    ((JPanel)mainFrame.getContentPane()).setOpaque(false);
	    
		this.mainFrame.add(page);
		this.mainFrame.setSize(WIDTH, HEIGHT);
		this.mainFrame.setVisible(true);
		this.mainFrame.repaint();
		++cnt;
		if (cnt % 2 == 0)
			System.gc();
	}

	void push(JPanel panel) {
		if (top > 0) this.mainFrame.remove(stack[top - 1]);
		stack[top++] = panel;
		displayPage(panel);
	}
	
	void pop() {
		if (top > 0) this.mainFrame.remove(stack[top - 1]);
		if (top > 1) displayPage(stack[top - 2]);
		--top;
	}

}
