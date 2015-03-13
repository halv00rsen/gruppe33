package components;

import java.time.LocalDate;
import java.util.ArrayList;

import classes.Calendar;
import classes.Event;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import gui.Component;
import gui.Main;

public class CalendarGUI extends Component{
	RadioButton weekButton;
	RadioButton monthButton;
	ArrayList<CalendarGUIListener> listeners = new ArrayList<CalendarGUIListener>();
	CalendarBase month;
	CalendarBase week;
	ToggleGroup group;
	CalendarBase currentCalendarBase;
	VBox components;
	HBox buttons;
	LocalDate date;
	ArrayList<Event> allEvents;
	Calendar[] calendars;
	Event highlightedEvent;

	LocalDate highlightedDate;
	public CalendarGUI(Pane parent, LocalDate date, Calendar... args) {
		super(parent);
//		allEvents = new ArrayList<Event>();
//		for(Calendar k : args){
//			System.out.println(k);
//			if(! k.getEvents().isEmpty()){
//
//				allEvents.addAll(k.getEvents());
//			}
//		}
		calendars = args;
		this.date = date;
		month = new CalendarMonthBase(parent, date, args, this);
		week = new CalendarWeekBase(parent, date, args, this);
		currentCalendarBase = week;
		weekButton = new RadioButton("Uke");
		monthButton = new RadioButton("Måned");
		group = new ToggleGroup();
		weekButton.setToggleGroup(group);
		monthButton.setToggleGroup(group);
		weekButton.setOnAction(e -> weekButtonMethod(e));
		weekButton.setSelected(true);
		monthButton.setOnAction(e -> monthButtonMethod(e));
		buttons = new HBox(5,weekButton, monthButton);
		components = new VBox(10);
		components.getChildren().add(buttons);
		components.getChildren().add(currentCalendarBase);
		buttons.setAlignment(Pos.TOP_RIGHT);
		this.getChildren().add(components);
	}
	private void weekButtonMethod(ActionEvent e) {
		currentCalendarBase = week;
		components.getChildren().set(1,currentCalendarBase);
		
	}
	private void monthButtonMethod(ActionEvent e) {
		currentCalendarBase = month;
		components.getChildren().set(1,currentCalendarBase);
	}

	public void changeToWeek(LocalDate date) {
		if(currentCalendarBase instanceof CalendarMonthBase){
			weekButton.fire();
		}
		
	}
	public ArrayList<Event> getEventsByDay(LocalDate date){
		ArrayList<Event> events = new ArrayList<Event>();
		for (Calendar calendar : calendars) {
			events.addAll(calendar.getEventsByDay(date));
			
		}
		return events;
	}
	public void updateCalendars(Calendar... calendars){
		month.setNewCalendarList(calendars);
		month.generateCalendars();
		month.redrawCalendar();
		week.setNewCalendarList(calendars);
		week.generateCalendars();
		week.redrawCalendar();
	}
	public void addListener(CalendarGUIListener hei){
		this.listeners.add(hei);
	}
	public interface CalendarGUIListener {
		public void dayIsHighligthed(LocalDate date, ArrayList<Event> events);
		public void eventIsHighligthed(Event event);

	}
	public void highlightEvent(Event event) {
		if(highlightedEvent != null){
			if(highlightedEvent.getID() == event.getID()){

//				System.out.println("highlightedEvent.id" + highlightedEvent.getID() + " event.id" + event.getID());
				

				week.removeHighlightEvent();
				month.removeHighlightEvent();
				highlightedEvent = null;
				return;
			}else{
				week.removeHighlightEvent();
				month.removeHighlightEvent();
				highlightedEvent = null;
			}
			
		}
		
		this.highlightedEvent = event;
		for (CalendarGUIListener i : listeners) {
			i.eventIsHighligthed(event);
		}
		month.highlightEvent(event);
		week.highlightEvent(event);
	}
	public void highlightDate(LocalDate date) {
		if(highlightedEvent != null){
			if(!highlightedEvent.getStartDate().equals(date)){
				week.removeHighlightEvent();
				month.removeHighlightEvent();
				highlightedEvent = null;
			}
		}
		
		if(highlightedDate != null){
			if(highlightedDate.equals(date)){
			month.removeHighlightDate();
			week.removeHighlightDate();
			highlightedDate = null;
			return;
			}
		}
		highlightedDate = date;
		for (CalendarGUIListener i : listeners) {
			i.dayIsHighligthed(date, getEventsByDay(date));
			
		}
		month.highlightDate(date);
		week.highlightDate(date);
	}
}
