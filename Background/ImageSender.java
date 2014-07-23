import java.io.*;
import java.net.*;

public class ImageSender implements Runnable {

	File receiveAddress;
	
	ImageSender(String add)
	{
		this.receiveAddress = new File(add);
	}
	
    public static void main(String[] args) {
    	ImageSender test = new ImageSender("./b.png");
    	new Thread(test).start();
    }

    public void run() {
        try {
            final ServerSocket server = new ServerSocket(33457);
            Thread th = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            //System.out.println("��ʼ����...");
                            Socket socket = server.accept();
                            //System.out.println("������");
                            receiveFile(socket);
                            break;
                        } catch (Exception e) {
                        }
                    }
                }

            });

            th.run(); //�����߳�����
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
                //System.out.println("��ʼ��������...");
                while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
                    //System.out.println(length);
                    fos.write(inputByte, 0, length);
                    fos.flush();
                }
                //System.out.println("��ɽ���");
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
