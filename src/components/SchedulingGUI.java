package components;

import gui.Component;
import gui.DebugMain;
import gui.SideMenu;
import gui.Window;

import java.time.LocalDate;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SchedulingGUI extends Component{
    CalendarGUI calendargui;
    
    VBox calendarAndInfo;
    HBox mainBox;
    
	private Button settings;
	public SchedulingGUI(Window parent) {
		super(parent);
		mainBox = new HBox(0);
		calendarAndInfo = new VBox(10);
		
		settings = new Button("Brukerinnstillinger");
//		settings.setOnAction(e -> main.requestSettingsWindow());
		
		
		calendargui = new CalendarGUI(this, LocalDate.now(), DebugMain.getEvents());
		SideMenu menu = new SideMenu(this, LocalDate.now(), DebugMain.getEvents());
		calendarAndInfo.getChildren().addAll(calendargui);
		calendarAndInfo.getChildren().addAll(menu);
		
		mainBox.getChildren().addAll();
		this.getChildren().add(calendarAndInfo);
		

		
		
		mainBox.getChildren().addAll(calendarAndInfo,menu);
		this.getChildren().add(mainBox);

	}
}
