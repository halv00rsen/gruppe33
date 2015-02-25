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
	
	public void bookRoom(Date fromDate, Date toDate, Event event) {
		if (calendar.isAvailable(fromDate, toDate)) {
			calendar.addEvent(event);
		}
	}
	
	public int getRoomNr() {
		return roomNr;
	}

}
