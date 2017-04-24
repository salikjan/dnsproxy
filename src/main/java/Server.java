import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Arrays;

/**
 * Created by Salavat.Yalalov on 24.04.2017.
 */
public class Server {
    public static void main(String[] args) {
        DatagramSocket client = null;
        DatagramSocket server = null;
        DatagramPacket clientPacket = null;
        int port = 53;
        byte[] buffer = new byte[1024];

        try {
            client = new DatagramSocket(port);
            System.out.println("DNS Proxy server started on port udp/" + port);

            while (true) {
                Arrays.fill(buffer, (byte)0);
                server = new DatagramSocket();

                // Initialize packet with buffer to store data
                clientPacket = new DatagramPacket(buffer, buffer.length);
                // Wait until receive data on port we are listening on (53/udp)
                client.receive(clientPacket);

                // Store ip address and port if client
                InetAddress clientInetAddress = clientPacket.getAddress();
                int clientPort = clientPacket.getPort();

                // Prepare datagram to send to uplink dns server and send it
                InetSocketAddress serverAddress = new InetSocketAddress("8.8.8.8", 53);
                DatagramPacket serverPacket = new DatagramPacket(buffer, buffer.length, serverAddress);
                server.send(serverPacket);

                // Prepare datagram packet to receive an answer from uplink dns
                serverPacket = new DatagramPacket(buffer, buffer.length);
                server.receive(serverPacket);

                server.close();

                // Retreive data from an answer from uplink dns and send it to client
                buffer = serverPacket.getData();
                clientPacket = new DatagramPacket(buffer, serverPacket.getLength(), clientInetAddress, clientPort);
                client.send(clientPacket);
            }

        } catch (IOException e) {
            if (client != null) {
                client.close();
            }
            e.printStackTrace();
        }
    }
}
