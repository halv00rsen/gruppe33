package components;

import gui.Component;
import gui.Main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import components.CalendarGUI.CalendarGUIListener;
import classes.Event;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class SideMenu extends Component implements CalendarGUIListener{

	Button createEvent;
	Button editEvent;
	Label title;
	Label fromTime;
	Label toTime;
	Label location;
	Label info;
	Label priority;
	
	Text fromTimeData;
	Text toTimeData;
	Text locationData;
	Text infoData;
	
	
	ListView<String> list;
	ObservableList<String> items;
	
	
	GridPane eventInformation;
	VBox vbox;

	public SideMenu(Pane parent, ArrayList<Event> events) {
		super(parent);
		init(events);
	}
	
	private void init(ArrayList<Event> events) {
		
		// Viser dagens events
		title = new Label("Dagens arrangementer");
		title.setFont(Main.header1);
		title.setPadding(new Insets(10, 0, 0, 0));
		
		list = new ListView<String>();

		list.setMaxWidth(150);
		list.setPrefHeight(150);
		items =FXCollections.observableArrayList ();
		
		addListElements(events);
		
		createEvent = new Button("Endre dette arrangementet");
		createEvent.setOnAction(e -> editEventMethod(e));
		
		editEvent = new Button("Slett dette arrangementet");
		editEvent.setOnAction(e -> deleteEventMethod(e));
		
		
		
		fromTime = new Label("Fra:");
		toTime = new Label("Til:");
		location = new Label("Sted:");
		info = new Label("Informasjon");
		priority = new Label("Priority:");
		
		fromTimeData = new Text("");
		toTimeData = new Text("");
		locationData = new Text("");
		infoData = new Text("");
		
		
		
		eventInformation = new GridPane();
		eventInformation.setHgap(50); //horizontal gap in pixels 
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
		    	for (Event event : events) {
					if(event.getEventName().equals(newValue)) {
						changeEvent(event, newValue);
					}
		        }
		    	
		       
				
		        /*
		        Label temp = new Label(newValue);
		        eventInformation.getChildren().remove(5);
		        eventInformation.add(temp, 1, 0);
		        */
		        
		    }
		});

		HBox listAndInformation = new HBox(5, list, eventInformation);
		
		
		HBox eventButtons = new HBox(5);
		eventButtons.getChildren().addAll(createEvent, editEvent);
		
		
		
		
		vbox = new VBox();
		vbox.getChildren().addAll(title, listAndInformation, eventButtons);
		this.getChildren().add(vbox);
	}

	
	private void deleteEventMethod(ActionEvent e) {
		if(list.getSelectionModel().getSelectedIndex() == -1) {
			
		}
		else {
			//Slett eventet
		}
	}

	private void editEventMethod(ActionEvent e) {
		
		if(list.getSelectionModel().getSelectedIndex() == -1) {
			
		}
		else {
			// Her skal det åpnes vinduet hvor man oppretter arrangement med utfylt info
		}
	}


	private void addListElements(ArrayList<Event> events) {
			for (Event event : events) {
				try { items.add(event.getEventName()); }
				catch (NullPointerException e) {}
			}
			if (items != null) {
				list.setItems(items);
			}
			else {
				items.add(" ");
				list.setItems(items);
			}
			
			
		
	}
	
	public void changeDate(ArrayList<Event> events) {
		vbox.getChildren().clear();
		init(events);
	}
	
	private void changeEvent(Event event, String newValue) {
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
	
	@Override
	public void dayIsHighligthed(LocalDate date, ArrayList<Event> events) {
		changeDate(events);
	}

	@Override
	public void eventIsHighligthed(Event event) {
		list.getSelectionModel().select(event.getEventName());
		changeEvent(event, event.getEventName());
		//System.out.println(event.getEventName() + "is highlighted");
	}
	
}
