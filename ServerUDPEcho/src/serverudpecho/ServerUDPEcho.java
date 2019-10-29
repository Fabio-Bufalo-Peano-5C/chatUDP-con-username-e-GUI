/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverudpecho;

import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Accoun Utente
 */
public class ServerUDPEcho {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        int c;
        Thread thread;
        try {
            // TODO code application logic here
            UDPEcho echoServer= new UDPEcho(5000);
            thread= new Thread(echoServer);
            thread.start();
            c=System.in.read();
            thread.interrupt();
            thread.join();
            System.out.println("Sono il main!");
        } catch (SocketException ex) {
            Logger.getLogger(ServerUDPEcho.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
