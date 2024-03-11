package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

class ClientHandler implements Runnable{
    public final Socket clientSocket; 
    public Integer clientUdpPort;
    public int clientPort;

    private String clientName;
    public PrintWriter out;
    private BufferedReader in;
  
    public ClientHandler(Socket socket) throws SocketException 
    { 
        this.clientSocket = socket;
    }

    public void run()
    { 

        boolean newClient = false;

        try { 
            out = new PrintWriter(clientSocket.getOutputStream(), true); 
            in = new BufferedReader( new InputStreamReader( clientSocket.getInputStream())); 
            clientName = in.readLine();
            
            newClient = Server.addClient(clientName, this);
            if(newClient){
                out.println("ACK");
                Server.sendTCPMessage("", "(server) " + clientName + " just joined the server");
            }
            else{
                out.println("NACK");
                return;
            }

            clientPort = Integer.parseInt(in.readLine());

            String line = "";
            while((line = in.readLine()) != null){
                Server.sendTCPMessage(clientName, "(" + clientName + ") " + line);
            }

            Server.sendTCPMessage(clientName, "(server) " + clientName + " left the server");
        } 
        catch (Exception e) { 
        } 
        finally { 
            if(newClient){
                Server.removeClient(clientName);
            }
            try{
                if (out != null) { 
                    out.close(); 
                } 
                if (in != null) { 
                    in.close(); 
                } 
                if (clientSocket != null){
                    clientSocket.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
