
package components;
import gui.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LoginGUI extends Component{
	
	Label title;
	Label infoLabel;
	Text errorLabel;
	Label usernameLabel;
	Label passwordLabel;
	Button loginButton;
	Button createUserButton;
	Button sendUserInfoButton;
	ToggleButton forgottenUserInfoToggle;
	TextField usernameTextField;
	TextField passwordTextField;
	TextField emailTextField;
	
	HBox usernameBox;
	HBox passwordBox;
	HBox hbox;
	HBox mainHBox;
	VBox vbox;
	VBox mainVBox;
	GridPane mainGridPane;
	
	public LoginGUI(Window parent, Main main) {
		super(parent,main);
		init();
	}

	private void init(){
		this.setTranslateX(0);
		this.setPrefWidth(100);
		this.setPrefHeight(500);
		
		title = new Label();
		title.setText("Kalender");
		title.setFont(Font.font ("Verdana", 20));
		
		errorLabel = new Text();
		usernameLabel = new Label();
		usernameLabel.setText("Username: ");
		usernameLabel.setPadding(new Insets(10, 10, 10, 10));
		passwordLabel = new Label();
		passwordLabel.setText("Password: ");
		passwordLabel.setPadding(new Insets(10, 10, 10, 10));
		
		loginButton = new Button();
		loginButton.setText("Log in");
		loginButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				
				main.requestLogin(usernameTextField.getText(),passwordTextField.getText());
				
				
			}
	});
		
		
		createUserButton = new Button();
		createUserButton.setText("Create user");
		createUserButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				
				System.out.println("Nytt vindu for ny bruker");
				
			}
	});
		sendUserInfoButton = new Button();
		sendUserInfoButton.setText("Send");
		sendUserInfoButton.setVisible(false);
		sendUserInfoButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				
				if(validateEmail() == "") System.out.println("SMTP FRA KTN");
				
				else System.out.println(validateEmail());
			}
	});
		
		usernameTextField = new TextField();
		usernameTextField.setPromptText("Enter username");
		passwordTextField = new TextField();
		passwordTextField.setPromptText("Enter password");
		emailTextField = new TextField();
		emailTextField.setVisible(false);
		emailTextField.setPromptText("Epostadresse");
		
		forgottenUserInfoToggle = new ToggleButton();
		forgottenUserInfoToggle.setText("Forgotten password/username?");
		forgottenUserInfoToggle.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				
				if(forgottenUserInfoToggle.isSelected()){
					
					sendUserInfoButton.setVisible(true);
					emailTextField.setVisible(true);
				}
				else{
					
					sendUserInfoButton.setVisible(false);
					emailTextField.setVisible(false);
					emailTextField.clear();
					
				}
			}
		});
		
		mainGridPane = new GridPane();
		mainGridPane.add(usernameLabel, 0, 0);
		mainGridPane.add(usernameTextField, 1, 0);
		mainGridPane.add(passwordLabel, 0, 1);
		mainGridPane.add(passwordTextField, 1, 1);
		
		hbox = new HBox();
		hbox.getChildren().add(emailTextField);
		hbox.getChildren().add(sendUserInfoButton);
		
		vbox = new VBox();
		vbox.getChildren().add(forgottenUserInfoToggle);
		vbox.getChildren().add(hbox);
		
		
		mainHBox = new HBox();
		mainHBox.getChildren().add(loginButton);
		mainHBox.getChildren().add(vbox);
		
		mainVBox = new VBox();
		mainVBox.getChildren().add(title);
		mainVBox.getChildren().add(mainGridPane);
		mainVBox.getChildren().add(mainHBox);
		mainVBox.getChildren().add(createUserButton);
		
		this.getChildren().add(mainVBox);

	}
	
	private String validate(){
		String errorMessage = "";
		
		//LOOP errormethods
			
			errorMessage = validateTextFields();

		return errorMessage;
	}
	
	private String validateTextFields(){
		String isEmptyMessage = "";
		if(usernameTextField.getText().isEmpty()) isEmptyMessage += "Brukernavn ikke oppgitt. ";
		
		if(passwordTextField.getText().isEmpty()) isEmptyMessage += "Passord ikke oppgitt. ";
		
		return isEmptyMessage;
	}
	private String validateUsernane(){
		
		return "sjekk i database";
		
	}
	private String validatePassword(){
		
		return "sjekk i database";
		
	}
	private String validateEmail(){
		String isEmptyMessage = "";
		if(emailTextField.getText().isEmpty()) isEmptyMessage += "Epost ikke oppgitt. ";
		else isEmptyMessage += "Sjekk i database";
		return isEmptyMessage;
		
	}
	
}
