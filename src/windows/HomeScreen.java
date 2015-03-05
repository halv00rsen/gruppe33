package windows;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import classes.Event;
import components.*;
import gui.*;
public class HomeScreen extends Window{

	public HomeScreen(Main main) {
		super(main);
		this.setStyle("-fx-color-background: #ff0044");
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		GridPane b1 = new GridPane();
		CalendarMonthGUI gui = new CalendarMonthGUI(this, LocalDate.now(), new ArrayList<Event>(), main);
		SideMenu menu = new SideMenu(this, LocalDate.now(), DebugMain.getEvents(), main);
		b1.add(gui, 0, 0);
		b1.add(menu, 1, 0);
		this.getChildren().add(b1);
	}

}
