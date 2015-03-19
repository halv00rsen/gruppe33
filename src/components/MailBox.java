package components;

import classes.MailInfo;
import gui.Main;
import gui.Main.GoToEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MailBox extends Pane{
	String backupStyle;
	private final int eventId;
	String defaultStyle = "-fx-background-color: #FFFF99; -fx-border:3";
	public MailBox(MailInfo info, GoToEvent goToEvent){
		this.eventId = info.eventId;
		System.out.println("EVENTID in mailbox" + eventId);
		VBox box = new VBox(5);
		Text header = new Text(info.header);
		Text from = new Text("Fra: " + info.from);
		Text date = new Text("Dato: " + info.date);
		Text mail = new Text(info.status);
		setPrefWidth(Main.getWidth() / 2);
		box.getChildren().addAll(header, from, date, mail);
		getChildren().add(box);
		setStyle(defaultStyle);
		this.setOnMouseEntered(e -> hoverOn(e));
		this.setOnMouseExited(e -> hoverOff(e));
		setOnMouseClicked(new EventHandler<Event>(){
			
			@Override
			public void handle(Event event){
				defaultStyle = "-fx-background-color: #DDDDDD; -fx-border:3";
				backupStyle = "-fx-background-color: #DDDDDD; -fx-border:3";
				setStyle("-fx-background-color: #DDDDDD; -fx-border:3");
				goToEvent.goToEvent(eventId);
			}
		});
		
	}
	protected void hoverOn(MouseEvent e) {
		backupStyle = this.getStyle();
		this.setStyle("-fx-background-color: #FFFF33; -fx-border:3");
		setCursor(Cursor.HAND);
	}
	protected void hoverOff(MouseEvent e) {
		this.setStyle(backupStyle);
	}
}
