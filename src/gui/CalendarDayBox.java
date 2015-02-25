package gui;

import java.util.ArrayList;
import java.util.Date;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import classes.Event;

public class CalendarDayBox extends Pane{
	int dayOfMonth;
	int month;
	Label day;
	public int calHeight = 100;
	public int calWidth = 100;
	public CalendarDayBox(ZonedDateTime date,ArrayList<Event> events){
		this.dayOfMonth = date.getDayOfMonth();
		this.month = date.getMonthValue();
//		this.dayOfMonth = 31;
		this.setPrefWidth(calWidth);
		this.setPrefHeight(calHeight);
		//Add red color if sunday
		if(date.getDayOfWeek() == DayOfWeek.SUNDAY){
			this.setStyle("-fx-background-color: #FAA");
		}else{
			this.setStyle("-fx-background-color: #FFF");
		}
		day = new Label();
		day.setText("" + dayOfMonth);
		this.getChildren().add(day);
		
		
	}

	public void setSize(int calHeight, int calWidth){
		this.calHeight = calHeight;
		this.calWidth = calWidth;
		this.setPrefWidth(calWidth);
		this.setPrefHeight(calHeight);
	}
}
