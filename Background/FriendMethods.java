package background;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class FriendMethods {
	
	Pattern p = Pattern.compile("\\]");
	Matcher m;
	
	String SendFdRequest(String fromID, String toID){
		
		try{
			File ft = new File("./data/" + toID + "/FdRequest.txt");
			FileReader fr = new FileReader(ft);
			BufferedReader br = new BufferedReader(fr);
			String tmpString = br.readLine();
			br.close();
			
			m = p.matcher(tmpString);
			if(m.find())
				tmpString = tmpString.substring(1, tmpString.length()-1);
			
			FileWriter fw = new FileWriter("./data/" + toID + "/FdRequest.txt");
			
			fw.write(tmpString + fromID + " ");
			
			fw.close();
			return "-1";
		}catch(Exception e){
			System.out.println("Error @.FriendMethods.java: " + e);
		}
		
		return "0";
	}
	
	String ReturnFdRequests(String ID){
		
		String requests = "";
		
		try{
			System.out.println("@30.FriendMethods.java");
			File ft = new File("./data/" + ID + "/FdRequest.txt");
			FileReader fr = new FileReader(ft);
			BufferedReader br = new BufferedReader(fr);
			requests = br.readLine();
			br.close();
			m = p.matcher(requests);
			if(m.find()){
				return requests.substring(1, requests.length()-1);
			}else
				return requests;
			
		}catch(Exception e){
			System.out.println("Error @33.FriendMethods.java");
		}
		
		return requests;
	}
	
	String AcceptFdRequest(String fromID, String toID){
		
		ArrayList<String>tmp = new ArrayList<String>();
		String tmpID;
		String tmpString;
		Scanner in;
		//boolean first = true;
		
		System.out.println("from: " + fromID + " to: " + toID + " @50.FriendMethods.java");
		
		try{
			File fl = new File("./data/" + toID + "/FdRequest.txt");
			File ff = new File("./data/" + fromID + "/FriendList.txt");
			File ft = new File("./data/" + toID + "/FriendList.txt");
			
			FileWriter fwf =(ff.exists())?new FileWriter("./data/" + fromID + "/FriendList.txt",true):new FileWriter("./data/" + fromID + "/FriendList.txt");
			FileWriter fwt =(ft.exists())?new FileWriter("./data/" + toID + "/FriendList.txt",true):new FileWriter("./data/" + toID + "/FriendList.txt");
			FileReader fr = new FileReader(fl);
			BufferedReader br = new BufferedReader(fr);
			tmpString = br.readLine();
			br.close();
			m = p.matcher(tmpString);
			if(m.find())
				in = new Scanner(tmpString.substring(1, tmpString.length()-1));
			else
				in = new Scanner(tmpString);
			
			while(in.hasNext()){
				tmpID = in.next();
				System.out.println(tmpID + " @66.FriendMethods.java");
				if(tmpID.equals(fromID)){
					continue;
				}
				else{
					tmp.add(tmpID + " ");
				}
				
			}
			in.close();
			FileWriter fwl = new FileWriter("./data/" + toID + "/FdRequest.txt");
			fwl.write(tmp.toString());
			fwl.close();
			fwf.write(toID + "\n");
			fwt.write(fromID + "\n");
			fwf.close();
			fwt.close();
			System.out.println("@87.FriendMethods.java");
			
		}catch(Exception e){
			
		}
		return "-1";
	}

}
