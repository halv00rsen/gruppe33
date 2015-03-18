package components;

import gui.Component;
import gui.Main.AddNewEvent;
import gui.Main.ChangeTab;
import gui.Window;

import java.time.LocalDate;
import java.util.ArrayList;

import components.SideMenu.SideMenuInterface;
import classes.Calendar;
import classes.Event;
import classes.Group;
import classes.Person;
import classes.PersonCalendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SchedulingGUI extends Component{
    CalendarGUI calendargui;
    SideMenu menu;
    UserInfoGUI userInfo;
    
    public GroupCheckBox groupChechBox;
    VBox interactiveElementsBox;
    HBox mainBox;
    ObservableList<Group> eksempel = FXCollections.observableArrayList(
            new Group("chocolate",(int)Math.random()*10000), 
            new Group("salmon",(int)Math.random()*10000),
            new Group("gold",(int)Math.random()*10000),
            new Group("coral",(int)Math.random()*10000),
            new Group("darkorchid",(int)Math.random()*10000),
            new Group("darkgoldenrod",(int)Math.random()*10000),
            new Group("lightsalmon",(int)Math.random()*10000),
            new Group("black",(int)Math.random()*10000)
            );

	private Button settings;
	private AddNewEvent eventAdder;
	public SchedulingGUI(Window parent, ChangeTab tab, Calendar... calendars ) {
		super(parent);

		BorderPane borderPane = new BorderPane();
		
		interactiveElementsBox = new VBox(8);
		interactiveElementsBox.setTranslateY(CalendarBase.headerHeight);
		BorderPane.setMargin(interactiveElementsBox, new Insets(10));
		groupChechBox = new GroupCheckBox(this);
		groupChechBox.addGroups(eksempel);
//		settings.setOnAction(e -> main.requestSettingsWindow());
//		calendars[0].addEvent(DebugMain.getEvents());
		calendargui = new CalendarGUI(this, LocalDate.now(), calendars);
		ArrayList<Event> dagens = new ArrayList<Event>();
		for (int i = 0; i < calendars.length; i++) {
			dagens.addAll(calendars[i].getEventsByDay(LocalDate.now()));
		}
		menu = new SideMenu(this, dagens, tab);
		calendargui.addListener(menu);
		menu.addListener(calendargui);
		userInfo = new UserInfoGUI(this);
		Applicants applicants = new Applicants(this);
//		calendarAndInfo.getChildren().addAll(userInfo, menu);
		interactiveElementsBox.getChildren().addAll(groupChechBox,menu,applicants);
		borderPane.setTop(userInfo);
		borderPane.setCenter(calendargui);
		borderPane.setRight(interactiveElementsBox);
		
//		mainBox.getChildren().addAll(calendargui, calendarAndInfo);
		this.getChildren().add(borderPane);
	}
	
	public void changePerson(Person p){
		userInfo.changePerson(p);
	}

	public void updateCalendars(Calendar... calendars){
		calendargui.updateCalendars(calendars);
		
	}
	public void highlightEvent(Event event){
		calendargui.highlightEvent(event,true);
	}
//	public void newEvent(Event event){
//		calendargui.highlightEvent(event,true);
//	}
	
	public void addEventFromCalendar(Event event) {
		eventAdder.addEvent(event);
	}

	public void setEventAdder(AddNewEvent eventAdder) {
		this.eventAdder = eventAdder;
		
	}

}
