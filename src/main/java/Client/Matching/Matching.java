package Client.Matching;

import Client.Chat.Chat;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Matching implements Runnable{
    private Socket socket;
    private DataOutputStream dos = null;
    private DataInputStream dis = null;
    private String name;
    public Matching(Socket socket, String name) throws IOException {
        this.socket = socket;
        this.name = name;
        this.dos = new DataOutputStream(this.socket.getOutputStream());
        this.dis = new DataInputStream(this.socket.getInputStream());
    }
    public Socket createConnect() throws IOException {
        ServerSocket svSocket = new ServerSocket(0);
        dos.writeUTF(svSocket.getLocalSocketAddress().toString());
        System.out.println("Đang chờ client kết nối");
        return svSocket.accept();
    }
    public Socket connect(String address) throws Exception{
        System.out.println(address);
        StringTokenizer st = new StringTokenizer(address,"/");
        st.nextToken();
        st = new StringTokenizer(st.nextToken(),":");
        String hot = st.nextToken();
        int port = Integer.parseInt(st.nextToken());
        System.out.println(hot + port);
        return socket = new Socket(hot, port);
    }
    @Override
    public void run() {
        try {
            while (true) {
                String read = dis.readUTF();
                StringTokenizer st = new StringTokenizer(read, ";");
                Boolean waiter = (st.nextToken().equalsIgnoreCase("A")) ? true : false;
                String name = st.nextToken();
                System.out.println(name);
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like to chat with " + name + " ?", "Find match", dialogButton);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    dos.writeBoolean(true);
                    if (waiter) {
                        Chat chat = new Chat(createConnect(), name, this.name);
                        chat.setVisible(true);
                    } else {
                        String address = dis.readUTF();
                        if (!address.equalsIgnoreCase("reject")) {
                            Chat chat = new Chat(connect(address), name, this.name);
                            chat.setVisible(true);
                        } else {
                            //thong bao tu choi ghep doi
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
