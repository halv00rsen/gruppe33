package components;
import gui.Main;

import java.util.ArrayList;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import classes.Event;

public class CalendarWeekDay extends CalendarDay{

	public CalendarWeekDay(CalendarBase gui, LocalDate date,ArrayList<Event> events, boolean isUpperDisabled,boolean isLowerDisabled) {
		super(gui, date, events, isUpperDisabled, isLowerDisabled);
		this.setPrefHeight(calHeight);
	}
	
	@Override
	void addEvents(){
		for (int i = 0; i < events.size(); i++) {
			Event thisEvent = events.get(i);
			
			Pane eventBox = new EventBox(thisEvent);
			
			body.getChildren().add(eventBox);		
			
			
		}
	}

	@Override
	void setCalHeight() {
		calHeight = CalendarBase.defaultCalHeight;
		
	}
}