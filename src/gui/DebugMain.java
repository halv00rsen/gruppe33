package gui;

import javafx.scene.layout.Pane;

public class DebugMain {

	/***
	 * 
	 * For å kjøre GUI komponenter til debuging. 
	 * Kan tulles med så mye man ønsker, ikke en del av programmet.
	 */
	public DebugMain(Pane root) {
		CalendarMonthGUI c = new CalendarMonthGUI(root);
	}

}
