package Client;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ReceiveClient extends javax.swing.JFrame  implements Runnable{
	private Socket socket;
	private DataInputStream dis;
	public ReceiveClient(Socket socket) throws IOException {
		this.socket = socket;
		this.dis = new DataInputStream(this.socket.getInputStream());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String sms;
		try{
			while(true){
				Chat.a1.setLayout(new BorderLayout());
				sms = dis.readUTF();
				JPanel p2 = Chat.formatLabel(sms);
				JPanel left = new JPanel(new BorderLayout());
				left.add(p2, BorderLayout.LINE_START);

				Chat.vertical.add(left);
				Chat.vertical.add(Box.createVerticalStrut(15));
				Chat.a1.add(Chat.vertical, BorderLayout.PAGE_START);
				Chat.f1.validate();
			}

		}catch(Exception e){}

		
		
	}
	
}
