package background;

import java.util.ArrayList;
import java.util.Scanner;
import java.net.*;

//import javax.swing.ImageIcon;

public class SReceiveData{
	
	String id;
	String name;
	String password;
	String hasUnreadMMS = "No";
	Boolean unread;
	String image;
	ArrayList<String> friendID = new ArrayList<String>();
	String command;
	int result;
	
	
	String receiveClientData(String source, Socket clientSocket){
		
		Scanner in = new Scanner(source);
		
		this.command = in.next();
		
		System.out.println(this.command + " command @28.SReceiveData.java");
		
		if(this.command.equals("CREATE")){
		
			System.out.println("Command "+this.command+" accepted");
			
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
			case "image":
				this.image = in.next();
				break;
			default:
				System.out.println("Something haven't dealt.");
			}
			
		}
		CreateAccount addAccount = new CreateAccount();
		this.result = addAccount.writeData(this.id, this.password, this.name, this.hasUnreadMMS, this.image, friendID);
		in.close();
		LogMethods log = new LogMethods();
		log.LogIn(this.id, clientSocket);
		return String.valueOf(this.result);
	}else if(this.command.equals("LOGIN")){
		
		boolean idExist;
		
		this.id = in.next();
		this.password = in.next();
		
		SearchMethods test = new SearchMethods();
		idExist = test.search(this.id, "NoUse");
		in.close();
		
		if(!idExist)
			return "1";
		else{
			if(test.search(this.id, " key " + this.password)){
				LogMethods log = new LogMethods();
				log.LogIn(this.id, clientSocket);
				return "-1";
				}
			else
				return "2";
		}
		
	}else if(this.command.equals("GETUSER")){
		String tmp;
		System.out.println("GetUserCommand 97@SReceiveData.java");
		this.id = in.next();
		in.close();
		SearchMethods findUser = new SearchMethods();
		tmp = findUser.searchdata(this.id);
		if(tmp.equals("1"))
			return tmp;
		else{
			ImageSender test = new ImageSender("./data/"+ this.id + "/" + this.id + ".jpg", clientSocket);
	    	new Thread(test).start();
	    	return tmp;
		}
	}else if(this.command.equals("EDIT")){
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
				if(this.hasUnreadMMS.equals("Yes")){
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
			case "image":
				this.image = in.next();
				break;
			default:
				System.out.println("Something haven't dealt.");
			}
			
		}
		EditAccount editAccount = new EditAccount();
		this.result = editAccount.editData(this.id, this.password, this.name, this.hasUnreadMMS, this.image, friendID);
		in.close();
		return String.valueOf(this.result);
	}else if(this.command.equals("LOGOUT")){
		this.id = in.next();
		System.out.println(this.id + " logout @152.SReceiveData.java");
		in.close();
		
		return new LogMethods().LogOut(this.id);
		
	}else if(this.command.equals("IDEXISTS")){
		System.out.println("156@SReceiveData");
		this.id = in.next();
		in.close();
		System.out.println(this.id + " IDEXISTS@161.SReceiveData");
		return new SearchMethods().idExist(this.id);
		
	}else if(this.command.equals("SENDREQUEST")){
		
		String from = in.next();
		String to = in.next();
		in.close();
		return new FriendMethods().SendFdRequest(from, to);
		
	}else if(this.command.equals("REQUEST")){
		
		this.id = in.next();
		in.close();
		return new FriendMethods().ReturnFdRequests(this.id);
	}else if(this.command.equals("ACCEPTREQUEST")){
		String from = in.next();
		String to = in.next();
		in.close();
		return new FriendMethods().AcceptFdRequest(from, to);	
	}else if(this.command.equals("FRIEND")){
		this.id = in.next();
		in.close();
		return new FriendMethods().ReturnFdList(this.id);	
	}else if(this.command.equals("DELETEFRIEND")){
		String from = in.next();
		String to = in.next();
		in.close();
		return new FriendMethods().DeleteFriend(from, to);
	}else if(this.command.equals("UNREAD")){
		String from = in.next();
		String to = in.next();
		in.close();
		return MessageMethods.CheckUnread(from, to);
	}else if(this.command.equals("SENDMESSAGE")){
		String from = in.next();
		String to = in.next();
		String text = in.next();
		in.close();
		return MessageMethods.SendMessage(from, to, text);
	}
		
		in.close();
		return "0";
	}
	
	/*String receiveClientRequest(String source){
		
		Scanner in = new Scanner(source);
		this.command = in.next();
		
		if(this.command.equals("GETUSER")){
			
			this.id = in.next();
			in.close();
			SearchMethods findUser = new SearchMethods();
			return findUser.searchdata(this.id);
		}
		
		in.close();
		return "1";
	}*/
	
}
