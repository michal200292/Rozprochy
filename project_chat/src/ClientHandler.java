package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable{
    private final Socket clientSocket; 
    private String clientName;
    public PrintWriter out;
    private BufferedReader in;
  
    public ClientHandler(Socket socket) 
    { 
        this.clientSocket = socket;
    } 

    public void run()
    { 

        try { 
            out = new PrintWriter(clientSocket.getOutputStream(), true); 
            in = new BufferedReader( new InputStreamReader( clientSocket.getInputStream())); 
            
            clientName = in.readLine();
            System.out.println("Accepted new client: " + clientName); 
            Server.addClient(clientName, this);

            out.println("Hello there!");  

            String line = "";
            while((line = in.readLine()) != null){
                System.out.println("(" + clientName + ") " + line);
                Server.sendMessage(clientName, line);
            }

            System.out.println(clientName + " out"); 
        } 
        catch (Exception e) { 
        } 
        finally { 
            Server.removeClient(clientName);
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
