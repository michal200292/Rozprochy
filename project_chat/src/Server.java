package src;

import java.io.IOException;
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

    static synchronized void sendMessage(String nickname, String message){
        for (Map.Entry<String, ClientHandler> entry : Server.clients.entrySet()) {
            String key = entry.getKey();
            ClientHandler handler = entry.getValue();
            if(nickname.equals(key)){
                continue;
            }
            handler.out.println("(" + nickname + ") " + message);
        }
    }

    static synchronized void addClient(String nickname, ClientHandler handler){
        Server.clients.put(nickname, handler);
    }

    static synchronized void removeClient(String nickname){
        if(Server.clients.containsKey(nickname)){
            Server.clients.remove(nickname);
        }
    }

}