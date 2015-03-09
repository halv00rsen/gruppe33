package components;
import gui.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import classes.Event;
import classes.Priority;
import classes.TimeMethods;

public class EventBox extends Pane{
	int dayOfMonth;
	int[] def = {0,0,0};
	int month;
	LocalDate date;
	Label clock;
	String backupStyle;
	String defaultStyle;
	Priority priority;
	String highLightStyle = "-fx-background-color: #00FF88";
	boolean isHighLighted = false;
	LocalDateTime startTimeDay;
	boolean isUpperDisabled = false;
	boolean isLowerDisabled = false;
	BorderPane base;
	VBox body;
	Event event;
	public int calHeight = 600;
	public int calWidth = 100;
	LocalDateTime startTime;
	LocalDateTime endTime;
	int overlapCount = 1;
	int overlap = 1;
	CalendarWeekBase calGui;
	private Label name;
	public EventBox(Event event){
		this.priority = event.getPriority();
		startTime = event.getStartTime();
		endTime = event.getEndTime();
		this.event = event;

		this.startTimeDay = (startTime.minusHours(startTime.getHour())).minusMinutes(startTime.getMinute());
//		System.out.println(startTimeDay);
		base = new BorderPane();
			base.setPadding(new Insets(5));
				clock = new Label();
					base.setRight(clock);
//					clock.setText("23:15-23:20");
					clock.setText(startTime.getHour() +":" + startTime.getMinute() + "-" + endTime.getHour() +":" + endTime.getMinute());
				name = new Label();
					base.setLeft(name);
					name.setText(event.getEventName());
				body = new VBox(1);
					base.setBottom(body);
					
		this.setStyle("-fx-background-color:"+ Main.colorToHex(priority.getColor()));			
		this.getChildren().add(base);
		this.setOnMouseClicked(e -> onAction(e));
		this.setOnMouseEntered(e -> hoverOn(e));
		this.setOnMouseExited(e -> hoverOff(e));
		double heightUnit = ((double)CalendarBase.defaultCalHeight)/(24.0*60.0);
		long minuteDiff = ChronoUnit.MINUTES.between(startTime, endTime);
		double height = minuteDiff*heightUnit;
		System.out.println(height);
		long minuteFromStartOfDay = ChronoUnit.MINUTES.between(startTimeDay, startTime);
		int startPos = (int) (minuteFromStartOfDay*heightUnit);
		
		this.setPrefHeight(height);
		this.setMaxHeight(height);
		this.setMaxWidth(CalendarBase.defaultCalWidth/(7));
		this.setTranslateY(startPos);
	}

	public void addToOverlapp(int i) {
		overlap +=i;
		this.setMaxWidth(CalendarBase.defaultCalWidth/(7*overlap));
		this.setTranslateX((CalendarBase.defaultCalWidth/(7*overlap))*(overlapCount-1));
		System.out.println(overlap);
	}

	private void hoverOff(MouseEvent e) {
		this.setStyle(backupStyle);
	}
	
	
	private void hoverOn(MouseEvent e) {
		backupStyle = this.getStyle();
//		System.out.println(backupStyle);
		int[] a = {0,0,0};
		Main.applyContrast(this, 0.95,a);
	}


	
	public boolean isHighLighted() {
		return isHighLighted;
	}

	public void setHighlighted(boolean isHighLighted) {
		this.isHighLighted = isHighLighted;
		if(isHighLighted){
			backupStyle = highLightStyle;
			this.setStyle(highLightStyle);
		}else{
			backupStyle = defaultStyle;
			this.setStyle(defaultStyle);
		}
		
	}	

	private LocalDate onAction(MouseEvent e) {
//		calGui.requestFocus();
//		calGui.dayClicked(date);
		return null;
	}
	private LocalDate getLocalDate() {
		return this.date;
	}
	public void setSize(int calHeight, int calWidth){
		this.calHeight = calHeight;
		this.calWidth = calWidth;
		this.setPrefWidth(calWidth);
		this.setPrefHeight(calHeight);
	}
}
