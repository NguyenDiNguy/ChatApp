package Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SentClient implements Runnable{
    private Socket socket;
    private String sms;
    private DataOutputStream dos;
    public SentClient(Socket socket, String sms) {
        this.socket = socket;
        this.sms = sms;
        try {
            this.dos = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            dos.writeUTF(sms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
