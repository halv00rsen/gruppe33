package gui;

import components.GroupGUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DebugWindow extends Application{

	@Override
	public void start(Stage stage){
		try{
			Pane pane = new Pane();
//			GroupGUI g = new GroupGUI(pane);
//			pane.getChildren().add(g);
			Scene scene = new Scene(pane, 1000, 500);
			stage.setScene(scene);
			stage.show();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		launch(args);
	}

}
