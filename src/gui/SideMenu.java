package gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import classes.Event;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class SideMenu extends Component {

	Button createEvent;
	Button editEvent;
	Label daysEvents;
	Label fromTime;
	Label toTime;
	Label location;
	Label info;
	Label priority;
	
	
	ListView<String> list = new ListView<String>();
	ObservableList<String> items =FXCollections.observableArrayList ();
	
	
	public SideMenu(Pane parent, LocalDate date, ArrayList<Event> events, Main main) {
		super(parent, main);
		init(date, events);
	}
	
	private void init(LocalDate date, ArrayList<Event> events) {
		
		// Viser dagens events
		daysEvents = new Label("Dagens arrangementer");
		daysEvents.setFont(Main.header1);
		addListElements(events);
		
		createEvent = new Button("Opprett nytt arrangement");
		createEvent.setOnAction(e -> createEventMethod(e));
		
		editEvent = new Button("Endre eksisterende arrangement");
		editEvent.setOnAction(e -> editEventMethod(e));
		
		String eventName = list.getSelectionModel().getSelectedItem();
		
		
		
		
		fromTime = new Label("Fra:");
		toTime = new Label("Til:");
		location = new Label("Sted:");
		info = new Label("Informasjon");
		priority = new Label("Priority:");
		
		Text fromTimeData = new Text("");
		Text toTimeData = new Text("");
		Text locationData = new Text("");
		Text infoData = new Text("");
		
		
		
		GridPane eventInformation = new GridPane();
		eventInformation.setHgap(5); //horizontal gap in pixels => that's what you are asking for
		eventInformation.setVgap(5); //vertical gap in pixels
		eventInformation.setGridLinesVisible(false);
		eventInformation.add(fromTime, 0, 0);
		eventInformation.add(toTime, 0, 1);
		eventInformation.add(location, 0, 2);
		eventInformation.add(info, 0, 3);
		eventInformation.add(priority, 0, 4);

		eventInformation.add(fromTimeData, 1, 0);
		eventInformation.add(toTimeData, 1, 1);
		eventInformation.add(locationData, 1, 2);
		eventInformation.add(infoData, 1, 3);
		
		
		//Endrer tabellen når man klikker på listen
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        System.out.println("ListView selection changed from oldValue = " 
		                + oldValue + " to newValue = " + newValue);
		        for (Event event : events) {
					if(event.getEventName().equals(newValue)) {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
						String formattedStartTime = event.getStartTime().format(formatter);
						String formattedEndTime = event.getEndTime().format(formatter);
						
						eventInformation.getChildren().removeAll(fromTime, toTime, location, info, priority, fromTimeData, toTimeData, locationData, infoData);
						eventInformation.add(fromTime, 0, 0);
						eventInformation.add(toTime, 0, 1);
						eventInformation.add(location, 0, 2);
						eventInformation.add(info, 0, 3);
						eventInformation.add(priority, 0, 4);
						
						fromTimeData.setText(formattedStartTime);
						toTimeData.setText(formattedEndTime);
						locationData.setText(event.getLocation());
						infoData.setText(event.getInfo());

						eventInformation.add(fromTimeData, 1, 0);
						eventInformation.add(toTimeData, 1, 1);
						eventInformation.add(locationData, 1, 2);
						eventInformation.add(infoData, 1, 3);
						
						
					}
				}
		        /*
		        Label temp = new Label(newValue);
		        eventInformation.getChildren().remove(5);
		        eventInformation.add(temp, 1, 0);
		        */
		        
		    }
		});

		HBox listAndInformation = new HBox(list, eventInformation);
		
		
		
		HBox eventButtons = new HBox(5);
		eventButtons.getChildren().addAll(createEvent, editEvent);
		
		
		
		
		VBox vbox = new VBox();
		vbox.getChildren().addAll(daysEvents, listAndInformation, eventButtons);
		parent.getChildren().add(vbox);
	}

	
	private Object editEventMethod(ActionEvent e) {
		// Her skal det åpnes vinduet hvor man oppretter arrangement
		return null;
	}

	private Object createEventMethod(ActionEvent e) {
		// Her skal det åpnes vinduet hvor man oppretter arrangement
		return null;
	}

	private void addListElements(ArrayList<Event> events) {
		for (Event event : events) {
			items.add(event.getEventName());
		}
		list.setItems(items);
		list.setMaxWidth(150);
		list.setPrefHeight(150);
	}
	
}
