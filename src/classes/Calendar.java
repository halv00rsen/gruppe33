package classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Calendar {
	
	private List<Event> events;
	
	public Calendar(){
		events = new ArrayList<Event>();
		//hent data fra database		
	}
	
	public void addEvent(Event event) {
		events.add(event);
	}
	
	public void removeEvent(Event event) {
		events.remove(event);
	}
	
	public List getEvents() {
		return events;
	}
	
}
