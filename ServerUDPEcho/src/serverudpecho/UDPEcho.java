/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverudpecho;

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
import java.util.HashMap;
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
class Clients {

    int port;
    InetAddress addr;
    String IP_address = "127.0.0.1";

    public Clients(InetAddress addr, int port) throws UnknownHostException {
        this.port = port;
        addr = InetAddress.getByName(IP_address);
    }

    Clients(int port) {
        this.port = port;
    }

}

public class UDPEcho extends JFrame implements ActionListener {

    Clients client = new Clients(9999);

    private JTextField toclient = new JTextField();
    private JTextArea display = new JTextArea();
    private JButton send = new JButton("Send/Start Server");
    DatagramSocket Client, server;
    ServerSocket socket;
    byte[] buffer, buffer1;
    HashMap<String, Clients> clients = new HashMap<String, Clients>();
    String username;
    String messaggio;

    public UDPEcho(int port) throws SocketException, UnknownHostException {
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

        setTitle("Chat Server");
        setSize(500, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server = new DatagramSocket();
                    socket = new ServerSocket();
                    while (true) {
                        socket.accept();
                        DatagramPacket datapack = new DatagramPacket(buffer, buffer.length);
                        server.receive(datapack);
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
                buffer = message.getBytes();
                DatagramPacket sendpack = new DatagramPacket(buffer, buffer.length, InetAddress.getLocalHost() , 9999);
                server.send(sendpack);
                display.append("\nMyself:" + message);
                toclient.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
