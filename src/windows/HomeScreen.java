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
	
    ToggleButton weekButton;
    ToggleButton monthButton;
    ToggleGroup group;
    
    CalendarWeekGUI weekGui;
    CalendarMonthGUI monthGui;
    
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
		
		weekButton = new ToggleButton("Uke");
		monthButton = new ToggleButton("Måned");
		group = new ToggleGroup();
		weekButton.setToggleGroup(group);
		monthButton.setToggleGroup(group);
		
		weekButton.setOnAction(e -> weekButtonMethod(e));
		monthButton.setOnAction(e -> monthButtonMethod(e));
		
		mainBox = new HBox(0);
		calendarAndInfo = new VBox(10);
		
		settings = new Button("Brukerinnstillinger");
		settings.setOnAction(e -> main.requestSettingsWindow());
		
		weekGui = new CalendarWeekGUI(this, LocalDate.now(), new ArrayList<Event>(), main);
		monthGui = new CalendarMonthGUI(this, LocalDate.now(), new ArrayList<Event>(), main);
		SideMenu menu = new SideMenu(this, LocalDate.now(), DebugMain.getEvents(), main);
		
		
		
		
		HBox buttons = new HBox(weekButton, monthButton);
		buttons.setAlignment(Pos.BOTTOM_RIGHT);
		calendarAndInfo.getChildren().addAll(buttons, weekGui);
		
		
		
		mainBox.getChildren().addAll(calendarAndInfo,menu);
		this.getChildren().add(mainBox);

//		this.getChildren().add(gui);
//		gui.setTranslateX(300);

	}
	
	
	private void monthButtonMethod(ActionEvent e) {
		calendarAndInfo.getChildren().remove(weekGui);
		calendarAndInfo.getChildren().add(monthGui);
	}

	private void weekButtonMethod(ActionEvent e) {
		calendarAndInfo.getChildren().remove(monthGui);
		calendarAndInfo.getChildren().add(weekGui);
	}

}
