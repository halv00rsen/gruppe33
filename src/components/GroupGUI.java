package components;

import java.util.ArrayList;
import java.util.List;

import classes.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import gui.Component;

public class GroupGUI extends Component{

	private final TextField nameField;
	private final ListView<String> groups, persons, deleteGroups, deletePersons;
	private final ObservableList<String> groupItems, personItems;
	
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
		create.setOnAction(e -> addGroup(new Group(nameField.getText(), 9)));
		cancel.setOnAction(e -> nameField.setText(""));
		leftBot.getChildren().addAll(create, cancel);
		VBox left = new VBox(10);
		left.setStyle("-fx-border-color: black; -fx-border: 3");
		left.getChildren().addAll(header, leftTop, leftBot);
		left.setPadding(new Insets(10));

		
		VBox right = new VBox(10);
		Label addUser = new Label("Legg til personer i gruppe");
		addUser.setFont(Font.font("Verdane", 20));
		groups = new ListView<String>();
		groupItems = FXCollections.observableArrayList ();
		persons = new ListView<String>();
		persons.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		personItems = FXCollections.observableArrayList();
		addListElements(getDebugGroups());
		addPersons(getPersonNames());
		GridPane rTop = new GridPane();
		rTop.setHgap(10);
		rTop.add(new Label("Velg gruppe:"), 0, 0);
		rTop.add(groups, 1, 0);
		rTop.add(new Label("Velg personer:"), 0, 1);
		rTop.add(persons, 1, 1);
		HBox buttons = new HBox(10);
		Button addUsers = new Button("Legg til personer"),
				cancelUsers = new Button("Avbryt");
		buttons.getChildren().addAll(addUsers, cancelUsers);
		right.getChildren().addAll(rTop, buttons);
		
		
		VBox botRig = new VBox(10);
		Label botHead = new Label("Slett gruppe");
		botHead.setFont(Font.font("Verdane", 20));
		GridPane p1 = new GridPane();
		p1.setHgap(10);
		p1.add(new Label("Velg gruppe:"), 0, 0);
		deleteGroups = new ListView<String>();
		deleteGroups.setItems(groupItems);
		deleteGroups.setMaxWidth(150);
		deleteGroups.setPrefHeight(75);
		p1.add(deleteGroups, 1, 0);
		Button delete = new Button("Slett gruppe");
		delete.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				String groupName = deleteGroups.getSelectionModel().getSelectedItem();
				groupItems.remove(groupName);
			}
			
		});
		p1.add(delete, 1, 1);
		
		botRig.getChildren().addAll(botHead, p1);
		
		VBox botLef = new VBox(10);
		Label botLefHead = new Label("Fjern personer fra gruppe");
		botLefHead.setFont(Font.font("Verdane", 20));
		deletePersons = new ListView<String>();
		deletePersons.setMaxWidth(150);
		deletePersons.setPrefHeight(75);
		deletePersons.setItems(personItems);
		
		botLef.getChildren().addAll(botLefHead);
		
		
		
		master.getChildren().addAll(left, right, botRig);
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
	
	private void addPersons(List<String> persons){
		for (String p : persons){
			personItems.add(p);
		}
		this.persons.setItems(personItems);
		this.persons.setMaxWidth(150);
		this.persons.setPrefHeight(75);
	}
	
	private void addListElements(List<Group> groups) {
		for (Group group : groups) {
			groupItems.add(group.getName());
		}
		this.groups.setItems(groupItems);
		this.groups.setMaxWidth(150);
		this.groups.setPrefHeight(75);
		
	}
	
	private void removeGroup(Group group){
		groupItems.remove(group.getName());
	}
	
	private void addGroup(Group group){
		groupItems.add(group.getName());
	}
	
	public static List<String> getPersonNames(){
		List<String> n = new ArrayList<String>();
		n.add("J�rgen Halvorsen");
		n.add("Ola Gunndersen");
		n.add("kul kulersen");
		return n;
	}
	
	public static List<Group> getDebugGroups(){
		List<Group> groups = new ArrayList<Group>();
		groups.add(new Group("Tingekulla", 1));
		groups.add(new Group("Tulleghj�rnet", 2));
		groups.add(new Group("Filmgjengen", 3));
		groups.add(new Group("Vannsklie", 4));
		groups.add(new Group("Monster", 5));
		return groups;
	}
	
}
