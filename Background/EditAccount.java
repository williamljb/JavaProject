package background;

import java.io.*;
import java.util.ArrayList;

public class EditAccount {

	int editData(String ID, String password, String nickname, String unread, String picture, ArrayList<String> friendList){
		
		
		int flag = 0;
		//boolean found;
		
		System.out.println("14@EditAccount.java");
		
		try{
		
			//File f = new File("data.txt");
			/*FileWriter fw = new FileWriter("data.txt");
			FileReader fr = new FileReader("data.txt");
	        BufferedReader br = new BufferedReader(fr);
	        String charSet = new String();
			
			SearchMethods find = new SearchMethods();
			found = find.search(ID);
			System.out.println(found);
		
		if(!found){
			fw.close();
			br.close();
			return 1;//ID not found
		}
		else{
			 	
		        
		        
		        do{
		            
		               charSet=br.readLine();
		               
		               if(charSet==null)
		                   break;
		            
		               if(find.checkData(charSet, ID)){
		            	   fw.write("profile "+ ID + " key " + password + " nickname " + nickname + " Unread " + unread 
		   		        		+ " friend " + friendList + "\n");  
		   		           fw.flush();
		               }else{
		            	   fw.write(charSet);
		            	   fw.flush();
		               }
		               
		               //System.out.println(charSet);
		           } while(true);
		        
		        
		        
		       
		        
				
		        br.close();
		        fw.close();
		        }*/
				FileWriter fw = new FileWriter("./data/" + ID + "/info.txt");
				fw.write("profile "+ ID + " key " + password + " nickname " + nickname + " Unread " + unread 
   		        		+ " friend " + friendList + "\n");  
   		        fw.flush();
   		        fw.close();
				
   		        System.out.println("70@EditAccount.java");
				flag = -1;//Imply file rewrite successed
				//new File("./" + ID).mkdir();
				ImageReceiver test = new ImageReceiver("./data/"+ ID + "/" + ID +".jpg");
		    	new Thread(test).start();
		    	System.out.println("75@EditAccount.java");
		    	return flag;
			
		}catch(Exception e){
			return flag;
		}
        
	}
	
	
}
