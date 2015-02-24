package classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Calendar {
	
	private final List<Event> events;
	
	public Calendar(){
		events = new ArrayList<Event>();
		//hent data fra database		
	}
	
	public boolean isAvailable(Date date) {
		for (Event event : events) {
			return !(event.getStartDate().after(date) && event.getEndDate().before(date));
			//Returnerer false hvis tiden er når det allerede finnes et event
		}
		return true;
	}
	
	public List getEvents() {
		return events;
	}
	
	public void addEvent(Event event){
		if (event != null)
			events.add(event);
	}
	
	public void removeEvent(Event event){
		if (event != null)
			events.remove(event);
	}
}
