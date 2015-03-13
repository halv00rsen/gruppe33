package components;
import gui.*;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Slider extends StackPane{
	private String backupStyle;
	public static int width = 40;
	

	public Slider(boolean left){
		
		this.getChildren();
		this.setMaxHeight(CalendarBase.defaultCalHeight);
		this.setMaxWidth(width);
		this.setStyle("-fx-background-color: #E0E0E0");
		Polygon arrow = new Polygon();
		arrow.getPoints().add(0.0);
		arrow.getPoints().add(width + 0.00);
		arrow.getPoints().add(width + 0.00);
		arrow.getPoints().add(0.0);
		arrow.getPoints().add(0.0);
		arrow.getPoints().add(-40.0);
		arrow.setFill(Color.LIGHTGREY);
		this.getChildren().add(arrow);
		StackPane.setAlignment(this,Pos.CENTER_LEFT);
		if(left){
			arrow.setRotate(180);
		}
		this.setOnMouseEntered(e -> hoverOn(e));
		this.setOnMouseExited(e -> hoverOff(e));
	}


	private void hoverOff(MouseEvent e) {
		this.setStyle(backupStyle);
	}


	private void hoverOn(MouseEvent e) {
		backupStyle = this.getStyle();
		//System.out.println(backupStyle);
		int[] a = {0,0,0};
		Main.applyContrast(this, 0.9,a);
		setCursor(Cursor.HAND);
	}
	
}
