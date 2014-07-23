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
		client = cli;
		//heading
		JPanel headPanel = new JPanel(new BorderLayout());
		back = new JButton("Back");
		delete = new JButton("Delete");
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
						"Are you sure to delete all records?", "Confirm", 
						JOptionPane.YES_NO_OPTION);
				if (ret == JOptionPane.NO_OPTION)
					return;
				client.deleteAllRecordWith(userID);
				client.ui.pop();
			}
			
		});
		//messages
		int num = client.getNumberOfMessages(userID);
		JPanel messagePanel = new JPanel(new GridLayout(num, 1));
		JScrollPane screen = new JScrollPane(messagePanel);
		screen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//screen.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		screen.setPreferredSize(new Dimension(UIDisplay.WIDTH - 20, UIDisplay.HEIGHT - 50 * 3));
		for (int i = 0; i < num; ++i)
		{
			String sentence = client.getConversation(client.getCurUser(), client.getUserById(userID), i);
			boolean me = sentence.startsWith("0");
			sentence = sentence.substring(1);
			JPanel display = new JPanel(new FlowLayout(me ? FlowLayout.RIGHT : FlowLayout.LEFT));
			display.setPreferredSize(new Dimension(UIDisplay.WIDTH, (UIDisplay.HEIGHT - 150) / 10));
			JLabel image = new JLabel(new ImageIcon(client.getUserIcon
					(me ? client.getCurUser() : client.getUserById(userID)).
					getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
			
			JLabel say = new JLabel("  " + sentence + "  ");
			say.setOpaque(true);
			say.setSize(new Dimension(0, 50));
			say.setBorder(BorderFactory.createRaisedBevelBorder());
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
		//join together
		this.setLayout(new BorderLayout());
		this.add("North", headPanel);
		this.add("Center", screen);
		this.add("South", operationPanel);
		this.setSize(UIDisplay.WIDTH, UIDisplay.HEIGHT);
		this.setVisible(true);
	}

}
