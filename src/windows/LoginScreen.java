
package windows;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import components.*;
import gui.*;
import gui.Main.LoginCall;
public class LoginScreen extends Window{

	private final LoginCall loginCall;
	
	public LoginScreen(LoginCall loginCall) {
		this.loginCall = loginCall;
		init();
	}



	public void init(){
//		this.setStyle("-fx-background-color: #00FF00");
		LoginGUI login = new LoginGUI(this, loginCall);

		this.getChildren().add(login);
		//
	}
	
	public void loginFailed(){
		//Do something;
	}

}