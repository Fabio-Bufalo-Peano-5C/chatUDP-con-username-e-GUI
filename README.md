# chatUDP-con-username-e-GUI
Classi java per chat UDP tra client e server con GUI e username

Lo User Datagram Protocol, abbreviato UDP, è un protocollo non sicuro che consente l’invio senza connessione di datagrammi nelle reti basate su indirizzi IP. Per raggiungere i servizi desiderati sugli host di destinazione, il protocollo utilizza le porte (che sono il componente principale nella connessione UDP). Utilizzando il protocollo UDP un’applicazione può inviare delle informazioni molto velocemente, in quanto non occorre creare una connessione con il destinatario né attendere una risposta, però non vi sono garanzie che i pacchetti arrivino interi e nella stessa sequenza in cui sono stati inviati.
Quindi:
- UDP è senza connessione;
- UDP utilizza le porte;
- UDP consente una comunicazione rapida e senza ritardi;
- UDP non offre alcuna garanzia in merito alla sicurezza e alla genuinità dei dati;

In java per creare una connessione UDP si usa la classe DatagramSocket:
Le socket DATAGRAM permettono a due thread di scambiarsi messaggi senza stabilire una
connessione tra i thread convolti. Questo meccanismo di comunicazione non è affidabile perchè possiamo avere possibili perdite di messaggi (per problemi di rete) e una consegna non ordinata dei pacchetti.
In java dobbiamo creare un solo tipo di socket DATAGRAM sia per il Client sia per il Server.

                                                    LATO SERVER
- Con la riga di codice "DatagramSocket nomeSocket = new DatagramSocket(numeroPorta)" creiamo un socket in ascolto sulla porta indicata.
- Dobbiamo creare un datagram packet "DatagramPacket nomeDatagramPacket = new DatagramPacket(buffer, buffer.length)" che crea un pacchetto coi buffer che noi abbiamo precedentemente creato utilizzando il codice "byte[] buffer = new byte[1024]".
- Dopo abbiamo bisogno di ricevere il pacchetto in arrivo dal client e lo facciamo tramite un server.receive(nomeDatagramPacket).
- I comandi nomeDatagrampacket.getAddress() e nomeDatagrampacket.getPort() ci permettono di risalire all'indirizzo IP  e alla porta con i quali il pacchetto è stato inviato, quindi ci permettono di scoprire il mittente del messaggio in arrivo.


                                                    LATO CLIENT
- Con la riga di codice "DatagramSocket nomeSocket = new DatagramSocket()" creiamo un socket per l'invio dei messaggi.
- Dobbiamo creare un datagram packet " DatagramPacket sendpack = new DatagramPacket(buffer, buffer.length, InetAddress.getLoopbackAddress() , 9999);" che crea il pacchetto da inviare col buffer che noi abbiamo precedentemente creato utilizzando il codice "byte[] buffer = new byte[1024]" e nel buffer insieriamo i dati da inviare con i comandi: 
                      - buffer1 = message.getBytes();
                      - buffer = username1.getBytes();
- Dopo abbiamo bisogno di inviare il pacchetto in arrivo dal client e lo facciamo tramite un client.send(nomeDatagramPacket).
- I comandi nomeDatagrampacket.getAddress() e nomeDatagrampacket.getPort() ci permettono di risalire all'indirizzo IP  e alla porta con i quali il pacchetto è stato inviato, quindi ci permettono di scoprire il mittente del messaggio in arrivo.                                         
