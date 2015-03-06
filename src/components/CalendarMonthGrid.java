package components;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;

import classes.Event;

public class CalendarMonthGrid extends CalendarGrid{
	HashMap<LocalDate, CalendarMonthDay> datesHash = new HashMap<LocalDate, CalendarMonthDay>();
	LocalDate date;
	Month month; 
	int daysInMonth;
	GridPane grid; 
	String brand = "Kalender";
	Label l;
	VBox box;
	Pane dayBox;
	CalendarMonthGUI gui;
	public int calHeight = CalendarMonthGUI.defaultCalHeight;
	public int calWidth = CalendarMonthGUI.defaultCalWidth;
	ArrayList<Event> events;
	public CalendarMonthGrid(CalendarMonthGUI gui, LocalDate date,ArrayList<Event> events) {
		date = date.minusDays(0);
		this.events = events;
		this.date = date;
		this.month = date.getMonth();
		this.gui = gui;
		if( Year.isLeap(date.getYear())){
			daysInMonth = month.maxLength();
		}else{
			daysInMonth = month.minLength();
		}
		
		
		
		generateGrid(date);
		setSize(calHeight, calWidth);
		
		box = new VBox();
		box.getChildren().add(grid);
		this.getChildren().add(box);
		grid.setGridLinesVisible(true);

	}
	public LocalDate getLocalDate(){
		return date;
	}
	private void generateGrid(LocalDate date) {
		grid = new GridPane();
		LocalDate firstDayInMonth = date.minusDays(date.getDayOfMonth()-1);
		int firstDayInMonthWeekday = firstDayInMonth.getDayOfWeek().getValue();	
		if(firstDayInMonthWeekday == 1){
			firstDayInMonthWeekday = 8;
		}
		LocalDate firstDayInCalendar = firstDayInMonth.minusDays(firstDayInMonthWeekday-1);
		LocalDate dayLooper = firstDayInCalendar;
		int x = 0;
		
		int y = 0;
		for (int i = 0; i < firstDayInMonthWeekday-1; i++) {
			
			CalendarMonthDay thisBox = new CalendarMonthDay(gui,dayLooper,events,false,true);
			thisBox.setIsLowerDisabled(true);
			grid.add(thisBox,x,y);
			datesHash.put(dayLooper, thisBox);
			x += 1;
			if(x % 7 == 0){
				y += 1;
				x = 0;
			}
			dayLooper = dayLooper.plusDays(1);
		}
		
		for (int i = 0; i < daysInMonth; i++) {
			CalendarMonthDay thisBox = new CalendarMonthDay(gui,dayLooper,events,false,false);
			grid.add(thisBox,x,y);

			datesHash.put(dayLooper, thisBox);
			dayLooper = dayLooper.plusDays(1);

			x += 1;
			if(x % 7 == 0){
				y += 1;
				x = 0;
			}
		}
		for (int i = firstDayInMonthWeekday+daysInMonth-1; i < 7*6; i++) {
			CalendarMonthDay thisBox = new CalendarMonthDay(gui,dayLooper,events,true,false);
			thisBox.setIsUpperDisabled(true);
			grid.add(thisBox,x,y);

			datesHash.put(dayLooper, thisBox);
			dayLooper = dayLooper.plusDays(1);
			x += 1;
			if(x % 7 == 0){
				y += 1;
				x = 0;
			}
		}
	}
	public void setSize(int calHeight, int calWidth){
		this.calHeight = calHeight;
		this.calWidth = calWidth;
		for (int i = 0; i < grid.getChildren().size(); i++) {
			((CalendarMonthDay)grid.getChildren().get(i)).setSize(calHeight / 6, calWidth / 7);
		}
	}

	public CalendarMonthDay getDateBox(LocalDate date){
		return datesHash.get(date);
	};
	public String toString(){
		return this.date.getMonth().toString();
	}
}
