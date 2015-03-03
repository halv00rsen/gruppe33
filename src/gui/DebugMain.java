package gui;

import java.time.*;

import javafx.scene.layout.Pane;

public class DebugMain {

	/***
	 * 
	 * For � kj�re GUI komponenter til debuging. 
	 * Kan tulles med s� mye man �nsker, ikke en del av programmet.
	 */
	public DebugMain(Pane root) {
		
		
		
		
		CalendarMonthGUI c = new CalendarMonthGUI(root,LocalDate.now());
		LoginGUI l = new LoginGUI(root);
		root.getChildren().add(c);
	}

}
