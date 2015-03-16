package components;
import gui.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import classes.Event;
import classes.Priority;
import classes.TimeMethods;

public abstract class EventBox extends Pane{
	

	Priority priority = Priority.NOT_IMPORTANT;
	Event event;
	String highLightStyle = "-fx-background-color: #8888FF";
	String backupStyle;
	String defaultStyle;
	CalendarDay calday;
	LocalDateTime startTime;
	LocalDateTime endTime;
	Label clock;
	boolean isHighLighted = false;
	protected CalendarGUI calGui;
	public EventBox(Event event,CalendarGUI calGui,CalendarDay calday){
		this.calday = calday;
		this.calGui = calGui;
		if(event != null){
			this.event = event;
			this.priority = event.getPriority();
			startTime = event.getStartTime();
			endTime = event.getEndTime();
			this.event = event;
		}
		
		
		setNormalStyle("-fx-background-color:"+ Main.colorToHex(priority.getColor()));
		
		this.setOnMouseEntered(e -> hoverOn(e));
		this.setOnMouseExited(e -> hoverOff(e));
		this.setOnMouseClicked(e -> calday.onEventAction(e));
		
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
	
	public void setNormalStyle(String string){
		this.setStyle(string);			
		defaultStyle = string;
	}
	
	public void setEvent(Event event){
		this.event = event;
	}
	public void setClockText(String string){
		this.clock.setText(string);
	}
	protected void hoverOn(MouseEvent e) {
		backupStyle = this.getStyle();
		int[] a = {0,0,0};
		Main.applyContrast(this,0.95,a);
		setCursor(Cursor.HAND);
	}
	protected void hoverOff(MouseEvent e) {
		this.setStyle(backupStyle);
	}
}
