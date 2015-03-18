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

public class EventBoxWeek extends EventBox{
	int dayOfMonth;
	int[] def = {0,0,0};
	int month;
	LocalDate date;
	Pane strokething;
	LocalDateTime startTimeDay;
	boolean isUpperDisabled = false;
	boolean isLowerDisabled = false;
	BorderPane base;
	VBox body;
	public int calHeight = 600;
	public int calWidth = 100;
	int overlapCount = 1;
	int overlap = 1;
	
	
	public EventBoxWeek(Event event,CalendarGUI calGui,CalendarDay calday){
		super(event,calGui,calday);
		strokething = new Pane();
		strokething.setStyle("-fx-background-color: #00FFF0");
			base = new BorderPane();
				base.setPadding(new Insets(5));
				strokething.getChildren().add(base);
				clock = new Label();
					base.setRight(clock);
				nameLabel = new Label();
					base.setLeft(nameLabel);
				body = new VBox(1);
					base.setBottom(body);
			if(event != null){
				this.startTimeDay = (startTime.minusHours(startTime.getHour())).minusMinutes(startTime.getMinute());
				double heightUnit = ((double)CalendarBase.defaultCalHeight)/(24.0*60.0);
				long minuteDiff = ChronoUnit.MINUTES.between(startTime, endTime);
				double height = minuteDiff*heightUnit;
				//System.out.println(height);
				long minuteFromStartOfDay = ChronoUnit.MINUTES.between(startTimeDay, startTime);
				int startPos = (int) (minuteFromStartOfDay*heightUnit);
				this.setPrefHeight(height);
				this.setMaxHeight(height);
				this.setMaxWidth(CalendarBase.defaultCalWidth/(7));
				this.setMinWidth(CalendarBase.defaultCalWidth/(7));
				this.setMinHeight(height);
				this.setTranslateY(startPos);
		}else{
			this.nameLabel.setText("(Høyreklikk)");
			this.setPrefHeight(calHeight/24);
			this.setPrefWidth(calWidth);
			this.setMinHeight(calHeight/24);
			this.setMinWidth(calWidth);
		}
		this.getChildren().add(strokething);
		this.toFront();
		strokething.toFront();
		this.setMouseTransparent(false);
		strokething.setPrefHeight(this.getHeight());
		strokething.setPrefWidth(this.getWidth());
		strokething.setMinHeight(this.getMinHeight());
		strokething.setMinWidth(this.getMinWidth());
		strokething.setStyle("-fx-border-color: black");
	}
	public void addToOverlapp(int i) {
		overlap +=i;
		this.setMaxWidth(CalendarBase.defaultCalWidth/(7*overlap));
		this.setTranslateX((CalendarBase.defaultCalWidth/(7*overlap))*(overlapCount-1));
		//System.out.println(overlap);
	}
	public boolean isHighLighted() {
		return isHighLighted;
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
