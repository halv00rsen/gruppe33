package classes;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public enum Priority{
	NOT_IMPORTANT(Color.LIGHTYELLOW), IMPORTANT(Color.ORANGE), VERY_IMPORTANT(Color.RED);
	private Pane pane = new Pane();
	private Circle circle = new Circle();
	private Color c;
	private boolean active;
	private int radius = 15;
	Priority(Color color){
		this.c = color;
		circle.setRadius(radius);
		circle.setFill(color);
		circle.setOnMouseEntered(e -> hoverOn(e));
		circle.setOnMouseExited(e -> hoverOff(e));
		circle.setTranslateX(radius);
		circle.setTranslateY(radius);
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
		if(c.equals(Color.RED)){
			circle.setFill(Color.valueOf("#CC0000"));
		}else if(c.equals(Color.ORANGE)){
			circle.setFill(Color.valueOf("#DD8800"));
		}else if (c.equals(Color.LIGHTYELLOW)){
			circle.setFill(Color.valueOf("#EEEE88"));
		}
		circle.setCursor(Cursor.HAND);
	}
	public Color getColor() {
		return c;
	}

}