package classes;

import java.util.Collection;

public class Group {
	private String name;
	private Collection<Person> members;
	private final Calendar groupCalendar;
	private Collection<Group> subGroups;
	
	public Group(String name, Collection<Person> members) {
		groupCalendar = new GroupCalendar(this);
		this.name = name;
		this.members = members;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Person> getMembers() {
		return members;
	}

	public void setMembers(Collection<Person> members) {
		this.members = members;
	}

	public Calendar getGroupCalendar() {
		return groupCalendar;
	}

	public Collection<Group> getSubGroups() {
		return subGroups;
	}

	public void setSubGroups(Collection<Group> subGroups) {
		this.subGroups = subGroups;
	}
	
}
