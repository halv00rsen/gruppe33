package windows;
import components.*;
import gui.*;
import gui.Window;
public class LoginScreen extends Window{

	public LoginScreen(Main main) {
		super(main);
		// TODO Auto-generated constructor stub
	}



	public void init(){
//		this.setStyle("-fx-background-color: #00FF00");
		LoginGUI login = new LoginGUI(this,main);
		NewUserWindow create = new NewUserWindow(main);
		SettingsGUI settings = new SettingsGUI(this, main);
		this.getChildren().add(settings);
		//
	}

}
