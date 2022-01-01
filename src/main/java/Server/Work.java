package Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class Work implements Runnable {
	private String name;
	private Socket socket;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	public String portCl;
	private Thread match = null;
	public Boolean matchFlag = true;
	public Work(Socket socket) {
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
		Boolean exit = false;
		try{
			while(!exit){
				System.out.println("Đợi client");
				String command = dis.readUTF();
				switch ( command ) {
					case  "\\Login":
						System.out.println("Login");
						checkName();
						break;
					case  "\\Match":
						match();
						System.out.println("Match");
						break;
					case  "\\Stop":
						Server.waitingList.remove(name);
						matchFlag = false;
						break;
					case  "\\Exit":
						System.out.println("exit");
						exit();
						exit = true;
						break;
					default:
				}
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	synchronized String getAccept(Socket socket) throws Exception {
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		dos.writeUTF("A;"+name);
		Thread.sleep(2000);
		if (dis.readBoolean()) {
			return dis.readUTF();
		}else {
			return null;
		}
	}
	synchronized void match() throws Exception {
		Boolean flag = false;
		matchFlag =true;
		for (Map.Entry<String, Socket> entry : Server.waitingList.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
			dos.writeUTF("B;" + entry.getKey());
			boolean result = dis.readBoolean();
			System.out.println(result);
			if (result) {
				String accept = getAccept(entry.getValue());
				System.out.println(accept);
				if (accept!=null) {
					flag = true;
					dos.writeUTF(accept);
					Server.waitingList.remove(entry.getKey());
					System.out.println("Đã gửi thông tin socket");
					break;
				}else {
					dos.writeUTF("reject");
				}
			}
		}
		if (!flag) {
			System.out.println("Chuyển vào hàng đợi");
			Server.waitingList.put(name, socket);
			if(dis.readBoolean()){
				Thread.sleep(2000);
			};

			System.out.println("Continute");
		}else {
			System.out.println("Kết nối thành công");
		}

	}
	public void exit() throws IOException {
		socket.close();
		dos.close();
		dis.close();
		Server.waitingList.remove(name);
		Server.loginList.remove(name);
	}
	public void checkName() throws IOException {
		name = dis.readUTF();
		System.out.println(name);
		Boolean matched = false;
		if (name.equalsIgnoreCase("\\close")) {
			System.out.println("Ngắt kết nối");
			socket.close();
			return;
		}

		while(Server.loginList.containsKey(name)) {
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
		Server.loginList.put(name, socket);
		dos.writeBoolean(true);
	}

}