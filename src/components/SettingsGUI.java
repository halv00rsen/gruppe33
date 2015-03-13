package components;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import gui.Component;
import gui.Main;
import gui.Window;

public class SettingsGUI extends Component{
	
	Label title;
	Label firstNameLabel;
	Label lastNameLabel;
	Label usernameLabel;
	Label passwordLabel;
	Label rePasswordLabel;
	Label emailLabel;
	Label phoneLabel;
	Text errorFirstNameText;
	Text errorLastNameText;
	Text errorEmailText;
	Text errorUsernameText;
	Text errorPasswordText;
	Text errorRePasswordText;
	
	TextField firstNameTextField;
	TextField lastNameTextField;
	TextField emailTextField;
	TextField phoneTextField;
	TextField usernameTextField;
	PasswordField passwordTextField;
	PasswordField rePasswordTextField;
	
	Button cancelButton;
	Button createUserButton;
	HBox hbox;
	VBox vbox;
	GridPane mainGridPane;

	public SettingsGUI(Window parent) {
		super(parent);
		init();
	}

	private void init() {
		
		this.setTranslateX(0);
		this.setPrefWidth(100);
		this.setPrefHeight(500);
		
		
		title = new Label();
		title.setText("Endre instillinger");
		title.setFont(Font.font ("Verdana", 20));
		title.setPadding(new Insets(10, 0, 0, 0));
		
		firstNameLabel = new Label("Fornavn*");
		firstNameLabel.setFont(Font.font("Verdana", 12));
		lastNameLabel = new Label("Etternavn*");
		lastNameLabel.setFont(Font.font("Verdana", 12));
		usernameLabel = new Label("Brukernavn*");
		usernameLabel.setFont(Font.font("Verdana", 12));
		passwordLabel = new Label("Passord*");
		passwordLabel.setFont(Font.font("Verdana", 12));
		rePasswordLabel = new Label("Passord om igjen*");
		rePasswordLabel.setFont(Font.font("Verdana", 12));
		emailLabel = new Label("Epostadresse*");
		emailLabel.setFont(Font.font("Verdana", 12));
		phoneLabel = new Label("Tlf");
		phoneLabel.setFont(Font.font("Verdana", 12));
		
		errorFirstNameText = new Text("Skriv inn fornavn*");
		errorFirstNameText.setVisible(false);
		errorFirstNameText.setFill(Color.RED);
		errorLastNameText = new Text("Skriv inn etternavn*");
		errorLastNameText.setVisible(false);
		errorLastNameText.setFill(Color.RED);
		errorUsernameText = new Text("Skriv inn brukernavn*");
		errorUsernameText.setVisible(false);
		errorUsernameText.setFill(Color.RED);
		errorPasswordText = new Text("Skriv inn passord*");
		errorPasswordText.setVisible(false);
		errorPasswordText.setFill(Color.RED);
		errorRePasswordText = new Text("Passord stemmer ikke");
		errorRePasswordText.setVisible(false);
		errorRePasswordText.setFill(Color.RED);
		errorEmailText = new Text("Skriv inn epost*");
		errorEmailText.setVisible(false);
		errorEmailText.setFill(Color.RED);
		
		firstNameTextField = new TextField();
		firstNameTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		    	errorFirstNameText.setVisible(false);
		    	Platform.runLater(new Runnable() {
	                @Override
	                public void run() {
	                    if (firstNameTextField.isFocused() && !firstNameTextField.getText().isEmpty()) {
	                    	firstNameTextField.selectAll();
	                    }
	                }
		    	});
		    }
		    	
		});
		firstNameTextField.textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if(!(newValue.matches("[A-Za-z]*"))){
					firstNameTextField.setText(oldValue);
				}
				
			}
			
		});	
		
		lastNameTextField = new TextField();
		lastNameTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		    	errorLastNameText.setVisible(false);
		    	Platform.runLater(new Runnable() {
	                @Override
	                public void run() {
	                    if (lastNameTextField.isFocused() && !lastNameTextField.getText().isEmpty()) {
	                    	lastNameTextField.selectAll();
	                    }
	                }
		    	});
		    }
		    	
		});
		lastNameTextField.textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if(!(newValue.matches("[A-Za-z]*"))){
					lastNameTextField.setText(oldValue);
				}
				
			}
			
		});	
		usernameTextField = new TextField();
		usernameTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		    	errorUsernameText.setVisible(false);
		    	Platform.runLater(new Runnable() {
	                @Override
	                public void run() {
	                    if (usernameTextField.isFocused() && !usernameTextField.getText().isEmpty()) {
	                    	usernameTextField.selectAll();
	                    }
	                }
		    	});
		    }
		    	
		});
		
		
		passwordTextField = new PasswordField();
		passwordTextField.setPromptText("******");
		passwordTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		    	errorPasswordText.setVisible(false);
		    	Platform.runLater(new Runnable() {
	                @Override
	                public void run() {
	                    if (passwordTextField.isFocused() && !passwordTextField.getText().isEmpty()) {
	                    	passwordTextField.selectAll();
	                    }
	                }
		    	});
		    }
		 });
		
		rePasswordTextField = new PasswordField();
		rePasswordTextField.setPromptText("******");
		rePasswordTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		    	errorRePasswordText.setVisible(false);
		    	Platform.runLater(new Runnable() {
	                @Override
	                public void run() {
	                    if (rePasswordTextField.isFocused() && !rePasswordTextField.getText().isEmpty()) {
	                    	rePasswordTextField.selectAll();
	                    }
	                }
		    	});
		    }
		 });
		
		emailTextField = new TextField();
		emailTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		    	errorEmailText.setVisible(false);
		    	Platform.runLater(new Runnable() {
	                @Override
	                public void run() {
	                    if (emailTextField.isFocused() && !emailTextField.getText().isEmpty()) {
	                    	emailTextField.selectAll();
	                    }
	                }
		    	});
		    }
		 });
		
		phoneTextField = new TextField();
		phoneTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		    	Platform.runLater(new Runnable() {
	                @Override
	                public void run() {
	                    if (phoneTextField.isFocused() && !phoneTextField.getText().isEmpty()) {
	                    	phoneTextField.selectAll();
	                    }
	                }
		    	});
		    }
		 });
		
		phoneTextField.textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				//System.out.println("ok");
				if(!(newValue.matches("[0-9]*")) || (phoneTextField.getText().length()) == 9){
					phoneTextField.setText(oldValue);
				}
				
			}
			
		});	
		
		createUserButton = new Button("Lagre endringer");
		createUserButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				
				if(validateTextFields()){} 
					//System.out.println("Lag bruker");
				
			}
		});
		cancelButton = new Button("Avbryt");
		cancelButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				
				//System.out.println("Avbryt");
				
			}
	});

		mainGridPane = new GridPane();
		mainGridPane.setHgap(10);
		mainGridPane.setVgap(10);
		mainGridPane.add(firstNameLabel, 0, 0);
		mainGridPane.add(firstNameTextField, 1, 0);
		mainGridPane.add(errorFirstNameText, 2, 0);
		mainGridPane.add(lastNameLabel, 0, 1);
		mainGridPane.add(lastNameTextField, 1, 1);
		mainGridPane.add(errorLastNameText, 2, 1);
		mainGridPane.add(emailLabel, 0, 2);
		mainGridPane.add(emailTextField, 1, 2);
		mainGridPane.add(errorEmailText, 2, 2);
		mainGridPane.add(phoneLabel, 0, 3);
		mainGridPane.add(phoneTextField, 1, 3);
		mainGridPane.add(usernameLabel, 0, 4);
		mainGridPane.add(usernameTextField, 1, 4);
		mainGridPane.add(errorUsernameText, 2, 4);
		mainGridPane.add(passwordLabel, 0, 5);
		mainGridPane.add(passwordTextField, 1, 5);
		mainGridPane.add(errorPasswordText, 2, 5);
		mainGridPane.add(rePasswordLabel, 0, 6);
		mainGridPane.add(rePasswordTextField, 1, 6);
		mainGridPane.add(errorRePasswordText, 2, 6);
		
		hbox = new HBox(20);
		hbox.getChildren().add(createUserButton);
		hbox.getChildren().add(cancelButton);
		
		vbox = new VBox(20);
		vbox.setPadding(new Insets(0, 0, 0, 20));
		vbox.getChildren().add(title);
		vbox.getChildren().add(mainGridPane);
		vbox.getChildren().add(hbox);
		
		this.getChildren().add(vbox);
		
		getUserInfo();
	}
	
	private boolean validateTextFields(){
		String isEmptyMessage = "";
		if(firstNameTextField.getText().isEmpty()){
			isEmptyMessage += "Fornavn ikke oppgitt. ";
			errorFirstNameText.setVisible(true);
		}
		if(lastNameTextField.getText().isEmpty()){
			isEmptyMessage += "Etternavn ikke oppgitt. ";
			errorLastNameText.setVisible(true);
		}
		if(emailTextField.getText().isEmpty()){
			isEmptyMessage += "Epost ikke oppgitt. ";
			errorEmailText.setVisible(true);
		}
		if(usernameTextField.getText().isEmpty()){
			isEmptyMessage += "Brukernavn ikke oppgitt. ";
			errorUsernameText.setVisible(true);
		}
		if(passwordTextField.getText().isEmpty()){
			isEmptyMessage += "Passord ikke oppgitt. ";
			errorPasswordText.setVisible(true);
		}
		if(!passwordTextField.getText().equals(rePasswordTextField.getText())){
			isEmptyMessage += "Passord stemmer ikke. ";
			
			errorRePasswordText.setVisible(true);
			passwordTextField.clear();
			rePasswordTextField.clear();
		}

		if(isEmptyMessage == "") return true;
		
		return false;
			
		}
	
	private void getUserInfo(){
		
		//Hent info fra database
		firstNameTextField.setText("Fra");
		lastNameTextField.setText("Database");
		emailTextField.setText("Fra Database");
		lastNameTextField.setText("Fra Database");
		usernameTextField.setText("Fra Database");
		
		//hvis tlf er registrert
		phoneTextField.setText("87654321");
		
		//hent passord
		
	}
		
	}

