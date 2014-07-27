import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class TalkPage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Client client;
	JButton back, delete, sendPhoto, sendSound, send;
	JLabel name;
	JTextField text;
	String ID, sentence;
	int lastRead;
	
	AudioFormat af = null;
	TargetDataLine td = null;
	SourceDataLine sd = null;
	ByteArrayInputStream bais = null;
	ByteArrayOutputStream baos = null;
	AudioInputStream ais = null;
	Boolean stopflag = false;

	public TalkPage(Client cli, final String userID, int reflush) {
		this.ID = userID;
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
				client.ui.push(new TalkPage(client, userID, -2));
			}
			
		});
		name.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				client.ui.push(new UserPage(client, userID, true));
			}
		});
		//messages
		int num = client.getNumberOfMessages(userID);
		lastRead = client.getLastRead(userID, num, reflush);
		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
		JScrollPane screen = new JScrollPane(messagePanel);
		screen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//screen.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		screen.setPreferredSize(new Dimension(UIDisplay.WIDTH - 50, UIDisplay.HEIGHT - 50 * 3));
		int end = 9 - num;
		for (int i = 0; i < num; ++i)
		{
			sentence = client.getConversation(i);
			boolean me = sentence.startsWith("0");
			sentence = sentence.substring(1);
			JPanel display = new JPanel(new FlowLayout(me ? FlowLayout.RIGHT : FlowLayout.LEFT));
			display.setSize(new Dimension(UIDisplay.WIDTH - 30, (UIDisplay.HEIGHT - 150) / 10));
			JLabel image = new JLabel(new ImageIcon(client.getUserIcon
					(me ? client.getCurUser() : client.getUserById(userID)).
					getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
			
			int tmp = isHashCode(sentence);
			if (tmp == 1)
			{
				--end;
				final ImageIcon icon = new ImageIcon("database" + File.separator + sentence);
				JLabel label = new JLabel(new ImageIcon(icon.getImage()
						.getScaledInstance(100*icon.getIconWidth()/icon.getIconHeight(), 100, Image.SCALE_SMOOTH)));
				label.setPreferredSize(new Dimension(100, 100));

				label.setOpaque(true);
				label.setBorder(BorderFactory.createRaisedBevelBorder());
				if (!me)
				{
					label.setBackground(new Color(110, 210, 110));
					display.add(image);
					display.add(label);
				}
				else
				{
					label.setBackground(new Color(220, 220, 220));
					display.add(label);
					display.add(image);
				}
				label.addMouseListener(new MouseAdapter(){

					@Override
					public void mouseClicked(MouseEvent e) {
						JFrame frame = new JFrame();
						frame.setSize(icon.getIconWidth(), icon.getIconHeight());
						frame.add(new JLabel(icon));
						frame.setVisible(true);
						frame.setLocationRelativeTo(null);
					}
				});
			}
			else
				if (tmp == 2)
				{
					final ImageIcon icon = new ImageIcon("resources" + File.separator + "a.png");
					JLabel label = new JLabel(new ImageIcon(icon.getImage().getScaledInstance
							(30, 30, Image.SCALE_SMOOTH)));
					label.setPreferredSize(new Dimension(50, 50));
					label.setHorizontalAlignment(JLabel.CENTER);
					label.setVerticalAlignment(JLabel.CENTER);
					label.setOpaque(true);
					label.setBorder(BorderFactory.createRaisedBevelBorder());
					if (!me)
					{
						label.setBackground(new Color(110, 210, 110));
						display.add(image);
						display.add(label);
					}
					else
					{
						label.setBackground(new Color(220, 220, 220));
						display.add(label);
						display.add(image);
					}
					label.addMouseListener(new MouseAdapter(){

						String add = sentence;
						@Override
						public void mouseClicked(MouseEvent e) {
							play(add);
						}
					});
				}
				else
			{
				JTextArea say = new JTextArea(sentence);
				if (sentence.length() > 30)
				{
					say.setLineWrap(true);
					say.setSize(new Dimension(200, 30));
				}
				say.setOpaque(true);
				say.setBorder(BorderFactory.createRaisedBevelBorder());
				say.setEditable(false);
				if (!me)
				{
					say.setBackground(new Color(110, 210, 110));
					display.add(image);
					display.add(say);
				}
				else
				{
					say.setBackground(new Color(220, 220, 220));
					display.add(say);
					display.add(image);
				}
			}
			messagePanel.add(display);
			if (i == lastRead)
			{
				JPanel display2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
				JLabel line = new JLabel("---------------Above are history records---------------");
				line.setSize(new Dimension(UIDisplay.WIDTH - 30, 20));
				display2.add(line);
				messagePanel.add(display2);
			}
		}
		for (int i = 0; i < end; ++i)
		{
			JPanel display = new JPanel(new BorderLayout());
			display.setPreferredSize(new Dimension(UIDisplay.WIDTH - 30, (UIDisplay.HEIGHT - 150) / 10));
			messagePanel.add(display);
		}
		screen.doLayout();
		JScrollBar jscrollBar = screen.getVerticalScrollBar();
		jscrollBar.setValue(jscrollBar.getMaximum());
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
		sendSound.addMouseListener(new MouseAdapter(){

			@Override
			public void mousePressed(MouseEvent e) {
	            capture();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				stopflag = true;
				String mess = save();
				client.sendMessage(userID, mess);
				client.ui.pop();
				client.ui.push(new TalkPage(client, userID, lastRead));
			}
		});
		sendPhoto.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Choose image");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("images", "jpg", "png", "bmp");
				fc.setFileFilter(filter);
				int ret = fc.showOpenDialog(client.ui.mainFrame);
				if (ret == JFileChooser.APPROVE_OPTION)
				{
					File file = fc.getSelectedFile();
					String hash = getHashCode(file);
					int k = (hash.hashCode() % 1000 + 1000) % 1000;
					String mess = String.valueOf(k) + hash;
					if (k < 10) mess = "0" + mess;
					if (k < 100) mess = "0" + mess;
					Communicator.savePic(file.getPath(), "database" + File.separator + mess);
					client.sendMessage(userID, mess);
					client.ui.pop();
					client.ui.push(new TalkPage(client, userID, lastRead));
				}
			}
			
		});
		send.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (text.getText().equals(""))
					return;
				client.sendMessage(userID, text.getText());
				text.setText("");
				client.ui.pop();
				client.ui.push(new TalkPage(client, userID, lastRead));
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
	
	String getHashCode(File file) {
		String ans = "";
        try {
			FileInputStream fis = new FileInputStream(file);
			byte[] sendBytes = new byte[1024];
			int length;
			while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
				ans = String.valueOf((ans.hashCode() % 1000 + 1000) % 1000);
				for (int i = 0; i < length; ++i)
					ans = ans + String.valueOf((char)sendBytes[i]);
			}
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return String.valueOf(ans.hashCode());
	}

	static int isHashCode(String sentence) {
		if (sentence.length() < 3)
			return 0;
		int code = sentence.substring(3).hashCode();
		try{
			if (Integer.parseInt(sentence.substring(0, 3)) == ((code % 1000 + 1000) % 1000))
				return 1;
			if (sentence.length() < 4)
				return 0;
			code = sentence.substring(4).hashCode();
			if (Integer.parseInt(sentence.substring(0, 4)) == ((code % 10000 + 10000) % 10000))
				return 2;
			else
				return 0;
		} catch(NumberFormatException e) {
			return 0;
		}
	}
	
	public void capture()
	{
		try {
			af = getAudioFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class,af);
			td = (TargetDataLine)(AudioSystem.getLine(info));
			td.open(af);
			td.start();
			
			Record record = new Record();
			Thread t1 = new Thread(record);
			t1.start();
			
		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
			return;
		}
	}

	public AudioFormat getAudioFormat() 
	{
		AudioFormat.Encoding encoding = AudioFormat.Encoding.
        PCM_SIGNED ;
		float rate = 8000f;
		int sampleSize = 16;
		boolean bigEndian = true;
		int channels = 1;
		return new AudioFormat(encoding, rate, sampleSize, channels,
				(sampleSize / 8) * channels, rate, bigEndian);
	}

	class Record implements Runnable
	{
		byte bts[] = new byte[10000];
		public void run() {	
			baos = new ByteArrayOutputStream();		
			try {
				//System.out.println("ok3");
				stopflag = false;
				while(stopflag != true)
				{
					int cnt = td.read(bts, 0, bts.length);
					if(cnt > 0)
						baos.write(bts, 0, cnt);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					if(baos != null)
						baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					td.drain();
					td.close();
				}
			}
		}
	}

	class Play implements Runnable
	{
		String add;
		Play(String a){add = a;}
		public void run() {
	          try {
	             // use one of the WAV of Windows installation
	             File tadaSound = new File("database" + File.separator + add);     
	             AudioInputStream audioInputStream = AudioSystem
	                   .getAudioInputStream(tadaSound);
	             AudioFormat audioFormat = audioInputStream
	                   .getFormat();
	             DataLine.Info dataLineInfo = new DataLine.Info(
	                   Clip.class, audioFormat);
	             Clip clip = (Clip) AudioSystem
	                   .getLine(dataLineInfo);
	             clip.open(audioInputStream);
	             clip.start();
	          } catch (Exception e) {
	             e.printStackTrace();
	          }
		}		
	}

	public String save()
	{
        af = getAudioFormat();
        byte audioData[] = baos.toByteArray();
        bais = new ByteArrayInputStream(audioData);
        ais = new AudioInputStream(bais,af, audioData.length / af.getFrameSize());
        File file = null;

		String add = "";
		for (int i = 0; i < audioData.length; ++i)
			add = add + String.valueOf((char)audioData[i]);
		add = String.valueOf(add.hashCode());
		int k = (add.hashCode() % 10000 + 10000) % 10000;
		add = String.valueOf(k) + add;
		if (k < 10) add = "0" + add;
		if (k < 100) add = "0" + add;
		if (k < 1000) add = "0" + add;
        try {
        	file = new File("database" + File.separator + add);      
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	try {
        		if(bais != null)
        			bais.close();
        		if(ais != null)
        			ais.close();
			} catch (Exception e) {
				e.printStackTrace();
			}   	
        }
        return add;
	}

	public void play(String add)
	{
		new Thread(new Play(add)).start();
	}

}
