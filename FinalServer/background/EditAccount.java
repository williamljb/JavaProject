package background;

import java.io.*;
import java.util.ArrayList;

/**
 * @author Srhjerry
 * @version 2014.07.30
 * 
 * */

public class EditAccount {
	
	/**
	 * 
	 * @param ID: User's ID wanted to change his profile
	 * @param password: User's new password. If it does't change, text will preserve previous one.
	 * @param nickname: User's new nickname. If it does't change, text will preserve previous one.
	 * @param unread.picture.friendList: Not used in reality.
	 * @return flag = -1 : Succeed, flag = 0 : Failed
	 */

	int editData(String ID, String password, String nickname, String unread, String picture, ArrayList<String> friendList){
		
		
		int flag = 0;
		
		
		System.out.println("@29.EditAccount.java");
		
		try{
		
			
				FileWriter fw = new FileWriter("./data" + File.separator + ID + File.separator + "info.txt");
				fw.write("profile "+ ID + " key " + password + " nickname " + nickname + " Unread " + unread 
   		        		+ " friend " + friendList + "\n");  
   		        fw.flush();
   		        fw.close();
				
   		        //System.out.println("70@EditAccount.java");
				flag = -1;//Imply file rewrite successed
				//new File("./" + ID).mkdir();
				ImageReceiver test = new ImageReceiver("./data" + File.separator + ID + File.separator + ID +".jpg");
		    	new Thread(test).start();
		    	//System.out.println("75@EditAccount.java");
		    	return flag;
			
		}catch(Exception e){
			return flag;
		}
        
	}
	
}
