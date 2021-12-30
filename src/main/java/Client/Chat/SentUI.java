package Client.Chat;

import Client.Chat.Chat;

import javax.swing.*;
import java.awt.*;

public class SentUI implements Runnable{
    private String out;
    public SentUI(String out){
        this.out = out;
    }
    @Override
    public void run() {
        Chat.a1.setLayout(new BorderLayout());
        JPanel p2 = Chat.formatLabel(out);
        JPanel right = new JPanel(new BorderLayout());
        right.add(p2, BorderLayout.LINE_END);
        Chat.vertical.add(right);
        Chat.vertical.add(Box.createVerticalStrut(15));
        Chat.a1.add(Chat.vertical, BorderLayout.PAGE_START);
        Chat.t1.setText("");
    }
}
