package background;

//import java.io.*;
import java.net.Socket;
import java.util.TreeMap;

public class LogMethods {
	
	static TreeMap<String, String> logList = new TreeMap<String, String>();
	
	void LogIn(String ID, Socket cSocket){
		
		/*try{
			File f = new File("./Online.txt");
			FileWriter fw =(f.exists())?new FileWriter("./Online.txt",true):new FileWriter("./Online.txt");
			
			SearchMethods check = new SearchMethods();
			if(check.SearchOnline(ID).equals("-1")){
				fw.write(ID + " " + cSocket.getInetAddress().getHostAddress());
			}
			
			
			
			fw.close();
		}catch(IOException e){
			System.out.println("Error: "+e);
		}*/
		
		logList.put(ID, cSocket.getInetAddress().getHostAddress());
		System.out.println(logList + " 30@LogMethods.java");
		
		
	}
	
	String LogOut(String ID){
		
		if(logList.containsKey(ID)){
			logList.remove(ID);
			System.out.println(logList + " 39@LogMethods.java");
			return "-1";
			}
		else{
			System.out.println("Test 43@LogMethods.java");
			return "0";
			}
		
	}
	
}
