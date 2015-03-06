package components;
import gui.Main;
import java.util.ArrayList;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import classes.Event;

public class CalendarWeekDay extends Pane{
	int dayOfMonth;
	int[] def = {0,0,0};
	int month;
	LocalDate date;
	Label day;
	String backupStyle;
	String defaultStyle;
	
	String highLightStyle = "-fx-background-color: #00FF88";
	boolean isHighLighted = false;

	boolean isUpperDisabled = false;
	boolean isLowerDisabled = false;
	BorderPane base;
	VBox body;
	ArrayList<Event> events;
	public int calHeight = 600;
	public int calWidth = 100;
	CalendarWeekGUI calGui;
	public CalendarWeekDay(CalendarWeekGUI gui, LocalDate date,ArrayList<Event> events, boolean isUpperDisabled, boolean isLowerDisabled){
		this.calGui = gui;
		this.events = events;
		this.date = date;
		this.isLowerDisabled = isLowerDisabled;
		this.isUpperDisabled = isUpperDisabled;
		this.dayOfMonth = date.getDayOfMonth();
		this.month = date.getMonthValue();
//		this.dayOfMonth = 31;
		this.setPrefWidth(calWidth);
		this.setPrefHeight(calHeight);
		
		if(isUpperDisabled || isLowerDisabled){
			this.defaultStyle = "-fx-background-color: #D0D0D0";
			
		}else if(date.getDayOfWeek() == DayOfWeek.SUNDAY){
			
			this.defaultStyle = "-fx-background-color: #FFAAAA";
			
		}else{
			this.defaultStyle = "-fx-background-color: #FFFFFF";
		}
		
		
		this.setStyle(defaultStyle);
		if ( date.isBefore(LocalDate.now() )){
			Main.applyContrast(this,0.7,def);
		}else if(date.equals(LocalDate.now())){
			
			Pane splitDay = new Pane();
				this.getChildren().add(splitDay);
				splitDay.toBack();
				this.toBack();
				splitDay.setPrefWidth(calWidth);
				double hourHeight = calHeight/24;
				double h = hourHeight*(LocalDateTime.now().getHour()-1) + (LocalDateTime.now().getMinute()/60)*hourHeight;
				splitDay.setPrefHeight(h);
				splitDay.setStyle(this.getStyle());
				Main.applyContrast(splitDay,0.7,def);
				Line l = new Line();
				l.setStroke(Color.RED);
				l.setStrokeWidth(2);
				l.setStartX(0);
				l.setStartY(h+1);
				l.setEndX(calWidth);
				l.setEndY(h+1);
				this.getChildren().add(l);
		}
		this.setStyle(this.getStyle());
		this.defaultStyle = this.getStyle();
		
		base = new BorderPane();
			base.setPadding(new Insets(5));
				day = new Label();
					base.setLeft(day);
					day.setText("" + dayOfMonth);
				body = new VBox(1);
					base.setBottom(body);
					if(!(isLowerDisabled || isUpperDisabled)){

						addEvents();
					}
		this.getChildren().add(base);
		this.setOnMouseClicked(e -> onAction(e));
		this.setOnMouseEntered(e -> hoverOn(e));
		this.setOnMouseExited(e -> hoverOff(e));
	}

	private void addEvents() {
		for (int i = 0; i < events.size(); i++) {
			Event thisEvent = events.get(i);
			
			Pane eventBox = new EventBox(thisEvent);
			
			body.getChildren().add(eventBox);		
			
			
		}
		
	}

	public void setIsUpperDisabled(boolean t){
		this.isUpperDisabled = t;
	}
	public void setIsLowerDisabled(boolean t){
		this.isLowerDisabled = t;
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

	public boolean isUpperDisabled() {
		return isUpperDisabled;
	}

	public boolean isLowerDisabled() {
		return isLowerDisabled;
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
		calGui.requestFocus();
		calGui.highlight(date);
		
			
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
