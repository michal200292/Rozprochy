import random
import time
from concurrent import futures
import threading
import grpc
from dataclasses import dataclass, field
from stub.python import match_pb2, match_pb2_grpc
from data_generator import generate_match_data, short_names, short_to_name
from queue import Queue, Empty
from typing import Iterator, ClassVar
from wrapt import synchronized
import datetime


@dataclass
class Subscriber:
    id: str
    context: grpc.ServicerContext
    _ping_time: datetime = datetime.datetime.now()
    subscribed_teams: set[str] = field(default_factory=set)
    event_queue: Queue[match_pb2.Stats] = field(default_factory=Queue)
    MAX_INACTIVITY: ClassVar[float] = 30

    @synchronized
    def reset_ping_time(self) -> None:
        self._ping_time = datetime.datetime.now()

    @synchronized
    def check_if_inactive(self) -> bool:
        if (datetime.datetime.now() - self._ping_time).seconds > Subscriber.MAX_INACTIVITY:
            return True
        return False


class Publisher(match_pb2_grpc.PublisherServicer):
    def __init__(self):
        self.subscriptions: dict[str, Subscriber] = {}
        threading.Thread(
            target=generate_events,
            args=(self,),
            daemon=True).start()

    def GetAvailableTeams(self, request, context):
        return (match_pb2.Team(short=short, name=short_to_name[short]) for short in short_names)

    def Subscribe(self, request_iterator, context):
        client_id = context.peer()
        client = self.subscriptions[client_id]

        msg = []
        try:
            for request in request_iterator:
                if request.team not in short_to_name:
                    msg.append(f"{request.team} is not a valid short name")
                    continue
                if request.subscribe:
                    if request.team in client.subscribed_teams:
                        msg.append(f"You are already subscribed to: {short_to_name[request.team]}")
                    else:
                        client.subscribed_teams.add(request.team)
                        msg.append(f"Added subscription: {short_to_name[request.team]}")
                else:
                    if request.team not in client.subscribed_teams:
                        msg.append(f"You are not subscribed to: {short_to_name[request.team]}")
                    else:
                        client.subscribed_teams.remove(request.team)
                        msg.append(f"Removed subscription: {short_to_name[request.team]}")
        except grpc.RpcError as e:
            print(e)

        return match_pb2.SubscriptionReply(msg=msg)

    def StreamEvents(self, request, context):
        client = Subscriber(id=context.peer(), context=context)
        if client.id not in self.subscriptions:
            self.subscriptions[client.id] = client

        while True:
            try:
                stats = client.event_queue.get(block=True, timeout=3)
                if client.check_if_inactive():
                    break
                yield stats
            except Empty:
                if client.check_if_inactive():
                    break

        print(f"Client {client.id} inactive, removing client")
        if client.id in self.subscriptions:
            self.subscriptions.pop(client.id)
        return

    def Ping(self, request, context):
        client_id = context.peer()
        if client_id in self.subscriptions:
            client = self.subscriptions[client_id]
            client.reset_ping_time()
        return match_pb2.Nothing()


def handle_match_events(
        publisher: Publisher,
        semaphore: threading.Semaphore,
        team1: str,
        team2: str,
        match_events_generator: Iterator[match_pb2.Stats]
) -> None:

    for stats in match_events_generator:
        for client in publisher.subscriptions.values():
            if team1 in client.subscribed_teams or team2 in client.subscribed_teams:
                client.event_queue.put(stats)
    print(f"Match between {team1} and {team2} ended")
    semaphore.release()


def generate_events(publisher: Publisher) -> None:
    MAX_MATCHES = 5
    semaphore = threading.Semaphore(value=MAX_MATCHES)
    while semaphore.acquire():
        time.sleep(random.randrange(5, 20))
        team1, team2 = random.sample(short_names, k=2)
        print(f"Started match between {team1} and {team2}")
        generator = generate_match_data(team1, team2)
        threading.Thread(
            target=handle_match_events,
            args=(publisher, semaphore, team1, team2, generator)).start()


def main() -> None:
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    publisher = Publisher()
    match_pb2_grpc.add_PublisherServicer_to_server(publisher, server)
    server.add_insecure_port("localhost:50051")
    server.start()
    try:
        server.wait_for_termination()
    except KeyboardInterrupt:
        server.stop(0)


if __name__ == "__main__":
    main()
