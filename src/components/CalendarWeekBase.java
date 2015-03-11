package components;

import gui.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;

import classes.Calendar;
import classes.Event;

public class CalendarWeekBase extends CalendarBase{

	public CalendarWeekBase(Pane parent,LocalDate date, Calendar[] args, CalendarGUI gui) {
		super(date,args,gui);
		
	}
	
	
	
	

	@Override
	void updateGridLast() {
		lastCalendar = new CalendarWeekGrid(this, this.date.plusWeeks(-1),calendars);
		
	}
	@Override
	void updateGridNext() {
		nextCalendar = new CalendarWeekGrid(this, this.date.plusWeeks(1),calendars);
	}

	@Override
	void updateGridThis() {
		this.mainCalendar = new CalendarWeekGrid(this,date,calendars);
		
	}
}
