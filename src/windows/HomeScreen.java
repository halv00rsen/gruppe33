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
import gui.Main.ChangeTab;
public class HomeScreen extends Window{

	private final ChangeTab tab;
    
    public SchedulingGUI schedulingGUI;
    Person p;
    PersonCalendar cal;
    
	public HomeScreen(ChangeTab tab) {
		p = DebugMain.getPerson();
		this.tab = tab;
		cal = new PersonCalendar(p);
//		this.setStyle("-fx-color-background: #ff0044");
		init();
		
	}

	@Override
	public void init() {
		schedulingGUI = new SchedulingGUI(this,p, tab,cal);
		this.getChildren().add(schedulingGUI);
	}

	public void highlightEvent(Event event) {
		schedulingGUI.highlightEvent(event);
		
	}
	
	
	

}
