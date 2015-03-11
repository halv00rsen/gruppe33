package server;
import java.sql.*;

public class ConnectionMySQL {

	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost/calendar";
		String user = "root";
		String password = "passord";
		
		try {
			
			Connection myConn = DriverManager.getConnection(url, user, password);
			
			Statement myStmt = myConn.createStatement();
			
			ResultSet myRs = myStmt.executeQuery("SELECT * FROM person");
			
			while (myRs.next()) {
				
				System.out.println(myRs.getString("firstName") + ", " + myRs.getString("lastName"));
				
			}
			
		} catch (Exception e) {
			
			
		}

	}

}
