package gui;
import java.util.List;

import windows.*;
import classes.Calendar;
import classes.Event;
import classes.Group;
import classes.Message;
import classes.Program;
import classes.ProgramListener;
import classes.Room;
import javafx.application.Application;
import javafx.geometry.Side;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Main extends Application implements ProgramListener{

	public final static int SCREENHEIGHT = 800;
	public final static int SCREENWIDTH = 1200;
	public final static Pane root = new Pane();
	public final static Font header1 = new Font("Calibri", 30);

	private final Program program;
	private final LoginScreen loginScreen;
	
	private static Stage stage;
	private Window currentWindow;
	private TabPane tabPane;
	
	private HomeScreen homeScreen;
	private NewUserWindow newUserScreen;
	private SettingsScreen settingsScreen;
	private InboxScreen inboxScreen;
	private EventScreen eventScreen;

	private MessageScreen messageScreen;
	
	public Main(){
		program = new Program();

		program.addListener(this);
		loginScreen = new LoginScreen(new LoginCall());
		openNewWindow(loginScreen);
	}
	
	public class LoginCall{
		
		public void requestLogin(String username, String password){
			program.personLogin(username, password);
		}
	}
	
	public class GoToEvent{
		
		public void goToEvent(int eventKey){
			program.requestEvent(eventKey);
		}
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
		window.setVisible(true);
		root.getChildren().add(window);
	}
	
	@Override
	public void loginFailed() {
		loginScreen.loginFailed();
	}
	
	@Override
	public void showEvent(Event event){
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		selectionModel.select(home);
		
		homeScreen.highlightEvent(event);
	}

	private Tab home, newEvent, room, persons, inbox, settings;
	
	@Override
	public void loginSuccess(String username, String name) {
		root.getChildren().remove(loginScreen);
		Button logout = new Button("Logg ut");
		
		logout.setOnAction(e -> program.logout());
		tabPane = new TabPane();
		home = new Tab("Hjem");
		homeScreen  = new HomeScreen();
		home.setContent(homeScreen);
		
		newEvent = new Tab("Ny event");
		eventScreen = new EventScreen();
		newEvent.setContent(eventScreen);
		
		room = new Tab("Rom");
		
		
		persons = new Tab("Personer");
		
		
		inbox = new Tab("Postkasse");
		inboxScreen = new InboxScreen(new GoToEvent());
		inbox.setContent(inboxScreen);
		
		settings = new Tab("Innstillinger");
		settingsScreen = new SettingsScreen();
		settings.setContent(settingsScreen);
		
		tabPane.getTabs().addAll(home, newEvent, room, persons, inbox, settings);
		tabPane.setTabMinWidth(75);
		if (program.isAdminLogIn()){
			Tab newUser = new Tab("Ny bruker");
			newUserScreen = new NewUserWindow();
			newUser.setContent(newUserScreen);
			tabPane.getTabs().add(newUser);
		}
		for (Tab tab : tabPane.getTabs()){
			tab.setClosable(false);
		}
//		box.getChildren().addAll(logout, tabPane);
//		openNewWindow(homeScreen);
		if (currentWindow != null)
			currentWindow.exitThisWindow();
		messageScreen = new MessageScreen();
		root.getChildren().addAll(tabPane, logout);
		logout.setLayoutX(1020);
		logout.setLayoutY(2);
		stage.setTitle("xKal (" + username + ")");
	}

	@Override
	public void logout() {
		root.getChildren().clear();
		tabPane = null;
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
	public void updateCalendar(List<Calendar> cal) {
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
		NewUserWindow w = new NewUserWindow();
		openNewWindow(w);
	}
	
	private void requestLoginWindow(){
		openNewWindow(loginScreen);
	}
	
	public void requestSettingsWindow(){
//		Window w = new 
	}
	
	public static final double getHeight(){
		if (stage == null)
			return SCREENHEIGHT;
		return stage.getHeight();
	}
	
	public static final double getWidth(){
		if (stage == null)
			return SCREENWIDTH;
		return stage.getWidth();
	}
	public static String colorToHex(Color color){
		return String.format( "#%02X%02X%02X",
	            (int)( color.getRed() * 255 ),
	            (int)( color.getGreen() * 255 ),
	            (int)( color.getBlue() * 255 ) );
	}
	
}
