package windows;
import classes.Event;
import components.*;
import gui.*;
import gui.Main.AddNewEvent;
import gui.Main.AddPersonListener;
import gui.Main.ChangeTab;
import javafx.scene.layout.Pane;
import gui.Window;
public class EventScreen extends Window{

	private final AddNewEvent event;
	private final ChangeTab tab;
	
	private AddPersonListener l;
	
	private EventGUI eg;
	
	public EventScreen(AddNewEvent event, ChangeTab tab, AddPersonListener l) {
		this.l = l;
		this.event = event;
		this.tab = tab;
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public void init() {
		eg = new EventGUI(this, event, tab, l);
		getChildren().add(eg);
	}
	
	public void showEvent(Event event){
		if (eg != null)
			eg.showEvent(event);
	}

}
