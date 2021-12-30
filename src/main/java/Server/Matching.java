package Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map.Entry;

public class Matching implements Runnable {
	private String name;
	private Socket socket;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	public String portCl; 
	private Boolean flag = false;
	public Matching(Socket socket) {
		this.socket = socket;
		try {
			this.dis = new DataInputStream(socket.getInputStream());
			this.dos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try{
			checkName();
			match();	
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void checkName() throws IOException {
		name = dis.readUTF();
		Boolean matched = false;
		if (name.equalsIgnoreCase("\\close")) {
			System.out.println("Ngắt kết nối");
			socket.close();
			return;
		}

		while(Server.matchingList.containsKey(name)||Server.waitingList.containsKey(name)) {
			//gửi yêu cầu nhập tên khác
			System.out.println("Tên đã tồn tại");
			dos.writeBoolean(false);
			name = dis.readUTF();
			System.out.println(name);
			if (name.equalsIgnoreCase("\\close")) {
				System.out.println("Ngắt kết nối");
				socket.close();
				return;
			}
		}
		System.out.println(name+ socket.getPort()) ;
		Server.matchingList.put(name, socket);
		dos.writeBoolean(true);
	}
	public String getAcept(Socket socket) throws IOException {
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		dos.writeUTF("A;"+name);
		if (dis.readBoolean()) {
			return dis.readUTF();
		}else {
			return null;
		}
		
		
	}
	public void match() throws IOException {
		for (Entry<String, Socket> entry : Server.waitingList.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
            dos.writeUTF("B;" + entry.getKey());
            boolean result = dis.readBoolean();
            System.out.println(result);
            if (result) {
            	String accept = getAcept(entry.getValue());
            	if (accept!=null) {
            		flag = true;
            		dos.writeUTF(accept);
            		break;
            	}else {
            		dos.writeUTF("reject");
            	}
            }
        }
		if (!flag) {
			System.out.println("Chuyển vào hàng đợi");
			Server.waitingList.put(name, socket);
			Server.matchingList.remove(name);
		}else {
			System.out.println("Kết nối thành công");
		}
	}
}