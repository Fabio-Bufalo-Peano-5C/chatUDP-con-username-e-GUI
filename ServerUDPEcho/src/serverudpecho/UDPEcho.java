/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverudpecho;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Accoun Utente
 */

class Clients {
    InetAddress addr;
    int port;
    
    public Clients(InetAddress addr, int port) {
        this.addr = addr;
        this.port = port;
    }
}

public class UDPEcho implements Runnable {

    private DatagramSocket socket;
    Clients client = new Clients(InetAddress.getByName("0.0.0.0"), 0);


    public UDPEcho(int port) throws SocketException, UnknownHostException {
        socket = new DatagramSocket(port);
    }

    public void run() {
        DatagramPacket answer;
        byte[] buffer = new byte[8192]; 
        DatagramPacket richiesta = new DatagramPacket(buffer, buffer.length);
        HashMap<String, Clients> clients = new HashMap<String, Clients>();
        String username;
        String messaggio;
        
        while (!Thread.interrupted()){
            try {
                socket.receive(richiesta);
                client.addr = richiesta.getAddress();
                client.port = richiesta.getPort();
                username = client.addr.getHostAddress() + client.port;
                System.out.println(username);
                if(clients.get(username) == null) {
                    clients.put(username, new Clients(client.addr, client.port)); 
                }
                System.out.println(clients);
                messaggio = new String(richiesta.getData(), 0, richiesta.getLength(), "ISO-8859-1");
                if(messaggio == "quit") {
                    clients.remove(username);
                }
                for(Clients clnt: clients.values()) {
                    answer = new DatagramPacket(richiesta.getData(), richiesta.getLength(), clnt.addr, clnt.port);
                    socket.send(answer);
                }
            } catch (IOException ex) {
                Logger.getLogger(UDPEcho.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
