package windows;
import components.*;
import gui.*;
import gui.Main.AddNewEvent;
import javafx.scene.layout.Pane;
import gui.Window;
public class EventScreen extends Window{

	private final AddNewEvent event;
	
	public EventScreen(AddNewEvent event) {
		this.event = event;
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public void init() {
		EventGUI eg = new EventGUI(this, event);
		getChildren().add(eg);
	}

}
