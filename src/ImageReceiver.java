import java.io.*;
import java.net.*;

public class ImageReceiver implements Runnable {

	File receiveAddress;
	ServerSocket server;
	boolean shouldClose = false;
	
	ImageReceiver(String add)
	{
		this.receiveAddress = new File(add);
	}
	
    /*public static void main(String[] args) {
    	ImageSender test = new ImageSender("./b.png");
    	new Thread(test).start();
    }*/

    public void run() {
        try {
            server = new ServerSocket(33457);
            Thread th = new Thread(new Runnable() {
                public void run() {
                    while (!shouldClose) {
                        try {
                            Socket socket = server.accept();
                            receiveFile(socket);
                            server.close();
                            break;
                        } catch (Exception e) {
                            try {
								server.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                            break;
                        }
                    }
                }

            });

            th.run(); 
        } catch (Exception e) {
            e.printStackTrace();
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
                while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
                    fos.write(inputByte, 0, length);
                    fos.flush();
                }
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
