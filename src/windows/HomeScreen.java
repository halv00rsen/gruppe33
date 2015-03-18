package windows;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import classes.Event;
import classes.Person;
import classes.PersonCalendar;
import components.*;
import gui.*;
import gui.Main.AddGroupListener;
import gui.Main.AddNewEvent;
import gui.Main.AddPersonListener;
import gui.Main.ChangeTab;
public class HomeScreen extends Window{

	private final ChangeTab tab;
    
    public SchedulingGUI schedulingGUI;
    private Person p;
    private PersonCalendar cal;

	private AddNewEvent eventAdder;
	private AddGroupListener addPerson;
    
	public HomeScreen(ChangeTab tab, Person person, AddNewEvent addNewEvent, AddGroupListener addGroup) {
		this.eventAdder = addNewEvent;
		this.p = person;
		this.tab = tab;
		this.addPerson = addGroup;
		cal = new PersonCalendar(p);
//		this.setStyle("-fx-color-background: #ff0044");
		init();
		
	}

	@Override
	public void init() {
		schedulingGUI = new SchedulingGUI(this, tab, addPerson, cal);
		schedulingGUI.changePerson(p);
		schedulingGUI.setEventAdder(eventAdder);
		borderPane.setCenter(schedulingGUI);
	}

	public void highlightEvent(Event event) {
		schedulingGUI.highlightEvent(event);
		
	}


}
