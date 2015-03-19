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
import gui.Main.SchedulingGuiMethods;
public class HomeScreen extends Window{

    
    public SchedulingGUI schedulingGUI;
    private PersonCalendar cal;


	private SchedulingGuiMethods mainMethods;
    //ChangeTab tab, Person person, AddNewEvent addNewEvent, AddGroupListener addGroup
	public HomeScreen(SchedulingGuiMethods mainMethods) {
		cal = new PersonCalendar(mainMethods.getCurrentUser());
		this.mainMethods = mainMethods;
		init();
		
	}

	@Override
	public void init() {
		schedulingGUI = new SchedulingGUI(this, mainMethods, cal);
		
		borderPane.setCenter(schedulingGUI);
	}

	public void highlightEvent(Event event) {
		schedulingGUI.highlightEvent(event);
		
	}



}
