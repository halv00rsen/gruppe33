package windows;
import components.*;
import gui.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import gui.Window;
public class NewUserWindow extends Window{
	
	Label title;
	Label firstNameLabel;
	Label lastNameLabel;
	Label usernameLabel;
	Label passwordLabel;
	Label emailLabel;
	Label phone;
	Text errorFirstNameText;
	Text errorLastNameText;
	Text errorUsernameText;
	Text errorPasswordText;
	Text errorEmailText;
	
	TextField firstNameTextField;
	TextField lastNameTextField;
	TextField usernameTextField;
	TextField emailTextField;
	TextField passwordTextField;
	
	Button createUserButton;
	HBox hbox;
	VBox vbox;
	GridPane mainGridPane;
	
	public NewUserWindow(Main main) {
		super(main);
		init();
	}

	@Override
	public void init() {
		this.setTranslateX(0);
		this.setPrefWidth(100);
		this.setPrefHeight(500);
		
		title = new Label();
		title.setText("Lag bruker");
		title.setFont(Font.font ("Verdana", 20));
		title.setPadding(new Insets(10, 0, 0, 20));
		
		firstNameLabel = new Label("Fornavn");
		firstNameLabel.setPadding(new Insets(0, 0, 0, 20));
		firstNameLabel.setFont(Font.font("Verdana", 12));
		lastNameLabel = new Label("Etternavn");
		lastNameLabel.setPadding(new Insets(0, 0, 0, 20));
		lastNameLabel.setFont(Font.font("Verdana", 12));
		usernameLabel = new Label("Brukernavn: ");
		usernameLabel.setPadding(new Insets(0, 0, 0, 20));
		usernameLabel.setFont(Font.font("Verdana", 12));
		passwordLabel = new Label("Passord: ");
		passwordLabel.setPadding(new Insets(0, 0, 0, 20));
		passwordLabel.setFont(Font.font("Verdana", 12));
		emailLabel = new Label("Epostadresse: ");
		emailLabel.setPadding(new Insets(0, 0, 0, 20));
		emailLabel.setFont(Font.font("Verdana", 12));
		
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
		errorEmailText = new Text("Skriv inn epost*");
		errorEmailText.setVisible(false);
		errorEmailText.setFill(Color.RED);
		
		firstNameTextField = new TextField();
		firstNameTextField.setPromptText("Ola");
		firstNameTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
		        	errorFirstNameText.setVisible(false);
		        }
		    }
		});
		lastNameTextField = new TextField();
		lastNameTextField.setPromptText("Nordmann");
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
		usernameTextField.setPromptText("OlaNordmann86");
		usernameTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
		        	errorUsernameText.setVisible(false);
		        }
		    }
		});
		
		passwordTextField = new TextField();
		passwordTextField.setPromptText("******");
		passwordTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
				{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
		        	errorPasswordText.setVisible(false);
		        }
		    }
		});
		
		emailTextField = new TextField();
		emailTextField.setPromptText("Ola@eksempel.no");
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
		
		createUserButton = new Button("Registrer deg");
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
		mainGridPane.add(usernameLabel, 0, 3);
		mainGridPane.add(usernameTextField, 1, 3);
		mainGridPane.add(errorUsernameText, 2, 3);
		mainGridPane.add(passwordLabel, 0, 4);
		mainGridPane.add(passwordTextField, 1, 4);
		mainGridPane.add(errorPasswordText, 2, 4);
		
		hbox = new HBox();
		hbox.setPadding(new Insets(0, 0, 0, 20));
		hbox.getChildren().add(createUserButton);
		vbox = new VBox(20);
		vbox.getChildren().add(title);
		vbox.getChildren().add(mainGridPane);
		vbox.getChildren().add(hbox);
		
		this.getChildren().add(vbox);
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
		if(isEmptyMessage == "") return true;
		
		return false;
			
		}

}
