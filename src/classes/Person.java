package classes;

import java.util.ArrayList;
import java.util.List;

public class Person {
	
	private final Calendar calendar;
	private String password;
	public final String username, name;
	private final int personid;
	private final List<Group> groups;
	
	public Person(String username, String password, int personid, String name){
		calendar = new Calendar();
		this.username = username;
		this.password = password;
		this.personid = personid;
		groups = new ArrayList<Group>();
		this.name = name;
	}
	
	public void changePassword(String password){
		this.password = password;
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
		//legg til calendar
	}
	
	
	
}
