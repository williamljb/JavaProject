import java.awt.image.RenderedImage;
import java.io.*;
import java.util.regex.*;

import javax.swing.*;
import javax.imageio.ImageIO;

public class creatAccount {
	
	/*private String ID = null;
	private String password = null;
	private String nickname = null;
	private ImageIcon profilepicture = null;*/
	
	//Server establish users Info
	int writeData(String ID, String password, String nickname, ImageIcon picture){
		
	
		int flag = 0;
		boolean found;
		/*String ID = "003";
		String password = "abc123";
		String nickname = "Test";*/
		
		try{
		
			File f = new File("data.txt");
			FileWriter fw =(f.exists())?new FileWriter("data.txt",true):new FileWriter("data.txt");
			
			found = search(ID);
		
		if(found){
			System.out.println("userID is already exist.");
		}
		else{
			 File im=new File("\\" + ID + "Icon.png");
		        ImageIO.write((RenderedImage)picture.getImage(),"png",im);

		        fw.write("profile "+ ID + " " + password + " " + nickname + " " + im.getPath() + "\n");  
		        fw.flush();
				
		        //br.close();
		        fw.close();
				flag = 1;//Imply file write successed
			}
		}catch(Exception e){
			return flag;
		}
        
        
       
		System.out.println(flag);
		
		return flag;
	}
	
	int readData(String ID){
		
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
	boolean search(String ID){
    
    try{
    	//File f = new File("data.txt");
    	Pattern p = Pattern.compile("profile " + ID);
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
    	//File f = new File("data.txt");
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
	
    }
}
