package database;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import classes.Event;
import classes.Person;
import classes.Priority;

public class PersonInformation {
	
	public static Map<String, String> getPersonInformation(String username, String password){
		//hent data fra databasen
		Map<String, String> info = new HashMap<String, String>();
		info.put("username", username);
		info.put("password", password);
		info.put("firstname", "Ola");
		info.put("lastname", "Nordmann");
		info.put("isAdmin", "false");
		info.put("email", "ola@nordmann.no");
		info.put("phone", "12345678");
		
		return info;
	}
	
	public static boolean changePassword(String username, String oldPassword, String newPassword){
		return true;
	}
	
	public static ArrayList<Person> getPeople() {
		Person kari = new Person("kariHols", "hallo", "Kari",  "Holst", false);
		Person benjamin = new Person("bennyBoy", "naughty", "Benjamin",  "Button", false);
		Person erlend = new Person("erlbe", "passord123", "Erlend",  "Berger", true);
		
		ArrayList<Person> list = new ArrayList<Person>(); 
		list.addAll(Arrays.asList(kari, benjamin, erlend));
		return list;
		
	}
	
	public static ArrayList<Event> getEvents(){
		ArrayList<Event> events = new ArrayList<Event>();
		Event event1 = new Event();
		Event event2 = new Event();
		Event event3 = new Event();
		Event event4 = new Event();
		Event event5 = new Event();

		
		event1.setEventName("Bowling");
		event2.setEventName("Ski");
		event3.setEventName("Jakt");
		event4.setEventName("Skydiving");
		event5.setEventName("Handle");
		
		event1.setStartTime(LocalDateTime.of(2015, Month.MARCH, 14, 3, 1));
		event1.setEndTime(LocalDateTime.of(2015, Month.MARCH, 14, 8, 59));
		
		event2.setStartTime(LocalDateTime.of(2015, Month.MARCH, 14, 7, 31));
		event2.setEndTime(LocalDateTime.of(2015, Month.MARCH, 14, 18, 30));
		
		event3.setStartTime(LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth(), 12, 32));
		event3.setEndTime(LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth(), 20, 30));
		
		event4.setStartTime(LocalDateTime.of(2015, Month.APRIL, 2, 4, 33));
		event4.setEndTime(LocalDateTime.of(2015, Month.APRIL, 2, 8, 30));

		event5.setStartTime(LocalDateTime.of(2015, Month.APRIL, 14, 12, 34));
		event5.setEndTime(LocalDateTime.of(2015, Month.APRIL, 18, 23, 30));
		
		
		event1.setLocation("P15");
		event2.setLocation("Allevegen 25");
		event3.setLocation("Månen");
		event4.setLocation("B-233");
		event5.setLocation("B-277");
		
		
		event1.setInfo("Det var en gang en event som ikke lignet maken");
		event2.setInfo("Ingen kunne forstå hvorfor dette feltet var så awsome");
		event3.setInfo("Morradi er en slank havfrue, ikke en feit ku");
		event4.setInfo("Dette er masse informasjon om denne eventen");
		event5.setInfo("Bla bla bla bla");
		
		event1.setInfo("Det var en gang en event som ikke lignet maken");
		event2.setInfo("Ingen kunne forstå hvorfor dette feltet var så awsome");
		event3.setInfo("Morradi er en slank havfrue, ikke en feit ku");
		event4.setInfo("Dette er masse informasjon om denne eventen");
		event5.setInfo("Bla bla bla bla");
		
		event1.setFreq(7,false, LocalDateTime.of(2015, Month.JULY, 25, 16, 30).toLocalDate());
		event4.setFreq(8,false, LocalDateTime.of(2015, Month.JULY, 25, 16, 30).toLocalDate());
		
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
}
