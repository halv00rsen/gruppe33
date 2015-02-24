package classes;

public class GroupCalendar extends Calendar{
	
	private Group ownerGroup;

	public GroupCalendar(Group ownerGroup) {
		this.ownerGroup = ownerGroup;
	}

	public Group getOwnerGroup() {
		return ownerGroup;
	}

	public void setOwnerGroup(Group ownerGroup) {
		this.ownerGroup = ownerGroup;
	}
	
	

}
