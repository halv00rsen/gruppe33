package classes;

import java.util.Date;
import java.util.List;

public class Room {
	private final int roomNr;
	private final RoomCalendar calendar;
	
	public Room(int roomNr) {
		calendar = new RoomCalendar(roomNr);
		this.roomNr = roomNr;
	}
	
	public boolean isAvailable(Date date) {
		List<Event> events = calendar.getEvents();
		for (Event event : events) {
			return !(event.getStartDate().after(date) && event.getEndDate().before(date));
		}
		return true;
	}
	
	public int getRoomNr() {
		return roomNr;
	}

}
