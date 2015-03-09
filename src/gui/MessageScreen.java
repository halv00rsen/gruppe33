package gui;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MessageScreen extends Group{

	private final int height = 100, width = 200;
	private final Rectangle rec;
	
	public MessageScreen(){
		rec = new Rectangle(width, height);
		rec.setFill(Color.BEIGE);
		rec.setStrokeWidth(3);
		rec.setStroke(Color.BLACK);
//		rec.setStyle("-fx-border: 3; -fx-border-color: #FF9933; -fx-background-color: #FFB870");
		Text header = new Text("Melding!");
		header.setLayoutY(10);
		getChildren().addAll(rec, header);
		setLayoutX(150);
		setLayoutY(80);
	}
	
	public void show(String message){
		
	}
	
	public void hide(){
		
	}
}
