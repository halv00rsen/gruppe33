package database;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConnectionMySQL {
	
	private static boolean DEBUG = false;
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost/";
	private static String dbName = "calendar";
	private static String user = "root";
	private static String password = "passord";
	
	private static ResultSet sendQuery(String query) {
		ResultSet myRs = null;
		try {
			
			Connection myConn= getConnection();
			Statement myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(query);
			
		} catch (Exception e) {
			
			if (DEBUG) e.printStackTrace();
			return null;
			
		}
		return myRs;
		
	}
	
	private static boolean sendStatement (String statement) {
		
		try {
			
			Connection conn = getConnection();
			Statement st = conn.createStatement();
			int res = st.executeUpdate(statement);
			conn.close();
			return true;
			
		} catch (Exception e) {
			
			if (DEBUG) e.printStackTrace();
			return false;
			
		}
		
	}
	
	private static Connection getConnection() {
		
		try {		
			Class.forName(driver).newInstance();
			Connection myConn = DriverManager.getConnection(url+dbName, user, password);
			return myConn;
			
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Map<String, String> getUserInfo(String username) {
		
		try{
			Map<String, String> info = new HashMap<String, String>();
			ResultSet myRs = sendQuery("SELECT * FROM person WHERE username = '" + username + "';");
			while (myRs.next()) {
				
				info.put("username", myRs.getString("username"));
				info.put("password", myRs.getString("password"));
				info.put("firstName", myRs.getString("firstName"));
				info.put("lastName", myRs.getString("lastName"));
				info.put("email", myRs.getString("email"));
				info.put("phone", myRs.getString("phone"));
				info.put("isAdmin", myRs.getString("isAdmin"));
				
			}
			return info;

			} catch (Exception e) {
				if (DEBUG) e.printStackTrace();
				return null;
		}
		
	}

	public static boolean createUser(String username, String firstName, String lastName, String password, String email, String phone, boolean isAdmin) { 
		
		String myStmt = "INSERT INTO person SET username = '" + username + "', firstName = '" + firstName + "', lastName = '" + lastName
				 + "', password = '" + password + "', email = '" + email + "'";
		if(!phone.isEmpty()) myStmt += ", phone = '" + phone + "'";
		myStmt += ";";
		System.out.println(myStmt);
		return sendStatement(myStmt);
		
	}
	
	public static boolean updateUser(String oldUsername, String newUsername, String firstName, String lastName, String password, String email, String phone, boolean isAdmin) { 
		
		String myStmt = "UPDATE person SET username = '" + newUsername + "', firstName = '" + firstName + "', lastName = '" + lastName
				 + "', password = '" + password + "', email = '" + email + "'";
		if(!phone.isEmpty()) myStmt += ", phone = '" + phone + "'";
		myStmt += " WHERE username = '" + oldUsername + "';";
		System.out.println(myStmt);
		return sendStatement(myStmt);
		
	}
	
	
	public static boolean createEvent(String eventName, String location, String start, String end, int priority, String lastChanged, int frequency, String info) {
		
		String myStmt = "INSERT INTO event set eventName = '" + eventName + "', start = '" + start + "', end = '" + end + "', lastChanged = now();";
		if(!location.isEmpty()) myStmt += ", location = '" + location + "'";
		if(priority != 0) myStmt += ", priority = " + priority;
		if(frequency != 0) myStmt += ", frequency = " + frequency;
		if(!location.isEmpty()) myStmt += ", info = '" + info + "'";
		myStmt += ";";
		System.out.println(myStmt);
		return sendStatement(myStmt);
		
	}
	
	public static boolean updateEvent(String eventId, String eventName, String location, String start, String end, int priority, String lastChanged, int frequency, String info) {
		
		String myStmt = "UPDATE event set eventName = '" + eventName + "', start = '" + start + "', end = '" + end + "', lastChanged = now();";
		if(!location.isEmpty()) myStmt += ", location = '" + location + "'";
		if(priority != 0) myStmt += ", priority = " + priority;
		if(frequency != 0) myStmt += ", frequency = " + frequency;
		if(!location.isEmpty()) myStmt += ", info = '" + info + "'";
		myStmt += " WHERE eventId = " + eventId + ";";
		System.out.println(myStmt);
		return sendStatement(myStmt);
		
	}
	
	public static boolean onClickedEvent(int eventId, String username) {
			
		String myStmt = "UPDATE isInvitedTo SET lastSeen = now() WHERE eventId = " + eventId + " AND username = '" + username + "';";
		return sendStatement(myStmt);
	}
	
	public static boolean setApplianceEvent(int eventId, String username, String appliance) {
		
		String myStmt = "UPDATE isInvitedTo SET lastSeen = now(), appliance = '" + appliance + "' WHERE eventId = " + eventId + " AND username = '" + username + "';";
		return sendStatement(myStmt);
	}	
	
	public static boolean deleteEvent(int eventId) {
		
		String myStmt = "DELETE FROM event WHERE eventId = " + eventId + ";";
		return sendStatement(myStmt);
	}
	
	public static boolean addMembersToEvent(int eventId, String username) {
		
		String myStmt = "INSERT INTO isInvitedTo VALUES(" + eventId + ", " + username + ";";
		return sendStatement(myStmt);
	}
	
	public static boolean removeMembersToEvent(int eventId, String username) {
		
		String myStmt = "DELETE FROM isInvitedTo VALUES(" + eventId + ", " + username + ";";
		return sendStatement(myStmt);
	}
	

	public static boolean addGroupsToEvent(int eventId, int groupId) {
		
		String myStmt = "INSERT INTO groupInvitation VALUES(" + eventId + ", " + groupId + ";";
		return sendStatement(myStmt);
	}
	
	public static boolean removeGroupsToEvent(int eventId, int groupId) {
		
		String myStmt = "INSERT INTO groupInvitation VALUES(" + eventId + ", " + groupId + ";";
		return sendStatement(myStmt);
	}
	
	
	public static boolean createGroup(String groupName, int parent) {
		
		String myStmt = "INSERT INTO personGroup SET groupName = '" + groupName + "'";
		if(parent != 0) myStmt += ", parent = '" + parent + "'";
		return sendStatement(myStmt);
		
	}
	
	public static boolean addMembersToGroup(int groupId, String username) {
		
		String myStmt = "INSERT INTO isMemberOf VALUES(" + groupId + ", '" + username + "');";
		return sendStatement(myStmt);
		
	}
	
	public static boolean removeMembersFromGroup(int groupId, String username) {
		
		String myStmt = "DELETE FROM isMemberOf WHERE groupId = " + groupId + " AND username = '" + username + "');";
		return sendStatement(myStmt);
		
	}
	
	public static boolean deleteGroup(int groupId) {
		
		String myStmt = "DELETE FROM personGroup WHERE groupId = " + groupId;
		return sendStatement(myStmt);
		
	}
	
	public static ArrayList<String> checkGroups(String username) {
		
		ArrayList<String> myGroups = new ArrayList<String>();
		try {
			ResultSet myRs = sendQuery("SELECT groupName " + 
					"FROM personGroup, person, isMemberOF " + 
					"WHERE personGroup.groupId = isMemberOF.groupId AND person.username = groupId.username AND username = '" + username + "';");
			while (myRs.next()){
				
				myGroups.add(myRs.getString("groupName"));
				
			}
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
		}

		return myGroups;
	}
	
	
	public static boolean reserveRoom(int eventId, int roomNr) {
		
		String myStmt = "INSERT INTO reserve VALUES(" + eventId + "," + roomNr + ");";
		return sendStatement(myStmt);
		
	}

	public static Map<String, String> getAvailableRooms(String start, String end, int capacity) {
		System.out.println(start);
		System.out.println(end);
		
		try {
			Map<String, String> availableRooms = new HashMap<String, String>();
			ResultSet myRs = sendQuery("SELECT roomNr, capacity FROM room" +
					" WHERE capacity >= " + capacity + " AND roomNr NOT IN " + 
					"(SELECT room.roomNr" + 
					"FROM room, event, reserve" + 
					"WHERE room.roomNr = reserve.roomNr AND event.eventId = reserve.eventId AND start < '" + end + "' AND end > '" + start + "')" +
					"ORDER BY roomNr;");
			System.out.println("ok");
			
			while (myRs.next()){
				availableRooms.put(myRs.getString("roomNr"), myRs.getString("capacity"));
			}
			return availableRooms;
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			return null;
		}
	}
	
	public static boolean createAlarm(int minBeforeEvent) {
		
		String myStmt = "INSERT INTO alarm SET minBeforeEvent = " + minBeforeEvent + ";";
		return sendStatement(myStmt);
		
	}
	
	public static boolean setAlarm(int eventId, String username, int alarmId) {
		
		String myStmt = "UPDATE isInvitedTO SET alarmId = " + alarmId + " WHERE eventId = " + eventId + " AND username = '" + username + "';";
		return sendStatement(myStmt);
		
	}
}
