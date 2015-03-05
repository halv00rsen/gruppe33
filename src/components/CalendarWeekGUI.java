package components;

import gui.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;

import classes.Event;

public class CalendarWeekGUI extends Component{
//	focusProperty = getFocs
	LocalDate date;
	static int defaultCalHeight = 600;
	static int defaultCalWidth = 700;
	CalendarWeekBase mainCalendar;
	CalendarWeekBase nextCalendar;
	CalendarWeekBase lastCalendar;
	Month month; 
	int daysInMonth;
	GridPane grid; 
	String brand = "Kalender";
	Label l;
	VBox box;
	HBox hbox;
	VBox innerBox;
	VBox header;
	HBox dayTitles;

	StackPane calCnt;
	Pane dayBox;
	Slider sliderLeft;
	Slider sliderRight;
	ArrayList<Event> events;
	public CalendarWeekGUI(Pane parent,LocalDate date, ArrayList<Event> events, Main main) {
		super(parent, main);
		init(date,events);
		
	}
	private void init(LocalDate date,ArrayList<Event> events) {
		updateDate(date);
		this.requestFocus();
		this.setTranslateX(0);
		this.setPrefWidth(defaultCalWidth+Slider.width*2);
		this.setPrefHeight(defaultCalHeight);
		this.events = events;
		
		this.lastCalendar = new CalendarWeekBase(this,this.date.plusMonths(-1),events);
		this.mainCalendar = new CalendarWeekBase(this,date,events);
		this.nextCalendar = new CalendarWeekBase(this,this.date.plusMonths(1),events);
		
		
		lastCalendar.setVisible(false);
		nextCalendar.setVisible(false);
		innerBox = new VBox();
		header = new VBox();            
		l = new Label();
		l.setText(date.getMonth().toString());
		dayTitles = new HBox(0.595*CalendarWeekGUI.defaultCalWidth/7);
		Label mon = new Label();
		Label tus = new Label();
		Label wed = new Label();
		Label tur = new Label();
		Label fri = new Label();
		Label sat = new Label();
		Label sun = new Label();
		mon.setText("Mandag");
		tus.setText("Tirsdag");
		wed.setText("Onsdag");
		tur.setText("Torsdag");
		fri.setText("Fredag");
		sat.setText("Lørdag");
		sun.setText("Søndag");
		sun.setTextFill(Color.RED);
		dayTitles.getChildren().addAll(mon,tus,wed,tur,fri,sat,sun);
		header.getChildren().add(l);
		header.getChildren().add(dayTitles);
		innerBox.getChildren().add(header);
		//animeBox.getChildren().add(lastCalendar);
		calCnt = new StackPane();
		calCnt.getChildren().add(lastCalendar);
		calCnt.getChildren().add(mainCalendar);
		calCnt.getChildren().add(nextCalendar);
		//animeBox.getChildren().add(nextCalendar);
		
		innerBox.getChildren().add(calCnt);
//		clip.getChildren().add(clippingShape);
//		clip.getChildren().get(0).setClip(clippingShape);
		
		sliderLeft = new Slider(true);
		sliderRight = new Slider(false);
		hbox = new HBox();
		hbox.getChildren().add(sliderLeft);
		hbox.getChildren().add(innerBox);
		hbox.getChildren().add(sliderRight);
		this.getChildren().add(hbox);
		sliderRight.setOnMouseClicked(e -> nextSlide(e));
		sliderLeft.setOnMouseClicked(e -> nextSlide(e));
		this.requestFocus();
		this.setOnKeyPressed(e -> handleOnKeyPressed(e));
		
		
	}
	private void handleOnKeyPressed(KeyEvent e){
		System.out.println("HEIHEIHEIHIE");
		if(e.getCode().equals(KeyCode.RIGHT)){
			System.out.println("HØYRE");
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
			
			Timeline timeline = slideRightAnimation();
			timeline.setOnFinished( event -> shiftRight(event));
			timeline.play();
			
		}else if (e.getSource().equals(sliderLeft)){
			
			
			Timeline timeline = slideLeftAnimation();
			timeline.setOnFinished( event -> shiftLeft(event));
			timeline.play();
			
		}
		

	}
	public Timeline slideLeftAnimation() {
		Timeline timeline = new Timeline();
		int animationTime = 500;
		lastCalendar.setVisible(true);
		
		
		Rectangle clippingShape1 = new Rectangle();
		clippingShape1.setHeight(CalendarWeekGUI.defaultCalHeight);
		clippingShape1.setWidth(CalendarWeekGUI.defaultCalWidth);
		mainCalendar.setClip(clippingShape1);
		mainCalendar.translateXProperty().set( 0 );
		
		KeyValue kvPr1 = new KeyValue(clippingShape1.widthProperty(),  0 );
		KeyValue kvPr3 = new KeyValue(mainCalendar.translateXProperty(), CalendarWeekGUI.defaultCalWidth);
		
		Rectangle clippingShape2 = new Rectangle();
		clippingShape2 = new Rectangle();
		clippingShape2.setHeight(CalendarWeekGUI.defaultCalHeight);
		clippingShape2.setWidth(0);
		clippingShape2.translateXProperty().set(CalendarWeekGUI.defaultCalWidth);
		lastCalendar.setClip(clippingShape2);
		lastCalendar.translateXProperty().set(- CalendarWeekGUI.defaultCalWidth);
		
		KeyValue kvNxt1 = new KeyValue(clippingShape2.widthProperty(), CalendarWeekGUI.defaultCalWidth);
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
		clippingShape1.setHeight(CalendarWeekGUI.defaultCalHeight);
		clippingShape1.setWidth(CalendarWeekGUI.defaultCalWidth);

		clippingShape1.translateXProperty().set(0);
		mainCalendar.setClip(clippingShape1);
		mainCalendar.translateXProperty().set( 0 );
		KeyValue kvPr1 = new KeyValue(clippingShape1.widthProperty(),  0 );
		KeyValue kvPr2 = new KeyValue(clippingShape1.translateXProperty(), CalendarWeekGUI.defaultCalWidth);
		KeyValue kvPr3 = new KeyValue(mainCalendar.translateXProperty(), -CalendarWeekGUI.defaultCalWidth);
		
		Rectangle clippingShape2 = new Rectangle();
		clippingShape2 = new Rectangle();
		clippingShape2.setHeight(CalendarWeekGUI.defaultCalHeight);
		clippingShape2.setWidth(CalendarWeekGUI.defaultCalWidth);
		clippingShape2.translateXProperty().set( - CalendarWeekGUI.defaultCalWidth);
		nextCalendar.setClip(clippingShape2);
		nextCalendar.translateXProperty().set( CalendarWeekGUI.defaultCalWidth);
		KeyValue kvNxt2 = new KeyValue(clippingShape2.translateXProperty(), 0);
		KeyValue kvNxt3 = new KeyValue(nextCalendar.translateXProperty(), 0);
		
		KeyFrame kfDwn = new KeyFrame(Duration.millis(animationTime), kvPr1,kvPr2,kvPr3 ,kvNxt2,kvNxt3);
		timeline.getKeyFrames().add(kfDwn);
		return timeline;
	}
	private void shiftRight(ActionEvent e) {
		lastCalendar = mainCalendar;
		mainCalendar = nextCalendar;
		updateDate(mainCalendar.getLocalDate());
		nextCalendar = new CalendarWeekBase(this, this.date.plusMonths(1),events);
		redrawCalendar();
	}
	private void shiftLeft(ActionEvent e) {
		nextCalendar = mainCalendar;
		mainCalendar = lastCalendar;
		updateDate(mainCalendar.getLocalDate());
		lastCalendar = new CalendarWeekBase(this,this.date.plusMonths(-1),events);
		redrawCalendar();
	}
	private void redrawCalendar() {
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
		System.out.println(mainCalendar);
		
	}
	CalendarWeekDayBox boxHighlighted;
	public void highlight(LocalDate date){
		CalendarWeekDayBox a = mainCalendar.getDateBox(date);
		
		
		if(boxHighlighted != null){
			boxHighlighted.setHighlighted(false);
			if(date.isEqual(boxHighlighted.date)){
				this.requestFocus();
				boxHighlighted = null;
				return;
			}
		}
		if(a.isUpperDisabled() == true){
			Timeline timeline = slideRightAnimation();
			timeline.setOnFinished( event -> shiftRightAndHighlight(event,date));
			timeline.play();
		}else if(a.isLowerDisabled() == true){
			Timeline timeline = slideLeftAnimation();
			timeline.setOnFinished( event -> shiftLeftAndHighlight(event,date));
			timeline.play();
		}else{
			highlightContinue(date);
		}
		
		
	}
	private void shiftLeftAndHighlight(ActionEvent event,LocalDate date) {
		shiftLeft(event);
		highlightContinue(date);
	}
	private void shiftRightAndHighlight(ActionEvent event,LocalDate date) {
		shiftRight(event);
		highlightContinue(date);
	}
	private void highlightContinue(LocalDate date){
		System.out.println(mainCalendar);
		CalendarWeekDayBox a = mainCalendar.getDateBox(date);
		boxHighlighted = a;
		a.setHighlighted(true);
		a.requestFocus();
	}
	
}
