package windows;
import components.*;
import gui.*;
import javafx.scene.layout.Pane;
import gui.Window;
public class LoginScreen extends Window{

	@Override
	public void init(Pane root) {
		this.setStyle("-fx-background-color: #00FF00");
		LoginGUI login = new LoginGUI(root);
		this.getChildren().add(login);
	}

}
