package windows;

import components.SettingsGUI;
import gui.Window;
import gui.Main.SaveUserInfo;

public class SettingsScreen extends Window{

	private final SaveUserInfo saveUserInfo;
	private SettingsGUI sg;
	
	public SettingsScreen(SaveUserInfo saveUserInfo){
		this.saveUserInfo = saveUserInfo;
		init();
	}
	
	@Override
	public void init() {
		sg = new SettingsGUI(this, saveUserInfo);
		
		borderPane.setCenter(sg);
	}
	
	public void setUserInfo(String firstname, String lastname, String email, String phone, String username){
		sg.setUserInfo(firstname, lastname, email, phone, username);
	}

}
