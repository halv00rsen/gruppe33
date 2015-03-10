package components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import gui.Component;

public class GroupGUI extends Component{

	private final TextField nameField;
	
	public GroupGUI(Pane parent) {
		super(parent);
		HBox master = new HBox(20);
		
		HBox leftTop = new HBox(10),
				leftBot = new HBox(10);
		nameField = new TextField();
		nameField.setPrefWidth(150);
		Label header = new Label("Opprett ny gruppe");
		leftTop.getChildren().addAll(new Label("Gruppenavn:"), nameField);
		header.setFont(Font.font("Verdane", 20));
		Button create = new Button("Lag gruppe"), 
				cancel = new Button("Avbryt");
		create.setOnAction(e -> System.out.println("Ny gruppe: " + nameField.getText()));
		cancel.setOnAction(e -> nameField.setText(""));
		leftBot.getChildren().addAll(create, cancel);
		VBox left = new VBox(10);
		left.setStyle("-fx-border-color: black; -fx-border: 3");
		left.getChildren().addAll(header, leftTop, leftBot);
		left.setPadding(new Insets(10));

		master.getChildren().addAll(left);
		
		
		
		
		getChildren().add(master);
//		VBox vBox = new VBox(5);
//		vBox.setPadding(new Insets(10));
//		GridPane gridPane = new GridPane();
//		Label header = new Label("Opprett ny gruppe");
//		header.setFont(Font.font ("Verdana", 20));
//		vBox.getChildren().addAll(header, gridPane);
//		gridPane.setHgap(10);
//		gridPane.setVgap(10);
//		gridPane.add(new Label("Navn:"), 0, 1);
//		nameField = new TextField();
//		nameField.setPrefWidth(200);
//		gridPane.add(nameField, 1, 1);
//		
//		getChildren().addAll(vBox);
	}
	
}
