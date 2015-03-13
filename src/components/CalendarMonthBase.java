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

public class CalendarMonthBase extends CalendarBase{
//	focusProperty = getFocs
	
	public CalendarMonthBase(Pane parent,LocalDate date, Calendar[] args, CalendarGUI gui) {
		super(date,args,gui);
		
	}

	
	@Override
	void updateGridLast() {
		lastCalendar = new CalendarMonthGrid(gui, this.date.plusMonths(-1),calendars);
		
	}
	@Override
	void updateGridNext() {
		nextCalendar = new CalendarMonthGrid(gui, this.date.plusMonths(1),calendars);
	}


	@Override
	void updateGridThis() {
		this.mainCalendar = new CalendarMonthGrid(gui,date,calendars);
		
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
		LocalDate startDayOfThisMonth = this.date.minusDays(this.date.getDayOfMonth()-1);
		LocalDate endDayOfThisMonth = this.date.plusDays(this.date.lengthOfMonth()-this.date.getDayOfMonth());
		LocalDate endDayOfNextMonth = endDayOfThisMonth.plusMonths(1);
		LocalDate startDayOfLastMonth = startDayOfThisMonth.minusMonths(1);
		
		if(date.isAfter(endDayOfThisMonth)){
		//er til venstre
			if(date.isBefore(endDayOfNextMonth)){
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
		}else if(date.isBefore(startDayOfThisMonth)){
		//er til høyre
			if(date.isAfter(startDayOfLastMonth)){
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
			//Er i denne
			if(obj instanceof LocalDate){
				continueHighlightDate(date);
			}else{
				continueHighlightEvent(event);
			}
//			setNewDate(date);
		}
		
	}
}
