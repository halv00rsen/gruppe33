package gui;
	
import com.sun.javafx.geom.transform.BaseTransform.Degree;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


public class Main extends Application {
	public final static int SCREENHEIGHT = 1000;
	public final static int SCREENWIDTH = 600;
	public final static Pane root = new Pane();
	
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
}
