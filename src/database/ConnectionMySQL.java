package database;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ConnectionMySQL {
	
	private static boolean DEBUG = false;
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost/";
	private static String dbName = "calendar";
	private static String user = "root";
	private static String password = "passord";
	
	private static ResultSet sendQuery(String query){
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
	
	private static boolean sendStatement (String statement){
		
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
	
	public static Map<String, String> getUserInfo(String username, String password){
		
		try{
			Map<String, String> info = new HashMap<String, String>();
			ResultSet myRs = sendQuery("SELECT * FROM person WHERE username = '" + username + "';");
			while (myRs.next()) {
				
				info.put("username", myRs.getString("username"));
				info.put("password", myRs.getString("password"));
				info.put("firstName", myRs.getString("firstName"));
				info.put("lastName", myRs.getString("lastName"));
				info.put("email", myRs.getString("email"));
				
			}
			return info;

			} catch (Exception e) {
				if (DEBUG) e.printStackTrace();
				return null;
		}
		
	}

}
