package gui;
import components.*;
import windows.*;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

import classes.Event;
import javafx.scene.layout.Pane;
//import jfxtras.scene.control.agenda.Agenda;

public class DebugMain {

	/***
	 * 
	 * For å kjøre GUI komponenter til debuging. 
	 * Kan tulles med så mye man ønsker, ikke en del av programmet.
	 * 
	 * 
	 */
	
	public static ArrayList<Event> getEvents(){
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
		event1.setStartTime(LocalDateTime.of(1986, Month.APRIL, 8, 12, 30));
		event2.setStartTime(LocalDateTime.of(1986, Month.APRIL, 8, 12, 31));
		event3.setStartTime(LocalDateTime.of(1986, Month.APRIL, 8, 12, 32));
		event4.setStartTime(LocalDateTime.of(1986, Month.APRIL, 8, 12, 33));
		event5.setStartTime(LocalDateTime.of(1986, Month.APRIL, 8, 12, 34));
		
		event1.setEndTime(LocalDateTime.of(1986, Month.APRIL, 8, 13, 30));
		event2.setEndTime(LocalDateTime.of(1986, Month.APRIL, 8, 14, 30));
		event3.setEndTime(LocalDateTime.of(1986, Month.APRIL, 8, 15, 30));
		event4.setEndTime(LocalDateTime.of(1986, Month.APRIL, 8, 16, 30));
		event5.setEndTime(LocalDateTime.of(1986, Month.APRIL, 8, 17, 30));
		
		event1.setLocation("Bagasjerommet");
		event2.setLocation("Bakrommet");
		event3.setLocation("Her");
		
		event1.setInfo("Ingenting");
		event2.setInfo("Alt");
		event3.setInfo("Morradi");
		
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
		return events;
	}
	
	public DebugMain(Pane root, Main main) {
		
//		Agenda agenda = new Agenda();
		
		

		
		SideMenu c = new SideMenu(root, LocalDate.now(), getEvents(), main);
		root.getChildren().add(c);
//		CalendarWeekGUI c = new CalendarWeekGUI(root,LocalDate.now(),events);
//		LoginGUI l = new LoginGUI(main);
		
//		root.getChildren().add(l);
	}

}
