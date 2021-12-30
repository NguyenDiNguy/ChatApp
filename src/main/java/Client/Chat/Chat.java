package Client.Chat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Chat extends  JFrame {
    private Socket socket;
    private String myname;
    private String name;
    public JPanel p1;
    public static JScrollPane scrollPane;
    public static JTextField t1;
    public static JButton b1;
    public static JPanel a1;
    public static JFrame f1 = new JFrame();
    public static Box vertical = Box.createVerticalBox();
    private ServerSocket skt;
    private Socket s;
    public static ExecutorService executorService = Executors.newCachedThreadPool();
    Boolean typing;
    Chat() { init(); }
    public Chat(Socket socket, String name, String myname) throws IOException {
        this.socket = socket;
        this.name = name;
        this.myname = myname;
        init();
        System.out.println(this.socket + this.name + this.myname);
        executorService.execute(new ReceiveClient(socket));
    }

    public void init(){
        f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        f1.add(p1);

        JLabel l3 = new JLabel(name);
        l3.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        l3.setForeground(Color.WHITE);
        l3.setBounds(110, 15, 100, 18);
        p1.add(l3);

        JLabel l4 = new JLabel("Active Now");
        l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        l4.setForeground(Color.WHITE);
        l4.setBounds(110, 35, 100, 20);
        p1.add(l4);

        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f1.add(a1);


        t1 = new JTextField();
        t1.setBounds(5, 655, 310, 40);
        t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f1.add(t1);

        scrollPane = new JScrollPane(a1,   ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(440, 570));
        scrollPane.setBounds(5,75,440,570);
        f1.add(scrollPane);

        b1 = new JButton("Send");
        b1.setBounds(320, 655, 123, 40);
        b1.setBackground(new Color(7, 94, 84));
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f1.add(b1);

        f1.getContentPane().setBackground(Color.WHITE);
        f1.setLayout(null);
        f1.setSize(460, 750);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setLocation(dim.width/2-f1.getSize().width/2, dim.height/2-f1.getSize().height/2);
        f1.setVisible(true);
        f1.setResizable(false);

        b1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                handleSent();
            }
        });
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
            }
        });
        t1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleSent();
                }
            }
        });
    }

    private void handleSent () {//GEN-FIRST:event_tfInputMessKeyPressed
        String out = t1.getText().trim();
        if(!out.equalsIgnoreCase("")) {
            executorService.execute(new SentUI(out));
            executorService.execute(new SentClient(socket, out));
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">" + out + "</p></html>");
        l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        l1.setBackground(new Color(37, 211, 102));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15, 15, 15, 50));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));

        p3.add(l1);
        p3.add(l2);
        return p3;
    }
}