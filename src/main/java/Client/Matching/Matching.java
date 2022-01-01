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
        this.name = name;
        this.waitingUI = waitingUI;
        this.dos = new DataOutputStream(this.socket.getOutputStream());
        this.dis = new DataInputStream(this.socket.getInputStream());
        flag = true;
        this.dos.writeUTF("\\Match");
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
                        dos.writeBoolean(true);
                        Chat chat = new Chat(createConnect(), name, waitingUI);
                        dos.writeUTF("\\Break");
                        chat.setVisible(true);
                        waitingUI.setVisible(false);
                        flag=false;
                        this.svSocket.close();
                    } else {
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
                    }
                }else{
                    dos.writeBoolean(false);
                }
            }
        }catch (Exception e){
        }
    }
}
