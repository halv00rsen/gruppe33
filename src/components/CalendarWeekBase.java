package components;

import gui.*;
import windows.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.TriangleMesh;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import classes.Event;

public class CalendarWeekBase extends Pane{
	HashMap<LocalDate, CalendarWeekDayBox> datesHash = new HashMap<LocalDate, CalendarWeekDayBox>();
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
	public CalendarWeekBase(CalendarWeekGUI gui, LocalDate date,ArrayList<Event> events) {
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
			CalendarWeekDayBox thisBox = new CalendarWeekDayBox(gui,dayLooper,events,false,false);
			grid.add(thisBox,x,y);

			datesHash.put(dayLooper, thisBox);
			dayLooper = dayLooper.plusDays(1);

			
		}

	}
	public void setSize(int calHeight, int calWidth){
		this.calHeight = calHeight;
		this.calWidth = calWidth;
		for (int i = 0; i < grid.getChildren().size(); i++) {
			((CalendarWeekDayBox)grid.getChildren().get(i)).setSize(calHeight, calWidth / 7);
		}
	}

	public CalendarWeekDayBox getDateBox(LocalDate date){
		return datesHash.get(date);
	};
	public String toString(){
		return this.date.getMonth().toString();
	}
}
