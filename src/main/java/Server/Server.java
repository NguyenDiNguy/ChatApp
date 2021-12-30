package Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private static int port = 8888;
	private static ServerSocket svSocket;
	private static ExecutorService executorService = null;
	public static HashMap <String, Socket> matchingList = new HashMap<String, Socket>();
	public static HashMap <String, Socket> waitingList = new HashMap<String, Socket>();
	public static HashMap <String, Socket> matchedList = new HashMap<String, Socket>();
	public static DataInputStream dis = null;
	public static DataOutputStream dos = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			svSocket = new ServerSocket(port);
			System.out.println("Server binding at port "+port);
	        System.out.println("Waiting for client...");
		}catch (Exception e){
			System.out.println("Không thể lắng nghe từ port: "+port);
		}
		executorService = Executors.newFixedThreadPool(30);
		try {
			while (true) {
				Socket socket= svSocket.accept();	
				executorService.execute(new Matching(socket));
			}
			
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}
}
