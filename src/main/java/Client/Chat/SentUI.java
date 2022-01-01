package Client.Chat;

import Client.Chat.Chat;

import javax.swing.*;
import java.awt.*;

public class SentUI implements Runnable{
    private String out;
    private Chat chat;
    public SentUI(String out, Chat chat){
        this.out = out;
        this.chat = chat;
    }
    @Override
    public void run() {
        chat.a1.setLayout(new BorderLayout());
        JPanel p2 = chat.formatLabel(out);
        JPanel right = new JPanel(new BorderLayout());
        right.add(p2, BorderLayout.LINE_END);
        chat.vertical.add(right);
        chat.vertical.add(Box.createVerticalStrut(15));
        chat.a1.add(chat.vertical, BorderLayout.PAGE_START);
        chat.t1.setText("");
    }
}
