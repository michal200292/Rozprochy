package sr.grpc.client;

import sr.proto.Nothing;

public class Ping implements Runnable{

    private final Client client;

    Ping(Client client){
        this.client = client;
    }

    @Override
    public void run() {
        int counter = 0;
        while(counter < 30){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Nothing request = Nothing.newBuilder().build();
            try {
                Nothing reply = client.stub.ping(request);
            }
            catch (Exception e){
                counter += 3;
            }
        }
        System.out.println("Lost connection to server");
        client.stop_running();
    }
}