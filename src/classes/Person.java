package classes;

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
		calendar.addEvent(event);
	}
	
	public void removeEvent(Event event){
		calendar.removeEvent(event);
	}
	
	
	
}
