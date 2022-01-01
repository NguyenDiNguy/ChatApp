package Client.Chat;

import javax.swing.*;
import java.awt.*;

public class ReceiveUI implements Runnable{
    private String sms;
    private  Chat chat;
    public ReceiveUI (String sms, Chat chat) {
        this.sms = sms;
        this.chat = chat;
    }
    @Override
    public void run() {
            JPanel p2 = chat.formatLabel(sms);
            JPanel left = new JPanel(new BorderLayout());
            left.add(p2, BorderLayout.LINE_START);

            chat.vertical.add(left);
            chat.vertical.add(Box.createVerticalStrut(15));
            chat.a1.add(chat.vertical, BorderLayout.PAGE_START);
            chat.f1.validate();
    }
}
