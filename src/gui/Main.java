package gui;
import java.util.List;

import windows.*;
import classes.Calendar;
import classes.Group;
import classes.Message;
import classes.Program;
import classes.ProgramListener;
import classes.Room;
import classes.View;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;


public class Main extends Application implements ProgramListener{

	public final static int SCREENHEIGHT = 800;
	public final static int SCREENWIDTH = 1200;
	public final static Pane root = new Pane();
	public final static Font header1 = new Font("Calibri", 30);

	private final Program program;
	private Stage stage;
	private Window currentWindow;
	private TabPane tabPane;
	
	public Main(){
		program = new Program();

		program.addListener(this);
		Window loginScreen = new LoginScreen(this);
		openNewWindow(loginScreen);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
//			DebugMain debuglauncher = new DebugMain(root, this);
			stage = primaryStage;
			
			Scene scene = new Scene(root,SCREENHEIGHT,SCREENHEIGHT);
			stage.setTitle("xKal");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	public static void applyContrast(Node n, double amount, int[] addColor) {
		String style = n.getStyle();
		
		style = style.substring(style.length()-6,style.length());
		int[] hex = {Integer.parseInt(style.substring(0,2),16),Integer.parseInt(style.substring(2,4),16),Integer.parseInt(style.substring(4,6),16)};
		style = "";
		for (int i = 0; i < 3; i++) {
			hex[i] = (int) (hex[i] * amount) + addColor[i];
			hex[i] = hex[i] > 255 ? 255 : hex[i]; 
			String string = Integer.toHexString( hex[i]);
			string = string.equals("0") ?  "00" : string;
			style = style + string;
		}
		n.setStyle("-fx-background-color: #" + style);
	}
	
	private void openNewWindow(Window window){
		if (currentWindow != null)
			currentWindow.exitThisWindow();
		currentWindow = window;
		root.getChildren().add(window);
	}
	
	@Override
	public void loginFailed() {
		
		
	}

	@Override
	public void loginSuccess(String username, String name) {
		HBox box = new HBox(20);
		Button logout = new Button("Logg ut");
		
		logout.setOnAction(e -> program.logout());
		tabPane = new TabPane();
		Tab home = new Tab("Hjem");
		HomeScreen homeScreen  = new HomeScreen(this);
		home.setContent(homeScreen);
		
		Tab newEvent = new Tab("Ny event");
		Tab room = new Tab("Rom");
		Tab persons = new Tab("Personer");
		Tab inbox = new Tab("Postkasse");
		Tab settings = new Tab("Innstillinger");
		
		tabPane.getTabs().addAll(home, newEvent, room, persons, inbox, settings);
		for (Tab tab : tabPane.getTabs()){
			tab.setClosable(false);
		}
		box.getChildren().addAll(logout, tabPane);
//		openNewWindow(homeScreen);
		if (currentWindow != null)
			currentWindow.exitThisWindow();
		root.getChildren().add(box);
		stage.setTitle("xKal (" + username + ")");
	}

	@Override
	public void logout() {
		requestLoginWindow();
		stage.setTitle("xKal");
	}

	@Override
	public void userCreated(boolean isCreated) {
		if (isCreated){
			requestLoginWindow();
		}
		
	}

	@Override
	public void passwordChange(boolean isChanged) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(Message msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGroups(Group... groups) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCalendar(List<Calendar> cal, View view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRoomNames(Room... rooms) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendNotification(String notif) {
		// TODO Auto-generated method stub
		
	}
	
	public void requestCreateUser(String username, String password, String name){
		program.createUser(username, password, name);
	}
	
	public void requestLogin(String userName, String password){
		program.personLogin(userName, password);
	}
	
	public void requestNewUserGUI(){
		NewUserWindow w = new NewUserWindow(this);
		openNewWindow(w);
	}
	
	public void requestLoginWindow(){
		Window w = new LoginScreen(this);
		openNewWindow(w);
	}
	
	public void requestSettingsWindow(){
//		Window w = new 
	}
}
