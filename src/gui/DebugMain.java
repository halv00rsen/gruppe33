package gui;

import java.time.*;
import java.util.ArrayList;

import classes.Event;
import javafx.scene.layout.Pane;

public class DebugMain {

	/***
	 * 
	 * For å kjøre GUI komponenter til debuging. 
	 * Kan tulles med så mye man ønsker, ikke en del av programmet.
	 */
	public DebugMain(Pane root) {
		
		
		ArrayList<Event> events = new ArrayList<Event>();
		Event event1 = new Event(null, null, null, null, null, null, null, null, null, null, null, null);
		Event event2 = new Event(null, null, null, null, null, null, null, null, null, null, null, null);
		Event event3 = new Event(null, null, null, null, null, null, null, null, null, null, null, null);
		Event event4 = new Event(null, null, null, null, null, null, null, null, null, null, null, null);
		Event event5 = new Event(null, null, null, null, null, null, null, null, null, null, null, null);

		
		event1.setEventName("Bowling");
		event2.setEventName("Ski");
		event3.setEventName("Jakt");
		event4.setEventName("Skydiving");
		event5.setEventName("Handle");
//		event1.setStartTime(new LocalDateTime(new LocalDate(5,5,5),new LocalTime(4,4, 0, 0)));
		event2.setEventName("Ski");
		event3.setEventName("Jakt");
		event4.setEventName("Skydiving");
		event5.setEventName("Handle");
		events.add(event1);
		events.add(event2);
		events.add(event3);
		events.add(event4);
		events.add(event5);
		CalendarWeekGUI c = new CalendarWeekGUI(root,LocalDate.now(),events);
		LoginGUI l = new LoginGUI(root);
		
		root.getChildren().add(c);
	}

}
