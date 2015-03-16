
package components;
import database.ConnectionMySQL;
import gui.*;
import gui.Main.LoginCall;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class LoginGUI extends Component{
	
	private final LoginCall loginCall;
	
	Label title;
	Label errorNameLabel;
	Label errorPasswordLabel;
	Label errorEmailLabel;
	Label usernameLabel;
	Label passwordLabel;
	Button loginButton;
	Button sendUserInfoButton;
	ToggleButton forgottenUserInfoToggle;
	TextField usernameTextField;
	TextField emailTextField;
	PasswordField passwordField;
	TextFlow createUserFlow;
	
	HBox usernameBox;
	HBox passwordBox;
	HBox hbox;
	HBox mainHBox;
	VBox vbox;
	VBox mainVBox;
	VBox doBox;
	GridPane mainGridPane;
	
	public LoginGUI(Window parent, LoginCall loginCall) {
		super(parent);
		this.loginCall = loginCall;
		init();
	}

	private void init(){
		this.setTranslateX(0);
		this.setPrefWidth(280);
		this.setPrefHeight(500);
		
//		Hyperlink l = new Hyperlink("Ny bruker?");
//		l.setOnMouseClicked(new EventHandler<MouseEvent>(){
//
//			@Override
//			public void handle(MouseEvent event) {
//				//System.out.println("CLicked");
//			}
//			
//		});
//		
//		createUserFlow = new TextFlow(l);
		
		title = new Label();
		title.setText("Velkommen");
		title.setFont(Font.font ("Verdana", 20));
		title.setPadding(new Insets(0, 0, 10, 0));
		
		
		errorNameLabel = new Label();
		errorNameLabel.setPadding(new Insets(10, 10, 10, 10));
		errorNameLabel.setText("Skriv brukernavn*");
		errorNameLabel.setVisible(false);
		errorNameLabel.setTextFill(Color.RED);
		errorPasswordLabel = new Label();
		errorPasswordLabel.setPadding(new Insets(10, 10, 10, 10));
		errorPasswordLabel.setText("Skriv passord*");
		errorPasswordLabel.setVisible(false);
		errorPasswordLabel.setTextFill(Color.RED);
		errorEmailLabel = new Label();
		errorEmailLabel.setPadding(new Insets(10, 10, 10, 10));
		errorEmailLabel.setText("Skriv epost*");
		errorEmailLabel.setVisible(false);
		errorEmailLabel.setTextFill(Color.RED);
		usernameLabel = new Label();
		usernameLabel.setText("Brukernavn: ");
		usernameLabel.setFont(Font.font("Verdana", 12));
		usernameLabel.setPadding(new Insets(10, 20, 10, 0));
		passwordLabel = new Label();
		passwordLabel.setText("Passord: ");
		passwordLabel.setFont(Font.font("Verdana", 12));
		passwordLabel.setPadding(new Insets(10, 20, 10, 0));
		
		EventHandler<KeyEvent> loginAction = new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER){
//					loginCall.requestLogin(usernameTextField.getText(), passwordField.getText());
					login();
//					main.requestLogin(usernameTextField.getText(), passwordField.getText());
				}
			}
			
		};
		loginButton = new Button();
		loginButton.setOnKeyReleased(loginAction);
		loginButton.setText("Logg in");
		loginButton.setFont(Font.font("Verdana", 12));
		loginButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				
//				forgottenUserInfoToggle.setSelected(false);
//				sendUserInfoButton.setVisible(false);
//				errorEmailLabel.setVisible(false);
//				emailTextField.setVisible(false);
//				emailTextField.clear();
//				
//				if (validateTextFields()) {
//					loginCall.requestLogin(usernameTextField.getText(), passwordField.getText());
//					usernameTextField.setText("");
//					passwordField.setText("");
//				}
				login();
//					main.requestLogin(usernameTextField.getText(),passwordField.getText());
				
				
			}
		});
		
