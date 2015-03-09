package windows;

import components.SettingsGUI;

import gui.Window;

public class SettingsScreen extends Window{

	public SettingsScreen(){
		init();
	}
	
	@Override
	public void init() {
		SettingsGUI sg = new SettingsGUI(this);
		getChildren().add(sg);
	}

}
