package components;
import gui.Main;

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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import classes.Event;

public class CalendarWeekDay extends CalendarDay{
	HashMap<Integer, EventBox> eventsHash;
	StackPane innerBody;
	public CalendarWeekDay(CalendarBase gui, LocalDate date,ArrayList<Event> events, boolean isUpperDisabled,boolean isLowerDisabled) {
		super(gui, date, events, isUpperDisabled, isLowerDisabled);
		this.setPrefHeight(calHeight);
	}
	
	@Override
	void addEvents(){
		eventsHash = new HashMap<Integer, EventBox>();
		int mente = 0;
		innerBody = new StackPane();
		
		for (int i = 0; i < events.size()-1; i++) {
			Event thisEvent = events.get(i);
			EventBox eventBox = new EventBox(thisEvent, calGui);
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
			
			EventBox eventBox = new EventBox(thisEvent, calGui);

			eventsHash.put(thisEvent.getID(), eventBox);
			if(mente > 0){

				eventBox.addToOverlapp(mente);
				eventBox.overlapCount += 1;
				mente = 0;
			}
			innerBody.getChildren().add(eventBox);	
		}
				
			
			
		
		
		body.getChildren().add(innerBody);	
	}

	@Override
	void setCalHeight() {
		calHeight = CalendarBase.defaultCalHeight;
		
	}

	@Override
	void continueHighlightEvent(Event event) {
		if (event == null)
			return;
		EventBox box = eventsHash.get(event.getID());
		if (box != null)
			box.setHighlighted(true);

	}
}
