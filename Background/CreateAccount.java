package background;

//import java.awt.image.RenderedImage;
//import java.io.BufferedReader;
import java.io.File;
//import java.io.FileReader;
import java.io.FileWriter;
//import java.io.IOException;
import java.util.ArrayList;
/*import java.util.regex.Matcher;
import java.util.regex.Pattern;
*/

public class CreateAccount {
	
	
	//Server establish users Info
	int writeData(String ID, String password, String nickname, String unread, String picture, ArrayList<String> friendList){
		
	
		int flag = 0;
		boolean found;
		
		try{
			//System.out.println("25@CreateAccount.java");
			new File("data" + File.separator + ID).mkdirs();
			File f = new File("./data" + File.separator + ID + File.separator + "info.txt");
			FileWriter fw =(f.exists())?new FileWriter("./data" + File.separator + ID + File.separator + "info.txt",true):new FileWriter("./data" + File.separator + ID + File.separator + "info.txt");
			//System.out.println("29@CreateAccount.java");
			SearchMethods find = new SearchMethods();
			found = find.search(ID, "NoUse");
			//System.out.println("32@CreateAccount.java");
		
		if(found){
			fw.close();
			return 1;//ID already exist
			//System.out.println("userID is already exist.");
		}
		else{
			 	/*File im=new File("\\" + ID + "Icon.png");
		        ImageIO.write((RenderedImage)picture.getImage(),"png",im);*/
		        
				System.out.println("43@CreateAccount.java");
		        
		        fw.write("profile "+ ID + " key " + password + " nickname " + nickname + " Unread " + unread 
		        		+ " friend " + friendList + "\n");  
		        fw.flush();
		       
		        System.out.println("49@CreateAccount.java");
				
		        //br.close();
		        fw.close();
				flag = -1;//Imply file write successed
				ImageReceiver test = new ImageReceiver("./data" + File.separator + ID + File.separator + ID +".jpg");
		    	new Thread(test).start();
				return flag;
			}
		}catch(Exception e){
			return flag;
		}
        
	}
	
}