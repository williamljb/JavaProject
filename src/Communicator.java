import java.io.*;
import java.net.*;
import java.util.*;

public class Communicator {
	PrintWriter out;
	BufferedReader in;
	boolean serverNotFound = false;
	
	Communicator()
	{
		Socket socket;
		try {
			socket = new Socket("166.111.227.148", 2048);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
		} catch (UnknownHostException e) {
			this.serverNotFound = true;
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int create(String id, String name, String password, String image) throws Exception {
		if (this.serverNotFound)
			return 0;
		out.println("CREATE profile " + id + " key " + password + " nickname " + name + " image " + image);
		out.flush();
		String ret = in.readLine();
		if (ret.charAt(0) == '1')
			return 1;
		return -1;
	}

	public int login(String userID, String password) throws Exception {
		if (this.serverNotFound)
			return 0;
		out.println("LOGIN " + userID + " " + password);
		out.flush();
		String ret = in.readLine();
		if (ret.charAt(0) == '1')
			return 1;
		if (ret.charAt(0) == '2')
			return 2;
		return -1;
	}

	public int getUser(String userID, User user) throws Exception {
		out.println("GETUSER " + userID);
		out.flush();
		String ret = in.readLine();
		if (ret.charAt(0) == '1')
			return 1;
		Scanner scanner = new Scanner(ret);
		while(scanner.hasNext()){
			String tmp = scanner.next();
			System.out.println(tmp);
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
		System.out.println("ok");
		scanner.close();
		return -1;
	}
}
