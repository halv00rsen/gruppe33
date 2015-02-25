package database;

import java.util.HashMap;
import java.util.Map;

public class PersonInformation {
	
	public static Map<String, String> getPersonInformation(String username, String password){
		//hent data fra databasen
		Map<String, String> info = new HashMap<String, String>();
		info.put("username", username);
		info.put("password", password);
		info.put("personid", "4");
		info.put("events", "events");
		info.put("name", "Ola Nordmann");
		return info;
	}
	
	public static boolean changePassword(String username, String oldPassword, String newPassword){
		return true;
	}
}
