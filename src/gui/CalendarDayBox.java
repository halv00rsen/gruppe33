package gui;

import java.util.ArrayList;
import java.util.Date;
import java.time.ZonedDateTime;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import classes.Event;

public class CalendarDayBox extends Pane{
	int dayOfMonth;
	int month;
	Label day;
	public CalendarDayBox(){//ZonedDateTime date,ArrayList<Event> events){
//		this.dayOfMonth = date.getDayOfMonth();
//		this.month = date.getMonthValue();
		this.dayOfMonth = 31;
		this.month = 12;
		this.setPrefWidth(80);
		this.setPrefHeight(80);
		this.setStyle("-fx-background-color: #AAAAAA");
		day = new Label();
		day.setText("" + dayOfMonth);
		this.getChildren().add(day);
		
		
	}
}
