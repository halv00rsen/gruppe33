package components;

import gui.Component;
import gui.Main.AddNewEvent;
import gui.Main.ChangeTab;
import gui.Window;

import java.time.LocalDate;
import java.util.ArrayList;

import classes.Calendar;
import classes.Event;
import classes.Person;
import classes.PersonCalendar;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SchedulingGUI extends Component{
    CalendarGUI calendargui;
    SideMenu menu;
    UserInfoGUI userInfo;
    
    VBox calendarAndInfo;
    HBox mainBox;
    

	private Button settings;
	private AddNewEvent eventAdder;
	public SchedulingGUI(Window parent, ChangeTab tab, Calendar... calendars ) {

		super(parent);
		mainBox = new HBox(30);
		calendarAndInfo = new VBox(0);
	
//		settings.setOnAction(e -> main.requestSettingsWindow());
		
//		calendars[0].addEvent(DebugMain.getEvents());
		calendargui = new CalendarGUI(this, LocalDate.now(), calendars);
		ArrayList<Event> dagens = new ArrayList<Event>();
		for (int i = 0; i < calendars.length; i++) {
			dagens.addAll(calendars[i].getEventsByDay(LocalDate.now()));
		}
		menu = new SideMenu(this, dagens, tab);
		calendargui.addListener(menu);
		userInfo = new UserInfoGUI(this);
		
		calendarAndInfo.getChildren().addAll(userInfo, menu);
		
		mainBox.getChildren().addAll(calendargui, calendarAndInfo);
		this.getChildren().add(mainBox);
	}
	
	public void changePerson(Person p){
		userInfo.changePerson(p);
	}

	public void updateCalendars(Calendar... calendars){
		calendargui.updateCalendars(calendars);
		
	}
	public void highlightEvent(Event event){
		calendargui.highlightEvent(event);
	}
	public void newEvent(Event event){
		calendargui.highlightEvent(event);
	}
	
	public void addEventFromCalendar(Event event) {
		eventAdder.addEvent(event);
	}

	public void setEventAdder(AddNewEvent eventAdder) {
		this.eventAdder = eventAdder;
		
	}
}
