package components;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import classes.Calendar;
import classes.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import gui.Component;
import gui.Main;

public abstract class CalendarGrid extends Pane{
	HashMap<LocalDate, CalendarDay> datesHash = new HashMap<LocalDate, CalendarDay>();
	LocalDate date;
	Month month; 
	int daysInMonth;
	GridPane grid;
	GridPane gridLabels; 
	String brand = "Kalender";
	Label l;
	VBox box;
	Pane dayBox;
	CalendarBase gui;
	Calendar[] calendars;
	public int calHeight = CalendarBase.defaultCalHeight;
	public int calWidth = CalendarBase.defaultCalWidth;

	public ArrayList<Label> dayTitles = new ArrayList<Label>();
	ArrayList<Event> events;
	public CalendarGrid(CalendarBase gui, LocalDate date,Calendar... calendars) {
		this.calendars = calendars;
		date = date.minusDays(0);
		this.events = events;
		this.date = date;
		this.month = date.getMonth();
		this.gui = gui;
		if( Year.isLeap(date.getYear())){
			daysInMonth = month.maxLength();
		}else{
			daysInMonth = month.minLength();
		}
		
		Label mon = new Label();
		mon.setText("Mandag");
		Label tus = new Label();
		tus.setText("Tirsdag");
		Label wed = new Label();
		wed.setText("Onsdag");
		Label tur = new Label();
		tur.setText("Torsdag");
		Label fri = new Label();
		fri.setText("Fredag");
		Label sat = new Label();
		sat.setText("Lørdag");
		Label sun = new Label();
		sun.setText("Søndag");
		sun.setTextFill(Color.RED);
		dayTitles.addAll(Arrays.asList(mon,tus,wed,tur,fri,sat,sun));
		
		addGrids(date);
//		setSize(calHeight, calWidth);
		
		box = new VBox();

		box.getChildren().add(gridLabels);
		box.getChildren().add(grid);
		this.getChildren().add(box);
		grid.setGridLinesVisible(true);

	}
	public LocalDate getLocalDate(){
		return date;
	}
	private void addGrids(LocalDate date){
		gridLabels = new GridPane();
		for (int i = 0; i < dayTitles.size(); i++) {
			Pane a = new Pane();
			dayTitles.get(i).setMaxHeight(0);

			dayTitles.get(i).setTranslateY(-20);
			a.getChildren().add(dayTitles.get(i));
			a.setPrefWidth(CalendarBase.defaultCalWidth/7);
			a.setMaxHeight(0);
			gridLabels.add(a, i, 0);

			
		}
		generateGrid(date);
	}
	abstract void generateGrid(LocalDate date);
	public void setSize(int calHeight, int calWidth){
		this.calHeight = calHeight;
		this.calWidth = calWidth;
		for (int i = 0; i < grid.getChildren().size(); i++) {
			((CalendarDay)grid.getChildren().get(i)).setSize(calHeight / 6, calWidth / 7);
		}
	}

	public CalendarDay getDateBox(LocalDate date){
		return datesHash.get(date);
	}
	public ArrayList<Event> getEventsByDay(LocalDate date){
		ArrayList<Event> events = new ArrayList<Event>();
		for (Calendar calendar : calendars) {
			events.addAll(calendar.getEventsByDay(date));
			
		}
		return events;
	}
	public void highlightedEvent(Event event) {
		datesHash.get(event.getStartDate()).highlightEvent(event);
		
	}

	
}
