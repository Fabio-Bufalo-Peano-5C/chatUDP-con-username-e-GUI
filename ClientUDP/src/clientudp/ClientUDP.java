/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientudp;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Accoun Utente
 */
public class ClientUDP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException {

        String IP_address = "127.0.0.1";
        InetAddress address = InetAddress.getByName(IP_address);
        int UDP_port = 5000;


        DatagramSocket socket;
        try {

            socket = new DatagramSocket();
            
            Thread receiveAndPrint = new Thread(new ReceiveFromServerAndPrint(socket));
            receiveAndPrint.start();
            System.out.println("sono in ascolto...");

            Thread sendUserInput = new Thread(new SendUserInputToServer(socket, address, UDP_port));
            sendUserInput.start();
            System.out.println("utente e' invitato di inserire un messaggio da inviare al server...");


            System.out.println("connessione server riuscita");

            sendUserInput.join(); 
            receiveAndPrint.interrupt();
            receiveAndPrint.join();  
            socket.close(); 

        } catch (SocketException ex) {
            System.out.println("ERROR: connessione server non riuscita");
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }

        }
    
}
