package Client;
import Client.Login.Login;

import javax.swing.*;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class Client extends JFrame{

    private static int portServer = 9999;
    private static Socket socket;
    private static ExecutorService executorService = null;
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            socket = new Socket("localhost",portServer);
            System.out.println(socket);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            Login login = new Login(socket);
            login.setVisible(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
