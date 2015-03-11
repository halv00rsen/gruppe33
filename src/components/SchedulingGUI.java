package components;

import gui.Component;
import gui.DebugMain;
import gui.SideMenu;
import gui.Window;

import java.time.LocalDate;

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
	public SchedulingGUI(Window parent) {
		super(parent);
		mainBox = new HBox(0);
		calendarAndInfo = new VBox(30);
		
		settings = new Button("Brukerinnstillinger");
//		settings.setOnAction(e -> main.requestSettingsWindow());
		Person p = DebugMain.getPerson();
		
		PersonCalendar cal = new PersonCalendar(p);
		cal.addEvent(DebugMain.getEvents());
		calendargui = new CalendarGUI(this, LocalDate.now(), cal);
		menu = new SideMenu(this, DebugMain.getEvents());
		userInfo = new UserInfoGUI(this, p);
		
		calendarAndInfo.getChildren().addAll(userInfo, menu);
		
		mainBox.getChildren().addAll(calendargui, calendarAndInfo);
		this.getChildren().add(mainBox);
		
		

	}
	public void highlightEvent(Event event) {
		calendargui.highlightEvent(event);
		
	}
}
