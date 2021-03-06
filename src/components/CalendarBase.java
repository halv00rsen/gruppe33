package components;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;

import classes.Calendar;
import classes.Event;
import gui.Component;
import gui.Main;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public abstract class CalendarBase extends Pane{
	LocalDate date;
	CalendarGrid mainCalendar;
	CalendarGrid nextCalendar;
	CalendarGrid lastCalendar;
	Month month; 
	int daysInMonth;
	GridPane grid; 
	String brand = "Kalender";
	Label l;
	VBox box;
	HBox hbox;
	StackPane innerBox;
	
	HBox dayTitles;
	CalendarDay dayHighlighted;
	StackPane calCnt;
	Pane dayBox;
	ArrayList<Event> events;
	static int defaultCalHeight = 440;
	static int defaultCalWidth = 500;
	VBox header;
	static int headerHeight = 30;
	Slider sliderLeft;
	Slider sliderRight;
	CalendarGUI gui;
	Calendar[] calendars;
	private BorderPane borderpane;
	private EventBox eventHighlighted;

	public CalendarBase(LocalDate date,Calendar[] args,CalendarGUI gui) {
		this.calendars = args;
		this.gui = gui;
//		this.events = args;
		updateDate(date);
		this.setPrefWidth(defaultCalWidth+Slider.width*2);
		this.setPrefHeight(defaultCalHeight);
		generateCalendars();
		lastCalendar.setVisible(false);
		nextCalendar.setVisible(false);
		borderpane = new BorderPane();
			this.getChildren().add(borderpane);
			header = new VBox();  
				borderpane.setCenter(header);
				borderpane.setAlignment(header, Pos.BASELINE_RIGHT);
				header.setPrefHeight(headerHeight);
				l = new Label();
					header.getChildren().add(l);
					header.setAlignment(Pos.TOP_CENTER);
					l.setText(date.getMonth().toString());
					
			hbox = new HBox();
				borderpane.setBottom(hbox);
				sliderLeft = new Slider(true);
					hbox.getChildren().add(sliderLeft);
					sliderLeft.setOnMouseClicked(e -> nextSlide(e));
				innerBox = new StackPane();
					hbox.getChildren().add(innerBox);
					calCnt = new StackPane();
						innerBox.getChildren().add(calCnt);
						calCnt.getChildren().add(lastCalendar);
						calCnt.getChildren().add(mainCalendar);
						calCnt.getChildren().add(nextCalendar);
				sliderRight = new Slider(false);
					hbox.getChildren().add(sliderRight);
					sliderRight.setOnMouseClicked(e -> nextSlide(e));
		this.requestFocus();
		this.setOnKeyPressed(e -> handleOnKeyPressed(e));
	}
	
	private void handleOnKeyPressed(KeyEvent e){
		if(e.getCode().equals(KeyCode.RIGHT)){
			Timeline timeline = slideRightAnimation();
			timeline.setOnFinished( event -> shiftRight(event));
			timeline.play();
			
		}else if (e.getCode() == KeyCode.LEFT){
			
			
			Timeline timeline = slideLeftAnimation();
			timeline.setOnFinished( event -> shiftLeft(event));
			timeline.play();
			
		}
		
	}
	private void updateDate(LocalDate d) {
		this.date = d;
		this.month = d.getMonth();
		
		if( Year.isLeap(d.getYear())){
			daysInMonth = month.maxLength();
		}else{
			daysInMonth = month.minLength();
		}
	}
	private void nextSlide(MouseEvent e) {

		if(e.getSource().equals(sliderRight)){
			
			slideRight();
			
		}else if (e.getSource().equals(sliderLeft)){
			
			slideLeft();
			
			
		}
		

	}
	public Timeline slideLeftAnimation() {
		Timeline timeline = new Timeline();
		int animationTime = 500;
		lastCalendar.setVisible(true);
		
		
		Rectangle clippingShape1 = new Rectangle();
		clippingShape1.setHeight(CalendarBase.defaultCalHeight+20);
		clippingShape1.translateYProperty().set( -20 );
		clippingShape1.setWidth(CalendarBase.defaultCalWidth);
		mainCalendar.setClip(clippingShape1);
		mainCalendar.translateXProperty().set( 0 );
		
		KeyValue kvPr1 = new KeyValue(clippingShape1.widthProperty(),  0 );
		KeyValue kvPr3 = new KeyValue(mainCalendar.translateXProperty(), CalendarBase.defaultCalWidth);
		
		Rectangle clippingShape2 = new Rectangle();
		clippingShape2 = new Rectangle();
		clippingShape2.setHeight(CalendarBase.defaultCalHeight+20);
		clippingShape2.setWidth(0);
		clippingShape2.translateXProperty().set(CalendarBase.defaultCalWidth);
		clippingShape2.translateYProperty().set(-20);
		lastCalendar.setClip(clippingShape2);
		lastCalendar.translateXProperty().set(- CalendarBase.defaultCalWidth);
		
		KeyValue kvNxt1 = new KeyValue(clippingShape2.widthProperty(), CalendarBase.defaultCalWidth);
		KeyValue kvNxt2 = new KeyValue(clippingShape2.translateXProperty(), 0);
		KeyValue kvNxt3 = new KeyValue(lastCalendar.translateXProperty(), 0);
		
		
		
		
		KeyFrame kfDwn = new KeyFrame(Duration.millis(animationTime), kvPr1,kvPr3 , kvNxt1,kvNxt2,kvNxt3);
		timeline.getKeyFrames().add(kfDwn);

		return timeline;
	}
	public Timeline slideRightAnimation() {
		Timeline timeline = new Timeline();
		int animationTime = 500;
		nextCalendar.setVisible(true);
		
		Rectangle clippingShape1 = new Rectangle();
		clippingShape1.setHeight(CalendarBase.defaultCalHeight+20);
		clippingShape1.setWidth(CalendarBase.defaultCalWidth);
		clippingShape1.translateYProperty().set( - 20);
		clippingShape1.translateXProperty().set(0);
		mainCalendar.setClip(clippingShape1);
		mainCalendar.translateXProperty().set( 0 );
		KeyValue kvPr1 = new KeyValue(clippingShape1.widthProperty(),  0 );
		KeyValue kvPr2 = new KeyValue(clippingShape1.translateXProperty(), CalendarBase.defaultCalWidth);
		KeyValue kvPr3 = new KeyValue(mainCalendar.translateXProperty(), -CalendarBase.defaultCalWidth);
		
		Rectangle clippingShape2 = new Rectangle();
		clippingShape2 = new Rectangle();
		clippingShape2.setHeight(CalendarBase.defaultCalHeight+20);
		clippingShape2.translateYProperty().set( - 20);
		clippingShape2.translateXProperty().set( - CalendarBase.defaultCalWidth);
		clippingShape2.setWidth(CalendarBase.defaultCalWidth);
		clippingShape2.translateXProperty().set( - CalendarBase.defaultCalWidth);
		nextCalendar.setClip(clippingShape2);
		nextCalendar.translateXProperty().set( CalendarBase.defaultCalWidth);
		KeyValue kvNxt2 = new KeyValue(clippingShape2.translateXProperty(), 0);
		KeyValue kvNxt3 = new KeyValue(nextCalendar.translateXProperty(), 0);
		
		KeyFrame kfDwn = new KeyFrame(Duration.millis(animationTime), kvPr1,kvPr2,kvPr3 ,kvNxt2,kvNxt3);
		timeline.getKeyFrames().add(kfDwn);
		return timeline;
	}
	public LocalDate getDate(){
		return this.date;
	}
	public void shiftRight(ActionEvent e) {
		lastCalendar = mainCalendar;
		mainCalendar = nextCalendar;
		updateDate(mainCalendar.getLocalDate());
		updateGridNext();
		
		redrawCalendar();
	}
	public void shiftLeft(ActionEvent e) {
		//System.out.println("ShiftLeft");
		nextCalendar = mainCalendar;
		mainCalendar = lastCalendar;
		updateDate(mainCalendar.getLocalDate());
		updateGridLast();
		redrawCalendar();
	}
	public void generateCalendars(){
		
		updateGridLast();
		updateGridThis();
		updateGridNext();
		
	}
	public void setNewDate(LocalDate date){
		updateDate(date);
		generateCalendars();
		lastCalendar.setVisible(false);
		nextCalendar.setVisible(false);
		redrawCalendar();
	}
	abstract void updateGridLast();
	public void setNewCalendarList(Calendar... calendars){
		this.calendars = calendars;
	}
	abstract void updateGridThis();
	abstract void updateGridNext();
	public void redrawCalendar() {
		mainCalendar.setVisible(false);
		lastCalendar.setVisible(false);
		nextCalendar.setVisible(false);
		calCnt.getChildren().clear();
		mainCalendar.setVisible(true);
		if(calCnt.getChildren().size() < 3){
			calCnt.getChildren().add(lastCalendar);
			calCnt.getChildren().add(mainCalendar);
			calCnt.getChildren().add(nextCalendar);
		}else{
			calCnt.getChildren().set(0,lastCalendar);
			calCnt.getChildren().set(1,mainCalendar);
			calCnt.getChildren().set(2,nextCalendar);
		}
		l.setText(date.getMonth().toString());
		
	}
	public void slideRight(){
		Timeline timeline = slideRightAnimation();
		timeline.setOnFinished( event -> shiftRight(event));
		timeline.play();
	}
	public void slideLeft(){
		Timeline timeline = slideLeftAnimation();
		timeline.setOnFinished( e -> shiftLeft(e));
		timeline.play();
	}
	abstract void handleShifting(Object obj);
	
	public void continueHighlightDate(LocalDate date){
		mainCalendar.highlightDate(date);
	}
	public void continueHighlightEvent(Event event){
		
		mainCalendar.highlightEvent(event);
	}
	public void highlightEvent(Event event){
		
		handleShifting(event);
		
	}
	public void highlightDate(LocalDate date){
		
		handleShifting(date);
		
		
	}

	public void removeHighlightDate() {
		mainCalendar.removeHighlightDate();
		lastCalendar.removeHighlightDate();
		nextCalendar.removeHighlightDate();
		
		
	}

	public void removeHighlightEvent() {
		
		mainCalendar.removeHighlightEvent();
		lastCalendar.removeHighlightEvent();
		nextCalendar.removeHighlightEvent();
		
	}

	





}
