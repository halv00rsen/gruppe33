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
	
	public boolean isAvailable(Date startDate, Date endDate) {
		return getEventsByInterval(startDate, endDate).isEmpty();
	}
	
	
	//Skal returnere alle events som har start eller slutt innenfor intervallet.
	public ArrayList<Event> getEventsByInterval(Date startDate, Date endDate) {
		ArrayList list = new ArrayList<Event>();
		for (Event event : events) {
			if(event.getStartDate().after(startDate) && event.getStartDate().before(endDate))  {
				list.add(event);
			}
			else if (event.getStartDate().before(startDate) && event.getEndDate().after(endDate)) {
				list.add(event);
			}
			else if (event.getEndDate().after(startDate) && event.getEndDate().before(endDate)) {	
				list.add(event);
			}
		}
		return list;
	}
	
	public ArrayList<Event> getEventsByDay(Date date) {
		Date startTime = new Date(date.getYear(), date.getMonth(), date.getDate(), 0, 0, 0);
		Date endTime = new Date(date.getYear(), date.getMonth(), date.getDate(), 23, 59, 59);
		return getEventsByInterval(startTime, endTime);
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