package windows;
import classes.Event;
import components.*;
import gui.*;
import gui.Main.AddNewEvent;
import gui.Main.ChangeTab;
import javafx.scene.layout.Pane;
import gui.Window;
public class EventScreen extends Window{

	private final AddNewEvent event;
	private final ChangeTab tab;
	
	private EventGUI eg;
	
	public EventScreen(AddNewEvent event, ChangeTab tab) {
		this.event = event;
		this.tab = tab;
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public void init() {
		eg = new EventGUI(this, event, tab);
		getChildren().add(eg);
	}
	
	public void showEvent(Event event){
		if (eg != null)
			eg.showEvent(event);
	}

}
