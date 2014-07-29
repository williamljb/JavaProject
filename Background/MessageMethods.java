package background;

import java.io.*;
import java.net.Socket;
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
			return new File("./data" + File.separator + Host + File.separator + Guest + "_UNREAD.txt");
		else if(command.equals("Send"))
			return new File("./data" + File.separator + Host + File.separator + Guest + "_HISTORY.txt");
		
			return new File("./data" + File.separator + Host + File.separator + "null_HISTORY.txt");
		
	}
	
	
	static String CheckUnread(String fromID, String tgID, Socket socket){
		
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
				{
					/*if (isHashCode(urd.get(i)) != 0)
					{
				    	ImageSender test = new ImageSender("data" + File.separator + urd.get(i), socket);
				    	new Thread(test).start();
					}*/
					tmp = tmp + urd.get(i) + "\n";
				}
				
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
			if (isHashCode(text) != 0)
			{
				ImageReceiver test = new ImageReceiver("data" + File.separator + text);
				new Thread(test).start();
			}
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


	static int isHashCode(String sentence) {
		if (sentence.length() < 3)
			return 0;
		int code = sentence.substring(3).hashCode();
		
		try{
		if (Integer.parseInt(sentence.substring(0, 3)) == ((code % 1000 + 1000) % 1000))
			return 1;
		if (sentence.length() < 4)
			return 0;
		code = sentence.substring(4).hashCode();
		if (Integer.parseInt(sentence.substring(0, 4)) == ((code % 10000 + 10000) % 10000))
			return 2;
		else
			return 0;
		}catch(NumberFormatException e){
			return 0;
		}
		
	}
	
	static String ReturnHistory(String fromID, String tgID, Socket socket){
		
		try{
			File f = FileAccessor(fromID, tgID, "Send");
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
				{
					/*if (isHashCode(urd.get(i)) != 0)
					{
				    	ImageSender test = new ImageSender("data" + File.separator + urd.get(i), socket);
				    	new Thread(test).start();
					}*/
					tmp = tmp + urd.get(i) + "\n";
				}
				tmp = String.valueOf(urd.size()) + "\n" + tmp;
				return tmp;
			}
		}catch(Exception e){
			e.getStackTrace();
			return "";
		}
	}
	
	static String SendFile(String filename, Socket socket){
		
		try{
			System.out.println(filename + "@196.SendFile");
			String get = new String("./data" + File.separator + filename);
			ImageSender test = new ImageSender(get, socket);
			new Thread(test).start();
			
			return "-1";
		}
		catch(Exception e){
			e.printStackTrace();
			return "1";
		}
	}
}
