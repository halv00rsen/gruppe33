package windows;

import javafx.scene.control.ChoiceBox;
import components.SchedulingGUI;
import gui.Window;

public class OtherPersonScreen extends Window{
	SchedulingGUI schedulingGUI;
	
	ChoiceBox<String> choiceBox;
	
	public OtherPersonScreen() {
		choiceBox = new ChoiceBox<String>();
		init();
	}
	
	@Override
	public void init() {
		
		
	}

}
