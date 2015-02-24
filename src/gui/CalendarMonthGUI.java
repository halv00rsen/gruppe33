package gui;


import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.time.Month;
import java.time.Year;
import java.time.ZonedDateTime;

public class CalendarMonthGUI extends Component{
	ZonedDateTime date;
	Month month; 
	int daysInMonth;
	GridPane grid; 
	String brand = "Kalender";
	Label l;
	VBox box;
	Pane dayBox;
	public CalendarMonthGUI(Pane parent, ZonedDateTime date) {
		super(parent);
		this.date = date;
		this.month = date.getMonth();
		
		if( Year.isLeap(date.getYear())){
			daysInMonth = month.maxLength();
		}else{
			daysInMonth = month.minLength();
		}
		this.setPrefWidth(500);
		this.setPrefHeight(500);
		grid = new GridPane();
		
		BorderStroke gridBorderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT);
		Border gridBorder = new Border(gridBorderStroke);
		grid.setBorder(gridBorder);
		int x = 0;
		int y = 0;
		for (int i = 0; i < daysInMonth; i++) {
			CalendarDayBox thisDay = new CalendarDayBox();
			y += 1;
			if(i % 7 == 0){
				x += 1;
				y = 0;
			}
			grid.add(thisDay,y,x);
		}
		
		l = new Label();
		l.setText(brand);
		box = new VBox();
		this.getChildren().add(box);
		box.getChildren().add(l);
		box.getChildren().add(grid);
		grid.setGridLinesVisible(true);
		
	}
		
	
	
}
