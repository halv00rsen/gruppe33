package classes;

public class PersonCalendar extends Calendar{
	private Person ownerPerson;
	
	public PersonCalendar(Person ownerPerson) {
		this.ownerPerson = ownerPerson;
	}

	public Person getOwnerPerson() {
		return ownerPerson;
	}

	public void setOwnerPerson(Person ownerPerson) {
		this.ownerPerson = ownerPerson;
	}
	
	

}
