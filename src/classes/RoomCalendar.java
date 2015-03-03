package classes;

public class RoomCalendar extends Calendar {
	
	private final int roomNr;
	
	public RoomCalendar(int roomNr) {
		this.roomNr = roomNr;
	}
	
	public boolean isOwner(Object roomNr){
		return ("" + roomNr).equals(roomNr.toString());
	}

}
