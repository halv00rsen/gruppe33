package windows;

import javafx.scene.control.ChoiceBox;
import components.SchedulingGUI;
import gui.Window;

public class OtherPersonScreen extends Window{
	SchedulingGUI schedulingGUI;
	
	 ChoiceBox cb = new ChoiceBox();
	 cb.getItems().addAll("item1", "item2", "item3");
	
	public OtherPersonScreen() {
		init();
	}
	
	@Override
	public void init() {

		
	}

}
