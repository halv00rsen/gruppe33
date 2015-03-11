package database;

import java.io.FileInputStream;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/** Håndterer kommunikasjon mellom programmet og MySQL-databasen */
public class Database {
	public static boolean DEBUG = false;
	private static String url = "jdbc:mysql://mysql.stud.ntnu.no/";
	private static String dbName = "alekh_prosjekt1";
	private static String driver = "com.mysql.jdbc.Driver";
	private static String userName = "alekh_IT1901";
	private static String password = "abcd1234";
	
//	private static String initKoie = "Core/src/initialiseringAvKoier.txt";
//	private static String initItem = "Core/src/dbinit_item.txt";
//	private static String initVed = "Core/src/initialiseringAvVedstatus.txt";
//	private static String initBruker = "Core/src/dbinit_bruker.txt";
	

	/**
	 * Gjør en spørring mot databasen
	 * @param query En query-streng
	 * @return Et resultat-sett
	 */
	private static ResultSet makeQuery(String query) {
		ResultSet res = null;
		try {
			Connection conn = getConnection();
			Statement st = conn.createStatement();
			res = st.executeQuery(query);
			//conn.close(); // Må kommenteres ut for at getIdNameMap skal fungere...
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
			return null;
		}
		return res;
	}

	/**
	 * Utfører statements mot databasen
	 * @param statement Statement som skal utføres
	 * @return returnerer om statementen ble fullført
	 */
	private static boolean makeStatement(String statement) {
		try {
			Connection conn = getConnection();
			Statement st = conn.createStatement();
			int res = st.executeUpdate(statement);
			conn.close();
			return true;
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Returnerer en kobling til databasen
	 * @return Et Connection-objekt
	 */
	private static Connection getConnection() {
		try {		
			Class.forName(driver).newInstance();
			Connection conn = DriverManager.getConnection(url+dbName,userName,password);
			return conn;
			
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Returnerer en HashMap med ID og navn til en koie
	 * @return hashmap key:koie_id, value: koie_navn
	 */
	public static HashMap<Integer,String> getIdNameMap() {
		try {
			ResultSet res = makeQuery("SELECT id, name FROM koie");
			HashMap<Integer, String> idNameMap = new HashMap<Integer, String>();
			while (res.next()){
				idNameMap.put(res.getInt("id"), res.getString("name"));
			}
			return idNameMap;
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
			return null;
		}
	}
	


	
	
	/**
	 * Sender rapporten/kommentaren til databasen
	 * @param koie_id Unik koie-ID
	 * @param person Unik bruker-ID
	 * @param kommentar Kommentar til rapporten
	 * @param resID Reservasjons-ID
	 * @return returnerer om rapporten ble lagret
	 */
	public static boolean rapporter(int koie_id, String person, String kommentar, int resID) {
		String statement = "INSERT INTO rapport (koie_id, bruker_id, kommentar, reservasjon_id) VALUES ('"
						 + koie_id +"', '" + person + "', '" + kommentar + "', '" + resID + "')";
		return makeStatement(statement);
	}
	
	/**
	 * Returnerer alle rapportene til en koie
	 * @param koie_id Unik koie-ID
	 * @return Liste med en streng på formen "personen-som-har-rapportert: rapporten" 
	 */
	public static ArrayList<String> getRapport(int koie_id) {
		ArrayList<String> rapport = new ArrayList<String>();
		try {
			String query = "SELECT kommentar, bruker_id FROM rapport WHERE koie_id =" + koie_id;
			ResultSet res = makeQuery(query);
			while (res.next()) {
				String kommentar = res.getString("bruker_id") + ": ";
				kommentar += res.getString("kommentar");
				rapport.add(kommentar);
			}
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
		}
		return rapport;
	}

}
