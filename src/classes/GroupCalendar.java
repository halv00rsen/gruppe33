package classes;

public class GroupCalendar extends Calendar{
	
	private Group ownerGroup;

	public GroupCalendar(Group ownerGroup) {
		super(TypeOfCalendar.Group);
		this.ownerGroup = ownerGroup;
	}

	public Group getOwnerGroup() {
		return ownerGroup;
	}

	public void setOwnerGroup(Group ownerGroup) {
		this.ownerGroup = ownerGroup;
	}
	
	public boolean isOwner(int id, TypeOfCalendar type){
		return id == ownerGroup.id && type == super.type;
	}
	
	public String toString(){
		return ownerGroup.getName();
	}

}
