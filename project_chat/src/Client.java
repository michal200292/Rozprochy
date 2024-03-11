package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        if(args.length != 3){
            System.err.println("Arguments should of the form <client_name> <server_ip> <port_number>");
        }
        String clientName = args[0];
        String hostName = args[1];
        Integer portNumber = null;
        try{
            portNumber = Integer.parseInt(args[2]);
        }
        catch (NumberFormatException e){
            System.err.println("Port should be an Integer");
            System.exit(1);
        }

        Scanner input = new Scanner(System.in);
        Socket socket = null;
        DatagramSocket udpSocket = null;
        DatagramPacket packet = null;
        InetAddress address = InetAddress.getByName(hostName);
        byte[] buff;

        try {
            socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(clientName);
            String response = in.readLine();

            if(response.equals("NACK")){
                System.out.println("Nickname " + clientName + " is already taken");
                return;
            }
            else{
                System.out.println("Connected to server");
            }
            
            udpSocket = new DatagramSocket();

            UDPhandler listenerUDP = new UDPhandler(socket.getLocalPort(), false);
            new Thread(listenerUDP).start();
            out.println(socket.getLocalPort());

            ListenerTCP listenerTCP = new ListenerTCP(socket, in);
            new Thread(listenerTCP).start();
            
            String line = "";
            StringBuilder udpMsg = new StringBuilder();
            
            while(!line.equals("end") && input.hasNextLine()){
                line = input.nextLine();
                line.trim();
                if(line.length() == 1 && line.charAt(0) == 'U'){
                    udpMsg.setLength(0);
                    udpMsg.append(clientName);
                    udpMsg.append("\n");
                    while(!line.equals("") && input.hasNextLine()){
                        line = input.nextLine();
                        udpMsg.append(line);
                        udpMsg.append('\n');
                    }
                    udpMsg.delete(udpMsg.length() - 2, udpMsg.length() - 1);
                    buff = udpMsg.toString().getBytes();
                    packet = new DatagramPacket(buff, buff.length, address, portNumber);
                    udpSocket.send(packet);
                }
                else{
                    out.println(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null){
                input.close();
            }
            if (socket != null){
                socket.close();
            }
            if(udpSocket != null){
                udpSocket.close();
            }
        }
    }
}
