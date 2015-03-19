package windows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

import components.NumberField;
import components.TimeField;
import classes.Event;
import classes.Room;
import database.ConnectionMySQL;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import gui.Main;
import gui.Window;

public class ReserveRoomScreen extends Window{

	private final TimeField fromTime, toTime;

	Label title, dateLabel, fromTimeLabel, toTimeLabel, wrongFormatLabel;
	
	
//	TextField fromTimeField, toTimeField;
	
	DatePicker datePicker;
	
	GridPane gridPane;
	
	VBox mainBox;
	Button reserveRoomButton;
	ToggleButton seeAvailableToggle;
	
	ComboBox romChoice = new ComboBox();
	ListView<String> availableRooms;
	ObservableList<String> items;
	
	int fromHour;
	int fromMinute;;

	int toHour;
	int toMinute;
	
	public ReserveRoomScreen() {
		fromTime = new TimeField(true);
		toTime = new TimeField(false);
		fromTime.setOtherTime(toTime);
		toTime.setOtherTime(fromTime);
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
		
		fromTime.setPrefWidth(55);
		toTime.setPrefWidth(55);
		
		datePicker = new DatePicker(LocalDate.now());
		
		gridPane = new GridPane();
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		
		
		seeAvailableToggle = new ToggleButton("Se tilgjengelige rom");
		seeAvailableToggle.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (seeAvailableToggle.isSelected()) {
					int sh = fromTime.getHour(), sm = fromTime.getMinutes(),
							th = toTime.getHour(), tm = toTime.getMinutes();
					if (sh == -1 || sm == -1 || th == -1 || sm == -1){
						wrongFormatLabel = new Label("Skriv inn tidspunktet på riktig format: hh:mm");
						wrongFormatLabel.setTextFill(Color.RED);
						gridPane.add(wrongFormatLabel, 1, 3);
					}else{
						if(gridPane.getChildren().contains(wrongFormatLabel)) {
							gridPane.getChildren().remove(wrongFormatLabel);
						}
						romChoice = showAvailableRooms(LocalDateTime.of(datePicker.getValue(), LocalTime.of(sh, sm)), 
								LocalDateTime.of(datePicker.getValue(), LocalTime.of(th, tm)));
						gridPane.add(availableRooms, 1, 3);
						
						availableRooms.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
							@Override
							public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
								if (gridPane.getChildren().contains(reserveRoomButton)) {
									gridPane.getChildren().remove(reserveRoomButton);
								}
								reserveRoomButton = new Button("Reserver " + availableRooms.getSelectionModel().getSelectedItem());
								reserveRoomButton.setOnAction(e -> reserveRoomMethod(e));
								gridPane.add(reserveRoomButton, 1, 4);
							}
						});
						
					}
				}
				
				else {
					if (gridPane.getChildren().contains(availableRooms)) {
						gridPane.getChildren().remove(availableRooms);
					}
					if (gridPane.getChildren().contains(reserveRoomButton)) {
			        	gridPane.getChildren().remove(reserveRoomButton);
					}
					if(gridPane.getChildren().contains(wrongFormatLabel)) {
						gridPane.getChildren().remove(wrongFormatLabel);
					}
				}
			}
		});
		
		
		HBox timeBox = new HBox(10);
		timeBox.getChildren().addAll(fromTime, toTimeLabel, toTime);
		
		gridPane.add(dateLabel, 0, 0);
		gridPane.add(datePicker, 1, 0);
		gridPane.add(fromTimeLabel, 0, 1);
		gridPane.add(timeBox, 1, 1);
		gridPane.add(seeAvailableToggle, 1, 2);
		gridPane.add(romChoice, 2, 2);
		
		mainBox = new VBox(20);
		mainBox.getChildren().addAll(title, gridPane);
		
		this.getChildren().add(mainBox);
	}
	
	private void reserveRoomMethod(ActionEvent e) {
		String roomName = availableRooms.getSelectionModel().getSelectedItem();
			Event event = new Event();
			event.setEventName("Reservert møterom");
			event.setStartTime(LocalDateTime.of(datePicker.getValue(), getLocalTime(fromTime)));
			event.setEndTime(LocalDateTime.of(datePicker.getValue(), getLocalTime(toTime)));
	}
	
	private LocalTime getLocalTime(TimeField timeField) {
		return LocalTime.of(timeField.getHour(), timeField.getMinutes());
	}

	private ComboBox<String> showAvailableRooms(LocalDateTime fromTime, LocalDateTime toTime) {
		
		String[] from = fromTime.toString().split("T");
		String[] to = toTime.toString().split("T");
		String start = from[0] + " " + from[1] + ":00";
		String end = to[0] + " " + to[1] + ":00";
		
		ArrayList<String> availableRooms = ConnectionMySQL.getAvailableRooms(start, end);
		ListView<String> availableRoomsListView = new ListView<String>();
		ObservableList<String> items =FXCollections.observableArrayList ();
		
		for (String room: availableRooms){
			
			items.add(room);
			
		}
		
		romChoice.setItems(items);
		romChoice.setMaxWidth(150);
		romChoice.setPrefHeight(75);
		return romChoice;
	}
	
	
}
