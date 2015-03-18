package classes;

public class EventAppliance implements Comparable<EventAppliance>{
	public final Person person;
	public Appliance appliance;
	public boolean hasCalendarShowing;
	
	public EventAppliance(Person p, Appliance a){
		person = p;
		appliance = a;
		hasCalendarShowing = false;
	}

	public Appliance getAppliance() {
		return appliance;
	}

	public void setAppliance(Appliance appliance) {
		this.appliance = appliance;
	}

	public boolean isHasCalendarShowing() {
		return hasCalendarShowing;
	}

	public void setHasCalendarShowing(boolean hasCalendarShowing) {
		this.hasCalendarShowing = hasCalendarShowing;
	}

	public Person getPerson() {
		return person;
	}

	@Override
	public int compareTo(EventAppliance o1) {
		if(this.getAppliance().getValue() > o1.getAppliance().getValue()){
			return -1;
		}else if(this.getAppliance().getValue() < o1.getAppliance().getValue()){
			return 1;
		}else{
			return 0;
		}
		
	}
}
