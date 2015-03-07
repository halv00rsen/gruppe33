package windows;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import classes.Event;
import components.*;
import gui.*;
public class HomeScreen extends Window{
	
    
   
    
    CalendarGUI calendargui;
    
    VBox calendarAndInfo;
    HBox mainBox;
    
	private Button settings;
	public HomeScreen(Main main) {
		super(main);
//		this.setStyle("-fx-color-background: #ff0044");
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
		
		
		mainBox = new HBox(0);
		calendarAndInfo = new VBox(10);
		
		settings = new Button("Brukerinnstillinger");
		settings.setOnAction(e -> main.requestSettingsWindow());
		
		calendargui = new CalendarGUI(this, main, LocalDate.now(), new ArrayList<Event>());
		SideMenu menu = new SideMenu(this, LocalDate.now(), DebugMain.getEvents(), main);


		calendarAndInfo.getChildren().addAll(calendargui);

		
		
		mainBox.getChildren().addAll(calendarAndInfo,menu);
		this.getChildren().add(mainBox);

//		this.getChildren().add(gui);
//		gui.setTranslateX(300);

	}
	
	
	

}
