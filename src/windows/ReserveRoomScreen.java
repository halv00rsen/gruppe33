package windows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import classes.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import jfxtras.scene.control.LocalDateTimePicker;
import jfxtras.scene.control.LocalTimePicker;
import gui.Main;
import gui.Window;

public class ReserveRoomScreen extends Window{

	Label title, dateLabel, fromTimeLabel, toTimeLabel, wrongFormatLabel;
	
	TextField fromTimeField, toTimeField;
	
	DatePicker datePicker;
	
	GridPane gridPane;
	
	VBox mainBox;
	
	ToggleButton seeAvailableToggle;
	
	ListView<String> availableRooms;
	ObservableList<String> items;
	
	ArrayList<Room> rooms;
	
	
	public ReserveRoomScreen(ArrayList<Room> rooms) {
		init();
		this.rooms = rooms;
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
		toTimeField.setPromptText("hh-mm");
		toTimeField.setPrefWidth(55);
		
		datePicker = new DatePicker(LocalDate.now());
		
		gridPane = new GridPane();
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		
		seeAvailableToggle = new ToggleButton("Se tilgjengelige rom");
		seeAvailableToggle.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (seeAvailableToggle.isSelected()) {
					if(fromTimeField.getText().matches("[0-2][0-9]-[0-2][0-9]") && toTimeField.getText().matches("[0-2][0-9]-[0-2][0-9]")) {
						
						if(gridPane.getChildren().contains(wrongFormatLabel)) {
							gridPane.getChildren().remove(wrongFormatLabel);
						}
						
						int fromHour = Integer.parseInt(fromTimeField.getText(0, 2));
						int fromMinute = Integer.parseInt(fromTimeField.getText(3, fromTimeField.getText().length()));
				
						int toHour = Integer.parseInt(toTimeField.getText(0, 2));
						int toMinute = Integer.parseInt(toTimeField.getText(3, toTimeField.getText().length()));
				
						availableRooms = showAvailableRooms(LocalDateTime.of(datePicker.getValue(), LocalTime.of(fromHour, fromMinute)), LocalDateTime.of(datePicker.getValue(), LocalTime.of(toHour, toMinute)));
						gridPane.add(availableRooms, 1, 3);
					}
					else {
						wrongFormatLabel = new Label("Skriv inn tidspunktet på riktig format: hh-mm");
						wrongFormatLabel.setTextFill(Color.RED);
						gridPane.add(wrongFormatLabel, 1, 3);
					}
				}
				
				else {
					if (gridPane.getChildren().contains(availableRooms)) {
						gridPane.getChildren().remove(availableRooms);
					}
					if(gridPane.getChildren().contains(wrongFormatLabel)) {
						gridPane.getChildren().remove(wrongFormatLabel);
					}
				}
			}
		});
		
		
		HBox timeBox = new HBox(10);
		timeBox.getChildren().addAll(fromTimeField, toTimeLabel, toTimeField);
		
		gridPane.add(dateLabel, 0, 0);
		gridPane.add(datePicker, 1, 0);
		gridPane.add(fromTimeLabel, 0, 1);
		gridPane.add(timeBox, 1, 1);
		gridPane.add(seeAvailableToggle, 1, 2);
		
		mainBox = new VBox(20);
		mainBox.getChildren().addAll(title, gridPane);
		
		this.getChildren().add(mainBox);
	}
	
	private ListView<String> showAvailableRooms(LocalDateTime fromTime, LocalDateTime toTime) {
		ListView<String> availableRooms = new ListView<String>();
		ObservableList<String> items =FXCollections.observableArrayList ();
		
		for (Room room : rooms) {
			if (room.getCalendar().isAvailable(fromTime, toTime)) {
				items.add(room.getRoomName());
			}
		}
		availableRooms.setItems(items);
		availableRooms.setMaxWidth(150);
		availableRooms.setPrefHeight(75);
		
		return availableRooms;
	}
	
	
}
