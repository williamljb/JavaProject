package background;

import java.util.ArrayList;
import java.util.Scanner;

//import javax.swing.ImageIcon;

public class SReceiveData{
	
	String id;
	String name;
	String password;
	String hasUnreadMMS;
	Boolean unread;
	String image;
	ArrayList<String> friendID = new ArrayList<String>();
	String command;
	int result;
	
	
	String receiveClientData(String source){
		
		Scanner in = new Scanner(source);
		
		this.command = in.next();
		
		System.out.println(this.command);
		
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
		return String.valueOf(this.result);
	}else if(this.command.equals("LOGIN")){
		
		boolean idExist;
		
		this.id = in.next();
		this.password = in.next();
		
		SearchMethods test = new SearchMethods();
		idExist = test.search(this.id);
		in.close();
		
		if(!idExist)
			return "1";
		else{
			if(test.search(this.id + " key " + this.password))
				return "-1";
			else
				return "2";
		}
		
	}else if(this.command.equals("GETUSER")){
		
		System.out.println("GetUserCommand");
		this.id = in.next();
		in.close();
		SearchMethods findUser = new SearchMethods();
		return findUser.searchdata(this.id);
	}{
		
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
