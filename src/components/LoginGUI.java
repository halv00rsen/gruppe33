package components;
import gui.*;
import windows.*;
import java.awt.TextField;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class LoginGUI extends Component{
	
	Label infoLabel;
	Label usernameLabel;
	Label passwordLabel;
	Button loginButton;
	Button createUserButton;
	Button sendPasswordButton;
	TextField usernameTextField;
	TextField passwordTextField;
	GridPane gridpane;
	BorderPane borderpane;
	
	public LoginGUI(Pane parent) {
		super(parent);
		init();
	}

	private void init(){
		
		this.setTranslateX(0);
		this.setPrefWidth(100);
		this.setPrefHeight(500);
		
		infoLabel = new Label();
		usernameLabel = new Label();
		passwordLabel = new Label();
		loginButton = new Button();
		loginButton.setText("Log in");
		createUserButton = new Button();
		createUserButton.setText("Create user");
		sendPasswordButton = new Button();
		sendPasswordButton.setText("Forgotten password?");
		usernameTextField = new TextField();
		usernameTextField.setText("Enter username");
		passwordTextField = new TextField();
		passwordTextField.setText("Enter password");
		infoLabel.setText("test");
		
		borderpane = new BorderPane();
		gridpane = new GridPane();
		gridpane.add(loginButton, 1, 0 );
		this.getChildren().add(gridpane);
	}
}
