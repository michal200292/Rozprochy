import signal
import sys
import Ice
from db_handler import DB
from Servants import Locator, DefaultUser


Ice.loadSlice('../slice/Database.ice')


def shutdown(signum, frame):
    db.close()
    communicator.shutdown()


if __name__ == "__main__":
    with Ice.initialize(sys.argv) as communicator:
        signal.signal(signal.SIGINT, shutdown)
        db = DB()
        adapter = communicator.createObjectAdapterWithEndpoints("Adapter", "default -h localhost -p 50051")

        adapter.addDefaultServant(servant=DefaultUser(db), category="user_info")
        adapter.addServantLocator(locator=Locator(db), category="update_info")

        adapter.activate()
        communicator.waitForShutdown()
