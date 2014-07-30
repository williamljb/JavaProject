import java.io.*;
import java.net.*;
import java.util.*;

public class Communicator {
	PrintWriter out;
	BufferedReader in;
	boolean serverNotFound = false;
	final static String SEP = File.separator; 
	
	Communicator()
	{
		Socket socket;
		try {
			socket = new Socket(Client.ipAddress, 2048);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
		} catch (UnknownHostException e) {
			this.serverNotFound = true;
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}

	public int create(String id, String name, String password, String imagePath) throws Exception {
		if (this.serverNotFound)
			return 0;
		out.println("CREATE profile " + id + " key " + password + " nickname " + name + " image " + imagePath);
		out.flush();
		String ret = in.readLine();
		if (ret.charAt(0) == '1')
			return 1;
		
		Communicator.savePic(imagePath, "database"+SEP + id + ".jpg");
		ImageSender test = new ImageSender("database"+SEP + id + ".jpg");
    	Thread thread = new Thread(test);
    	thread.start();
    	thread.join();
    	
		return -1;
	}

	public int login(String userID, String password) throws Exception {
		if (this.serverNotFound)
			return 0;
		out.println("LOGIN " + userID + " " + password);
		System.out.println("communicator 46 " + password);
		out.flush();
		String ret = in.readLine();
		if (ret.charAt(0) == '1')
			return 1;
		if (ret.charAt(0) == '2')
			return 2;
		return -1;
	}

	public int getUser(String userID, User user) throws Exception {
    	ImageReceiver test = new ImageReceiver("database"+SEP + userID + ".jpg");
    	Thread send = new Thread(test);
    	send.start();
    	
		out.println("GETUSER " + userID);
		out.flush();
		String ret = in.readLine();
		if (ret.charAt(0) == '1')
		{
			test.server.close();
			test.shouldClose = true;
			return 1;
		}
		Scanner scanner = new Scanner(ret);
		while(scanner.hasNext()){
			String tmp = scanner.next();
			//System.out.println("@Communicator.java, line 76 : " + tmp);
			switch(tmp){
			
			case "profile":
				user.id = scanner.next();
				break;
			case "nickname":
				user.name = scanner.next();
				break;
			case "key":
				user.password = scanner.next();
				break;
			case "image":
				user.image = scanner.next();
				break;
			default:
				//System.out.println("Something haven't dealt.");
			}
		}
		scanner.close();
		send.join();
		return -1;
	}
	
	public static void savePic(String imagePath, String add){
		if (imagePath.equals(add.intern()))
			return;
		DataInputStream dis = null;
		FileOutputStream fos = null;
		try {
			dis = new DataInputStream(new FileInputStream(imagePath));
			fos = new FileOutputStream(add);
			byte[] inputByte = new byte[1024];
	        int length = 0;
            while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
                fos.write(inputByte, 0, length);
                fos.flush();
            }
		} catch (IOException e) {
			//e.printStackTrace();
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					//e.printStackTrace();
				}
            if (dis != null)
				try {
					dis.close();
				} catch (IOException e) {
					//e.printStackTrace();
				}
		}
    }

	public int edit(String id, String name, String password, String imagePath) throws Exception {
		if (this.serverNotFound)
			return 0;
		out.println("EDIT profile " + id + " key " + password + " nickname " + name + " image " + imagePath);
		out.flush();
		String ret = in.readLine();
		if (ret.charAt(0) == '1')
			return 1;
		System.out.println("communicator 137");
		Communicator.savePic(imagePath, "database"+SEP + id + ".jpg");
		ImageSender test = new ImageSender("database"+SEP + id + ".jpg");
    	Thread thread = new Thread(test);
    	thread.start();
    	thread.join();
    	
		return -1;
	}

	public int logout(String id) throws Exception {
		//System.out.println("ok");
		if (this.serverNotFound)
			return 0;
		out.println("LOGOUT " + id);
		out.flush();
		String ret = in.readLine();
		if (ret.charAt(0) == '1')
			return 1;
		if (ret.charAt(0) == '2')
			return 2;
		return -1;
	}

	public int idExists(String userID) throws Exception {
		if (this.serverNotFound)
			return 0;
		out.println("IDEXISTS " + userID);
		out.flush();
		String ret = in.readLine();
		//System.out.println(ret);
		if (ret.charAt(0) == '1')
			return 1;
		return -1;
	}

	public void sendRequest(String curID, String userID) throws Exception {
		out.println("SENDREQUEST " + curID + " " + userID);
		out.flush();
		in.readLine();
	}

	public void request(HashSet<String> set, String userID) throws Exception {
		out.println("REQUEST " + userID);
		out.flush();
		String ret = in.readLine();
		if (ret == null || ret.equals(""))
			return;
		//System.out.println("communicator, line 154:" + ret);
		for (String id : ret.split(" "))
		{
			//System.out.println("id"+id);
			set.add(id);
		}
	}

	public void acceptRequest(String curID, String userID) throws Exception {
		out.println("ACCEPTREQUEST " + curID + " " + userID);
		out.flush();
		in.readLine();
	}
	
	public void declineRequest(String curID, String userID) throws Exception {
		out.println("DECLINEREQUEST " + curID + " " + userID);
		out.flush();
		in.readLine();
	}

	public void friend(ArrayList<String> friends, String userID) throws Exception {
		out.println("FRIEND " + userID);
		out.flush();
		String ret = in.readLine();
		//System.out.println("communicator 172 : " + ret.charAt(0));
		if (ret == null || ret.equals(""))
		{
			//System.out.println("communicator175 : ok");
			return;
		}
		for (String id : ret.split(" "))
		{
			//System.out.println("id"+id);
			friends.add(id);
		}
	}

	public void deleteFriend(String curID, String userID) throws Exception {
		out.println("DELETEFRIEND " + curID + " " + userID);
		out.flush();
		in.readLine();
	}

	public void sendMessage(String curID, String userID, String text) throws Exception {
		out.println("SENDMESSAGE " + curID + " " + userID + " " + text);
		out.flush();
		//System.out.println("communicator 199 : " + text);
		in.readLine();
		if (TalkPage.isHashCode(text.substring(18)) != 0)
		{
			ImageSender test = new ImageSender("database" + File.separator + text.substring(18));
	    	Thread thread = new Thread(test);
	    	thread.start();
	    	thread.join();
		}
	}

	public String getUnread(String curID, String userID) throws Exception {
		String []b;
		out.println("UNREAD " + curID + " " + userID);
		out.flush();
		String ans = "", buffer;
		buffer = in.readLine();
		if (buffer.equals(""))
			return "";
		int num = Integer.parseInt(buffer);
		b = new String[num];
		for (int i = 0; i < num; ++i)
		{
			buffer = in.readLine();
			ans = ans + buffer + "\n";
			b[i] = "";
			if (TalkPage.isHashCode(buffer.substring(1 + 18)) != 0)
			{
				b[i] = new String(buffer.substring(1 + 18).intern());
				//System.out.println("file : " + b[i]);
			}
		}
		buffer = in.readLine();
		//System.out.println("ok");
		for (int i = 0; i < num; ++i)
			if (!b[i].equals(""))
			{
				//System.out.println("request file : " + b[i]);
				ImageReceiver test = new ImageReceiver("database" + File.separator + b[i]);
		    	Thread thread = new Thread(test);
		    	thread.start();
				out.println("SENDFILE " + b[i]);
				out.flush();
				//System.out.println("ok0");
				in.readLine();
				//System.out.println("ok1");
				thread.join();
			}
		//System.out.println(ans);
		return ans;
	}

	public String download(String curID, String userID) throws Exception {
		String []b;
		out.println("DOWNLOAD " + curID + " " + userID);
		out.flush();
		String ans = "", buffer;
		buffer = in.readLine();
		if (buffer.equals(""))
			return "";
		int num = Integer.parseInt(buffer);
		b = new String[num];
		for (int i = 0; i < num; ++i)
		{
			buffer = in.readLine();
			ans = ans + buffer + "\n";
			b[i] = "";
			if (TalkPage.isHashCode(buffer.substring(1 + 18)) != 0)
			{
				b[i] = new String(buffer.substring(1 + 18).intern());
				//System.out.println("file : " + b[i]);
			}
		}
		buffer = in.readLine();
		//System.out.println("ok");
		for (int i = 0; i < num; ++i)
			if (!b[i].equals(""))
			{
				//System.out.println("request file : " + b[i]);
				ImageReceiver test = new ImageReceiver("database" + File.separator + b[i]);
		    	Thread thread = new Thread(test);
		    	thread.start();
				out.println("SENDFILE " + b[i]);
				out.flush();
				//System.out.println("ok0");
				in.readLine();
				//System.out.println("ok1");
				thread.join();
			}
		//System.out.println(ans);
		return ans;
	}

}
