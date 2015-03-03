package gui;
	
import classes.Program;

import com.sun.javafx.geom.transform.BaseTransform.Degree;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;


public class Main extends Application {
	public final static int SCREENHEIGHT = 1000;
	public final static int SCREENWIDTH = 600;
	public final static Pane root = new Pane();
	public final static Font header1 = new Font("Calibri", 30);
	private final Program program;
	
	public Main(){
		program = new Program();

	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			DebugMain debuglauncher = new DebugMain(root);
			Scene scene = new Scene(root,SCREENHEIGHT,SCREENHEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init(BorderPane p){
		
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
	}
