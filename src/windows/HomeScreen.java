package windows;
import java.time.LocalDate;
import java.util.ArrayList;

import classes.Event;
import components.*;
import gui.*;
import gui.Window;
public class HomeScreen extends Window{

	public HomeScreen(Main main) {
		super(main);
		this.setStyle("-fx-color-background: #ff0044");
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		CalendarMonthGUI gui = new CalendarMonthGUI(this, LocalDate.now(), new ArrayList<Event>(), main);
		this.getChildren().add(gui);
	}

}
