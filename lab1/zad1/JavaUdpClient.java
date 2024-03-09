import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class JavaUdpClient {

    public static void main(String args[]) throws Exception
    {
        System.out.println("JAVA UDP CLIENT");

        DatagramSocket socket = null;
        int portNumber = 9008;
        
        int receivingPort = 9009;
        DatagramSocket recSocket = null;
        byte[] receiveBuffer = new byte[1024];

        try {
            socket = new DatagramSocket();
            recSocket = new DatagramSocket(receivingPort);

            InetAddress address = InetAddress.getByName("localhost");
            byte[] sendBuffer = "Ping Java Udp".getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
            socket.send(sendPacket);
            
            Arrays.fill(receiveBuffer, (byte)0);
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

            recSocket.receive(receivePacket);
            String msg = new String(receivePacket.getData());
            System.out.println("Message from server: " + msg);

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if (socket != null) {
                socket.close();
                recSocket.close();
            }
        }
    }
}
