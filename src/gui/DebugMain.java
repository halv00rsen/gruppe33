package gui;
import components.*;
import windows.*;
import java.time.*;
import java.util.ArrayList;

import classes.Event;
import javafx.scene.layout.Pane;
import jfxtras.scene.control.agenda.Agenda;

public class DebugMain {

	/***
	 * 
	 * For � kj�re GUI komponenter til debuging. 
	 * Kan tulles med s� mye man �nsker, ikke en del av programmet.
	 */
	public DebugMain(Pane root) {
		
		Agenda agenda = new Agenda();
		
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
<<<<<<< HEAD
		event1.setStartTime(LocalDateTime.of(1986, Month.APRIL, 8, 12, 30));
		event2.setStartTime(LocalDateTime.of(1986, Month.APRIL, 8, 12, 31));
		event3.setStartTime(LocalDateTime.of(1986, Month.APRIL, 8, 12, 32));
		event4.setStartTime(LocalDateTime.of(1986, Month.APRIL, 8, 12, 33));
		event5.setStartTime(LocalDateTime.of(1986, Month.APRIL, 8, 12, 34));
=======
//		event1.setStartTime(new LocalDateTime(new LocalDate(5,5,5),new LocalTime(4,4, 0, 0)));
		event2.setEventName("Ski");
		event3.setEventName("Jakt");
		event4.setEventName("Skydiving");
		event5.setEventName("Handle");
>>>>>>> 52576ed6de17ca71bdfd25a2e6e3866e738a2e1c
		events.add(event1);
		events.add(event2);
		events.add(event3);
		events.add(event4);
		events.add(event5);
<<<<<<< HEAD
		SideMenu c = new SideMenu(root, LocalDate.now(), events);
=======
		CalendarWeekGUI c = new CalendarWeekGUI(root,LocalDate.now(),events);
		LoginGUI l = new LoginGUI(root);
>>>>>>> 52576ed6de17ca71bdfd25a2e6e3866e738a2e1c
		
		root.getChildren().add(c);
	}

}
