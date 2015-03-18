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
	
	public boolean isOwner(int id, TypeOfCalendar type){
		if (ownerPerson.username == null)
			return false;
		return false;
	}
	
	public String toString(){
		if (ownerPerson == null)
			return "Personlig kalender";
		return ownerPerson.getFirstname() + " " +  ownerPerson.getLastname();
	}

}
