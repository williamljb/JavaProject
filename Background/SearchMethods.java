package background;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchMethods {

	boolean checkData(String source,String ID){
		
		Pattern p = Pattern.compile("profile "+ ID);
		Matcher m = p.matcher(source);
		
		if(m.find())
			return true;
		else
			return false;
	}
	
	String idExist(String ID){
		
		File father = new File("./data");
		File[] childFiles = father.listFiles();
		
		for (File childFile : childFiles) {
			if (childFile.getName().equals(ID)) {
				System.out.println("idExist@30.SearchMethods.java");
				return "-1";
			}
		}
		System.out.println("idExist@34.SearchMethods.java");
		return "1";
	}


	//May be generally used for checking before writing or loading
	boolean search(String target, String key){
    
    try{
    	//File f = new File("data.txt");
    	
    	FileReader fr;
    	BufferedReader br;
    	Pattern p;
    	if(key.equals("NoUse")){
    		p = Pattern.compile("profile " + target);
    		fr = new FileReader("./data/" + target + "/info.txt");
            br = new BufferedReader(fr);
    	}
    	else{
    		p = Pattern.compile("profile " + target + key);
    		fr = new FileReader("./data/" + target + "/info.txt");
    		br = new BufferedReader(fr);
    	}
    	
    	Matcher search;
    	
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
    	FileReader fr = new FileReader("./data/" + ID + "/info.txt");
        BufferedReader br = new BufferedReader(fr);
        String charSet = new String();
    	System.out.println("GetUser 79@SearchMethods.java");
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

	String SearchOnline(String ID){
		try{
			
			FileReader fr = new FileReader("./Online.txt");
	    	BufferedReader br = new BufferedReader(fr);
	    	Pattern p = Pattern.compile(ID);
	    	Matcher search;
	    	
	        String charSet = new String();
	    	
	    do{
	        
	           charSet=br.readLine();
	        
	           if(charSet==null){
	        	   
	        	   br.close();
	        	   return "-1";
	               
	           }
	           
	           search = p.matcher(charSet);
	           if(search.find()){
	        	   
	        	   br.close();
	        	   return "1";
	           }
	           
	       } while(true);
	    }catch (IOException e) {
			System.out.println("Input wrong: ");
		}
	    
		return "0";
		
	    }
	
}
