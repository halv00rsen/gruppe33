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
		LocalDate l1 = LocalDate.now();
		LocalTime l2 = LocalTime.now();
		LocalDateTime l3 = LocalDateTime.now();
		ZonedDateTime l4 = ZonedDateTime.now();
		CalendarMonthGUI c = new CalendarMonthGUI(root,LocalDate.now());
	}

}
