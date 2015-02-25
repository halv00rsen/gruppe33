package gui;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Slider extends StackPane{
	public Slider(boolean left){
		
		this.getChildren();
		this.setPrefHeight(CalendarMonthGUI.defaulCalHeight);
		this.setPrefWidth(40);
		this.setStyle("-fx-background-color: #EEE");
		Polygon arrow = new Polygon();
		arrow.getPoints().add(0.0);
		arrow.getPoints().add(40.0);
		arrow.getPoints().add(40.0);
		arrow.getPoints().add(0.0);
		arrow.getPoints().add(0.0);
		arrow.getPoints().add(-40.0);
		arrow.setFill(Color.LIGHTGREY);
		this.getChildren().add(arrow);
		StackPane.setAlignment(this,Pos.CENTER_LEFT);
		if(left){
			arrow.setRotate(180);
		}
	}
}