//		createUserButton = new Button();
//		createUserButton.setText("Lag bruker");
//		createUserButton.setOnAction(new EventHandler<ActionEvent>(){
//
//			@Override
//			public void handle(ActionEvent arg0) {
//				
//				//System.out.println("Nytt vindu for ny bruker");
//				
//			}
//		});
		sendUserInfoButton = new Button();
		sendUserInfoButton.setText("Send");
		sendUserInfoButton.setFont(Font.font("Verdana", 12));
		sendUserInfoButton.setVisible(false);
		sendUserInfoButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				
				validateEmail();
				
			}
		});
		
		usernameTextField = new TextField();
		usernameTextField.setPromptText("Brukernavn");
		usernameTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
		        	errorNameLabel.setVisible(false);
		        }
		    }
		});
		
		passwordField = new PasswordField();
		passwordField.setPromptText("Passord");
		passwordField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
		        	errorPasswordLabel.setVisible(false);
		        }
		    }
		});
		
		emailTextField = new TextField();
		emailTextField.setVisible(false);
		emailTextField.setPromptText("Epostadresse");
		emailTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
		        	errorEmailLabel.setVisible(false);
		        }
		    }
		});
		
		forgottenUserInfoToggle = new ToggleButton();
		forgottenUserInfoToggle.setText("Glemt passord?");
		forgottenUserInfoToggle.setFont(Font.font("Verdana", 12));
		forgottenUserInfoToggle.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				
				if(forgottenUserInfoToggle.isSelected()){
					
					errorNameLabel.setVisible(false);
					errorPasswordLabel.setVisible(false);
					
					sendUserInfoButton.setVisible(true);
					emailTextField.setVisible(true);
				}
				else{
					
					sendUserInfoButton.setVisible(false);
					errorEmailLabel.setVisible(false);
					emailTextField.setVisible(false);
					emailTextField.clear();
					
				}
			}
		});
		
		mainGridPane = new GridPane();
		mainGridPane.add(usernameLabel, 0, 0);
		mainGridPane.add(usernameTextField, 1, 0);
		mainGridPane.add(errorNameLabel, 2, 0);
		mainGridPane.add(passwordLabel, 0, 1);
		mainGridPane.add(passwordField, 1, 1);
		mainGridPane.add(errorPasswordLabel, 2, 1);
		hbox = new HBox(5);
		hbox.getChildren().add(emailTextField);
		hbox.getChildren().add(sendUserInfoButton);
		hbox.getChildren().add(errorEmailLabel);
		
		vbox = new VBox(10);
		vbox.getChildren().add(forgottenUserInfoToggle);
		vbox.getChildren().add(hbox);
		
		doBox = new VBox(5);
		doBox.getChildren().add(loginButton);
//		doBox.getChildren().add(createUserFlow);
		
		mainHBox = new HBox(30);
		mainHBox.setPadding(new Insets(10, 10, 10, 0));
		mainHBox.getChildren().add(doBox);
		mainHBox.getChildren().add(vbox);
		
		usernameTextField.setOnKeyReleased(loginAction);
		passwordField.setOnKeyReleased(loginAction);
		
		mainVBox = new VBox(10);
		mainVBox.setPadding(new Insets(10, 10, 10, 20));
		mainVBox.getChildren().add(title);
		mainVBox.getChildren().add(mainGridPane);
		mainVBox.getChildren().add(mainHBox);
		
		this.getChildren().add(mainVBox);

	}
	
	private void login(){
		forgottenUserInfoToggle.setSelected(false);
		sendUserInfoButton.setVisible(false);
		errorEmailLabel.setVisible(false);
		emailTextField.setVisible(false);
		emailTextField.clear();
		
		if (validateTextFields()) {
			loginCall.requestLogin(usernameTextField.getText(), passwordField.getText());
			usernameTextField.setText("");
			passwordField.setText("");
		}
	}
	
	private boolean validateTextFields(){
		String isEmptyMessage = "";
		if(usernameTextField.getText().isEmpty()){
			isEmptyMessage += "Brukernavn ikke oppgitt";
			errorNameLabel.setVisible(true);
		}
		if(passwordField.getText().isEmpty()){
			isEmptyMessage += "Passord ikke oppgitt";
			errorPasswordLabel.setVisible(true);
		}
		//System.out.println("Message: " + isEmptyMessage);
		if(isEmptyMessage == "") return true;
		
		return false;
			
		}

	private void validateEmail(){
		String isEmptyMessage = "";
		if(emailTextField.getText().isEmpty()) errorEmailLabel.setVisible(true);
		
	}
	
}
