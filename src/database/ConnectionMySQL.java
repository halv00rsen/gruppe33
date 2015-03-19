package database;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/*noen forskjeller fra xkal-scheme.sql, ny versjon på drive
 * personGroup / group
 * appliance   / isGoing
 * små bokstaver på tabeller
 * ikke tatt hensyn til nye endringer i Event-tabellen enda:
 * (freqToDate DATETIME,
	parentEvent INT unsigned,
	info VARCHAR(255),
	FOREIGN KEY (parentEvent) REFERENCES `Event`(eventId) ON DELETE CASCADE)
	
	tid/dato lagres som DATETIME i databasen (yyyy-mm-dd hh:mm:ss)
*/

public class ConnectionMySQL {
	
	private static boolean DEBUG = false;
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://mysql.stud.ntnu.no/";
	private static String dbName = "simendw_calendar";
	private static String user = "simendw_gruppe33";
	private static String password = "passord";
		
	private static ResultSet sendQuery(String query) {
		System.out.println(query);
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
		System.out.println(statement);
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
				info.put("firstname", myRs.getString("firstName"));
				info.put("lastname", myRs.getString("lastName"));
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
	public static ArrayList<HashMap<String, String>> getUsers(){
		
		ArrayList<HashMap<String, String>> allUsers = new ArrayList<HashMap<String, String>>();
			
			try {
				ResultSet myRs = sendQuery("SELECT username, firstName, lastName FROM person;");
				while (myRs.next()){
					HashMap<String, String> users = new HashMap<String, String>();
					users.put("username", myRs.getString("username"));
					users.put("firstName", myRs.getString("firstName"));
					users.put("lastName", myRs.getString("lastName"));
					allUsers.add(users);
					
				}
			} catch (Exception e) {
				if (DEBUG)
					e.printStackTrace();
				return null;
			}
			return allUsers;	
		}

	public static boolean createUser(String username, String firstName, String lastName, String password, String email, String phone, boolean isAdmin) { 
		
		String myStmt = "INSERT INTO person SET username = '" + username + "', firstName = '" + firstName + "', lastName = '" + lastName
				 + "', password = '" + password + "', email = '" + email + "', isAdmin = " + isAdmin;
		if(!phone.isEmpty()) myStmt += ", phone = '" + phone + "'";
		myStmt += ";";
		return sendStatement(myStmt);
		
	}
	
	public static boolean updateUser(String username, String firstName, String lastName, String email, String phone, boolean isAdmin) { 
		
		String myStmt = "UPDATE person SET firstName = '" + firstName + "', lastName = '" + lastName
				 + "', email = '" + email + "', isAdmin = " + isAdmin;
		if(!phone.isEmpty()) myStmt += ", phone = '" + phone + "'";
		myStmt += " WHERE username = '" + username + "';";
		return sendStatement(myStmt);
		
	}
	
	public static boolean changePassword(String username, String password){
		
		String myStmt = "UPDATE person SET password = '" + password + "' WHERE username = '" + username + "';";
		return sendStatement(myStmt);
		
	}
	
	
	public static ArrayList<HashMap<String, String>> getEvents(String username){
		
		ArrayList<HashMap<String, String>> allEvents = new ArrayList<HashMap<String, String>>();
		
		try {
			ResultSet myRs = sendQuery("SELECT event.eventId, eventName, location, start, end, freqToDate, priority, lastChanged, frequency, info, alarmId, lastSeen, appliance, isHidden " + 
					"FROM event, isInvitedTo, person " + 
					"WHERE event.eventId = isInvitedTo.eventId AND person.username = isInvitedTo.username AND person.username = '" + username + "';");
			while (myRs.next()){
				HashMap<String, String> events = new HashMap<String, String>();
				events.put("eventId", myRs.getString("eventId"));
				events.put("eventName", myRs.getString("eventName"));
				events.put("location", myRs.getString("location"));
				events.put("start", myRs.getString("start"));
				events.put("end", myRs.getString("end"));
				events.put("priority", myRs.getString("priority"));
				events.put("lastChanged", myRs.getString("lastChanged"));
				events.put("frequency", myRs.getString("frequency"));
				events.put("info", myRs.getString("info"));
				events.put("freqDate", myRs.getString("freqToDate"));
				events.put("alarmId", myRs.getString("alarmId"));
				events.put("lastSeen", myRs.getString("lastSeen"));
				events.put("appliance", myRs.getString("appliance"));
				events.put("isHidden", myRs.getString("isHidden"));
				allEvents.add(events);
				
			}
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
		}

		return allEvents;
		
	}
	
	public static ArrayList<HashMap<String, String>> getEventsForGroup(int groupId){
		
		ArrayList<HashMap<String, String>> allEvents = new ArrayList<HashMap<String, String>>();
		try {
			ResultSet myRs = sendQuery("SELECT event.eventId, eventName, location, start, freqToDate, end, priority, lastChanged, frequency, info, alarmId, lastSeen, appliance, isHidden " + 
					"FROM event, groupInvitation, personGroup " + 
					"WHERE event.eventId = groupInvitation.eventId AND personGroup.groupId = groupInvitation.groupId AND personGroup.groupId = '" + groupId + "';");
			while (myRs.next()){
				HashMap<String, String> events = new HashMap<String, String>();
				events.put("eventId", myRs.getString("eventId"));
				events.put("eventName", myRs.getString("eventName"));
				events.put("location", myRs.getString("location"));
				events.put("start", myRs.getString("end"));
				events.put("end", myRs.getString("end"));
				events.put("priority", myRs.getString("priority"));
				events.put("lastChanged", myRs.getString("lastChanged"));
				events.put("freqDate", myRs.getString("freqToDate"));
				events.put("frequency", myRs.getString("frequency"));
				events.put("info", myRs.getString("info"));
				events.put("alarmId", myRs.getString("alarmId"));
				events.put("lastSeen", myRs.getString("lastSeen"));
				events.put("appliance", myRs.getString("appliance"));
				events.put("isHidden", myRs.getString("isHidden"));
				allEvents.add(events);
				
			}
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
		}

		return allEvents;
		
	}
	
	public static String getEventCreator(int eventId){
		
		String owner = null;
		try {
			ResultSet myRs = sendQuery("SELECT username from madeBy WHERE eventId = " + eventId + ";");
			while (myRs.next()){
				
				owner = myRs.getString("username");
				
			}
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
		}

		return owner;
		
	}
	
	public static boolean setEventCreator(int eventId, String username){
		
		String myStmt = "INSERT INTO madeBy VALUES(" + eventId + ", '" + username + "');";
		return sendStatement(myStmt);
		
	}
	
	public static int createEvent(String eventName, String location, String start, String end, int priority, int frequency, String info) {
		
		String myStmt = "INSERT INTO event set eventName = '" + eventName + "', start = '" + start + "', end = '" + end + "', frequency = " + frequency + ", lastChanged = now()" + ", priority = " + priority;
		if(!location.isEmpty()) myStmt += ", location = '" + location + "'";
		if(!info.isEmpty()) myStmt += ", info = '" + info + "'";
		myStmt += ";";
		if(!sendStatement(myStmt)) return -1;
		try {
			int groupId = -1;
			ResultSet myRs = sendQuery("SELECT eventId FROM event ORDER BY eventId DESC LIMIT 1;");
			while (myRs.next()){
				groupId = Integer.parseInt(myRs.getString("eventId"));
			}
			return groupId;
			
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			return -1;
		}
	}
	
	
	public static boolean updateEvent(String eventId, String eventName, String location, String start, String end, int priority,  String lastChanged, int frequency, String info) {
		
		String myStmt = "UPDATE event set eventName = '" + eventName + "', start = '" + start + "', end = '" + end + "', lastChanged = now()" + ", priority = " + priority;
		if(!location.isEmpty()) myStmt += ", location = '" + location + "'";
		if(frequency != 0) myStmt += ", frequency = " + frequency;
		if(!location.isEmpty()) myStmt += ", info = '" + info + "'";
		myStmt += " WHERE eventId = " + eventId + ";";
		return sendStatement(myStmt);
		
	}
	
	public static boolean onClickedEvent(int eventId, String username) {
			
		String myStmt = "UPDATE isInvitedTo SET lastSeen = now() WHERE eventId = " + eventId + " AND username = '" + username + "';";
		return sendStatement(myStmt);
	}
	
	public static ArrayList<HashMap<String, String>> getAppliances(int eventId){
		
		ArrayList<HashMap<String, String>> allAppliances = new ArrayList<HashMap<String, String>>();
		try {
			ResultSet myRs = sendQuery("SELECT username, appliance FROM isInvitedTo WHERE eventId = " + eventId + ";");
			while (myRs.next()){
				HashMap<String, String> appliance = new HashMap<String, String>();
				appliance.put("username", myRs.getString("username"));
				appliance.put("appliance", myRs.getString("appliance"));

				allAppliances.add(appliance);
				
			}
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
		}

		return allAppliances;
		
	}
	//sjekker om alle har sett eventen, og evt om lastSeen er senere enn lastChanged-attributten fra event
	public static ArrayList<HashMap<String, String>> getLastSeen(int eventId){
		
		ArrayList<HashMap<String, String>> allLastSeen = new ArrayList<HashMap<String, String>>();
		try {
			ResultSet myRs = sendQuery("SELECT username, lastSeen FROM isInvitedTo WHERE eventId = " + eventId + ";");
			while (myRs.next()){
				HashMap<String, String> lastSeen = new HashMap<String, String>();
				lastSeen.put("username", myRs.getString("username"));
				lastSeen.put("lastSeen", myRs.getString("lastSeen"));

				allLastSeen.add(lastSeen);
				
			}
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
		}

		return allLastSeen;
		
	}
	
	public static boolean setAppliance(int eventId, String username, String appliance) {
		
		String myStmt = "UPDATE isInvitedTo SET lastSeen = now(), appliance = '" + appliance + "' WHERE eventId = " + eventId + " AND username = '" + username + "';";
		return sendStatement(myStmt);
	}
	
	public static boolean setGroupAppliance(int eventId, int groupId, String appliance) {
		
		String myStmt = "UPDATE groupInvitation SET lastSeen = now(), appliance = '" + appliance + "' WHERE eventId = " + eventId + " AND groupId = " + groupId + ";";
		return sendStatement(myStmt);
	}
	
	public static boolean hideEvent(int eventId, String username, boolean isHidden){
		
		String myStmt = "UPDATE isInvitedTo SET isHidden = " + isHidden + " WHERE eventId = " + eventId + " AND username = '" + username + "';";
		return sendStatement(myStmt);
	}
	
	public static boolean hideEventGroup(int eventId, int groupId, boolean isHidden){
		
		String myStmt = "UPDATE groupInvitation SET isHidden = " + isHidden + " WHERE eventId = " + eventId + " AND groupId = " + groupId + ";";
		return sendStatement(myStmt);
	}
	
	public static boolean deleteEvent(int eventId) {
		
		String myStmt = "DELETE FROM event WHERE eventId = " + eventId + ";";
		return sendStatement(myStmt);
	}
	
	public static boolean addMembersToEvent(int eventId, String username) {
		
		String myStmt = "INSERT INTO isInvitedTo set eventId = " + eventId + ", username = '" + username + "', alarmid = null;";
		return sendStatement(myStmt);
	}
	
	public static boolean removeMembersFromEvent(int eventId, String username) {
		
		String myStmt = "DELETE FROM isInvitedTo WHERE eventId = " + eventId + " AND username = '" + username + "';";
		return sendStatement(myStmt);
	}
	
	public static boolean addGroupsToEvent(int eventId, int groupId) {
		
		String myStmt = "INSERT INTO groupInvitation set eventId = " + eventId + ", groupId = " + groupId + ";";
		return sendStatement(myStmt);
	}
	
	public static boolean removeGroupsFromEvent(int eventId, int groupId) {
		
		String myStmt = "DELETE FROM groupInvitation WHERE eventId = " + eventId + " AND groupId = " + groupId + ";";
		return sendStatement(myStmt);
	}
	
	public static ArrayList<String> getGroupMembers(int groupId){
		
		ArrayList<String> group = new ArrayList<String>();
		
		try {
			ResultSet myRs = sendQuery("SELECT * FROM isMemberOf WHERE groupId = " + groupId + ";");
			while (myRs.next()){
				group.add(myRs.getString("username"));

			}
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
		}
		return group;	
	}
	
	public static ArrayList<HashMap<String, String>> getGroups(String username) {
		
		ArrayList<HashMap<String, String>> allGroups = new ArrayList<HashMap<String, String>>();
		try {
			ResultSet myRs = sendQuery("SELECT personGroup.groupid, groupName, parent " + 
					"FROM personGroup, person, isMemberOF " + 
					"WHERE personGroup.groupId = isMemberOF.groupId AND person.username = isMemberOf.username AND person.username = '" + username + "';");
			while (myRs.next()){
				
				HashMap<String, String> groups = new HashMap<String, String>();
				groups.put("groupId", myRs.getString("groupId"));
				groups.put("groupName", myRs.getString("groupName"));
				groups.put("parent", myRs.getString("parent"));
				allGroups.add(groups);
				
			}
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
			return null;
		}

		return allGroups;
	}
	
	public static boolean addParent(int groupId, int parent){
		
		String myStmt = "UPDATE personGroup set parent = " + parent + " WHERE groupId = " + groupId + ";";
		return sendStatement(myStmt);
		
	}
	
	public static boolean removeParent(int groupId, int parent){
		
		String myStmt = "UPDATE personGroup set parent = null WHERE groupId = " + groupId + ";";
		return sendStatement(myStmt);
		
	}
	
	public static int createGroup(String groupName, int parent) {
		
		String myStmt = "INSERT INTO personGroup SET groupName = '" + groupName + "'";
		if(parent != 0) myStmt += ", parent = '" + parent + "'";
		if(!sendStatement(myStmt)) return -1;
		try {
			int groupId = -1;
			ResultSet myRs = sendQuery("SELECT groupId FROM personGroup ORDER BY groupId DESC LIMIT 1;");
			while (myRs.next()){
				groupId = Integer.parseInt(myRs.getString("groupId"));
			}
			return groupId;
			
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			return -1;
		}
		
	}
	
	public static boolean addMembersToGroup(int groupId, String username) {
		
		String myStmt = "INSERT INTO isMemberOf VALUES(" + groupId + ", '" + username + "');";
		return sendStatement(myStmt);
		
	}
	
	public static boolean removeMembersFromGroup(int groupId, String username) {
		
		String myStmt = "DELETE FROM isMemberOf WHERE groupId = " + groupId + " AND username = '" + username + "';";
		return sendStatement(myStmt);
		
	}
	
	public static boolean deleteGroup(int groupId) {
		
		String myStmt = "DELETE FROM personGroup WHERE groupId = " + groupId;
		return sendStatement(myStmt);
		
	}
	
	
	public static boolean reserveRoom(int eventId, int roomNr) {
		
		String myStmt = "INSERT INTO reserve VALUES(" + eventId + "," + roomNr + ");";
		return sendStatement(myStmt);
		
	}
	
	public static ArrayList<HashMap<String, String>> getAllRooms(){
		
	ArrayList<HashMap<String, String>> allEvents = new ArrayList<HashMap<String, String>>();
		
		try {
			ResultSet myRs = sendQuery("SELECT * FROM room;");
			while (myRs.next()){
				HashMap<String, String> events = new HashMap<String, String>();
				events.put("roomNr", myRs.getString("roomNr"));
				events.put("capacity", myRs.getString("capacity"));
				allEvents.add(events);
				
			}
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
		}

		return allEvents;
		
	}

	public static ArrayList<String> getAvailableRooms(String start, String end) {
		
		try {
			ArrayList<String> availableRooms = new ArrayList<String>();
			ResultSet myRs = sendQuery("SELECT roomNr, capacity FROM room " +
					" WHERE roomNr NOT IN " + 
					"(SELECT room.roomNr " + 
					"FROM room, event, reserve " + 
					"WHERE room.roomNr = reserve.roomNr AND event.eventId = reserve.eventId AND start < '" + end + "' AND end > '" + start + "') " +
					"ORDER BY roomNr;");
			
			while (myRs.next()){
				availableRooms.add(myRs.getString("roomNr"));
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
		
		String myStmt = "UPDATE isInvitedTO SET alarmId = " + alarmId + ", lastSeen = now() WHERE eventId = " + eventId + " AND username = '" + username + "';";
		return sendStatement(myStmt);
		
	}
	
	public static boolean sendMessage(String username_from, String username_to, String message){
		
		String myStmt = "INSERT INTO Message VALUES(NULL, '" + message + "', '" + username_from + "', '" + username_to + "');";
		return sendStatement(myStmt);
	}
	
	public static ArrayList<HashMap<String, String>> getMessage(String username){
		
		ArrayList<HashMap<String, String>> allMessages = new ArrayList<HashMap<String, String>>();
		try {
			ResultSet myRs = sendQuery("SELECT username_from, message FROM message WHERE username_to = '" + username + "';");
			while (myRs.next()){
				
				HashMap<String, String> messages = new HashMap<String, String>();
				messages.put("username_from", myRs.getString("username_from"));
				messages.put("message", myRs.getString("message"));
				allMessages.add(messages);
				
			}
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
			return null;
		}
		String myStmt = "DELETE FROM Message WHERE username_to = '" + username + "';";
		sendStatement(myStmt);
		return allMessages;
		
	}
	
}
