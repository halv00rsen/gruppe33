package windows;
import components.*;
import gui.*;
import gui.Window;
public class LoginScreen extends Window{

	public LoginScreen(Main main) {
		super(main);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(){
//		this.setStyle("-fx-background-color: #00FF00");
		LoginGUI login = new LoginGUI(this,main);
		this.getChildren().add(login);
		//
	}

}
