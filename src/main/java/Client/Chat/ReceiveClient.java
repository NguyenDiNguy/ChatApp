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
	public ReceiveClient(Socket socket) throws IOException {
		this.socket = socket;
		this.dis = new DataInputStream(this.socket.getInputStream());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Chat.scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});
		String sms;
		try{
			while(true){
				Chat.a1.setLayout(new BorderLayout());
				sms = dis.readUTF();
				Chat.executorService.execute(new ReceiveUI(sms));
			}

		}catch(Exception e){}

		
		
	}
	
}
