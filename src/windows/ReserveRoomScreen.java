package windows;

import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import gui.Main;
import gui.Window;

public class ReserveRoomScreen extends Window{

	Label title, dateLabel, fromTimeLabel, toTimeLabel;
	
	TextField fromTimeField, toTimeField;
	
	DatePicker datePicker;
	
	GridPane gridPane;
	
	VBox mainBox;
	
	Button seeAvailable;
	
	public ReserveRoomScreen() {
		init();
	}
	
	
	@Override
	public void init() {
		title = new Label("Reserver rom");
		title.setFont(Main.header1);
		title.setPadding(new Insets(10, 0, 0, 0));
		
		
		dateLabel = new Label("Dato");
		fromTimeLabel = new Label("Fra klokken");
		toTimeLabel = new Label("til");
		
		fromTimeField = new TextField();
		fromTimeField.setPromptText("hh-mm");
		fromTimeField.setPrefWidth(55);
		
		toTimeField = new TextField();
		fromTimeField.setPromptText("hh-mm");
		toTimeField.setPrefWidth(55);
		
		datePicker = new DatePicker(LocalDate.now());
		
		gridPane = new GridPane();
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		
		seeAvailable = new Button("Se tilgjengelige rom");
		
		
		HBox timeBox = new HBox(10);
		timeBox.getChildren().addAll(fromTimeField, toTimeLabel, toTimeField);
		
		gridPane.add(dateLabel, 0, 0);
		gridPane.add(datePicker, 1, 0);
		gridPane.add(fromTimeLabel, 0, 1);
		gridPane.add(timeBox, 1, 1);
		gridPane.add(seeAvailable, 1, 2);
		
		mainBox = new VBox(20);
		mainBox.getChildren().addAll(title, gridPane);
		
		this.getChildren().add(mainBox);
		
	}

}
