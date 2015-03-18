package classes;

public class PersonCalendar extends Calendar{
	
	private Person ownerPerson;
	
	public PersonCalendar(Person ownerPerson) {
		super(TypeOfCalendar.Personal);
		this.ownerPerson = ownerPerson;
	}

	public Person getOwnerPerson() {
		return ownerPerson;
	}

	public void setOwnerPerson(Person ownerPerson) {
		this.ownerPerson = ownerPerson;
	}
	
	public boolean isOwner(Object username, TypeOfCalendar type){
		if (ownerPerson.username == null)
			return false;
		return ownerPerson.username.equals(username) && type == super.type;
	}
	
	public String toString(){
		return ownerPerson.getFirstname() + " " +  ownerPerson.getLastname();
	}

}
