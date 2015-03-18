package windows;
import classes.Event;
import components.*;
import gui.*;
import gui.Main.AddGroupListener;
import gui.Main.AddNewEvent;
import gui.Main.AddPersonListener;
import gui.Main.ChangeTab;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableNumberValue;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import gui.Window;
public class EventScreen extends Window{

	private final AddNewEvent event;
	private final ChangeTab tab;
	
	private AddPersonListener l;
	private AddGroupListener gl;
	
	private EventGUI eg;
	
	public EventScreen(AddNewEvent event, ChangeTab tab, AddPersonListener l, AddGroupListener gl) {
		this.l = l;
		this.gl = gl;
		this.event = event;
		this.tab = tab;
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public void init() {
		eg = new EventGUI(this, event, tab, l, gl);
		
		borderPane.setCenter(eg);
		
	}
	
	public void showEvent(Event event){
		if (eg != null)
			eg.showEvent(event);
	}

}
