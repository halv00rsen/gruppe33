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
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;


public class Main extends Application implements ProgramListener{
	public final static int SCREENHEIGHT = 1000;
	public final static int SCREENWIDTH = 600;
	public final static Pane root = new Pane();
	public final static Font header1 = new Font("Calibri", 30);
	private final Program program;
	private Window currentWindow;
	
	public Main(){
		program = new Program();
		program.addListener(this);
		Window loginScreen = new LoginScreen(this);
		openNewWindow(loginScreen);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
//			DebugMain debuglauncher = new DebugMain(root);

			
			Scene scene = new Scene(root,SCREENHEIGHT,SCREENHEIGHT);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loginSuccess(String username, String name) {
		HomeScreen homeScreen  = new HomeScreen(this);
		openNewWindow(homeScreen);
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userCreated(boolean isCreated) {
		// TODO Auto-generated method stub
		
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
	public void requestLogin(String userName, String password){
		program.personLogin(userName, password);
	}
}
