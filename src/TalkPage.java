import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class TalkPage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Client client;
	JButton back, delete, sendPhoto, sendSound, send;
	JLabel name;
	JTextField text;

	public TalkPage(Client cli, final String userID) {
		System.gc();
		client = cli;
		//heading
		JPanel headPanel = new JPanel(new BorderLayout());
		back = new JButton("Back");
		delete = new JButton("Clear");
		name = new JLabel(client.getUserName(client.getUserById(userID)));
		name.setFont(new Font("Dialog", 1, 20));
		name.setHorizontalAlignment(JLabel.CENTER);
		headPanel.add("West", back);
		headPanel.add("Center", name);
		headPanel.add("East", delete);
		headPanel.setPreferredSize(new Dimension(UIDisplay.WIDTH, 50));
		back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				client.ui.pop();
			}
			
		});
		delete.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int ret = JOptionPane.showConfirmDialog(client.ui.mainFrame, 
						"Are you sure to clear all records?", "Confirm", 
						JOptionPane.YES_NO_OPTION);
				if (ret == JOptionPane.NO_OPTION)
					return;
				client.deleteAllRecordWith(userID);
				client.ui.pop();
			}
			
		});
		name.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				client.ui.push(new UserPage(client, userID, true));
			}

			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});
		//messages
		int num = client.getNumberOfMessages(userID);
		JPanel messagePanel = new JPanel(new GridLayout(num < 10 ? 10 : num, 1));
		JScrollPane screen = new JScrollPane(messagePanel);
		screen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//screen.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		screen.setPreferredSize(new Dimension(UIDisplay.WIDTH - 50, UIDisplay.HEIGHT - 50 * 3));
		for (int i = 0; i < num; ++i)
		{
			String sentence = client.getConversation(i);
			boolean me = sentence.startsWith("0");
			sentence = sentence.substring(1);
			JPanel display = new JPanel(new FlowLayout(me ? FlowLayout.RIGHT : FlowLayout.LEFT));
			display.setPreferredSize(new Dimension(UIDisplay.WIDTH - 30, (UIDisplay.HEIGHT - 150) / 10));
			JLabel image = new JLabel(new ImageIcon(client.getUserIcon
					(me ? client.getCurUser() : client.getUserById(userID)).
					getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
			
			JLabel say = new JLabel(sentence);
			say.setOpaque(true);
			say.setPreferredSize(new Dimension(say.getPreferredSize().width + 20, 30));
			say.setBorder(BorderFactory.createRaisedBevelBorder());
			say.setHorizontalAlignment(JLabel.CENTER);
			if (!me)
			{
				say.setBackground(new Color(70, 190, 100));
				display.add(image);
				display.add(say);
			}
			else
			{
				say.setBackground(new Color(220, 220, 220));
				display.add(say);
				display.add(image);
			}
			messagePanel.add(display);
		}
		JScrollBar jscrollBar = screen.getVerticalScrollBar();
		jscrollBar.setValue(jscrollBar.getMaximum());
		jscrollBar.setValue(jscrollBar.getMaximum());
		for (int i = 0; i < 10 - num; ++i)
		{
			JPanel display = new JPanel(new BorderLayout());
			display.setSize(new Dimension(UIDisplay.WIDTH - 30, (UIDisplay.HEIGHT - 150) / 10));
			messagePanel.add(display);
		}
		//operations
		JPanel operationPanel = new JPanel(new FlowLayout());
		operationPanel.setPreferredSize(new Dimension(UIDisplay.WIDTH, 50 * 2));
		JPanel addition = new JPanel(new GridLayout(1, 2));
		sendPhoto = new JButton("Send Photo");
		addition.add(sendPhoto);
		sendSound = new JButton("Send Sound");
		addition.add(sendSound);
		operationPanel.add(addition);
		JPanel origin = new JPanel(new BorderLayout());
		text = new JTextField(20);
		origin.add("Center", text);
		send = new JButton("Send");
		origin.add("East", send);
		operationPanel.add(origin);
		send.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (text.getText().equals(""))
					return;
				client.sendMessage(userID, text.getText());
				text.setText("");
				client.ui.pop();
				client.ui.push(new TalkPage(client, userID));
			}
			
		});
		text.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER)
					(send.getListeners(ActionListener.class))[0].actionPerformed(null);
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
		});
		//join together
		this.setLayout(new BorderLayout());
		this.add("North", headPanel);
		this.add("Center", screen);
		this.add("South", operationPanel);
		this.setSize(UIDisplay.WIDTH, UIDisplay.HEIGHT);
		this.setVisible(true);
	}

}
