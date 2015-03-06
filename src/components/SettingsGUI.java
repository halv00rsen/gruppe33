package components;

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

	Button createUserButton;
	HBox hbox;
	HBox innerHbox;
	VBox vbox;
	GridPane mainGridPane;

	public SettingsGUI(Window parent, Main main) {
		super(parent,main);
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
	        	firstNameTextField.selectAll();
		        if (newPropertyValue)
		        {
		        	errorFirstNameText.setVisible(false);
		        }
		        else
		        {
		            System.out.println("Textfield out focus");
		        }
		    }
		});
		lastNameTextField = new TextField();
		lastNameTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
		        	errorLastNameText.setVisible(false);
		        }
		    }
		});
		usernameTextField = new TextField();
		usernameTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
			    	System.out.println("FUNKER");
		        	errorUsernameText.setVisible(false);
		        }
		    }
		});
		
		passwordTextField = new PasswordField();
		passwordTextField.setPromptText("******");
		passwordTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
		        	errorPasswordText.setVisible(false);
		        	errorRePasswordText.setVisible(false);
		        }
		    }
		});
		
		rePasswordTextField = new PasswordField();
		rePasswordTextField.setPromptText("******");
		rePasswordTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
		        	errorRePasswordText.setVisible(false);
		        }
		    }
		});
		
		emailTextField = new TextField();
		emailTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
		        	errorEmailText.setVisible(false);
		        }
		    }
		});
		
		phoneTextField = new TextField();
		phoneTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
		        	System.out.println("get number");
		        }
		    }
		});
		
		createUserButton = new Button("Lagre endringer");
		createUserButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				
				if(validateTextFields()) System.out.println("lag bruker");
				
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
		
		vbox = new VBox(20);
		vbox.setPadding(new Insets(0, 0, 0, 20));
		vbox.getChildren().add(title);
		vbox.getChildren().add(mainGridPane);
		vbox.getChildren().add(createUserButton);
		
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
		firstNameTextField.setText("Fra Database");
		lastNameTextField.setText("Fra Database");
		emailTextField.setText("Fra Database");
		lastNameTextField.setText("Fra Database");
		usernameTextField.setText("Fra Database");
		
		//hvis tlf er registrert
		phoneTextField.setText("Fra Database");
		
		//hent passord
		
	}
		
	}

