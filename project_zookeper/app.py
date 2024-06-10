import threading
import time
from kazoo.client import KazooClient
from kazoo.protocol.states import WatchedEvent, EventType, ZnodeStat
from typing import Optional
from collections import deque
import graphviz
import tkinter as tk
from queue import Queue
from enum import Enum
from PIL import Image, ImageTk


class Op(Enum):
    OPEN = 0,
    CLOSE = 1,
    UPDATE = 2


class ZookeeperClient:
    def __init__(self, main_path: str, msg_queue: Queue):
        print("Started zookeeper client")

        self.queue = msg_queue
        self.main_path = main_path
        self.client = KazooClient()
        self.client.start()

        self.tree: dict[str, list[str]] = {}
        self.first = True
        self.client.DataWatch(self.main_path)(self.watch_data)

    def watch_data(self, data: Optional[bytes], stat: ZnodeStat, event: Optional[WatchedEvent]):
        if event:
            if event.type == EventType.CREATED:
                print("Created node a")
                self.tree[self.main_path] = []
                self.queue.put((Op.OPEN, "", 0))
                self.client.ChildrenWatch(self.main_path)(self.watch_children)

            elif event.type == EventType.DELETED:
                self.queue.put((Op.CLOSE, "", 0))
                print("Deleted node a")

        return True

    def watch_children(self, children):
        if self.first:
            self.first = False
            self.update_tree()

    def update_tree(self):
        d = deque([self.main_path])
        dot = graphviz.Digraph()
        dot.node(self.main_path, label="a")
        while d:
            path = d.popleft()
            self.tree[path] = []
            for child in self.client.get_children(path):
                new_path = f"{path}/{child}"
                self.tree[path].append(child)
                self.client.ChildrenWatch(new_path)(self.watch_children)

                dot.node(new_path, label=child)
                dot.edge(path, new_path)
                d.append(new_path)

        self.queue.put((Op.UPDATE, dot, len(self.tree[self.main_path])))
        self.first = True

    def close(self):
        print("Closing zookeeper client")
        self.client.stop()
        self.client.close()


def run_zookeeper_client(msg_queue):
    app = ZookeeperClient("/a", msg_queue)
    try:
        while True:
            time.sleep(1000)
    except:
        app.close()


if __name__ == "__main__":
    queue = Queue()
    threading.Thread(
        target=run_zookeeper_client,
        args=(queue, ),
        daemon=True
    ).start()

    root: Optional[tk.Tk] = None
    img_label: Optional[tk.Label] = None
    info_label: Optional[tk.Label] = None

    while True:
        op, content, no_of_children = queue.get()
        if op == Op.CLOSE:
            if root is not None:
                root.destroy()
            root = None
        elif op == Op.OPEN:
            if root is not None:
                root.destroy()
            root = tk.Tk()
            info_label = tk.Label(root)
            info_label.pack(pady=10)
            img_label = tk.Label(root)
            img_label.pack(pady=20)
            root.update()
        else:
            if root is not None:
                content.render("graph", format="png", cleanup=True)
                image = Image.open("graph.png")
                image = ImageTk.PhotoImage(image)
                img_label.config(image=image)
                img_label.image = image

                info_label.config(text=f"Node a has {no_of_children} children")
                root.update()

