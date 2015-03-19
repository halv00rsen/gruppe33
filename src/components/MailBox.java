package components;

import classes.MailInfo;
import gui.Main;
import gui.Main.GoToEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MailBox extends Pane{
	
	private final int eventId;

	public MailBox(MailInfo info, GoToEvent goToEvent){
		this.eventId = info.eventId;
		VBox box = new VBox(5);
		Text header = new Text(info.header);
		Text from = new Text("Fra: " + info.from);
		Text date = new Text("Dato: " + info.date);
		Text mail = new Text(info.status);
		setPrefWidth(Main.getWidth() / 2);
		box.getChildren().addAll(header, from, date, mail);
		getChildren().add(box);
		setStyle("-fx-background-color: #FFCC66; -fx-border:3");
		setOnMouseEntered(new EventHandler<Event>(){

			@Override
			public void handle(Event event) {
				setStyle("-fx-background-color: #FFE0A3");
				setCursor(Cursor.HAND);
			}
			
		});
		setOnMouseClicked(new EventHandler<Event>(){
			
			@Override
			public void handle(Event event){
				goToEvent.goToEvent(eventId);
			}
		});
		setOnMouseExited(new EventHandler<Event>(){
			
			@Override
			public void handle(Event event){
				setStyle("-fx-background-color: #FFCC66");
				setCursor(Cursor.DEFAULT);
			}
		});
	}
}
