import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public static void main(String[] args) throws IOException {
        // socket to listen for datagrams
        DatagramSocket socket = new DatagramSocket(8080);

        // recieve buffer
        byte[] buffer = new byte[65535];

        // intialize datagram variable
        DatagramPacket datagram;

        // [UNDER CONSTRUCTION]
    }
}
