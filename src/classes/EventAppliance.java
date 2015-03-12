package classes;

public class EventAppliance {
	Person person;
	Appliance appliance;
	boolean hasCalendarShowing;
	
	public EventAppliance(Person p, Appliance a){
		person = p;
		appliance = a;
		hasCalendarShowing = false;
	}
}
