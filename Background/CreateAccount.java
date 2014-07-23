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

/*import javax.imageio.ImageIO;
import javax.swing.ImageIcon;*/

public class CreateAccount {
	
	
	//Server establish users Info
	int writeData(String ID, String password, String nickname, String unread, String picture, ArrayList<String> friendList){
		
	
		int flag = 0;
		boolean found;
		
		try{
		
			File f = new File("data.txt");
			FileWriter fw =(f.exists())?new FileWriter("data.txt",true):new FileWriter("data.txt");
			
			SearchMethods find = new SearchMethods();
			found = find.search(ID);
		
		if(found){
			fw.close();
			return 1;//ID already exist
			//System.out.println("userID is already exist.");
		}
		else{
			 	/*File im=new File("\\" + ID + "Icon.png");
		        ImageIO.write((RenderedImage)picture.getImage(),"png",im);*/
		        
		        System.out.println("here");
		        
		        fw.write("profile "+ ID + " key " + password + " nickname " + nickname + " Unread " + unread 
		        		+ /*im.getPath()" friend " + friendList +*/ "\n");  
		        fw.flush();
		        
		        System.out.println("there");
				
		        //br.close();
		        fw.close();
				flag = -1;//Imply file write successed
			}
		}catch(Exception e){
			return flag;
		}
        
        
       
		//System.out.println(flag);
		
		return flag;
	}
	
	/*int readData(String ID){
		
		String Info = searchdata(ID);
		
		if(Info.equals(null)){
			return -1;//Which means not found
		}
		else{
			System.out.println(Info);
			return 1;
		}
		
	}


	//May be generally used for checking before writing or loading
	boolean search(String target){
    
    try{
    	//File f = new File("data.txt");
    	Pattern p = Pattern.compile("profile " + target);
    	Matcher search;
    	FileReader fr = new FileReader("data.txt");
        BufferedReader br = new BufferedReader(fr);
        String charSet = new String();
    	
    do{
        
           charSet=br.readLine();
        
           if(charSet==null){
        	   
        	   br.close();
        	   return false;
               
           }
           
           search = p.matcher(charSet);
           if(search.find()){
        	   
        	   br.close();
        	   return true;
           }
           
       } while(true);
    }catch (IOException e) {
		System.out.println("Input wrong");
	}
    
	return false;
	
    }


String searchdata(String ID){
    
    try{
    	File f = new File("data.txt");
    	Pattern p = Pattern.compile("profile " + ID);
    	Matcher search;
    	FileReader fr = new FileReader("data.txt");
        BufferedReader br = new BufferedReader(fr);
        String charSet = new String();
    	
    do{
        
           charSet=br.readLine();
        
           if(charSet==null){
        	   
        	   br.close();
        	   return null;
               
           }
           
           search = p.matcher(charSet);
           if(search.find()){
        	   
        	   br.close();
        	   return charSet;
           }
           
       } while(true);
    }catch (IOException e) {
		System.out.println("Input wrong");
	}
    
	return null;
	
    }*/
}