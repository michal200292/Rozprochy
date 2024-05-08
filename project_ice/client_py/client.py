import sys
import Ice
import Database
import signal

Ice.loadSlice('../slice/Database.ice')


def user_proxy(login, communicator):
    user_info_proxy = Database.UserInfoPrx.uncheckedCast(
        communicator.stringToProxy(
            f'{login}: default -h localhost -p 50051'))
    return user_info_proxy


def update_proxy(login, communicator):
    user_info_proxy = Database.UpdateInfoPrx.uncheckedCast(
        communicator.stringToProxy(
            f'{login}: default -h localhost -p 50051'))
    return user_info_proxy


def main() -> None:
    with Ice.initialize(sys.argv) as communicator:
        signal.signal(signal.SIGINT, lambda signum, frame: exit(0))
        user_update_proxy = None
        while True:
            print("""
                1. Check user name
                2. Check user phone number
                3. Log in to your account
                4. Update name
                5. Update phone
                6. Quit
            """)
            opt = int(input("=> "))
            if opt == 1:
                user_name = input("Pass user login: ").strip()
                user_info_proxy = user_proxy(f"user_info/{user_name}", communicator)
                print(user_info_proxy.getName())
            elif opt == 2:
                user_name = input("Pass user login: ").strip()
                user_info_proxy = user_proxy(f"user_info/{user_name}", communicator)
                print(user_info_proxy.getPhoneNumber())

            elif opt == 3:
                cur_login = input("Pass your login: ").strip()
                password = input("Pass your password: ").strip()
                user_update_proxy = update_proxy(f"update_info/{cur_login}", communicator)
                print(user_update_proxy.authenticate(password=password))
            elif opt == 4:
                if user_update_proxy is None:
                    print("You are not logged in")
                else:
                    new_name = input("Pass new name: ").strip()
                    print(user_update_proxy.changeName(newName=new_name))
            elif opt == 5:
                if user_update_proxy is None:
                    print("You are not logged in")
                else:
                    new_number = int(input("Pass new number: ").strip())
                    print(user_update_proxy.changeNumber(newNumber=new_number))
            elif opt == 6:
                break
            else:
                print("Unknown command")


if __name__ == "__main__":
    main()
