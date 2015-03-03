package components;
import gui.*;
import windows.*;
import java.util.ArrayList;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import classes.Event;

public class EventBox extends Pane{
	int dayOfMonth;
	int[] def = {0,0,0};
	int month;
	LocalDate date;
	Label clock;
	String backupStyle;
	String defaultStyle;
	
	String highLightStyle = "-fx-background-color: #00FF88";
	boolean isHighLighted = false;

	boolean isUpperDisabled = false;
	boolean isLowerDisabled = false;
	BorderPane base;
	VBox body;
	Event event;
	public int calHeight = 600;
	public int calWidth = 100;
	LocalDateTime startTime;
	LocalDateTime endTime;
	
	CalendarWeekGUI calGui;
	private Label name;
	public EventBox(Event event){
		startTime = event.getStartTime();
		endTime = event.getEndTime();
		this.event = event;
		
		
		base = new BorderPane();
			base.setPadding(new Insets(5));
				clock = new Label();
					base.setRight(clock);
					clock.setText("23:15-23:20");
//					clock.setText(startTime.getHour() +":" + startTime.getMinute() + "-" + endTime.getHour() +":" + endTime.getMinute());
				name = new Label();
					base.setLeft(name);
					name.setText(event.getEventName());
				body = new VBox(1);
					base.setBottom(body);
					
		this.setStyle("-fx-background-color: #AAAAAA");			
		this.getChildren().add(base);
		this.setOnMouseClicked(e -> onAction(e));
		this.setOnMouseEntered(e -> hoverOn(e));
		this.setOnMouseExited(e -> hoverOff(e));
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
