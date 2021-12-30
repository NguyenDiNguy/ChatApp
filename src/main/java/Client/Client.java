package Client;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client extends JFrame{

    private static int portServer = 8888;
    private static Socket socket;
    private static ExecutorService executorService = null;
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            socket = new Socket("localhost",8888);
            System.out.println(socket);

            Login login = new Login(socket);
            login.setVisible(true);
//			Thread matching = new Thread(new Matching(socket));
//			matching.start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
