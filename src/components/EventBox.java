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
	

	Priority priority;
	Event event;
	String highLightStyle = "-fx-background-color: #8888FF";
	String backupStyle;
	String defaultStyle;
	CalendarDay calday;
	boolean isHighLighted = false;
	protected CalendarGUI calGui;
	public EventBox(Event event,CalendarGUI calGui,CalendarDay calday){
		this.calday = calday;
		this.event = event;
		this.calGui = calGui;
		this.priority = event.getPriority();
		this.setStyle("-fx-background-color:"+ Main.colorToHex(priority.getColor()));			
		defaultStyle = this.getStyle();
		this.setOnMouseEntered(e -> hoverOn(e));
		this.setOnMouseExited(e -> hoverOff(e));
		this.setOnMouseClicked(e -> calday.onAction(e));
		
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
