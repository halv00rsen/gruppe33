package classes;

import java.util.ArrayList;
import java.util.List;

public class Group {
	
	public final int id;
	private String name;
	private final List<Person> members;
	private final Calendar groupCalendar;
	private final List<Group> subGroups;
	
	public Group(String name, int id, Person... members) {
		groupCalendar = new GroupCalendar(this);
		this.id = id;
		this.name = name;
		this.members = new ArrayList<Person>();
		for (Person p : members)
			if (p != null)
				this.members.add(p);
		subGroups = new ArrayList<Group>();
	}

	public String getName() {
		return name;
	}

	public void changeName(String name) {
		this.name = name;
	}

	public void removePerson(Person p){
		members.remove(p);
	}
	
	public List<Person> getMembers() {
		return new ArrayList<Person>(members);
	}

	public void addMembers(Person... members) {
		for (Person p : members)
			if (p != null)
				this.members.add(p);
	}

	public Calendar getGroupCalendar() {
		return groupCalendar;
	}

	public List<Group> getSubGroups() {
		return new ArrayList<Group>(subGroups);
	}

	public void addSubGroups(Group... subGroups) {
		for (Group g: subGroups)
			if (g != null)
				this.subGroups.add(g);
	}
	
	public void removeSubGroup(Group g){
		subGroups.remove(g);
	}
	
}
