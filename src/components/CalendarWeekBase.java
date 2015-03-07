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

import classes.Event;

public class CalendarWeekBase extends CalendarBase{

	public CalendarWeekBase(Pane parent,LocalDate date, ArrayList<Event> events, Main main) {
		super(date,events);
		
	}
	
	
	
	@Override
	void generateNeighbors() {
		this.lastCalendar = new CalendarWeekGrid(this,this.date.plusMonths(-1),events);
		this.mainCalendar = new CalendarWeekGrid(this,date,events);
		this.nextCalendar = new CalendarWeekGrid(this,this.date.plusMonths(1),events);
		
		
	}
	@Override
	void updateGridLast() {
		lastCalendar = new CalendarWeekGrid(this, this.date.plusMonths(-1),events);
		
	}
	@Override
	void updateGridNext() {
		nextCalendar = new CalendarWeekGrid(this, this.date.plusMonths(1),events);
	}
}
