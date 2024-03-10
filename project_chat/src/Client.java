package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
        System.out.println("Client " + clientName);

        Scanner input = new Scanner(System.in);
        Socket socket = null;

        try {
            socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(clientName);
            String response = in.readLine();
            System.out.println("received response: " + response);


            String line = "";
            while((response = in.readLine()) != null){
                // line = input.nextLine();
                // out.println(line);
                System.out.println(response);
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
        }
    }
}
