
package windows;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
//		login.setStyle("-fx-background-color: #FFFF00");
		BorderPane.setAlignment(login, Pos.CENTER);
		BorderPane.setMargin(login, new Insets(150));
		borderPane.setCenter(login);
		//
	}
	
	public void loginFailed(){
		//Do something;
	}

}