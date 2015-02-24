package gui;

import com.sun.prism.paint.Color;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class CalendarMonthGUI extends Component{
	GridPane grid; 
	String brand = "Kalender";
	Label l;
	HBox box;
	Pane dayBox;
	public CalendarMonthGUI(Pane parent) {
		super(parent);
	}
	@Override
	public void init(Pane parent) {
		this.setPrefWidth(500);
		this.setPrefHeight(500);
		this.setStyle("-fx-background-color: #A5B");
		grid = new GridPane();
		
		BorderStroke gridBorder = new BorderStroke(Paint.valueOf(Color.BLACK), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT);
		
		l = new Label();
		l.setText(brand);
		box = ew HBox();
		this.getChildren().add(box);
		box.getChildren().add(l);
		box.getChildren().add(grid);
		grid.setGridLinesVisible(true);
		
	}

	
	
}
