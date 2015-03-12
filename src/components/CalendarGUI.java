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
		if(month.getHighlighted() != null){
			week.setNewDate(month.getHighlightedDate());
			week.dayClicked(month.getHighlightedDate());
			month.removeHighlighted();
		}else{
			week.setNewDate(month.getDate().minusDays(month.getDate().getDayOfMonth()-1));
		}
		currentCalendarBase = week;
		components.getChildren().set(1,currentCalendarBase);
		
	}
	private void monthButtonMethod(ActionEvent e) {
		if(week.getHighlighted() != null){
			month.setNewDate(week.getHighlightedDate());
			month.dayClicked(week.getHighlightedDate());
			week.removeHighlighted();
		}else{
			month.setNewDate(week.getDate());
		}
		currentCalendarBase = month;
		components.getChildren().set(1,currentCalendarBase);
	}
	public void setHighlighted(LocalDate date, ArrayList<Event> events) {
		this.date = date;
		for (CalendarGUIListener i : listeners) {
			i.dayIsHighligthed(date, events);
			
		}
		
	}
	public void doubleClicked(LocalDate date) {
		if(currentCalendarBase instanceof CalendarMonthBase){
			weekButton.fire();
		}
		
	}
	public void addListener(CalendarGUIListener hei){
		this.listeners.add(hei);
	}
	public interface CalendarGUIListener {
		public void dayIsHighligthed(LocalDate date, ArrayList<Event> events);
		public void eventIsHighligthed(Event event);

	}
	public void highlightEvent(Event event) {
		for (CalendarGUIListener i : listeners) {
			i.eventIsHighligthed( event);
		}
		ArrayList<Event> events = new ArrayList<Event>();
		for (int i = 0; i < calendars.length; i++) {
			events.addAll(calendars[i].getEventsByDay(event.getStartDate()));
		}
		setHighlighted(event.getStartDate(), events);
		
		currentCalendarBase.highlightEvent(event);
	}
	public void highlightEventUpstream(Event event) {
		for (CalendarGUIListener i : listeners) {
			i.eventIsHighligthed( event);
		}
		ArrayList<Event> events = new ArrayList<Event>();
		for (int i = 0; i < calendars.length; i++) {
			events.addAll(calendars[i].getEventsByDay(event.getStartDate()));
		}
		setHighlighted(event.getStartDate(), events);
		
	}
}
