package background;

import java.io.*;
import java.util.*;

class MessageMethods {

	/*public static void main(String[] args){
		
		Scanner in = new Scanner(System.in);
		String text;
		String fromID = "a";
		String toID = "b";
		
		while(in.hasNext()){
			text = in.next();
			SendMessage(fromID, toID, fromID + ": " + text);
			text = in.next();
			SendMessage(fromID, toID, fromID + ": " + text);
			text = in.next();
			SendMessage(toID, fromID, toID + ": " + text);
		}
		
		in.close();
		
		String fromID = "a";
		String toID = "b";
		CheckUnread(fromID, toID);
		CheckUnread(toID, fromID);
		
		
		return;
	}*/
	
	
	static synchronized File FileAccessor(String Host, String Guest, String command){
		
		if(command.equals("Check"))
			return new File("./data/" + Host + "/" + Guest + "_UNREAD.txt");
		else if(command.equals("Send"))
			return new File("./data/" + Host + "/" + Guest + "_HISTORY.txt");
		
			return new File("./data/" + Host + "/null_HISTORY.txt");
		
	}
	
	
	static String CheckUnread(String fromID, String tgID){
		
		try{
			File f = FileAccessor(fromID, tgID, "Check");
			if(!f.exists())
				return "";
			else{
				
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				ArrayList<String> urd = new ArrayList<String>();
				String tmp = new String();
				tmp = br.readLine();
				if(tmp==null){
					br.close();	
					return "";
				}
				else{
					while(tmp!=null){
						urd.add(tmp);
						tmp = br.readLine();
					}
					br.close();
					tmp = "";
				}
				
				tmp = "";
				for (int i = 0; i < urd.size(); ++i)
					tmp = tmp + urd.get(i) + "\n";
				
				FileWriter fw = new FileWriter(f);
				ArrayList<String> clean = new ArrayList<String>();
				for(String w: clean)
					fw.write(w);
				fw.close();
				fw = new FileWriter(FileAccessor(fromID, tgID, "Send"), true);
				fw.write(tmp);	
				fw.close();
				tmp = String.valueOf(urd.size()) + "\n" + tmp;
				return tmp;
			}

		}catch(Exception e){
			System.out.println("Error: " + e + " @88.CheckUnread.MessageMethods.java");
		}
		return "";
	}
	
	static synchronized String SendMessage(String fromID, String toID, String text){
		
		try{
			FileWriter fwu = new FileWriter(FileAccessor(toID, fromID, "Check"), true);
			fwu.write("1" + text + "\n");
			fwu.close();
			FileWriter fwh = new FileWriter(FileAccessor(fromID, toID, "Send"), true);
			fwh.write("0" + text + "\n");
			fwh.close();
		}catch(Exception e){
			System.out.println("Error: " + e + " @103.SendMessage.MessageMethods.java");
		}
		
		
		return "-1";
	}
	
	
}
