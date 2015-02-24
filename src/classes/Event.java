package classes;

import java.sql.Time;
import java.util.Collection;
import java.util.Date;


public class Event {
	String eventName;
	String location;
	Room room;
	Time startTime;
	Time endTime;
	Date startDate;
	Date stopDate;
	Integer freq;
	Person madeBy;
	Collection<EventAppliance> appliance;
	Boolean priority;
	String info;
	
	public Event(String eventName, String location, Room room, Time startTime,
			Time endTime, Date startDate, Date stopDate, Integer freq,
			Person madeBy, Collection<EventAppliance> appliance,
			Boolean priority, String info) {
		
		this.eventName = eventName;
		this.location = location;
		this.room = room;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startDate = startDate;
		this.stopDate = stopDate;
		this.freq = freq;
		this.madeBy = madeBy;
		this.appliance = appliance;
		this.priority = priority;
		this.info = info;
	}
	
	
	
	

}
