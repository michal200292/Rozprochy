import Ice
from LRUCache import LRUCache
import Database


class UserUpdate(Database.UpdateInfo):

    def __init__(self, db):
        self.db = db
        self.authenticated = False
        self.login: str = ""
        self.conn_id: str = ""

    def authenticate(self, password, current):

        if not self.authenticated:
            login = current.id.name
            if self.db.check_credentials(login, password):
                self.authenticated = True
                self.login = login
                self.conn_id = current.con.getInfo().remoteAddress
                return "Succesfully logged in"
            return "Wrong login or password"
        else:
            if current.con.getInfo().remoteAddress != self.conn_id:
                return "Already logged in from another machine"
            return "You are already logged in"

    def logOut(self, current):
        if self.authenticated:
            if current.con.getInfo().remoteAddress == self.conn_id:
                return "Succesfully logged out"

            return "You are logged in from another machine"

        return "You are not logged in"

    def changeName(self, new_name, current):
        if self.authenticated:
            return f"Changed {self.login}'s name to {new_name}"

        return "You are not logged in"

    def changeNumber(self, new_number, current):
        if self.authenticated:
            return f"Changed {self.login}'s number to {new_number}"
        return "You are not logged in"


class DefaultUser(Database.UserInfo):
    def __init__(self, db):
        self.db = db

    def getName(self, current):
        identity = current.id.name
        user_info = self.db.load_user_info(identity)
        if not user_info:
            return "No such user"

        return user_info[0][1]

    def getPhoneNumber(self, current):
        identity = current.id.name
        user_info = self.db.load_user_info(identity)
        if not user_info:
            return "No such user"

        return user_info[0][2]


class Locator(Ice.ServantLocator):
    def __init__(self, db):
        self.lru: LRUCache = LRUCache(capacity=3)
        self.db = db

    def locate(self, current):
        identity = current.id.name

        if identity not in self.lru:
            new_servant = UserUpdate(self.db)
            self.lru.put(identity, new_servant)
            print(f"Added {identity} to cache")
            return new_servant

        return self.lru.get(identity)

    def finished(self, current, servant, cookie):
        pass

    def deactivate(self, category):
        print("Deactivated")
