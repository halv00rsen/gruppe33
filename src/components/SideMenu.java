package components;

import gui.Component;
import gui.Main;
import gui.Main.ChangeTab;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

	private final ChangeTab changeTab;
	private final ListView<Event> list;
	private final ObservableList<Event> items;
	private final HBox listAndInformation;
	
	private final GridPane eventInformation;
	private final VBox vbox;
	
	private final Button editEvent, deleteEvent;
	private final Label title, fromTime, toTime, location, info, priority;
	
	private final Text fromTimeData, toTimeData, locationData, infoData, priorityData;
	
	public SideMenu(Pane parent, List<Event> events, ChangeTab changeTab) {
		super(parent);
		this.changeTab = changeTab;
		title = new Label("Dagens arrangementer");
		title.setFont(Main.header1);
		title.setPadding(new Insets(10, 0, 0, 0));
		
		list = new ListView<Event>();
		list.setMaxWidth(150);
		list.setPrefHeight(150);
		items =FXCollections.observableArrayList ();
		
		list.setItems(items);
		
		addListElements(events);
		
		editEvent = new Button("Endre dette arrangementet");
		editEvent.setOnAction(e -> editEventMethod());
		
		deleteEvent = new Button("Slett dette arrangementet");
		deleteEvent.setOnAction(e -> deleteEventMethod());
		
		
		
		fromTime = new Label("Fra:");
		toTime = new Label("Til:");
		location = new Label("Rom");
		info = new Label("Informasjon");
		priority = new Label("Priority:");
		
		fromTimeData = new Text("");
		toTimeData = new Text("");
		locationData = new Text("");
		infoData = new Text("");
		priorityData = new Text("");
		
		
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
		eventInformation.add(priorityData, 1, 4);
		
		String hhhhh = this.toString();
		//Endrer tabellen når man klikker på listen
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Event>() {
			@Override
			public void changed(ObservableValue<? extends Event> observable, Event oldValue, Event newValue) {
//				System.out.println(hhhhh);
//		    	System.out.println(newValue.getID());
				for (Event event : items) {
					if (event == newValue){
						System.out.println("eventstuff");
						changeEvent(event);
						break;
					}
				}
			}
		});
		
		listAndInformation = new HBox(5, list, eventInformation);
		
		
		HBox eventButtons = new HBox(5);
		eventButtons.getChildren().addAll(editEvent, deleteEvent);
		
		
		vbox = new VBox();
		
		vbox.getChildren().addAll(title, listAndInformation, eventButtons);
		this.getChildren().add(vbox);
	}
	
	private void deleteEventMethod() {
		Event event = list.getSelectionModel().getSelectedItem();
		if (event == null)
			return;
		changeTab.deleteEvent(event);
		items.remove(event);
//		if(list.getSelectionModel().getSelectedIndex() == -1) {
//			
//		}
//		else {
//			//Slett eventet
//		}
	}

	private void editEventMethod() {
		Event event = list.getSelectionModel().getSelectedItem();
		if (event == null)
			return;
		changeTab.showEvent(event);
	}


	private void addListElements(List<Event> events) {
			if(events == null){
				return;
			}
			for (Event event : events) {
				if (event != null)
					items.add(event);
			}
	}
	
	public void changeDate(List<Event> events) {
//	 	init(events);
		System.out.println("changedate");
		resetFields();
		items.clear();
		addListElements(events);
	}
	
	private void resetFields(){
		fromTimeData.setText("");
		toTimeData.setText("");
		locationData.setText("");
		infoData.setText("");
		priorityData.setText("");
		System.out.println("Field reseted");
	}
	
	private void changeEvent(Event event) {
		
		resetFields();
		System.out.println(event);
		if(event == null){
			eventInformation.getChildren().removeAll(fromTime, toTime, location, info, priority, fromTimeData, toTimeData, locationData, infoData, priorityData);
			return;
		}
		System.out.println(event.getStartDate() + "  " + event.getEndTime());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String formattedStartTime = event.getStartTime().format(formatter);
		String formattedEndTime = event.getEndTime().format(formatter);
		
		
		fromTimeData.setText(formattedStartTime);
		toTimeData.setText(formattedEndTime);
		try {
			locationData.setText(event.getRoom().getRoomName());
		}
		catch(Exception e) {
			locationData.setText("Rom ikke valgt");
		}
		
		infoData.setText(event.getInfo());
		priorityData.setText("" + event.getPriority());
		
	}
	
	@Override
	public void dayIsHighligthed(LocalDate date, ArrayList<Event> events) {
		changeDate(events);
 	}

	@Override
	public void eventIsHighligthed(Event event) {
//		changeEvent(event);
		System.out.println("eventishigligheterweoigjegoieojgojgio");
//		list.requestFocus();
		list.getSelectionModel().select(event);
	}
	
}
