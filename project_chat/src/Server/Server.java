package src.Server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import src.UdpHandlers.Multicastreceiver;
import src.UdpHandlers.UDPhandler;

public class Server {

    public Map<String, ClientHandler> clients;
    public String serverName;
    public int portNumber;

    public Server(String serverName, int portNumber){
        this.serverName = serverName;
        this.portNumber = portNumber;
        this.clients = new HashMap<>();
    }

    public static void main(String[] args) throws IOException{
        
        if(args.length != 2){
            System.err.println("\nArguments should of the form <server_name> <port_number>\n");
            System.exit(1);
        }
        Server server = new Server(args[0], Integer.parseInt(args[1]));
        server.run();
    }

    public void run() throws IOException{
        System.out.println("Started server " + serverName);
        try(ServerSocket serverSocket = new ServerSocket(portNumber)) {

            new Thread(new UDPhandler(this, portNumber)).start();
            new Thread(new Multicastreceiver("")).start();

            while(true){
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(this, clientSocket);
                new Thread(handler).start();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendTCPMessage(String nickname, String message){
        System.out.println(message);
        for (Map.Entry<String, ClientHandler> entry : clients.entrySet()) {
            String key = entry.getKey();
            ClientHandler handler = entry.getValue();
            if(nickname.equals(key)){
                continue;
            }
            handler.out.println(message);
        }
    }

    public synchronized void sendUDPMessage(String nickname, String message, DatagramSocket socket) throws UnsupportedEncodingException{
        System.out.println("(" + nickname + ")\n" + message);
        for (Map.Entry<String, ClientHandler> entry : clients.entrySet()) {
            String key = entry.getKey();
            ClientHandler handler = entry.getValue();
            if(nickname.equals(key)){
                continue;
            }
            byte[] buff = (nickname + " " + message).getBytes();
            DatagramPacket packet = new DatagramPacket(buff, buff.length, 
            handler.clientSocket.getLocalAddress(), handler.clientSocket.getPort());
            try {
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized boolean addClient(String nickname, ClientHandler handler){
        if(clients.containsKey(nickname)){
            return false;
        }
        clients.put(nickname, handler);
        return true;
    }

    public synchronized void removeClient(String nickname){
        if(clients.containsKey(nickname)){
            clients.remove(nickname);
        }
    }
}