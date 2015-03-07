package components;

import java.time.LocalDate;
import java.util.ArrayList;

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
	CalendarBase month;
	CalendarBase week;
	ToggleGroup group;
	CalendarBase currentCalendarBase;
	VBox components;
	HBox buttons;
	public CalendarGUI(Pane parent, Main main, LocalDate date, ArrayList<Event> events) {
		super(parent, main);
		
		month = new CalendarMonthBase(parent, date, events, main);
		week = new CalendarWeekBase(parent, date, events, main);
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
		components = new VBox();
		components.getChildren().add(buttons);
		components.getChildren().add(currentCalendarBase);
		buttons.setAlignment(Pos.BOTTOM_RIGHT);
		this.getChildren().add(components);
	}
	
	



	private void weekButtonMethod(ActionEvent e) {
//		components.getChildren().clear();
//		components.getChildren().add(buttons);
		currentCalendarBase = week;
		components.getChildren().set(1,currentCalendarBase);
	}
	private void monthButtonMethod(ActionEvent e) {
//		components.getChildren().clear();
//		components.getChildren().add(buttons);
		currentCalendarBase =month;
		components.getChildren().set(1,currentCalendarBase);
	}


	
}
