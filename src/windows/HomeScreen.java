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
		HBox b1 = new HBox(50);
		CalendarMonthGUI gui = new CalendarMonthGUI(this, LocalDate.now(), new ArrayList<Event>(), main);
		LoginScreen login = new LoginScreen(main);
		SideMenu menu = new SideMenu(this, LocalDate.now(), DebugMain.getEvents(), main);
		b1.getChildren().addAll(gui, login);
		
		this.getChildren().addAll(gui,menu);
	}

}
