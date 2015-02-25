package gui;


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

import java.time.Month;
import java.time.Year;
import java.time.ZonedDateTime;

public class CalendarMonthBase extends Pane{
	ZonedDateTime date;
	Month month; 
	int daysInMonth;
	GridPane grid; 
	String brand = "Kalender";
	Label l;
	VBox box;
	Pane dayBox;
	public int calHeight = CalendarMonthGUI.defaulCalHeight;
	public int calWidth = CalendarMonthGUI.defaulCalWidth;
	public CalendarMonthBase(ZonedDateTime date) {
		date = date.minusDays(0);
		this.date = date;
		this.month = date.getMonth();
		
		if( Year.isLeap(date.getYear())){
			daysInMonth = month.maxLength();
		}else{
			daysInMonth = month.minLength();
		}
		
		
		
		generateGrid(date);
		l = new Label();
		l.setText(date.getMonth().toString());
		box = new VBox();
		box.getChildren().add(l);
		box.getChildren().add(grid);
		this.getChildren().add(box);
		grid.setGridLinesVisible(true);

	}
	public ZonedDateTime getZonedDateTime(){
		return date;
	}
	private void generateGrid(ZonedDateTime date) {
		grid = new GridPane();
		ZonedDateTime firstDayInMonth = date.minusDays(date.getDayOfMonth()-1);
		int firstDayInMonthWeekday = firstDayInMonth.getDayOfWeek().getValue();	
		if(firstDayInMonthWeekday == 1){
			firstDayInMonthWeekday = 8;
		}
		ZonedDateTime firstDayInCalendar = firstDayInMonth.minusDays(firstDayInMonthWeekday-1);
		ZonedDateTime dayLooper = firstDayInCalendar;
		int x = 0;
		
		System.out.println(firstDayInMonthWeekday-1);
		int y = 0;
		for (int i = 0; i < firstDayInMonthWeekday-1; i++) {
			
			CalendarDayBox thisBox = new CalendarDayBox(dayLooper,null);
			thisBox.setStyle("-fx-background-color: #DDD");
			grid.add(thisBox,x,y);
			x += 1;
			if(x % 7 == 0){
				y += 1;
				x = 0;
			}
			dayLooper = dayLooper.plusDays(1);
		}
		
		for (int i = 0; i < daysInMonth; i++) {
			CalendarDayBox thisBox = new CalendarDayBox(dayLooper,null);
			grid.add(thisBox,x,y);
			dayLooper = dayLooper.plusDays(1);
			x += 1;
			if(x % 7 == 0){
				y += 1;
				x = 0;
			}
		}
		for (int i = firstDayInMonthWeekday+daysInMonth-1; i < 7*6; i++) {
			CalendarDayBox thisBox = new CalendarDayBox(dayLooper,null);
			grid.add(thisBox,x,y);
			thisBox.setStyle("-fx-background-color: #DDD");
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
			((CalendarDayBox)grid.getChildren().get(i)).setSize(calHeight / 5, calWidth / 7);
		}
	}
	
	
}
