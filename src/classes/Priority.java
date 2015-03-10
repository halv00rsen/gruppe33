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
	
	Priority(Color color){
		this.c = color;
		circle.setRadius(20.0f);
		circle.setFill(color);
		circle.setOnMouseEntered(e -> hoverOn(e));
		circle.setOnMouseExited(e -> hoverOff(e));
		this.pane.getChildren().add(circle);
	}
	public Pane getVisualization(){
		return pane;
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