package classes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Person {
	
	public final String username;
	public final boolean admin;
	
	private final Calendar calendar;
	private final List<Group> groups;
	
	private String password, phone, email, firstname, lastname;
	private boolean toStringName;
	
	public Person(String username, String password, String firstname, String lastname
			, boolean admin){
		calendar = new PersonCalendar(this);
		groups = new ArrayList<Group>();
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.admin = admin;
		toStringName = true;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setOtherInfo(String phone, String email){
		this.phone = phone;
		this.email = email;
	}
	
	public String getMail(){
		return email;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public boolean isCorrectPassword(String password){
		if (this.password == null)
			return false;
		return this.password.equals(hashPassword(password));
	}
	
	public void changePassword(String password){
		String pswrd = hashPassword(password);
		if (pswrd != null)
			this.password = pswrd;
		//husk å endre passord i database
	}
	
	public void setFirstname(String firstname){
		this.firstname = firstname;
	}
	
	public void setLastname(String lastname){
		this.lastname = lastname;
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

	public String getUsername() {
		return username;
	}

	public String getFirstname() {
		return firstname;
	}
	
	public String getLastname(){
		return lastname;
	}

	public List<Group> getGroups() {
		return groups;
	}
	
	public void changeToString(){
		toStringName = !toStringName;
	}
	
	public String toString(){
		if (toStringName)
			return firstname + lastname;
		return username;
	}
	
}
