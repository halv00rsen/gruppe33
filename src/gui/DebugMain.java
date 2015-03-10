package gui;
import components.*;
import windows.*;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

import classes.Event;
import classes.Group;
import classes.Person;
import classes.Priority;
import javafx.scene.layout.Pane;
//import jfxtras.scene.control.agenda.Agenda;

public class DebugMain {

	/***
	 * 
	 * For � kj�re GUI komponenter til debuging. 
	 * Kan tulles med s� mye man �nsker, ikke en del av programmet.
	 * 
	 * 
	 */
	
	public static Person getPerson() {
		Person ola = new Person("olaNord", "hei", 4, "Ola Nordmann", true);
		Group konsernetGruppe = new Group("Konsernet", 0, ola);
		Group langrennsGruppe = new Group("Langrennslaget", 1, ola);
		
		ola.addGroup(konsernetGruppe);
		ola.addGroup(langrennsGruppe);
		
		return ola;
	}
	
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
		
		event1.setStartTime(LocalDateTime.of(2015, Month.MARCH, 14, 3, 1));
		event1.setEndTime(LocalDateTime.of(2015, Month.MARCH, 14, 8, 59));
		
		event2.setStartTime(LocalDateTime.of(2015, Month.MARCH, 14, 7, 31));
		event2.setEndTime(LocalDateTime.of(2015, Month.MARCH, 14, 18, 30));
		
		event3.setStartTime(LocalDateTime.of(2015, Month.MARCH, 14, 17, 32));
		event3.setEndTime(LocalDateTime.of(2015, Month.MARCH, 14, 20, 30));
		
		event4.setStartTime(LocalDateTime.of(2015, Month.APRIL, 2, 4, 33));
		event4.setEndTime(LocalDateTime.of(2015, Month.APRIL, 2, 8, 30));

		event5.setStartTime(LocalDateTime.of(2015, Month.APRIL, 14, 12, 34));
		event5.setEndTime(LocalDateTime.of(2015, Month.APRIL, 18, 23, 30));
		
		
		event1.setLocation("P15");
		event2.setLocation("Allevegen 25");
		event3.setLocation("M�nen");
		event4.setLocation("B-233");
		event5.setLocation("B-277");
		
		
		event1.setInfo("Det var en gang en event som ikke lignet maken");
		event2.setInfo("Ingen kunne forst� hvorfor dette feltet var s� awsome");
		event3.setInfo("Morradi er en slank havfrue, ikke en feit ku");
		event4.setInfo("Dette er masse informasjon om denne eventen");
		event5.setInfo("Bla bla bla bla");
		
		event1.setInfo("Det var en gang en event som ikke lignet maken");
		event2.setInfo("Ingen kunne forst� hvorfor dette feltet var s� awsome");
		event3.setInfo("Morradi er en slank havfrue, ikke en feit ku");
		event4.setInfo("Dette er masse informasjon om denne eventen");
		event5.setInfo("Bla bla bla bla");
		
		event1.setFreq(7);
		event4.setFreq(8);
		
		event1.setFreqEndTime(LocalDateTime.of(2015, Month.JULY, 25, 16, 30));

		event4.setFreqEndTime(LocalDateTime.of(2015, Month.JUNE, 25, 16, 30));
		
		event1.setPriority(Priority.IMPORTANT);
		event2.setPriority(Priority.VERY_IMPORTANT);
		event3.setPriority(Priority.NOT_IMPORTANT);
		event4.setPriority(Priority.IMPORTANT);
		event5.setPriority(Priority.NOT_IMPORTANT);
		
		events.add(event1);
		events.add(event2);
		events.add(event3);
		events.add(event4);
		events.add(event5);
		return events;
	}
	
	public DebugMain(Pane root) {
		
//		Agenda agenda = new Agenda();
		
		

		

//		CalendarWeekGUI c = new CalendarWeekGUI(root,LocalDate.now(),events);
//		LoginGUI l = new LoginGUI(main);
		
//		root.getChildren().add(l);
	}

}
