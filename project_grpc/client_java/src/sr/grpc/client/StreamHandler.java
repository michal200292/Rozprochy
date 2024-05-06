package sr.grpc.client;

import sr.proto.Stats;
import sr.proto.Team;

import java.util.Iterator;

public class StreamHandler implements Runnable{

    private final Client client;
    private final Iterator<Stats> streamIterator;

    StreamHandler(Client client, Iterator<Stats> streamIterator){
        this.client = client;
        this.streamIterator = streamIterator;
    }

    @Override
    public void run() {
        try {
            while (streamIterator.hasNext()) {
                Stats stats = streamIterator.next();
                System.out.println(stats);
            }
        }
        catch (Exception e){
            System.out.println("Lost connection to server");
            client.stop_running();
        }
    }
}
