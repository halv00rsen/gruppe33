package tests;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateComparisonTest {
	public boolean getEventsByInterval(LocalDateTime startTime, LocalDateTime endTime) {
		LocalDateTime eventStartTime = LocalDateTime.of(2015, 2, 27, 10, 59);
		LocalDateTime eventEndTime = LocalDateTime.of(2015, 2, 27, 12, 00);
		
		if(eventStartTime.isAfter(startTime) && eventEndTime.isBefore(endTime)) {
			//System.out.println("overlapp");
		}
		else if(eventStartTime.isAfter(startTime) && eventStartTime.isBefore(endTime))  {
			//System.out.println("overlapp");
		}
		else if (eventStartTime.isBefore(startTime) && eventEndTime.isAfter(endTime)) {
			//System.out.println("overlapp");
		}
		else if (eventEndTime.isAfter(startTime) && eventEndTime.isBefore(endTime)) {	
			//System.out.println("overlapp");
		}
		return true;
	}
	
	public static void main(String[] args) {
		DateComparisonTest dt = new DateComparisonTest();
		LocalDateTime eventStartTime = LocalDateTime.now();
		LocalDateTime eventEndTime = LocalDateTime.of(2015, 2, 27, 11, 00);
		
		dt.getEventsByInterval(eventStartTime, eventEndTime);
	}
}
