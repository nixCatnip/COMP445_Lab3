import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        // socket to send and recieve datagrams
        DatagramSocket socket = new DatagramSocket(8080);

        // recieve buffer
        byte[] buffer = new byte[65535];

        // local ip address
        InetAddress ipAddress = InetAddress.getLocalHost();

        // Server flags
        boolean sending = false;
        boolean quiting = false;

        // server runs until client sends quit
        while (true) {
            while (!sending) {
                // clear buffer
                buffer = new byte[65535];

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
                    quiting = true;
                    break;
                }

                // swap mode
                sending = true;
            }
            while (sending) {
                // clear buffer
                buffer = new byte[65535];

                // send ACK
                // create dummy imput for testing
                String dummyInput = "Testing";
                buffer = dummyInput.getBytes();

                // create datagram
                DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, ipAddress, 8081);

                // send datagram
                socket.send(datagram);

                // swap mode
                sending = false;
            }
            // end complete loop if broken.
            if (quiting)
                break;
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
