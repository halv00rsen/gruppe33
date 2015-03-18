package gui;
import gui.MessageScreen.EventInfo;

import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import windows.*;
import classes.Calendar;
import classes.Event;
import classes.Group;
import classes.Message;
import classes.Person;
import classes.Program;
import classes.ProgramListener;
import classes.Room;
import javafx.application.Application;
import javafx.beans.value.ObservableNumberValue;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Main extends Application implements ProgramListener{

	public final static int SCREENHEIGHT = 650;
	public final static int SCREENWIDTH = 1000;
	public final static Pane root = new Pane();
	public final static Font header1 = new Font("Helvetica", 15);
	public final static Insets paddingInsets = new Insets(10, 0, 0, 0);
	
	private final Program program;
	private final LoginScreen loginScreen;
	private final List<GetPersonListener> personListeners;
	private final List<GetGroupListener> groupListeners;
	
	public static Stage stage;
	
	private Window currentWindow;
	private TabPane tabPane;
	private HomeScreen homeScreen;
	private NewUserWindow newUserScreen;
	private SettingsScreen settingsScreen;
	private InboxScreen inboxScreen;
	private EventScreen eventScreen;
	private OtherPersonScreen otherPersonScreen;
	private ReserveRoomScreen reserveRoomScreen;
	private MessageScreen messageScreen;
	
	public Main(){

		program = new Program();
		personListeners = new ArrayList<GetPersonListener>();
		groupListeners = new ArrayList<GetGroupListener>();
		program.addListener(this);
		loginScreen = new LoginScreen(new LoginCall());
		openNewWindow(loginScreen);
	}
	
	public class ChangeTab{
		
		public void goToHomeScreen(){
			tabPane.getSelectionModel().select(home);
			program.updateCalendars();
		}
		
		public void showEvent(Event event){
			tabPane.getSelectionModel().select(newEvent);
			Calendar cal = program.getCalendarFor(event.getID());
			eventScreen.showEvent(event, cal);
		}
		public void showEventInHomeScreen(Event event){
			goToHomeScreen();
			homeScreen.highlightEvent(event);
		}
		public void deleteEvent(Event event){
			program.deleteEvent(event);
		}
	}
	
	public class CreateUser{
		
		public void createNewUser(String username, String password, String firstname, String lastname,
				String phone, String email, boolean isAdmin){
			program.createUser(username, password, firstname, lastname, phone, email, isAdmin);
		}
	}
	
	public class SaveUserInfo{
		
		public void saveUserInfo(String firstname, String lastname, String phone, String email){
			program.updateCurrentPerson(firstname, lastname, email, phone);
		}
		
		public void changePassword(String newPassword, String oldPassword){
			program.changePasswordUser(oldPassword, newPassword);
		}
	}
	
	public class AddGroupListener{
		
		public void addListener(GetGroupListener l){
			groupListeners.add(l);
		}
		
		public void addNewGroup(String name){
			program.createGroup(name);
		}
		
		public void deleteGroup(int groupId){
			program.deleteGroup(groupId);
		}
	}
	
	
	public class AddPersonListener{
		
		public void addListener(GetPersonListener l){
			personListeners.add(l);
		}
	}
	
	public class LoginCall{
		
		public void requestLogin(String username, String password){
			program.personLogin(username, password);
		}
	}
	
	public class AddNewEvent{
		
		public void addEvent(Event event, Calendar cal){
			program.createEvent(event, cal);
		}
		
		public void changeEvent(int eventId, Calendar old, Calendar newCal, Event newEvent){
			System.out.println(old);
			System.out.println(newCal);
			program.changeEvent(eventId, old, newCal, newEvent);
		}
		
	}
	
	public class GoToEvent{
		
		public void goToEvent(int eventKey){
//			//Dette er bare for å gå direkte til homescreen uten å gå gjennom program. Kan endres på!
//			ChangeTab hei = new ChangeTab();
//			Event event = new Event();
//			
//			hei.showEventInHomeScreen(event);
		}
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
//			DebugMain debuglauncher = new DebugMain(root, this);
			stage = primaryStage;
			Scene scene = new Scene(root,SCREENWIDTH,SCREENHEIGHT);
			
			stage.setFullScreen(false);
			stage.setTitle("xKal");
			primaryStage.setScene(scene);
			primaryStage.setMinWidth(950);

			primaryStage.setMinHeight(660);
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
		if (event == null){
			System.out.println("showEvent(Event) kalt i main");
			return;
		}
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		selectionModel.select(home);
		
		homeScreen.highlightEvent(event);
	}

	private Tab home, newEvent, room, persons, inbox, settings, groups;
	
	@Override
	public void loginSuccess(Person person) {
		root.getChildren().remove(loginScreen);
		Button logout = new Button("Logg ut");
		
		logout.setOnAction(e -> program.logout());
		
		
		
		tabPane = new TabPane();
		tabPane.setPrefWidth(1920);;
		tabPane.setPrefHeight(1000);
		home = new Tab("Hjem");
		homeScreen  = new HomeScreen(new ChangeTab(), person,new AddNewEvent(), new AddGroupListener());
		home.setContent(homeScreen);
		tabPane.setPrefHeight(1000);
		
		newEvent = new Tab("Ny event");
		eventScreen = new EventScreen(new AddNewEvent(), new ChangeTab(), new AddPersonListener(), new AddGroupListener());
		newEvent.setContent(eventScreen);
		
		room = new Tab("Reserver Rom");
		reserveRoomScreen = new ReserveRoomScreen();
		room.setContent(reserveRoomScreen);
		
		groups = new Tab("Grupper");
		groups.setContent(new GroupScreen(new AddPersonListener(), new AddGroupListener()));
		
		
		persons = new Tab("Personer");
		otherPersonScreen = new OtherPersonScreen(new AddPersonListener(), new ChangeTab());
		persons.setContent(otherPersonScreen);
		
		
		inbox = new Tab("Postkasse");
		inboxScreen = new InboxScreen(new GoToEvent());
		inbox.setContent(inboxScreen);
		
		settings = new Tab("Innstillinger");
		settingsScreen = new SettingsScreen(new SaveUserInfo());
		settings.setContent(settingsScreen);
		settingsScreen.setUserInfo(person.getFirstname(), person.getLastname(), person.getMail(), person.getPhone(), person.username);
		
		tabPane.getTabs().addAll(home, newEvent, room, persons, inbox, settings, groups);
		tabPane.setTabMinWidth(75);
		if (program.isAdminLogIn()){
			Tab newUser = new Tab("Ny bruker");
			newUserScreen = new NewUserWindow(new CreateUser());
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
		Button slideButton = new Button("Vis melding");
		VBox vBox = new VBox(3);
		vBox.translateXProperty().bind(stage.widthProperty().subtract(120));
		vBox.setLayoutY(2);
		Button slideAway = new Button("Fjern melding");
		slideAway.setOnAction(e -> messageScreen.hide());
		vBox.getChildren().addAll(logout, slideButton, slideAway);
		slideButton.setOnAction(e -> messageScreen.show("heisann", EventInfo.FromInbox));
		root.getChildren().addAll(tabPane, messageScreen, vBox);
//		logout.setLayoutX(1020);
//		logout.setLayoutY(2);
//		slideButton.setLayoutX(1050);
//		slideButton.setLayoutY(2);
		messageScreen.setOnMouseClicked(new EventHandler<MouseEvent>(){
			
			public void handle(MouseEvent event){
				messageScreen.hide();
				if (messageScreen.getEventInfo() == EventInfo.FromInbox)
					tabPane.getSelectionModel().select(inbox);
			}
		});
		stage.setTitle("xKal (" + person.getUsername() + ")");
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
//			requestLoginWindow();
		}
		
	}

	@Override
	public void passwordChange(boolean isChanged) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(Message msg) {
		// TODO Auto-generated method stub
		messageScreen.show(msg.info, EventInfo.None);
	}

	@Override
	public void updateGroups(List<Group> groups) {
		// TODO Auto-generated method stub
		for (GetGroupListener l : groupListeners)
			l.setGroups(groups);
	}

	@Override
	public void updateCalendar(Calendar[] cal) {
		homeScreen.schedulingGUI.updateCalendars(cal);
		
	}

	@Override
	public void updateRoomNames(Room... rooms) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendNotification(String notif) {
		// TODO Auto-generated method stub
		
	}
	
	public void requestLogin(String userName, String password){
		program.personLogin(userName, password);
	}
	
//	public void requestNewUserGUI(){
//		NewUserWindow w = new NewUserWindow();
//		openNewWindow(w);
//	}
//	
	private void requestLoginWindow(){
		openNewWindow(loginScreen);
	}
//	
//	public void requestSettingsWindow(){
////		Window w = new 
//	}
	
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



	@Override
	public void setAllPersons(List<Person> persons) {
		for (GetPersonListener l : personListeners)
			l.updatePersons(persons);
	}



	@Override
	public void updatePersonInformation(Person person) {
		// TODO Auto-generated method stub
		
	}
	
	public interface GetGroupListener{
		public void setGroups(List<Group> groups);
	}
	
}
