package src;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    static Map<String, ClientHandler> clients = new HashMap<>();

    public static void main(String[] args) throws IOException{
        
        if(args.length != 2){
            System.err.println("\nArguments should of the form <server_name> <port_number>\n");
            System.exit(1);
        }
        String serverName = args[0];
        Integer portNumber = null;
        try{
            portNumber = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e){
            System.err.println("Port should be an Integer");
            System.exit(1);
        }
        System.out.println("Started server " + serverName);
        

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portNumber);   
            UDPhandler udpHandler = new UDPhandler(portNumber, true); 
            new Thread(udpHandler).start();

            while(true){
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if (serverSocket != null){
                serverSocket.close();
            }
        }
    }

    static synchronized void sendTCPMessage(String nickname, String message){
        System.out.println(message);
        for (Map.Entry<String, ClientHandler> entry : Server.clients.entrySet()) {
            String key = entry.getKey();
            ClientHandler handler = entry.getValue();
            if(nickname.equals(key)){
                continue;
            }
            handler.out.println(message);
        }
    }

    static synchronized void sendUDPMessage(String nickname, String message, DatagramSocket socket) throws UnsupportedEncodingException{
        System.out.println("(" + nickname + ")\n" + message);
        for (Map.Entry<String, ClientHandler> entry : Server.clients.entrySet()) {
            String key = entry.getKey();
            ClientHandler handler = entry.getValue();
            if(nickname.equals(key)){
                continue;
            }
            byte[] buff = (nickname + '\n' + message).getBytes();
            DatagramPacket packet = new DatagramPacket(buff, buff.length, 
            handler.clientSocket.getLocalAddress(), handler.clientPort);
            try {
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static synchronized boolean addClient(String nickname, ClientHandler handler){
        if(Server.clients.containsKey(nickname)){
            return false;
        }
        Server.clients.put(nickname, handler);
        return true;
    }

    static synchronized void removeClient(String nickname){
        if(Server.clients.containsKey(nickname)){
            Server.clients.remove(nickname);
        }
    }

}