package windows;
import components.*;
import gui.*;
import javafx.scene.layout.Pane;
import gui.Window;
public class LoginScreen extends Window{

	public LoginScreen(Main main) {
		super(main);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(Pane root) {
		LoginGUI login = new LoginGUI(root);

	public void init(){
//		this.setStyle("-fx-background-color: #00FF00");
		LoginGUI login = new LoginGUI(this,main);
		this.getChildren().add(login);
		//
	}

}
