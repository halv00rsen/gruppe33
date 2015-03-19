package components;

import gui.*;

import java.util.ArrayList;
import java.util.HashMap;
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

public class CalendarMonthDay extends CalendarDay{

	
	public CalendarMonthDay(CalendarGUI gui, LocalDate date,ArrayList<Event> events, boolean isUpperDisabled,boolean isLowerDisabled) {
		super(gui, date, events, isUpperDisabled, isLowerDisabled);
		this.setPrefHeight(calHeight);
	}
	@Override
	void addEvents() {
		eventsHash = new HashMap<Integer, EventBox>();
		for (int i = 0; i < events.size(); i++) {
			Event thisEvent = events.get(i);
//			if(! thisEvent.isHidden()){
				EventBox eventBox = new EventBoxMonth(thisEvent,calGui,this);
				
				
				this.getChildren().add(eventBox);		
				eventsHash.put(thisEvent.getID(), eventBox);
//			}
			
			
		}
		
	}
	@Override
	void setCalHeight() {
		calHeight = CalendarBase.defaultCalHeight/6;
		
	}
}
