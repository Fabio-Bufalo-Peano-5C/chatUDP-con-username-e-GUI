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
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
        this.addr = addr;
    }

    Clients(int port) {
        this.port = port;
    }

}

public class UDPEcho extends JFrame implements ActionListener {

    Clients client = new Clients(InetAddress.getByName("127.0.0.1"), 9999);
    private JTextArea display = new JTextArea();
    DatagramSocket Client, server;
    byte[] buffer;
    ArrayList<String> mex = new ArrayList<>();
    String messaggio;
    private HashMap<InetAddress, Integer> clientPort = new HashMap<>();

    public UDPEcho(int port) throws SocketException, UnknownHostException {
        display.setEditable(false);

        JPanel output = new JPanel();
        output.setLayout(new BorderLayout());
        output.setBorder(new TitledBorder("Conversation"));
        output.add(display, BorderLayout.NORTH);

        JPanel pnl = new JPanel();
        pnl.setLayout(new GridLayout(1, 1));
        pnl.add(output);
        buffer = new byte[1024];

        this.getContentPane().add(pnl, BorderLayout.CENTER);

        setTitle("Chat Server");
        setSize(500, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server = new DatagramSocket(9999);
                    while (true) {
                        DatagramPacket datapack = new DatagramPacket(buffer, buffer.length);
                        server.receive(datapack);
                        String msg = new String(datapack.getData());
                        display.append("\nHo ricevuto:" + msg + " da: " + datapack.getAddress() + "" + datapack.getPort());
                        server.send(new DatagramPacket(datapack.getData(), datapack.getLength(), datapack.getAddress(), datapack.getPort()));
                        display.append("\nHo inviato:" + msg + " a: " + datapack.getAddress() + "" + datapack.getPort());
                        if (!clientPort.containsKey(datapack.getAddress()) && !clientPort.containsValue(datapack.getPort())) {
                            display.append("Non ho trovato l'ip, lo salvo!");
                            clientPort.put(datapack.getAddress(), datapack.getPort());
                            if (mex.size() > 10) {
                                for (int i = mex.size() - 11; i < mex.size(); i++) {
                                    byte[] buff = mex.get(i).getBytes();
                                    DatagramPacket ultimiMSG = new DatagramPacket(buff, buff.length, datapack.getAddress(), datapack.getPort());
                                    server.send(ultimiMSG);
                                    display.append("\nHo inviato:" + new String(buff) + " a: " + datapack.getAddress() + " " + datapack.getPort());
                                }
                            } else {
                                for (int i = 0; i < mex.size(); i++) {
                                    byte[] buff = mex.get(i).getBytes();
                                    DatagramPacket ultimiMSG = new DatagramPacket(buff, buff.length, datapack.getAddress(), datapack.getPort());
                                    server.send(ultimiMSG);
                                    display.append("\nHo inviato:" + new String(buff) + " a: " + datapack.getAddress() + " " + datapack.getPort());
                                }
                            }
                        }
                        mex.add(msg);
                    }
                } catch (IOException ex) {
                }
            }
        }).start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
