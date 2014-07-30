package background;

import java.io.*;
import java.net.*;

public class ImageReceiver implements Runnable {

	File receiveAddress;
	ServerSocket server;
	
	ImageReceiver(String add)
	{
		this.receiveAddress = new File(add);
	}
	
    /*public static void main(String[] args) {
    	ImageReceiver test = new ImageReceiver("./b.png");
    	new Thread(test).start();
    }*/

    public void run() {
        try {
            server = new ServerSocket(33457);
            Thread th = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            //System.out.println("27@ImageReceiver.java");
                            Socket socket = server.accept();
                            //System.out.println("29@ImageReceiver.java");
                            receiveFile(socket);
                            server.close();
                            break;
                        } catch (Exception e) {
                        }
                    }
                }

            });

            th.run(); 
            th.join();
            System.out.println("41@ImageReceiver.java");
            //server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	if(server != null){
        		try {
					server.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
    }

    public void receiveFile(Socket socket) {

        byte[] inputByte = null;
        int length = 0;
        DataInputStream dis = null;
        FileOutputStream fos = null;
        try {
            try {

                dis = new DataInputStream(socket.getInputStream());
                fos = new FileOutputStream(receiveAddress);
                inputByte = new byte[1024];
                System.out.println("59@ImageReceiver.java");
                while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
                    //System.out.println(length);
                    fos.write(inputByte, 0, length);
                    fos.flush();
                }
                //System.out.println("65@ImageReceiver.java");
            } finally {
                if (fos != null)
                    fos.close();
                if (dis != null)
                    dis.close();
                if (socket != null)
                    socket.close();
            }
        } catch (Exception e) {

        }

    }
}
