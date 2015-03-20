package gui;


import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public class MessageScreen extends Group{

	private final int height = 100, width = 200;
	private final Rectangle rec;
	private final Text message;
	
	private boolean showing;
	private EventInfo info = EventInfo.None;
	
	public MessageScreen(){
		rec = new Rectangle(width, height);
		rec.setFill(Color.BEIGE);
		rec.setStrokeWidth(3);
		rec.setStroke(Color.BLACK);
		message = new Text("heisanndu");
		TextFlow textFlow = new TextFlow(message);
        textFlow.setMaxWidth(200);
		message.setX(20);
		message.setY(40);
//		rec.setStyle("-fx-border: 3; -fx-border-color: #FF9933; -fx-background-color: #FFB870");
		Text header = new Text("Melding!");
		header.setX(20);
		header.setY(20);
		getChildren().addAll(rec,textFlow);
		setLayoutX(280);
		setLayoutY(- 1 * height - 5);
		showing = false;
	}
	
	public EventInfo getEventInfo(){
		return info;
	}
	
	public void show(String message, EventInfo info){
		if (showing)
			return;
		this.info = info;
		TranslateTransition tt = new TranslateTransition(Duration.seconds(1), this);
		this.message.setText(message);
		tt.setFromY(- 1 * height);
		tt.setToY(40 + height);
	    tt.setCycleCount(1);
	    tt.play();
	    showing = true;
	}
	
	public void hide(){
		if (!showing)
			return;
		TranslateTransition tt = new TranslateTransition(Duration.seconds(1), this);
		tt.setFromY(40 + height);
		tt.setToY(- 1 * height);
		tt.setCycleCount(1);
		tt.play();
		showing = false;
	}
	
	enum EventInfo{
		FromInbox, None;
	}
}
