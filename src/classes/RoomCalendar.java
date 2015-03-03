package classes;

public class RoomCalendar extends Calendar {
	
	private final int roomNr;
	
	public RoomCalendar(int roomNr) {
		super(TypeOfCalendar.Room);
		this.roomNr = roomNr;
	}
	
	public boolean isOwner(Object roomNr, TypeOfCalendar type){
		return ("" + roomNr).equals(roomNr.toString()) && type == super.type;
	}

}
