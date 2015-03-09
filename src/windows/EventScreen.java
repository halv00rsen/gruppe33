package windows;
import components.*;
import gui.*;
import javafx.scene.layout.Pane;
import gui.Window;
public class EventScreen extends Window{

	public EventScreen() {
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public void init() {
		EventGUI eg = new EventGUI(this);
		getChildren().add(eg);
	}

}
