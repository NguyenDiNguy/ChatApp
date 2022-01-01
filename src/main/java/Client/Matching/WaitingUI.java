/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Client.Matching;

import Client.Matching.Matching;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaitingUI extends javax.swing.JFrame {
    private DataOutputStream dos = null;
    private DataInputStream dis = null;
    private  Socket socket;
    private String name;
    public Boolean flag;
    public Thread matching;
    public ExecutorService executorService;
    public WaitingUI(String name , Socket socket) throws Exception{
        this.socket = socket;
        this.name = name;
        this.dos = new DataOutputStream(this.socket.getOutputStream());
        this.dis = new DataInputStream(this.socket.getInputStream());
        executorService =  Executors.newCachedThreadPool();
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        btnStart = new javax.swing.JButton();
        lbUser = new javax.swing.JLabel();
        lbUser.setText(name);
        jLabel2 = new javax.swing.JLabel();
        jLabel2.setText("Loading...");
        jLabel2.setVisible(false);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                executorService.shutdownNow();
                try {
                    dos.writeBoolean(true);
                    dos.writeUTF("\\Exit");
                    dos.close();
                    dis.close();
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        });
        btnStart.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnStartActionPerformed(evt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        lbUser.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lbUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(104, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(lbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();

    }// </editor-fold>//GEN-END:initComponents
    public void open(){
        btnStart.setText("Start");
        btnStart.setEnabled(true);
        jLabel2.setVisible(false);
        this.setVisible(true);
    }

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) throws IOException {//GEN-FIRST:event_btnStartActionPerformed
        if(btnStart.getText().equalsIgnoreCase("Start")){
            loading();
            executorService.execute(new Matching(socket,this.name,this));
        }else{
            executorService.shutdownNow();
            jLabel2.setVisible(false);
            btnStart.setText("Start");
            executorService =  Executors.newCachedThreadPool();
        }

    }//GEN-LAST:event_btnStartActionPerformed
    public void loading(){
        jLabel2.setVisible(true);
        btnStart.setText("Stop");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStart;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lbUser;
    // End of variables declaration//GEN-END:variables
}
