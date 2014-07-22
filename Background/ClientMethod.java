import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import javax.swing.ImageIcon;


public class ClientMethod {

	
	/*public static void main(String[] args){
		
		String id = "123";
		String name = "Test";
		String password = "nthu";
		ImageIcon image = new ImageIcon();
		
		ClientMethod test = new ClientMethod();
		int result = test.sendClientdata(id, name, password, image);
		
		
		
		System.out.println(result);
		
	}*/
	
	
	
	int sendClientdata(String id, String name, String password, ImageIcon image){
		
		int resultnum = 0;
		/*String give = "profile "+id+" nickname "+name+" key "+password+" friend "+"147 258 369"+" command  "+" 1";
		
		cRecieveData send = new cRecieveData();
		send.receiveServerdata(give);
		
		System.out.println(send.sendID());
		System.out.println(send.sendName());
		System.out.println(send.sendPassword());
		System.out.println(send.sendFriendID());*/
		
		try{
			Socket socket = new Socket("127.0.0.1", 2048);
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String result = is.readLine();
			resultnum =Integer.parseInt(result);
			
				os.println(id + name + password);
				os.flush();
				
				
			
			os.close();
			is.close();
			socket.close();
		}catch(Exception e){
			System.out.println("Error: "+ e);
			return 0;
		}
		
		return resultnum;
	}
	
	
	
	
	
	
}

class cRecieveData{
	
	String id;
	String name;
	String password;
	String hasUnreadMMS;
	Boolean unread;
	ImageIcon image;
	ArrayList<String> friendID = new ArrayList<String>();
	String command;
	
	
	void receiveServerdata(String source){
		
		Scanner in = new Scanner(source);
		
		while(in.hasNext()){
			
			switch(in.next()){
			
			case "profile":
				this.id = in.next();
				break;
			case "nickname":
				this.name = in.next();
				break;
			case "key":
				this.password = in.next();
				break;
			case "Unread":
				this.hasUnreadMMS = in.next();
				if(this.hasUnreadMMS.equals("yes")){
					unread = true;
				}else{
					unread = false;
				}
				break;
			case "friend":
				while(in.hasNext()){
					this.friendID.add(in.next());
				}
				break;
			/*case "command":
				int result = in.nextInt();
				break;*/
			default:
				System.out.println("Something haven't dealt.");
			}
			
		}
		
		
		in.close();
	}
	
	String sendID(){
		return this.id;
	}
	
	String sendName(){
		return this.name;
	}
	
	String sendPassword(){
		return this.password;
	}
	
	ArrayList<String> sendFriendID(){
		return this.friendID;
	}
	
}