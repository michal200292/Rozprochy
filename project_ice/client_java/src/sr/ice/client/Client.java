package sr.ice.client;

import Database.UpdateInfoPrx;
import Database.UserInfoPrx;
import com.zeroc.Ice.*;

import java.io.IOException;
import java.lang.Exception;
import java.util.Objects;

public class Client {
	public static void main(String[] args) {
		int status = 0;
		Communicator communicator = Util.initialize();

		String line;
		String user_login;
		String user_password;

		ObjectPrx base_info;
		UserInfoPrx obj_info;

		ObjectPrx base_update;
		UpdateInfoPrx obj_update = null;

		java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		do {
			try {
				System.out.println("""
				
					1. Check user name
					2. Check user phone number
					3. Log in to your account
					4. Update name
					5. Update phone
					6. Quit
				""");
				System.out.print("==> ");
				line = in.readLine();
				switch (line) {
					case "1":
						System.out.print("Pass user login: ");
						user_login = in.readLine().trim();
						base_info = communicator.stringToProxy("user_info/" + user_login + ":tcp -h localhost -p 50051");
						obj_info = UserInfoPrx.uncheckedCast(base_info);
						System.out.println(obj_info.getName());
						break;
					case "2":
						System.out.print("Pass user login: ");
						user_login = in.readLine().trim();
						base_info = communicator.stringToProxy("user_info/" + user_login + ":tcp -h localhost -p 50051");
						obj_info = UserInfoPrx.uncheckedCast(base_info);
						System.out.println(obj_info.getPhoneNumber());
						break;
					case "3":
						System.out.print("Pass your login: ");
						user_login = in.readLine().trim();
						System.out.print("Pass your password: ");
						user_password = in.readLine().trim();

						base_update = communicator.stringToProxy("update_info/" + user_login + ":tcp -h localhost -p 50051");
						obj_update = UpdateInfoPrx.uncheckedCast(base_update);
						System.out.println(obj_update.authenticate(user_password));
						break;
					case "4":
						if(obj_update == null){
							System.out.println("You are not logged in");
						}
						else{
							System.out.print("Pass new name: ");
							String newName = in.readLine().trim();
							System.out.println(obj_update.changeName(newName));
						}
						break;
					case "5":
						if(obj_update == null){
							System.out.println("You are not logged in");
						}
						else{
							System.out.print("Pass new name: ");
							int newNumber = Integer.parseInt(in.readLine().trim());
							System.out.println(obj_update.changeNumber(newNumber));
						}
						break;
					case "6":
						break;

				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} while (!Objects.equals(line, "6"));


		if (communicator != null) { //clean
			try {
				communicator.destroy();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				status = 1;
			}
		}
		System.exit(status);
	}
}