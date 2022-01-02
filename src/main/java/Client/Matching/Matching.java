package Client.Matching;

import Client.Chat.Chat;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;
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
    private WaitingUI waitingUI;
    private ServerSocket svSocket;
    public Boolean flag;
    public Matching(Socket socket, String name, WaitingUI waitingUI) throws IOException {
        this.socket = socket;
        System.out.println(socket);
        this.name = name;
        this.waitingUI = waitingUI;
        this.dos = new DataOutputStream(this.socket.getOutputStream());
        this.dis = new DataInputStream(this.socket.getInputStream());
        flag = true;
        this.dos.writeUTF("\\Match");
        System.out.println("Match Begin");
    }
    public Socket createConnect() throws IOException {
        svSocket = new ServerSocket(0);
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
            while (flag) {
                System.out.println("Đợi server");
                String read = dis.readUTF();
                if (read.equalsIgnoreCase("\\Stop")){
                    System.out.println("Stop");
                    return;
                }
                StringTokenizer st = new StringTokenizer(read, ";");
                Boolean waiter = (st.nextToken().equalsIgnoreCase("A")) ? true : false;
                String name = st.nextToken();
                int dialogButton = JOptionPane.YES_NO_OPTION;
                if (waiter) {
                    System.out.println(name);
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like to chat with " + name + " ?", "Find match", dialogButton);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        dos.writeBoolean(true);
                        dos.writeBoolean(true);
                        Chat chat = new Chat(createConnect(), name, waitingUI);
                        chat.setVisible(true);
                        waitingUI.setVisible(false);
                        flag = false;
                    } else {
                        dos.writeBoolean(false);
                        dos.writeBoolean(false);
                    }
                } else {
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like to chat with " + name + " ?", "Find match", dialogButton);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        dos.writeBoolean(true);
                        System.out.println(name);
                        String address = dis.readUTF();
                        System.out.println(address);
                        if (!address.equalsIgnoreCase("reject")) {
                            Chat chat = new Chat(connect(address), name, waitingUI);
                            chat.setVisible(true);
                            waitingUI.setVisible(false);
                            flag = false;
                        } else {
                            JOptionPane.showMessageDialog(null, "Bạn đã bị từ chối");
                        }
                    } else {
                        dos.writeBoolean(false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
