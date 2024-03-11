package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class ListenerTCP implements Runnable{

    public final Socket socket;
    public final BufferedReader in;

    public ListenerTCP(Socket socket, BufferedReader in){
        this.socket = socket;
        this.in = in;
    }
    public void run(){
        try{
            String line = "";
            while((line = in.readLine()) != null){
                System.out.println(line);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
}
