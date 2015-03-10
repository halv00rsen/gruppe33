package classes;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public enum Priority{
	NOT_IMPORTANT(Color.BEIGE), IMPORTANT(Color.ORANGE), VERY_IMPORTANT(Color.RED);
	private Pane pane = new Pane();
	private Circle circle = new Circle();
	private Color c;
	private boolean active;
	
	Priority(Color color){
		this.c = color;
		circle.setRadius(20.0f);
		circle.setFill(color);
		circle.setOnMouseEntered(e -> hoverOn(e));
		circle.setOnMouseExited(e -> hoverOff(e));
		this.pane.getChildren().add(circle);
		circle.setStroke(color);
		active = false;
	}
	public Pane getVisualization(){
		return pane;
	}
	
	public boolean isActive(){
		return active;
	}
	
	public void turnOff(){
		active = false;
		circle.setStroke(c);
		hoverOff(null);
	}
	
	public void turnOn(){
		active = true;
		circle.setStroke(Color.BLACK);
	}
	
	private void hoverOff(MouseEvent e) {
		circle.setFill(c);
	}
	private void hoverOn(MouseEvent e) {
		circle.setFill(Color.valueOf("#FFFFAA"));
	}
	public Color getColor() {
		return c;
	}

}