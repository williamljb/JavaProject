import java.io.*;
import java.net.*;

public class ImageSender implements Runnable{

	File receiveAddress;
	
	ImageSender(String add)
	{
		this.receiveAddress = new File(add);
	}
	
	public void run()
	{
        int length = 0;
        byte[] sendBytes = null;
        Socket socket = null;
        DataOutputStream dos = null;
        FileInputStream fis = null;
        try {
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(Client.ipAddress, 33457), 10 * 1000);
                dos = new DataOutputStream(socket.getOutputStream());
                File file = this.receiveAddress;
                fis = new FileInputStream(file);
                sendBytes = new byte[1024];
                while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
                    dos.write(sendBytes, 0, length);
                    dos.flush();
                }
            } finally {
                if (dos != null)
                    dos.close();
                if (fis != null)
                    fis.close();
                if (socket != null)
                    socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
    /*public static void main(String[] args) {
    	ImageReceiver test = new ImageReceiver("./a.png");
    	new Thread(test).start();
    }*/
}