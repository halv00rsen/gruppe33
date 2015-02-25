package classes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Person {
	
	public final String username, name;
	
	private final Calendar calendar;
	private final int personid;
	private final List<Group> groups;
	
	private String password;
	
	public Person(String username, String password, int personid, String name){
		calendar = new PersonCalendar(this);
		groups = new ArrayList<Group>();
		this.username = username;
		this.password = password;
		this.personid = personid;
		this.name = name;
	}
	
	public boolean isCorrectPassword(String password){
		return this.password.equals(hashPassword(password));
	}
	
	public void changePassword(String password){
		String pswrd = hashPassword(password);
		if (pswrd != null)
			this.password = pswrd;
		//husk å endre passord i database
	}
	
	public void addGroup(Group group){
		if (group != null)
			groups.add(group);
	}
	
	public void removeGroup(Group group){
		if (group != null)
			groups.remove(group);
	}
	
	public void addEvent(Event event){
		calendar.addEvent(event);
	}
	
	public void removeEvent(Event event){
		calendar.removeEvent(event);
	}
	
	public Calendar getPersonalCalendar(){
		return calendar;
	}
	
	public static String hashPassword(String password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(password.getBytes());
			byte[] digest = messageDigest.digest();
			BigInteger n = new BigInteger(1, digest);
			String hash = n.toString(16);
			return hash;
		} catch (java.security.NoSuchAlgorithmException e) {
			return null;
		}
	}
}
