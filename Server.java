import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public static void main(String[] args) throws IOException {
        // socket to listen for datagrams
        DatagramSocket socket = new DatagramSocket(8080);

        // recieve buffer
        byte[] buffer = new byte[65535];

        // server runs until client sends quit
        while (true) {
            // create datagram shell
            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);

            // recive the datagram
            socket.receive(datagram);

            // show recieved data
            System.out.println("Client sent: " + byteToString(buffer));

            // quit if client quit
            if (byteToString(buffer).equals("quit")
                    || byteToString(buffer).equals("q")) {
                System.out.println("EXITING...");
                break;
            }

            // clear buffer after each message
            buffer = new byte[65535];
        }
        socket.close();
    }

    private static String byteToString(byte[] bytes) {
        // null check
        if (bytes == null)
            return null;
        StringBuilder stringBuilder = new StringBuilder();
        // iterates through bytes, casting into characters
        // stops when hits empty byte
        for (int i = 0; bytes[i] != 0; i++) {
            stringBuilder.append((char) bytes[i]);
        }
        return stringBuilder.toString();
    }
}
