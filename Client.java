import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) throws IOException {
        // takes input from command line for datagram
        Scanner sc = new Scanner(System.in);

        // socket to send datagrams
        DatagramSocket socket = new DatagramSocket();

        // local ip address
        InetAddress ipAddress = InetAddress.getLocalHost();

        // byte buffer for datagram creation
        byte[] buffer = null;

        // client runs until user enters "quit"
        while (true) {
            // collect datagram data from command line
            String input = sc.nextLine();
            buffer = input.getBytes();

            // create datagram
            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, ipAddress, 8080);

            // send datagram
            socket.send(datagram);

            // quit on user command
            if (input.equals("quit") || input.equals("q")) {
                break;
            }
        }
        sc.close();
        socket.close();
    }
}