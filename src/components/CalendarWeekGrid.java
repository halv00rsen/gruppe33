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

import classes.Calendar;
import classes.Event;

public class CalendarWeekGrid extends CalendarGrid{
	
	public CalendarWeekGrid(CalendarGUI gui, LocalDate date,
			Calendar... cal) {
		super(gui, date, cal);

		// TODO Auto-generated constructor stub
	}
	@Override
	void generateGrid(LocalDate date) {
		grid = new GridPane();
		LocalDate dayLooper = date.minusDays(date.getDayOfWeek().getValue()-1);
		//System.out.println(dayLooper);
		int y = 0;
		for (int x = 0; x < 7; x++) {
			CalendarWeekDay thisBox = new CalendarWeekDay(gui,dayLooper,gui.getEventsByDay(dayLooper),false,false);
			grid.add(thisBox,x,y);

			datesHash.put(dayLooper, thisBox);
			dayLooper = dayLooper.plusDays(1);

			
		}

	}
	
	public String toString(){
		return this.date.getMonth().toString();
	}
}
