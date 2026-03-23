import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) throws IOException {
        // takes input from command line for datagram
        Scanner sc = new Scanner(System.in);

        // socket to send and recieve datagrams
        DatagramSocket socket = new DatagramSocket(8081);

        // local ip address
        InetAddress ipAddress = InetAddress.getLocalHost();

        // byte buffer for datagram creation
        byte[] buffer = new byte[65535];

        // Client flags
        boolean sending = true;
        boolean quiting = false;

        // client runs until user enters "quit"
        // client swaps mode after sending
        while (true) {
            while (sending) {
                // clear buffer
                buffer = new byte[65535];

                // collect datagram data from command line
                String input = sc.nextLine();
                buffer = input.getBytes();

                // create datagram
                DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, ipAddress, 8080);

                // send datagram
                socket.send(datagram);

                // quit on user command
                if (input.equals("quit") || input.equals("q")) {
                    quiting = true;
                    break;
                }

                // swap mode
                sending = false;
            }

            // client swaps mode after recieving [or timeout]
            while (!sending) {
                // clear buffer
                buffer = new byte[65535];

                // create datagram shell
                DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);

                // receive the datagram (blocks until recieved)
                socket.receive(datagram);

                // show recieved data
                System.out.println("Server sent: " + byteToString(buffer));

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
            // end complete loop if broken.
            if (quiting)
                break;
        }
        sc.close();
        socket.close();
    }

    private static String byteToString(byte[] bytes) {
        // null check
        if (bytes == null)
            return null;
        StringBuilder stringBuilder = new StringBuilder();
        // iterates through bytes, casting into characters
        // stops when hits empty byte
        for (int i = 0; i < bytes.length; i++) {
            stringBuilder.append((char) bytes[i]);
        }
        return stringBuilder.toString();
    }
}