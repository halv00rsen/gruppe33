package components;
import gui.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import components.CalendarGUI.ClockLines;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import classes.Event;

public class CalendarWeekDay extends CalendarDay{
	
	StackPane innerBody;
	public CalendarWeekDay(CalendarGUI gui, LocalDate date,ArrayList<Event> events, boolean isUpperDisabled,boolean isLowerDisabled) {
		super(gui, date, events, isUpperDisabled, isLowerDisabled);
		this.setPrefHeight(calHeight);
		box = new EventBoxWeek(null, calGui, null);
		box.strokething.setStyle("-fx-border-color: rgba(0, 0, 0, 0.2);");
		box.setNormalStyle("-fx-background-color: rgba(70, 100, 0, 0.1);");
		box.setVisible(false);
		box.setOnMouseEntered(null);
		box.setOnMouseExited(null);
		box.setDisable(true);
		this.getChildren().add(box);

		this.setOnMouseMoved(e -> draggingOn(e));
	}
	
	private Object draggingOn(MouseEvent e) {
		if(isHighLighted){
			box.setVisible(true);
			setCursor(Cursor.NONE);
			double h =  (Math.round(e.getY()/(calHeight/48)));
			h = h*calHeight/48;
			if(h >calHeight-calHeight/48){
				box.setVisible(false);
				return null;
			}else{
				box.setTranslateY(h);
			}
		}else{
			setCursor(Cursor.HAND);
			
		}
		
		
		return null;
	}
	
	
	
	@Override
	void addEvents(){
		eventsHash = new HashMap<Integer, EventBox>();
		int mente = 0;
		innerBody = new StackPane();
		
		for (int i = 0; i < events.size()-1; i++) {
			Event thisEvent = events.get(i);
			EventBoxWeek eventBox = new EventBoxWeek(thisEvent, calGui,this);
			eventsHash.put(thisEvent.getID(), eventBox);
			if(mente > 0){

				eventBox.addToOverlapp(mente);
				eventBox.overlapCount += 1;
				mente = 0;
			}
			if(events.get(i+1).getStartTime().isBefore(thisEvent.getEndTime())){
				eventBox.addToOverlapp(1);
				mente = 1;
			}
			innerBody.getChildren().add(eventBox);		
			
			
		}
		if(events.size()>0){
			

			Event thisEvent = events.get(events.size()-1);
			
			EventBoxWeek eventBox = new EventBoxWeek(thisEvent, calGui,this);

			eventsHash.put(thisEvent.getID(), eventBox);
			if(mente > 0){

				eventBox.addToOverlapp(mente);
				eventBox.overlapCount += 1;
				mente = 0;
			}
			innerBody.getChildren().add(eventBox);	
		}
				
			
			
		
		
		this.getChildren().add(innerBody);	
	}

	@Override
	void setCalHeight() {
		calHeight = CalendarBase.defaultCalHeight;
		
	}
}
