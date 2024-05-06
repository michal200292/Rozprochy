import threading
import time
import grpc
from stub.python import match_pb2, match_pb2_grpc


def get_command() -> int:
    print(
        """
        1. List available teams
        2. Subscribe
        3. Unsubscribe
        4. Disconnect
        """
    )
    command = input(" => ")
    if not command.isdigit():
        print("Unknown command")
        return 0
    return int(command)


def handle_messages(client: 'Client', stream):
    try:
        for msg in stream:
            print(msg)
    except grpc.RpcError:
        print("Lost connection to server")
        client.running = False


def ping(client: 'Client'):
    counter = 0
    while counter < 30:
        time.sleep(3)
        try:
            _ = client.stub.Ping(match_pb2.Nothing())
            counter = 0
        except grpc.RpcError:
            counter += 3

    print("Lost Connection to server")
    client.running = False


class Client:
    def __init__(self):
        self.stub: match_pb2_grpc.PublisherStub
        self.running = True

    def start(self) -> None:
        with grpc.insecure_channel("localhost:50051") as channel:
            self.stub = match_pb2_grpc.PublisherStub(channel)
            stream = self.stub.StreamEvents(match_pb2.Nothing())
            threading.Thread(target=ping, args=(self,), daemon=True).start()
            threading.Thread(target=handle_messages, args=(self, stream), daemon=True).start()
            self.run()

    def run(self) -> None:
        while self.running:
            command = get_command()
            if command == 1:
                for idx, team in enumerate(self.stub.GetAvailableTeams(match_pb2.Nothing())):
                    print(f"{idx + 1}. {team.name} - {team.short}")
            elif command == 2 or command == 3:
                print("Select teams( short name ): ")
                requests = (
                    match_pb2.Subscription(team=team, subscribe=(command == 2))
                    for team in input().strip().split() if team
                )
                reply = self.stub.Subscribe(requests)
                for msg in reply.msg:
                    print(msg)
            elif command == 4:
                break


if __name__ == "__main__":
    cli = Client()
    cli.start()
