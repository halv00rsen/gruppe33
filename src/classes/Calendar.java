package classes;

import java.util.ArrayList;
import java.util.List;

public class Calendar {
	
	private final List<Event> events;
	
	public Calendar(){
		events = new ArrayList<Event>();
		//hent data fra database
		
		
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
