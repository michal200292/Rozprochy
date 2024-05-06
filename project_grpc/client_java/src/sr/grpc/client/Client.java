package sr.grpc.client;

import com.google.protobuf.ProtocolStringList;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import io.grpc.stub.StreamObserver;
import sr.proto.*;
import sr.proto.PublisherGrpc.PublisherStub;
import sr.proto.PublisherGrpc.PublisherBlockingStub;


public class Client
{
	public static void main(String[] args) throws Exception
	{
		Client client = new Client("localhost", 50051);
		client.run();
	}

	private static final Logger logger = Logger.getLogger(Client.class.getName());
	private final ManagedChannel channel;
	public final PublisherBlockingStub stub;
	private final PublisherStub streamStub;
	private final Scanner scanner = new Scanner(System.in);
	private boolean running;

	public Client(String remoteHost, int remotePort)
	{;
        channel = ManagedChannelBuilder.forAddress(remoteHost, remotePort)
				.usePlaintext()
				.build();
		stub = PublisherGrpc.newBlockingStub(channel);
		streamStub = PublisherGrpc.newStub(channel);
		this.running = true;
	}

	public synchronized void stop_running(){
		running = false;
	}

	public int getCommand(){
		System.out.println(
                """
                
                       	1. List available teams
                        2. Subscribe
                        3. Unsubscribe
                        4. Disconnect
                
                """
		);
		System.out.print(" => ");
		try{
			return Integer.parseInt(scanner.next());
		}
		catch (Exception e){
			return 0;
		}
	}

	public void printTeams(){
		Nothing request = Nothing.newBuilder().build();
		Iterator<Team> teams = stub.getAvailableTeams(request);
		int i = 1;
        while (teams.hasNext()) {
            Team team = teams.next();
            System.out.println(i + ". " + team.getName() + " - " + team.getShort());
            i++;
        }
    }

	public void subscribe(int command){
		System.out.println("Select teams( short name ): ");
		ArrayList<Subscription> subscriptions = new ArrayList<>();

		String line = null;
		while((line = scanner.nextLine()).isEmpty());
		System.out.println(line);
		for(String teamName : line.trim().split(" ")){
			if(teamName.isEmpty()) {
				continue;
			}
			subscriptions.add(
					Subscription.newBuilder().
							setTeam(teamName).
							setSubscribe((command == 2)).
							build()
			);
		}

		StreamObserver<SubscriptionReply> responseObserver = new StreamObserver<>() {
			ProtocolStringList msg;
			@Override public void onNext(SubscriptionReply result)	{
				msg = result.getMsgList();
			}
			@Override public void onError(Throwable t) {
				System.out.println("RPC ERROR");
			}
			@Override public void onCompleted()	{
				for(String sub : msg){
					System.out.println(sub);
				}
			}
		};

		StreamObserver<Subscription> requestObserver = streamStub.subscribe(responseObserver);

		try {
			for (Subscription sub : subscriptions) {
					requestObserver.onNext(sub);
			}
		} catch (RuntimeException e) {
			requestObserver.onError(e);
			throw e;
		}
		requestObserver.onCompleted();
		System.out.flush();
	}

	public void run() throws InterruptedException
	{
		Nothing request = Nothing.newBuilder().build();
		Iterator<Stats> stats = stub.streamEvents(request);

		Thread streamHandler = new Thread(new StreamHandler(this, stats));
		streamHandler.start();
		Thread pingHandler = new Thread(new Ping(this));
		pingHandler.start();

		while(running){
			int command = getCommand();
			if(command == 1){
				printTeams();
			}
			else if(command == 2 || command == 3){
				subscribe(command);
			}
			else if(command == 4){
				break;
			}

		}
		streamHandler.interrupt();
		pingHandler.interrupt();
		shutdown();
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}
}

