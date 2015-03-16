package components;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import classes.Event;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import gui.Component;
import gui.Main;

public abstract class CalendarDay extends Pane{

	int dayOfMonth;
	int[] def = {0,0,0};
	int month;
	LocalDate date;
	Label day;
	String backupStyle;
	String defaultStyle;
	
	String highLightStyle = "-fx-background-color: #DDDDFF";
	boolean isHighLighted = false;
	HashMap<Integer, EventBox> eventsHash;
	boolean isUpperDisabled = false;
	boolean isLowerDisabled = false;
	Pane base;
	EventBoxWeek box;
	VBox body;
	ArrayList<Event> events;
	int calHeight;
	public int calWidth = CalendarBase.defaultCalWidth/7;
	CalendarGUI calGui;
	public CalendarDay(CalendarGUI gui, LocalDate date,ArrayList<Event> events, boolean isUpperDisabled, boolean isLowerDisabled){
		setCalHeight();
		
		
		this.calGui = gui;
		this.events = events;
		this.date = date;
		this.isLowerDisabled = isLowerDisabled;
		this.isUpperDisabled = isUpperDisabled;
		this.dayOfMonth = date.getDayOfMonth();
		this.month = date.getMonthValue();
//		this.dayOfMonth = 31;
		this.setPrefWidth(calWidth);
		this.setPrefHeight(calWidth);
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
				double h = calHeight*LocalDateTime.now().getHour()/24;
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
		
		base = new Pane();
			base.setPadding(new Insets(5));
				day = new Label();
					base.getChildren().add(day);
					day.setText("" + dayOfMonth);
				
					if(!(isLowerDisabled || isUpperDisabled)){

						addEvents();
					}
		this.getChildren().add(base);
		this.setOnMouseClicked(e -> onAction(e));
		this.setOnMouseEntered(e -> hoverOn(e));
		this.setOnMouseExited(e -> hoverOff(e));
		this.setMouseTransparent(false);
	}

	abstract void setCalHeight();
	abstract void addEvents();
	public void setIsUpperDisabled(boolean t){
		this.isUpperDisabled = t;
	}
	public void setIsLowerDisabled(boolean t){
		this.isLowerDisabled = t;
	}
	private void hoverOff(MouseEvent e) {
		if(box!= null){
			box.setVisible(false);
		}
		this.setStyle(backupStyle);
	}

	private void hoverOn(MouseEvent e) {
		
		backupStyle = this.getStyle();
		int[] a = {0,0,0};
		Main.applyContrast(this,0.95,a);
		if(isHighLighted){
			setCursor(Cursor.NONE);
		}else{
			setCursor(Cursor.HAND);
		}
		

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

	
	public LocalDate getDate(){
		return this.date;
	}
	public Object onEventAction(MouseEvent e) {
		// TODO Auto-generated method stub
		return null;
	}
	boolean evenBoxWasJustCalled = false;
	public LocalDate onAction(MouseEvent e) {
		
		
		if(e.getSource() instanceof EventBox){
			Timeline timeline = new Timeline();
			KeyFrame h = new KeyFrame(Duration.millis(3));
			timeline.getKeyFrames().add(h);
			evenBoxWasJustCalled = true;
			timeline.setOnFinished( new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					evenBoxWasJustCalled = false;
					
				}
			});
			timeline.play();
			if(e.getClickCount() == 2){

				calGui.highlightEvent(((EventBox)e.getSource()).event);
			}else{
				calGui.highlightEvent(((EventBox)e.getSource()).event);
			}
		}else if(evenBoxWasJustCalled == false){
			if(e.getButton() == MouseButton.SECONDARY){
				if(calGui.currentCalendarBase instanceof CalendarMonthBase){
					calGui.changeToWeek(date);
				}else if(isHighLighted){
						
					double startTime = 0.5*Math.round(e.getY()/(calHeight/48));
					int hour = (int)(Math.floor(startTime));
					int minute = (int)((startTime-hour)*60);
					Event event = new Event();
					LocalDateTime dt = LocalDateTime.of(date, LocalTime.of(hour, minute));
					event.setEventName("Hendelse");						
					event.setStartTime(dt);
					event.setEndTime(dt.plusHours(1));
					calGui.addEventFromCalendar(event);
				}
			}else if(e.getClickCount() == 1){
				calGui.highlightDate(date);	
			}
			
				
		}
		
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
	public ArrayList<Event> getDayEvents(){
		return events;
	}
	EventBox highlightedEvent;
	public void highlightEvent(Event event) {

		EventBox requestedEvent = eventsHash.get(event.getID());
		if(highlightedEvent != null){
			highlightedEvent.setHighlighted(false);
		}
		highlightedEvent = requestedEvent;
		highlightedEvent.setHighlighted(true);
	}
	public void setHighlighted(boolean isHighLighted) {
		this.isHighLighted = isHighLighted;
		if(isHighLighted){
			
			backupStyle = highLightStyle;
			this.setStyle(highLightStyle);
		}else{
			if(box != null){
				box.setVisible(false);
			}
			backupStyle = defaultStyle;
			this.setStyle(defaultStyle);
		}
		
	}

	public void removeHighlightEvent() {
		highlightedEvent.setHighlighted(false);
		highlightedEvent = null;
		
	}

	
}
