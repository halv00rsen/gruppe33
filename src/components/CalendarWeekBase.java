package components;

import gui.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
		lastCalendar = new CalendarWeekGrid(gui, this.date.plusWeeks(-1),calendars);
		
	}
	@Override
	void updateGridNext() {
		nextCalendar = new CalendarWeekGrid(gui, this.date.plusWeeks(1),calendars);
	}

	@Override
	void updateGridThis() {
		this.mainCalendar = new CalendarWeekGrid(gui,date,calendars);
		
	}





	@Override
	void handleShifting(Object obj) {
		LocalDate date;
		Event event;
		if(obj instanceof LocalDate){
			event = null;
			date = (LocalDate) obj;
		}else if(obj instanceof Event){
			event = (Event) obj;
			date = event.getStartDate();
		}else{
			date = null;
			event = null;
		}
		LocalDate startDayOfThisWeek = this.date.minusDays(this.date.getDayOfWeek().getValue()-1);
		LocalDate endDayOfThisWeek = this.date.plusDays(7-this.date.getDayOfWeek().getValue());
		LocalDate endDayOfNextWeek = endDayOfThisWeek.plusWeeks(1);
		LocalDate startDayOfLastWeek = startDayOfThisWeek.minusWeeks(1);
		//System.out.println("startDayOfThisWeek: "+startDayOfThisWeek+"  endDayOfThisWeek: "+endDayOfThisWeek+" endDayOfNextWeek: "+endDayOfNextWeek+" startDayOfLastWeek:" + startDayOfLastWeek);
		if(date.isAfter(endDayOfThisWeek)){
		//er til venstre
			if(date.isBefore(endDayOfNextWeek)){
			//er i next
				
				Timeline timeline = slideRightAnimation();
				timeline.setOnFinished( new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {
						shiftRight(arg0);
						if(obj instanceof LocalDate){
							continueHighlightDate(date);
						}else{
							continueHighlightEvent(event);
						}
						
						
					}
					
				});
				timeline.play();
			}else{
			//er langt unna

				setNewDate(date);
				if(obj instanceof LocalDate){
					continueHighlightDate(date);
				}else{
					continueHighlightEvent(event);
				}

			}
		}else if(date.isBefore(startDayOfThisWeek)){
		//er til høyre
			if(date.isAfter(startDayOfLastWeek)){
			//er i last

				Timeline timeline = slideLeftAnimation();
				
				timeline.setOnFinished( new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {
						shiftLeft(arg0);
						if(obj instanceof LocalDate){
							continueHighlightDate(date);
						}else{
							continueHighlightEvent(event);
						}

						
					}
					
				});
//				timeline.setOnFinished();
				timeline.play();
			}else{
			//er langt unna

				setNewDate(date);
				if(obj instanceof LocalDate){
					continueHighlightDate(date);
				}else{
					continueHighlightEvent(event);
				}

			}
		}else{

//			setNewDate(date);
			if(obj instanceof LocalDate){
				continueHighlightDate(date);
			}else{
				continueHighlightEvent(event);
			}

		}
		
	}
}
