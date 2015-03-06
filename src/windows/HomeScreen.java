package windows;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import classes.Event;
import components.*;
import gui.*;
public class HomeScreen extends Window{
	
	private Button logout, settings;

	public HomeScreen(Main main) {
		super(main);
//		this.setStyle("-fx-color-background: #ff0044");
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		HBox b1 = new HBox(0);
		
		logout = new Button("Logg ut");
		logout.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				main.requestLogout();
			}
			
		});
		settings = new Button("Brukerinnstillinger");
		settings.setOnAction(e -> main.requestSettingsWindow());
		
		CalendarWeekGUI gui = new CalendarWeekGUI(this, LocalDate.now(), new ArrayList<Event>(), main);
		SideMenu menu = new SideMenu(this, LocalDate.now(), DebugMain.getEvents(), main);
		b1.getChildren().addAll(gui,menu, logout);
		this.getChildren().add(b1);
//		this.getChildren().add(gui);
//		gui.setTranslateX(300);
	}

}
