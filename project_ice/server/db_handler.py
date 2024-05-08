import sqlite3


class DB:
    def __init__(self, ):
        self._db = sqlite3.connect("../example.db", check_same_thread=False)
        self.cursor = self._db.cursor()

    def check_credentials(self, login, password) -> bool:
        self.cursor.execute(f'''
            Select * from passwords
            where login = "{login}" and password = "{password}"
        ''')
        res = self.cursor.fetchall()
        return len(res) > 0

    def load_user_info(self, login):
        self.cursor.execute(f'''
                    Select * from user_info
                    where login = "{login}"
                ''')
        return self.cursor.fetchall()

    def close(self):
        self._db.close()