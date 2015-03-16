package components;

import java.util.ArrayList;
import java.util.List;

import components.GroupList.Fancy;
import classes.Group;
import classes.Person;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import gui.Component;
import gui.GetPersonListener;
import gui.Main;
import gui.Main.AddPersonListener;

public class GroupGUI extends Component implements GetPersonListener{

//	private final TextField nameField;
//	private final ListView<Group> groups;
//	private final ListView<Person> persons;
//	private final ObservableList<Person> personItems;
//	private final ObservableList<Group> groupItems;
	private final GroupList<Group> subGroups;
	private final GroupList<Person> persons;
	
	private final ListView<Group> groupList;
	private final ObservableList<Group> groupItems;
	
	private Group selectedGroup;
	
	public GroupGUI(Pane parent, AddPersonListener apl) {
		super(parent);
		apl.addListener(this);
		selectedGroup = null;
		
		subGroups = new GroupList<Group>("Undergrupper", Fancy.Group, new FieldListener());
		persons = new GroupList<Person>("Medlemmer", Fancy.Person, new FieldListener());
//		persons.addChoices(DebugMain.getPeople());
		
		VBox vBox = new VBox(10);
		groupList = new ListView<Group>();
		groupItems = FXCollections.observableArrayList();
		groupList.setItems(groupItems);
		groupList.setPrefWidth(GroupList.width);
		groupList.setPrefHeight(GroupList.height);
		Label header = new Label("Grupper");
		header.setFont(Main.header1);
		Button delete = new Button("Slett");
		delete.setOnAction(e -> {
			Group group = groupList.getSelectionModel().getSelectedItem();
			groupItems.remove(group);
			changeStatus();
		});
		ToggleButton addToggle = new ToggleButton("Legg til ...");
		delete.setPrefWidth(GroupList.width / 2 - 5);
		addToggle.setPrefWidth(GroupList.width / 2 - 5);
		HBox one = new HBox(10);
		one.getChildren().addAll(addToggle, delete);
		HBox two = new HBox(10);
		TextField groupName = new TextField();
		groupName.setPrefWidth(GroupList.width * 2 / 3);
		groupName.setPromptText("Gruppenavn");
		
		Label name = new Label("Navn:");
		
		two.getChildren().addAll(name, groupName);
		
		Button createGroup = new Button("Opprett");
		createGroup.setPrefWidth(GroupList.width);
		
		vBox.setMaxWidth(GroupList.width);
		vBox.getChildren().addAll(header, groupList, one, two, createGroup);
		
		EventHandler<ActionEvent> actionEvent = new EventHandler<ActionEvent>(){
			
			public void handle(ActionEvent event){
				final boolean selected = addToggle.isSelected();
				createGroup.setVisible(selected);
				name.setVisible(selected);
				groupName.setVisible(selected);
				groupName.setText("");
				
			}
		}; 
		
		actionEvent.handle(null);
		
		addToggle.setOnAction(actionEvent);
		
		createGroup.setOnAction(new EventHandler<ActionEvent>(){
			
			public void handle(ActionEvent event){
				String name = groupName.getText();
				if (!"".equals(name)){
					Group g = new Group(name, 1);
					groupItems.add(g);
					groupList.getSelectionModel().select(g);
					groupList.requestFocus();
					
				}
				addToggle.setSelected(false);
				actionEvent.handle(null);
			}
		});
		
		groupList.focusedProperty().addListener(obs -> {
			changeStatus();
		});
		
		groupList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Group>() {

		    @Override
		    public void changed(ObservableValue<? extends Group> observable, Group oldValue, Group newValue) {
		        // Your action here
		    	changeStatus();
		    	selectedGroup = newValue;
		    	updateFields();
		    }
		});
		
		
		HBox box = new HBox(10);
		box.getChildren().addAll(vBox, persons, subGroups);
		getChildren().add(box);
		changeStatus();
		
		
//		HBox master = new HBox(20);
//		
//		HBox leftTop = new HBox(10),
//				leftBot = new HBox(10);
//		nameField = new TextField();
//		nameField.setPrefWidth(150);
//		Label header = new Label("Opprett ny gruppe");
//		leftTop.getChildren().addAll(new Label("Gruppenavn:"), nameField);
//		header.setFont(Font.font("Verdane", 20));
//		Button create = new Button("Lag gruppe"), 
//				cancel = new Button("Avbryt");
//		create.setOnAction(e -> addGroup(new Group(nameField.getText(), 9)));
//		cancel.setOnAction(e -> nameField.setText(""));
//		leftBot.getChildren().addAll(create, cancel);
//		VBox left = new VBox(10);
//		left.setStyle("-fx-border-color: black; -fx-border: 3");
//		left.getChildren().addAll(header, leftTop, leftBot);
//		left.setPadding(new Insets(10));
//
//		
//		VBox right = new VBox(10);
//		Label addUser = new Label("Legg til personer i gruppe");
//		addUser.setFont(Font.font("Verdane", 20));
//		groups = new ListView<String>();
//		groupItems = FXCollections.observableArrayList ();
//		persons = new ListView<String>();
//		persons.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//		personItems = FXCollections.observableArrayList();
//		addListElements(getDebugGroups());
//		addPersons(getPersonNames());
//		GridPane rTop = new GridPane();
//		rTop.setHgap(10);
//		rTop.add(new Label("Velg gruppe:"), 0, 0);
//		rTop.add(groups, 1, 0);
//		rTop.add(new Label("Velg personer:"), 0, 1);
//		rTop.add(persons, 1, 1);
//		HBox buttons = new HBox(10);
//		Button addUsers = new Button("Legg til personer"),
//				cancelUsers = new Button("Avbryt");
//		buttons.getChildren().addAll(addUsers, cancelUsers);
//		right.getChildren().addAll(rTop, buttons);
//		
//		
//		VBox botRig = new VBox(10);
//		Label botHead = new Label("Slett gruppe");
//		botHead.setFont(Font.font("Verdane", 20));
//		GridPane p1 = new GridPane();
//		p1.setHgap(10);
//		p1.add(new Label("Velg gruppe:"), 0, 0);
//		deleteGroups = new ListView<String>();
//		deleteGroups.setItems(groupItems);
//		deleteGroups.setMaxWidth(150);
//		deleteGroups.setPrefHeight(75);
//		p1.add(deleteGroups, 1, 0);
//		Button delete = new Button("Slett gruppe");
//		delete.setOnAction(new EventHandler<ActionEvent>(){
//
//			@Override
//			public void handle(ActionEvent event) {
//				String groupName = deleteGroups.getSelectionModel().getSelectedItem();
//				groupItems.remove(groupName);
//			}
//			
//		});
//		p1.add(delete, 1, 1);
//		
//		botRig.getChildren().addAll(botHead, p1);
//		
//		VBox botLef = new VBox(10);
//		Label botLefHead = new Label("Fjern personer fra gruppe");
//		botLefHead.setFont(Font.font("Verdane", 20));
//		deletePersons = new ListView<String>();
//		deletePersons.setMaxWidth(150);
//		deletePersons.setPrefHeight(75);
//		deletePersons.setItems(personItems);
//		
//		botLef.getChildren().addAll(botLefHead);
//		
//		
	}
	
	private void updateFields(){
		if (selectedGroup == null)
			return;
		persons.setValues(selectedGroup.getMembers());
		subGroups.setValues(selectedGroup.getSubGroups());
		
	}
	
	class FieldListener{
		
		public void addMember(Person p){
			if (selectedGroup != null){
				selectedGroup.addMembers(p);
			}
		}
		
		public void removeMember(Person p){
			if (selectedGroup != null)
				selectedGroup.removePerson(p);
		}
		
		public void addGroup(Group g){
			if (selectedGroup != null)
				selectedGroup.addSubGroups(g);
		}
		
		public void removeGroup(Group g){
			if (selectedGroup != null)
				selectedGroup.removeSubGroup(g);
		}
	}
	
	private void changeStatus(){
		if (groupList.getSelectionModel().isEmpty()){
			persons.setDisable(true);
			subGroups.setDisable(true);
			selectedGroup = null;
		}else{
			persons.setDisable(false);
			subGroups.setDisable(false);
			selectedGroup = groupList.getSelectionModel().getSelectedItem();
		}
	}
	
	
	public static List<Group> getDebugGroups(){
		List<Group> groups = new ArrayList<Group>();
		groups.add(new Group("Tingekulla", 1));
		groups.add(new Group("Tulleghjørnet", 2));
		groups.add(new Group("Filmgjengen", 3));
		groups.add(new Group("Vannsklie", 4));
		groups.add(new Group("Monster", 5));
		return groups;
	}

	@Override
	public void updatePersons(List<Person> persons) {
		// TODO Auto-generated method stub
		this.persons.addChoices(persons);
		
	}
	
}
