package components;

import java.time.LocalDate;
import java.util.ArrayList;

import components.SideMenu.SideMenuInterface;

import classes.Calendar;
import classes.Event;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import gui.Component;
import gui.Main;
import gui.Main.AddNewEvent;

public class CalendarGUI extends Component implements SideMenuInterface{
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
	static ClockLines clocklines;
	LocalDate highlightedDate;
//	private final AddNewEvent eventCall;
	SchedulingGUI sch;
	public CalendarGUI(Pane parent, LocalDate date, Calendar... args) {
		super(parent);
		if(parent instanceof SchedulingGUI){
			this.sch = (SchedulingGUI)parent;
		}
//		eventCall = new AddNewEvent();
		clocklines =  new ClockLines();
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
		if(highlightedEvent == null && highlightedDate== null){

			week.setNewDate(month.date.minusDays(month.date.getDayOfMonth()-1));
		}
		currentCalendarBase = week;
		components.getChildren().set(1,currentCalendarBase);
		
	}
	private void monthButtonMethod(ActionEvent e) {
		if(highlightedEvent == null && highlightedDate== null){

			month.setNewDate(week.date);
		}
		currentCalendarBase = month;
		components.getChildren().set(1,currentCalendarBase);
	}

	public void changeToWeek(LocalDate date) {
		if(currentCalendarBase instanceof CalendarMonthBase){
			weekButton.fire();
		}
		
	}
	public ArrayList<Event> getEventsByDay(LocalDate date){
		if(date == null){
			return null;
		}
		ArrayList<Event> events = new ArrayList<Event>();
		for (Calendar calendar : calendars) {
			events.addAll(calendar.getEventsByDay(date));
			
		}
		return events;
	}
	public void updateCalendars(Calendar... calendars){
		this.calendars = calendars;
		month.setNewCalendarList(calendars);
		month.generateCalendars();
		month.redrawCalendar();
		week.setNewCalendarList(calendars);
		week.generateCalendars();
		week.redrawCalendar();
//		highlightedDate = null;
//		highlightedEvent = null;
		if(highlightedEvent != null){
			highlightEvent(highlightedEvent,true);
		}
		if(highlightedDate != null){
			highlightDate(highlightedDate);
		}
	}
	public void addListener(CalendarGUIListener hei){
		this.listeners.add(hei);
	}
	public interface CalendarGUIListener {
		public void dayIsHighligthed(LocalDate date, ArrayList<Event> events);
		public void eventIsHighligthed(Event event);

	}
	public void highlightEvent(Event event,boolean alert) {
		System.out.println("highlightedEvent"  + highlightedEvent);
		if(highlightedDate != null){
				week.removeHighlightDate();
				month.removeHighlightDate();
				if(alert){
					alertListenersAboutDate(null);
				}
				
				highlightedDate = null;
		}
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
			if(alert){
				alertListenersAboutEvent(null);
			}
		}
		
		this.highlightedEvent = event;
		if(alert){
			alertListenersAboutDate(event.getStartDate());
			alertListenersAboutEvent(event);
		}
		
		month.highlightEvent(event);
		week.highlightEvent(event);
	}
	public void highlightDate(LocalDate date) {
		System.out.println("highlightedEvent"  + highlightedEvent);
		if(highlightedEvent != null){
				System.out.println("REMOVE REMOVE REMOVE");
				week.removeHighlightEvent();
				month.removeHighlightEvent();
				alertListenersAboutEvent(null);
				highlightedEvent = null;
		}
		
		if(highlightedDate != null){
			if(highlightedDate.equals(date)){
				month.removeHighlightDate();
				week.removeHighlightDate();
				highlightedDate = null;
				alertListenersAboutDate(null);
				return;
			}else{
				month.removeHighlightDate();
				week.removeHighlightDate();
				highlightedDate = null;
			}
			alertListenersAboutDate(null);
		}
		highlightedDate = date;
		alertListenersAboutDate(date);
		month.highlightDate(date);
		week.highlightDate(date);
	}
	public void alertListenersAboutDate(LocalDate date){
		for (CalendarGUIListener i : listeners) {
			
			i.dayIsHighligthed(date, getEventsByDay(date));
			
		}
	}
	public void alertListenersAboutEvent(Event event){
		for (CalendarGUIListener i : listeners) {
		
			i.eventIsHighligthed(event);
			
		}
	}
	public class ClockLines extends Pane{
		public double calWidth = CalendarBase.defaultCalWidth;
		public double calHeight = CalendarBase.defaultCalHeight;
		public ClockLines(){
			double hourHeight = calHeight/24;
			double u = hourHeight;
//			this.setTranslateY(8);
			for(int i = 0; i <24; i++){
				Label number = new Label();
				number.setText(""+(i+1)+".00");
				Line l = new Line();
				number.setTextFill(Color.DARKGREY);
				number.setFont(Font.font ("Verdana", 8));
				l.setStroke(Color.DARKGREY);
				l.getStrokeDashArray().addAll(2d, 4d);

				l.setStrokeWidth(0.5);
				l.setStartX(0);
				l.setEndX(calWidth);
				l.setStartY(0);
				l.setEndY(0);
				l.setTranslateY(u);
				l.setTranslateX(30);
				number.setTranslateY(u-6);
				number.setTranslateX(5);
				this.getChildren().add(number);
				this.getChildren().add(l);
				u += hourHeight;
				
			}
		
		this.setDisable(true);
			
	}
	}
	public void addEventFromCalendar(Event event) {
		sch.addEventFromCalendar(event);
		
		
	}
	@Override
	public void changingSelectionToEvent(Event event) {
		highlightEvent(event,false);
		
	}
}
