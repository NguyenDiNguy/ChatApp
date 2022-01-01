package Client.Chat;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReceiveClient extends javax.swing.JFrame  implements Runnable{
	private Socket socket;
	private DataInputStream dis;
	private Chat chat;
	public ReceiveClient(Socket socket, Chat chat) throws IOException {
		this.socket = socket;
		this.chat = chat;
		this.dis = new DataInputStream(this.socket.getInputStream());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		chat.scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});
		String sms;
		try{
			while(true){
				sms = dis.readUTF();
				System.out.println(sms);
				if(sms.equalsIgnoreCase("\\Quit")){
					try {
						chat.handleClose();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else {
					chat.a1.setLayout(new BorderLayout());
					chat.executorService.execute(new ReceiveUI(sms, chat));
				}
			}

		}catch(Exception e){}
	}
	
}
