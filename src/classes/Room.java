package classes;

import java.time.LocalDateTime;


public class Room {
	private final String roomName;
	private final int roomNr;
	private final RoomCalendar calendar;
	
	public Room(int roomNr, String roomName) {
		this.roomName = roomName;
		calendar = new RoomCalendar(roomNr);
		this.roomNr = roomNr;
	}
	
	public String getRoomName() {
		return roomName;
	}

	public void bookRoom(LocalDateTime fromTime, LocalDateTime toTime, Event event) {
		if (calendar.isAvailable(fromTime, toTime)) {
			calendar.addEvent(event);
		}
	}
	
	public int getRoomNr() {
		return roomNr;
	}
	
	public RoomCalendar getCalendar() {
		return calendar;
	}

}
