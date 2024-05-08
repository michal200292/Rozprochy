from llist import dllist
from dataclasses import dataclass, field
from typing import Optional


@dataclass
class LRUCache:
    capacity: int
    cur_size: int = 0
    list: dllist = field(default_factory=dllist)
    key_to_node: dict[str, object] = field(default_factory=dict)

    def get(self, key: str) -> Optional[object]:
        node = self.key_to_node[key]
        self.list.remove(node)
        self.list.append(node)
        self.key_to_node[key] = self.list.nodeat(self.cur_size - 1)
        return node.value[1]

    def put(self, key: str, value: object) -> None:
        if key not in self.key_to_node:
            self.list.append((key, value))
            self.key_to_node[key] = self.list.nodeat(self.cur_size)
            self.cur_size += 1
        else:
            node = self.key_to_node[key]
            self.list.remove(node)
            self.list.append((key, value))
            self.key_to_node[key] = self.list.nodeat(self.cur_size - 1)

        if self.cur_size > self.capacity:
            value = self.list.popleft()
            print(f"Removing {value[1].login} from cache")
            del self.key_to_node[value[0]]
            self.cur_size -= 1

    def __contains__(self, key) -> bool:
        if key not in self.key_to_node:
            return False
        return True

