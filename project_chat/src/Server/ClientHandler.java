package src.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

class ClientHandler implements Runnable{

    public final Socket clientSocket; 
    private String clientName;
    public PrintWriter out;
    private BufferedReader in;
    public Server server;
  
    public ClientHandler(Server server, Socket socket) throws SocketException 
    { 
        this.clientSocket = socket;
        this.server = server;
    }

    public void run()
    { 

        boolean newClient = false;
        try { 
            out = new PrintWriter(clientSocket.getOutputStream(), true); 
            in = new BufferedReader( new InputStreamReader( clientSocket.getInputStream())); 
            clientName = in.readLine();
            
            newClient = server.addClient(clientName, this);
            if(newClient){
                out.println("ACK");
                server.sendTCPMessage("", "(server) " + clientName + " just joined the server");
            }
            else{
                out.println("NACK");
                return;
            }

            String line = "";
            while((line = in.readLine()) != null){
                server.sendTCPMessage(clientName, "(" + clientName + ") " + line);
            }
            server.sendTCPMessage(clientName, "(server) " + clientName + " left the server");
        } 
        catch (Exception e) {} 
        finally { 
            if(newClient){
                server.removeClient(clientName);
            }
            try{
                if (out != null) out.close(); 
                if (in != null) in.close(); 
                if (clientSocket != null) clientSocket.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
