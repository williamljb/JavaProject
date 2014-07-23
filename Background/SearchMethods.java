package background;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchMethods {
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
    	//File f = new File("data.txt");
    	Pattern p = Pattern.compile("profile " + ID);
    	Matcher search;
    	FileReader fr = new FileReader("data.txt");
        BufferedReader br = new BufferedReader(fr);
        String charSet = new String();
    	System.out.println("GetUser");
    	do{
        
           charSet=br.readLine();
        
           if(charSet==null){
        	   
        	   br.close();
        	   return "1";
               
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
    
	return "1";
	
    }
}
