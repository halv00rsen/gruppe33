package gui;

import java.util.ArrayList;
import java.util.Date;
import java.time.ZonedDateTime;

import javafx.scene.layout.Pane;
import classes.Event;

public class CalendarDayBox extends Pane{
	int dayOfMonth;
	int month;
	public CalendarDayBox(ZonedDateTime date,ArrayList<Event> events){
//		this.dayOfMonth = date.getDayOfMonth();
//		this.month = date.getMonthValue();
		this.dayOfMonth = 31;
		this.month = 12;
		this.setWidth(60);
		this.setHeight(60);
		
		
	}
}
