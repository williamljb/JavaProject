package background;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiTalkServer {
	static int clientnum=0;
	public static void main(String args[]) throws IOException 
	{
		ServerSocket serverSocket=null;
		boolean listening=true;
		try
		{ 
			serverSocket=new ServerSocket(2048);
		}catch(IOException e) 
		{ 
			System.out.println("Could not listen on port:2048.");
			System.exit(-1);
		}
		while(listening) 
		{
			new  ServerThread(serverSocket.accept(),clientnum).start();
			clientnum++; 
		}
		serverSocket.close();
	}
}
class ServerThread extends Thread
{
	Socket socket=null;
	int clientnum;
	public ServerThread(Socket socket,int num) 
	{
		this.socket=socket;
		clientnum=num+1; 
	}
	
	public void run() 
	{ 
		try
		{
			String line;
			BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter os=new PrintWriter(socket.getOutputStream());
			//BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
			
			boolean listen = true;
			
			while(listen){
			/*os.println("Test"); 
			os.flush();*/
			line=is.readLine();
			SReceiveData receive = new SReceiveData();
			//receive.receiveClientData(line);
			os.println(receive.receiveClientData(line, this.socket)); 
			os.flush();
			/*System.out.println(line);
			System.out.flush();*/
			}
			
			os.close();
			is.close(); 
			socket.close();
		}
		catch(Exception e) 
		{
			System.out.println("Error:"+e);
		}
	}
}