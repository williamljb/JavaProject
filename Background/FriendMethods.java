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
			FileWriter fw =(ft.exists())? new FileWriter("./data/" + toID + "/FdRequest.txt", true): new FileWriter("./data/" + toID + "/FdRequest.txt");
			fw.close();
			FileReader fr = new FileReader(ft);
			BufferedReader br = new BufferedReader(fr);
			String tmpString = br.readLine();
			br.close();
			
			if(tmpString!=null){
			m = p.matcher(tmpString);
			if(m.find())
				tmpString = tmpString.substring(1, tmpString.length()-1);
			}
			else{
				tmpString = "";
			}
			
			fw = new FileWriter("./data/" + toID + "/FdRequest.txt");
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
			
			if(requests!=null){
			m = p.matcher(requests);
			if(m.find()){
				return requests.substring(1, requests.length()-1);
			}else
				return requests;
			}else
				return "";
			
		}catch(Exception e){
			System.out.println("Error @66.FriendMethods.java");
		}
		
		return requests;
	}
	
	String ReturnFdList(String ID){
		
		String fdList = "";
			
			try{
				System.out.println("@77.FriendMethods.java");
				File ft = new File("./data/" + ID + "/FriendList.txt");
				FileReader fr = new FileReader(ft);
				BufferedReader br = new BufferedReader(fr);
				fdList = br.readLine();
				br.close();
				
				if(fdList!=null){
				m = p.matcher(fdList);
				if(m.find()){
					return fdList.substring(1, fdList.length()-1);
				}else
					return fdList;
				}else
					return "";
				
			}catch(Exception e){
				System.out.println("Error @93.FriendMethods.java");
			}
			
			return fdList;
	}
	
	String AcceptFdRequest(String fromID, String toID){
		
		ArrayList<String>tmp = new ArrayList<String>();
		String tmpID;
		String tmpString;
		Scanner in;
		
		//boolean first = true;
		
		System.out.println("AddFriend from: " + fromID + " to: " + toID + " @109.FriendMethods.java");
		
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
				//System.out.println(tmpID + " @130.FriendMethods.java");
				if(tmpID.equals(fromID)){
					continue;
				}
				else{
					tmp.add(tmpID + " ");
				}
				
			}
			in.close();
			FileWriter fwl = new FileWriter("./data/" + toID + "/FdRequest.txt");
			for(int i = 0 ; i< tmp.size() ; i++)
				fwl.write(tmp.get(i));
			fwl.close();
			fwf.write(toID + " ");
			fwt.write(fromID + " ");
			fwf.close();
			fwt.close();
			System.out.println("@148.FriendMethods.java");
			
		}catch(Exception e){
			System.out.println("Error: " + e + "@142.FriendMethods.java");
		}
		return "-1";
	}
	
	String DeleteFriend(String fromID, String toID){
		
		ArrayList<String>tmpf = new ArrayList<String>();
		ArrayList<String>tmpt = new ArrayList<String>();
		String tmpID;
		String tmpString;
		Scanner in;
		boolean first =true;
		
		System.out.println("DeleteFriend from: " + fromID + " to: " + toID + " @157.FriendMethods.java");
		
		try{
			File ff = new File("./data/" + fromID + "/FriendList.txt");
			File ft = new File("./data/" + toID + "/FriendList.txt");
			if(!ff.exists()||!ft.exists())
				return "1";//Fail
			
			
			FileReader fr;
			BufferedReader br;
			
			
			for(int i=2 ; i>0; i--){
				
				if(first){
					fr = new FileReader(ff);
					br = new BufferedReader(fr);
					tmpString = br.readLine();
					in = new Scanner(tmpString);
					
					while(in.hasNext()){
						tmpID = in.next();
						System.out.println(tmpID + " @181.FriendMethods.java");
						if(tmpID.equals(toID)){
							continue;
						}
						else{
							tmpf.add(tmpID + " ");
						}
					}
					System.out.println(tmpf + " @188.FriendMethods.java");
					first = false;
				}
				else{
					fr = new FileReader(ft);
					br = new BufferedReader(fr);
					tmpString = br.readLine();
					in = new Scanner(tmpString);
					
					while(in.hasNext()){
						tmpID = in.next();
						System.out.println(tmpID + " @200.FriendMethods.java");
						if(tmpID.equals(fromID)){
							continue;
						}
						else{
							tmpt.add(tmpID + " ");
						}
					}
					System.out.println(tmpt + " @207.FriendMethods.java");
					br.close();
					in.close();
				}
				
			}
			
			
			if(tmpf.contains(toID)||tmpt.contains(fromID))
				return "1";
			
			FileWriter fwf = new FileWriter("./data/" + fromID + "/FriendList.txt");
			FileWriter fwt = new FileWriter("./data/" + toID + "/FriendList.txt");
			
			for(int i = 0 ; i< tmpf.size() ; i++)
				fwf.write(tmpf.get(i));
			for(String r: tmpt)
				fwt.write(r);
			
			fwf.close();
			fwt.close();
			System.out.println("DeleteComplete@224.FriendMethods.java");
			return "-1";
		}catch(Exception e){
			System.out.println("Error: " + e + "@193.FriendMethods.java");
		}
		
		
		return "0";
	}

}
