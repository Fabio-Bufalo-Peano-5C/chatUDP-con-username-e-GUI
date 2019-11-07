/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientudp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Accoun Utente
 */
public class ChatClient extends JFrame implements ActionListener {

    private JTextField toclient = new JTextField();
    private JTextArea display = new JTextArea();
    private JButton send = new JButton("Send/Start Client");
    byte[] buffer, buffer1;
    DatagramSocket client;
    ServerSocket socket;
    String username;
    String messaggio;
    String IP_address = "127.0.0.1";
    InetAddress address = InetAddress.getByName(IP_address);

    public ChatClient() throws SocketException, UnknownHostException {
        JPanel input = new JPanel();
        input.setLayout(new BorderLayout());
        input.setBorder(new TitledBorder("Enter Message"));
        input.add(toclient, BorderLayout.CENTER);
        input.add(send, BorderLayout.EAST);

        JPanel output = new JPanel();
        output.setLayout(new BorderLayout());
        output.setBorder(new TitledBorder("Conversation"));
        output.add(display, BorderLayout.CENTER);

        JPanel gabung = new JPanel();
        gabung.setLayout(new GridLayout(2, 1));
        gabung.add(input);
        gabung.add(output);
        buffer = new byte[1024];
        buffer1 = new byte[1024];

        this.getContentPane().add(gabung, BorderLayout.NORTH);
        send.addActionListener(this);

        setTitle("Chat Client");
        setSize(500, 300);
        setVisible(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client = new DatagramSocket();
                    socket = new ServerSocket();
                    while (true) {
                        DatagramPacket datapack = new DatagramPacket(buffer1, buffer1.length);
                        socket.accept();
                        client.receive(datapack);
                        String msg = new String(datapack.getData());
                        display.append("\nServer:" + msg);
                    }
                } catch (Exception e) {
                }
            }
        }).start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(send)) {
            try {
                String message = toclient.getText();
                buffer1 = message.getBytes();
                DatagramPacket sendpack = new DatagramPacket(buffer1, buffer1.length, InetAddress.getLocalHost(), 9998);
                client.send(sendpack);
                display.append("\nMyself:" + message);
                toclient.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
