package components;

import javafx.scene.control.Label;
import classes.Event;

public class EventBoxMonth extends EventBox{
	public EventBoxMonth(Event event,CalendarGUI calGui,CalendarDay calday){
		super(event,calGui,calday);
		Label eventLabel = new Label();
		eventLabel.setText(event.getEventName());
		this.getChildren().add(eventLabel);
	}
	
	

}
