/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatudpclient1;

import chatudpclient1.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Accoun Utente
 */
public class ChatUDPclient1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException {

        String IP_address = "10.100.21.56";
        InetAddress address = InetAddress.getByName(IP_address);
        int UDP_port = 1077;


        DatagramSocket socket;
        try {

            socket = new DatagramSocket();

        Thread sendUserInput = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    byte[] buffer;
                    String mex;
                    Scanner sc = new Scanner(System.in);
                    DatagramPacket userDatagram;
                    
                    System.out.print("> ");
                    do {
                        System.out.println("Inserisci l'username: ");
                        String username = sc.nextLine();
                        System.out.println("Inserisci il messaggio: ");
                        mex = sc.nextLine();
                        mex = "Username: " + username + " ----> Messaggio: " + mex;
                        buffer = mex.getBytes("UTF-8");
                        userDatagram = new DatagramPacket(buffer, buffer.length, address, UDP_port);
                        socket.send(userDatagram);
                    } while (mex.compareTo("quit") != 0);
                } catch (IOException ex) {
                    Logger.getLogger(ChatUDPclient1.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        Thread receiveAndPrint = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    byte[] buffer = new byte[100];
                    String received;
                    DatagramPacket datagramServer;
                    datagramServer = new DatagramPacket(buffer, buffer.length);
                    while (!Thread.interrupted()){
                        socket.receive(datagramServer);
                        received = new String(datagramServer.getData(), 0, datagramServer.getLength(), "ISO-8859-1");
                        System.out.print("\b\b\b\b\b\b> server: " + received);
                        System.out.print("\n> ");
                    }

                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(ChatUDPclient1.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ChatUDPclient1.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        System.out.println("Connessione server riuscita!");
        receiveAndPrint.start();
        System.out.println("Sono in ascolto! ");
        sendUserInput.start();
        System.out.println("Inserire un messaggio da inviare al server: ");
        sendUserInput.join();
        receiveAndPrint.interrupt();
        socket.close();
        
        } catch (SocketException ex) {
            System.err.println("ERROR: Connessione server non riuscita!");
        } catch (InterruptedException ex) {
            Logger.getLogger(ChatUDPclient1.class.getName()).log(Level.SEVERE, null, ex);
        }

        }
    
}
