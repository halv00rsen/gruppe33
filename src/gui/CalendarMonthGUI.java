package gui;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.TriangleMesh;

import java.time.Month;
import java.time.Year;
import java.time.ZonedDateTime;

public class CalendarMonthGUI extends Component{
	ZonedDateTime date;
	static int defaulCalHeight = 300;
	static int defaulCalWidth = 300;
	CalendarMonthBase mainCalendar;
	CalendarMonthBase nextCalendar;
	CalendarMonthBase lastCalendar;
	Month month; 
	int daysInMonth;
	GridPane grid; 
	String brand = "Kalender";
	Label l;
	VBox box;
	HBox hbox;
	HBox animeBox;
	StackPane clip;
	Pane dayBox;
	Slider sliderLeft;
	Slider sliderRight;
	public CalendarMonthGUI(Pane parent) {
		super(parent);
		updateDate(ZonedDateTime.now());
		
		
		this.setPrefWidth(100);
		this.setPrefHeight(500);

		this.lastCalendar = new CalendarMonthBase(this.date.plusMonths(-1));
		this.mainCalendar = new CalendarMonthBase(date);
		this.nextCalendar = new CalendarMonthBase(this.date.plusMonths(1));
		
		
		
		animeBox = new HBox();
		animeBox.setAlignment(Pos.BASELINE_CENTER);
		//animeBox.getChildren().add(lastCalendar);
		animeBox.getChildren().add(mainCalendar);
		//animeBox.getChildren().add(nextCalendar);

//		clip.getChildren().add(clippingShape);
//		clip.getChildren().get(0).setClip(clippingShape);
		
		sliderLeft = new Slider(true);
		sliderRight = new Slider(false);
		hbox = new HBox();
		hbox.getChildren().add(sliderLeft);
		hbox.getChildren().add(clip);
		hbox.getChildren().add(sliderRight);
		this.getChildren().add(hbox);
		sliderRight.setOnMouseClicked(e -> nextSlide(e));
		sliderLeft.setOnMouseClicked(e -> nextSlide(e));
		
	}
	private void updateDate(ZonedDateTime d) {
		this.date = d;
		this.month = d.getMonth();
		
		if( Year.isLeap(d.getYear())){
			daysInMonth = month.maxLength();
		}else{
			daysInMonth = month.minLength();
		}
	}
	private void nextSlide(MouseEvent e) {
		int offset = 0;
		clip.getChildren().add(animeBox);
		Rectangle clippingShape = new Rectangle();
		clip = new StackPane();
		if(e.getSource().equals(sliderRight)){
			offset = 1;
			mainCalendar.setVisible(false);
			mainCalendar = nextCalendar;
			animeBox.getChildren().set(0,mainCalendar);
			mainCalendar.setVisible(true);

			updateDate( mainCalendar.getZonedDateTime());
		}else if (e.getSource().equals(sliderLeft)){
			offset = -1;
			mainCalendar.setVisible(false);
			mainCalendar = lastCalendar;
			animeBox.getChildren().set(0,mainCalendar);

			mainCalendar.setVisible(true);
			updateDate(mainCalendar.getZonedDateTime());
		}
		this.lastCalendar = new CalendarMonthBase(this.date.plusMonths(-1));
		this.mainCalendar = new CalendarMonthBase(date);
		this.nextCalendar = new CalendarMonthBase(this.date.plusMonths(1));
		

	}
	

	
}
