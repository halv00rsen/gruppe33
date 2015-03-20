package components;

import classes.MailInfo;
import gui.Main;
import gui.Main.GoToEvent;
import gui.Main.MessageDeleter;
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
	public MessageDeleter mesDel;
	private Inbox inbox;
	public MailBox thisMailBox;
	public MailBox(MailInfo message, GoToEvent goToEvent,MessageDeleter mesDel,Inbox inbox){
		this.inbox = inbox;
		thisMailBox = this;
		this.eventId = message.eventId;
		System.out.println("EVENTID in mailbox" + eventId);
		VBox box = new VBox(5);
		Text header = new Text(message.header);
		this.mesDel = mesDel;
		Text from = new Text("Fra: " + message.from);
		Text date = new Text("Dato: " + message.date);
		Text mail = new Text(message.status);
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
				System.out.println(message.from +" " +  message.to + " " +message.status);
				mesDel.deleteMessage(message.from, message.to, message.status);
				goToEvent.goToEvent(eventId);
				inbox.removeMail(thisMailBox);
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
