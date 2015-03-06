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

public class CalendarWeekGrid extends CalendarGrid{
	HashMap<LocalDate, CalendarWeekDay> datesHash = new HashMap<LocalDate, CalendarWeekDay>();
	LocalDate date;
	Month month; 
	int daysInMonth;
	GridPane grid; 
	String brand = "Kalender";
	Label l;
	VBox box;
	Pane dayBox;
	CalendarWeekGUI gui;
	public int calHeight = CalendarWeekGUI.defaultCalHeight;
	public int calWidth = CalendarWeekGUI.defaultCalWidth;
	ArrayList<Event> events;
	public CalendarWeekGrid(CalendarWeekGUI gui, LocalDate date,ArrayList<Event> events) {
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
		
		LocalDate dayLooper = date.minusDays(date.getDayOfWeek().getValue()-1);
		System.out.println(dayLooper);
		int y = 0;
		for (int x = 0; x < 7; x++) {
			CalendarWeekDay thisBox = new CalendarWeekDay(gui,dayLooper,events,false,false);
			grid.add(thisBox,x,y);

			datesHash.put(dayLooper, thisBox);
			dayLooper = dayLooper.plusDays(1);

			
		}

	}
	public void setSize(int calHeight, int calWidth){
		this.calHeight = calHeight;
		this.calWidth = calWidth;
		for (int i = 0; i < grid.getChildren().size(); i++) {
			((CalendarWeekDay)grid.getChildren().get(i)).setSize(calHeight, calWidth / 7);
		}
	}

	public CalendarWeekDay getDateBox(LocalDate date){
		return datesHash.get(date);
	};
	public String toString(){
		return this.date.getMonth().toString();
	}
}
