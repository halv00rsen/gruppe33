package components;

import gui.Component;
import gui.Main;
import gui.Main.ChangeTab;
import gui.Main.SchedulingGuiMethods;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import components.CalendarGUI.CalendarGUIListener;
import classes.Event;
import classes.Person;
import classes.Priority;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


public class SideMenu extends Component implements CalendarGUIListener{

	private final ListView<Event> list;
	private final ObservableList<Event> items;
	private final HBox listAndInformation;
	
	private final GridPane eventInformation;
	private final VBox vbox;
	
	private final Button editEvent, deleteEvent, hideEvent;
	private final Label title, fromTime, toTime, location, info, priority;
	ArrayList<Event> events = new ArrayList<Event>();
	private final Text fromTimeData, toTimeData, locationData, infoData, priorityData;
	private ArrayList<SideMenuInterface> listeners = new ArrayList<SideMenuInterface>();
	private SchedulingGuiMethods mainMethods;
	
	public SideMenu(Pane parent, List<Event> events, SchedulingGuiMethods mainMethods) {
		super(parent);
		this.mainMethods = mainMethods;
		title = new Label("Arrangementinfo");
		title.setFont(Main.header1);
		title.setPadding(new Insets(10, 0, 0, 0));
		
		list = new ListView<Event>();
		list.setMaxWidth(150);
		list.setPrefHeight(150);
		items =FXCollections.observableArrayList ();
		
		list.setItems(items);
		
		addListElements(events);
		
		editEvent = new Button("Endre");
		editEvent.setDisable(true);
		editEvent.setOnAction(e -> editEventMethod());
		
		deleteEvent = new Button("Slett");
		deleteEvent.setDisable(true);
		deleteEvent.setOnAction(e -> deleteEventMethod());
		
		hideEvent = new Button("Skjul");
		hideEvent.setDisable(true);
		hideEvent.setOnAction(e -> hideEventMethod());
		
		
		fromTime = new Label("Fra:");
		toTime = new Label("Til:");
		location = new Label("Rom");
		info = new Label("Informasjon");
		priority = new Label("Prioritet:");
		
		fromTimeData = new Text("");
		toTimeData = new Text("");
		locationData = new Text("");
		infoData = new Text("");
		priorityData = new Text("");

		eventInformation = new GridPane();
		eventInformation.setHgap(20); //horizontal gap in pixels 
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
				for (Event event : items) {
					if (event == newValue){
						
						changeEvent(event);
						alertListenersAboutEventChange(event);
						if(event.getMadeBy().getUsername() != mainMethods.getCurrentUser().getUsername()){
							editEvent.setDisable(true);
							deleteEvent.setDisable(true);
							hideEvent.setDisable(false);
						}else{
							editEvent.setDisable(false);
							deleteEvent.setDisable(false);
							hideEvent.setDisable(true);
						}
						break;
					}
				}
				if(list.getSelectionModel().getSelectedIndex() == -1){
					editEvent.setDisable(true);
					deleteEvent.setDisable(true);
					hideEvent.setDisable(true);
				}
			}
		});
		
		listAndInformation = new HBox(5, list, eventInformation);
		
		
		HBox eventButtons = new HBox(5);
		eventButtons.getChildren().addAll(editEvent, deleteEvent,hideEvent);
		
		
		vbox = new VBox();
		
		vbox.getChildren().addAll(title, listAndInformation, eventButtons);
		this.getChildren().add(vbox);
	}
	
	private void hideEventMethod() {
		Button btnYes = new Button("Ja");
		Button btnNo = new Button("Nei");
		HBox b = new HBox(10,btnYes,btnNo);
		Text t = new Text("Er du sikker på at du vil fjerne eventen fra din kalender og ved dette melde avkomst fra arrangementet? Denne handlingen kan ikke reverseres!");
		TextFlow textFlow = new TextFlow(t);
        textFlow.setMaxWidth(300);
        textFlow.setPadding(new Insets(30));
        Pane p = new Pane();
		p.setMaxWidth(500);
		b.setAlignment(Pos.BASELINE_CENTER);
		VBox v = new VBox(20,textFlow,b);
		p.getChildren().addAll(v);
        Scene secondScene = new Scene(p, 300, 200);
        Main.root.setDisable(true);
        Stage secondStage = new Stage();
        secondStage.setTitle("Varsel");
        secondStage.setScene(secondScene);
        secondStage.show();
        btnNo.requestFocus();
        
        btnNo.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				secondStage.close();
				Main.root.setDisable(false);
			}
		});
        btnYes.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				secondStage.close();
				Main.root.setDisable(false);
				mainMethods.getEventHider().hideEvent(list.getSelectionModel().getSelectedItem(), mainMethods.getCurrentUser());
				
			}
		});
	}

	private void deleteEventMethod() {
		Event event = list.getSelectionModel().getSelectedItem();
		if (event == null)
			return;
		mainMethods.getChangeTab().deleteEvent(event);
		items.remove(event);
		editEvent.setDisable(true);
		deleteEvent.setDisable(true);
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
		if(! event.getMadeBy().equals(mainMethods.getCurrentUser())){
			return;
		}
		mainMethods.getChangeTab().showEvent(event);
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
		resetFields();
		this.events = (ArrayList<Event>) events;
		items.clear();
		addListElements(events);
	}
	
	private void resetFields(){
		if(events != null){

			events.clear();
		}
		fromTimeData.setText("");
		toTimeData.setText("");
		locationData.setText("");
		infoData.setText("");
		priorityData.setText("");
		System.out.println("Field reseted");
	}
	
	private void changeEvent(Event event) {
		
		resetFields();
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
//		list.requestFocus();
		if (event == null){
			return;
		}
		event.getID();
		
		for (int i = 0; i< events.size(); i++) {
			if(event.getID() == events.get(i).getID()){
				list.getSelectionModel().select(events.get(i));
				
			}
			
		}
		
		
	}
	public void addListener(SideMenuInterface obj){
		this.listeners.add(obj);
	}
	public void alertListenersAboutEventChange(Event event){
		for (SideMenuInterface listener : this.listeners) {
			listener.changingSelectionToEvent(event);
		}
	}
	public interface SideMenuInterface{
		public void changingSelectionToEvent(Event event);
	}
}
