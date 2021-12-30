package Client.Chat;

import Client.Chat.Chat;

import javax.swing.*;
import java.awt.*;

public class ReceiveUI implements Runnable{
    private String sms;
    public ReceiveUI (String sms) {
        this.sms = sms;
    }
    @Override
    public void run() {
        JPanel p2 = Chat.formatLabel(sms);
        JPanel left = new JPanel(new BorderLayout());
        left.add(p2, BorderLayout.LINE_START);

        Chat.vertical.add(left);
        Chat.vertical.add(Box.createVerticalStrut(15));
        Chat.a1.add(Chat.vertical, BorderLayout.PAGE_START);
        Chat.f1.validate();
    }
}
