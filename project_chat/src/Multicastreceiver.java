package src;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class Multicastreceiver implements Runnable{
    public final int portNumber;
    MulticastSocket socket;
    DatagramPacket packet;
    private byte[] buff;
    private InetAddress group;

    public Multicastreceiver(int portNumber){
        this.portNumber = portNumber;
        this.buff = new byte[1024];
    }
    
    public void run(){
        try {
            socket = new MulticastSocket(portNumber);
            group = InetAddress.getByName("224.0.0.1");
            socket.joinGroup(group);

            while(true){
                Arrays.fill(buff, (byte)0);
                packet = new DatagramPacket(buff, buff.length);
                socket.receive(packet);

                String msg = new String(packet.getData());
                String [] arr = msg.split(" ", 2);
                System.out.println("(" + arr[0] + ")");
                System.out.println(arr[1]); 
            }
        } catch (Exception e) {}
        finally{
            if(socket != null){
                try {
                    socket.leaveGroup(group);
                } catch (IOException e) {}
                socket.close();
            }
        }
    }
}
