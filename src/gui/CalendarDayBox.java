package gui;

import java.util.ArrayList;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import classes.Event;

public class CalendarDayBox extends Pane{
	int dayOfMonth;
	int[] def = {0,0,0};
	int month;
	LocalDate date;
	Label day;
	String backupStyle;
	boolean disabled;
	public int calHeight = 100;
	public int calWidth = 100;
	CalendarMonthGUI gui;
	public CalendarDayBox(CalendarMonthGUI gui, LocalDate date,ArrayList<Event> events, boolean isDisabled){
//		this.gui = gui;
		this.disabled = false;
		this.date = date;
		this.disabled = isDisabled;
		this.dayOfMonth = date.getDayOfMonth();
		this.month = date.getMonthValue();
//		this.dayOfMonth = 31;
		this.setPrefWidth(calWidth);
		this.setPrefHeight(calHeight);
		//Add red color if sunday
		if(disabled){
			this.setStyle("-fx-background-color: #D0D0D0");
			
		}else if(date.getDayOfWeek() == DayOfWeek.SUNDAY){
			
			this.setStyle("-fx-background-color: #FFAAAA");
			
		}else{
			this.setStyle("-fx-background-color: #FFFFFF");
		}
		
		
		if(date.equals(LocalDate.now()) && disabled){
			this.setStyle("-fx-background-color: #E0E0D0");
			
		}else if(date.equals(LocalDate.now())){
			
			this.setStyle("-fx-background-color: #FFFFDD");
		
		}
		boolean isInNextMonth = date.plusDays(9).getMonthValue()>LocalDate.now().getMonthValue();
		boolean isNotLongAway = LocalDate.now().minusDays(29).isBefore(date);
		if( isNotLongAway && isInNextMonth && date.isBefore(LocalDate.now()) && disabled){
			
			
		}else if ( date.isBefore(LocalDate.now()) ){
			Main.applyContrast(this,0.7,def);
		}
		day = new Label();
		day.setText("" + dayOfMonth);
		this.getChildren().add(day);
		this.setOnMouseClicked(e -> onAction(e));
		this.setOnMouseEntered(e -> hoverOn(e));
		this.setOnMouseExited(e -> hoverOff(e));
	}


	private void hoverOff(MouseEvent e) {
		this.setStyle(backupStyle);
	}


	private void hoverOn(MouseEvent e) {
		backupStyle = this.getStyle();
		System.out.println(backupStyle);
		int[] a = {0,0,0};
		Main.applyContrast(this, 0.95,a);
	}


	
	

	private LocalDate onAction(MouseEvent e) {
		this.setStyle("-fx-background-color: #00E0D0");
		gui.highlight(this.date);
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
