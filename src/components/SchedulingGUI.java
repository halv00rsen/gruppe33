package components;

import gui.DebugMain;
import gui.SideMenu;

import java.time.LocalDate;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SchedulingGUI {
    CalendarGUI calendargui;
    
    VBox calendarAndInfo;
    HBox mainBox;
    
	private Button settings;
	public SchedulingGUI() {
		mainBox = new HBox(0);
		calendarAndInfo = new VBox(10);
		
		settings = new Button("Brukerinnstillinger");
		settings.setOnAction(e -> main.requestSettingsWindow());
		
		calendargui = new CalendarGUI(this, main, LocalDate.now(), DebugMain.getEvents());
		SideMenu menu = new SideMenu(this, LocalDate.now(), DebugMain.getEvents(), main);


		calendarAndInfo.getChildren().addAll(calendargui);

		
		
		mainBox.getChildren().addAll(calendarAndInfo,menu);
		this.getChildren().add(mainBox);

	}
}
