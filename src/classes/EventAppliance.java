package classes;

public class EventAppliance {
	public final Person person;
	public Appliance appliance;
	public boolean hasCalendarShowing;
	
	public EventAppliance(Person p, Appliance a){
		person = p;
		appliance = a;
		hasCalendarShowing = false;
	}
}
