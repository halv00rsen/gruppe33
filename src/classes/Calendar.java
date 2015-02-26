package classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class Calendar {
	
	private final List<Event> events;
	
	public Calendar(){
		events = new ArrayList<Event>();
		//hent data fra database		
	}
	
	public boolean isAvailable(LocalDateTime startTime, LocalDateTime endTime) {
		return getEventsByInterval(startTime, endTime).isEmpty();
	}
	
	
	//Skal returnere alle events som har start eller slutt innenfor intervallet.
	public ArrayList<Event> getEventsByInterval(LocalDateTime startTime, LocalDateTime endTime) {
		ArrayList<Event> list = new ArrayList<Event>();
		for (Event event : events) {
			
			if(event.getStartTime().isAfter(startTime) && event.getEndTime().isBefore(endTime)){
				list.add(event);
			}
			else if(event.getStartTime().isAfter(startTime) && event.getStartTime().isBefore(endTime))  {
				list.add(event);
			}
			else if (event.getStartTime().isBefore(startTime) && event.getEndTime().isAfter(endTime)) {
				list.add(event);
			}
			else if (event.getEndTime().isAfter(startTime) && event.getEndTime().isBefore(endTime)) {	
				list.add(event);
			}
		}
		return list;
	}
	
	
	
	public ArrayList<Event> getEventsByDay(LocalDate date) {
		LocalTime early = LocalTime.of(00, 00, 00);
		LocalTime late = LocalTime.of(23, 59, 59);
		return getEventsByInterval(LocalDateTime.of(date, early), LocalDateTime.of(date, late));
	}
	
	
	public List<Event> getEvents() {
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