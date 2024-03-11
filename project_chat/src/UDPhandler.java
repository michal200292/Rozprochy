package src;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class UDPhandler implements Runnable{
    public final int portNumber;
    DatagramSocket socket;
    DatagramPacket packet;
    private final boolean server;

    public UDPhandler(int portNumber, boolean server){
        this.portNumber = portNumber;
        this.server = server;
    }
    
    public void run(){
        try {
            socket = new DatagramSocket(portNumber);
            byte[] buff = new byte[1024];
            while(true){
                Arrays.fill(buff, (byte)0);
                packet = new DatagramPacket(buff, buff.length);
                socket.receive(packet);
                String msg = new String(packet.getData());
                
                String [] arr = msg.split("\n", 2);
                if(!server){
                    System.out.println("(" + arr[0] + ")");
                    System.out.println(arr[1]);
                }
                else{
                    Server.sendUDPMessage(arr[0], arr[1], socket);
                }
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(socket != null){
                socket.close();
            }
        }
    }
}
