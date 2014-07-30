import java.awt.*;

import javax.swing.*;


public class PersonalInfo extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Client client;
	JLabel image, name, unread, request;
	
	PersonalInfo(Client cli)
	{
		client = cli;

		image = new JLabel(new ImageIcon(client.getUserIcon(client.getCurUser().id).getImage()
				.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		name = new JLabel(client.getCurUser().name);
		name.setFont(new Font("Dialog", 1, 20));
		name.setHorizontalAlignment(JLabel.CENTER);
		if (client.hasUnread())
			unread = new JLabel("New Message!");
		else
			unread = new JLabel("            ");
		if (client.hasRequest())
			request = new JLabel("New Request!");
		else
			request = new JLabel("            ");
		unread.setForeground(Color.RED);
		request.setForeground(Color.RED);
		JPanel grid = new JPanel(new GridLayout(2, 1));
		grid.add(unread);grid.add(request);
		this.setLayout(new BorderLayout());
		this.add("West", image);
		this.add("Center", name);
		this.add("East", grid);
		this.setVisible(true);
		this.setPreferredSize(new Dimension(UIDisplay.WIDTH, 50));
		this.setOpaque(false);
		grid.setOpaque(false);
	}
}
