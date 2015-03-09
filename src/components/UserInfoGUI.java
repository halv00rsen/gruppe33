package components;

import java.util.List;

import classes.Group;
import classes.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import gui.Component;
import gui.Main;

public class UserInfoGUI extends Component{
	
	GridPane pane;
	Label header;
	Label navnLabel;
	Label brukernavnLabel;
	Label grupperLabel;
	
	Text navn;
	Text brukernavn;
	
	ListView<String> grupper = new ListView<String>();
	ObservableList<String> items =FXCollections.observableArrayList ();

	public UserInfoGUI(Pane parent, Main main, Person person) {
		super(parent, main);
		init(person);
	}

	private void init(Person person) {
		header = new Label("Brukerinformasjon:");
		header.setFont(Main.header1);
		
		pane = new GridPane();
		pane.setHgap(30);
		pane.setVgap(5);
		
		navnLabel = new Label("Navn:");
		brukernavnLabel = new Label("Brukernavn:");
		grupperLabel = new Label("Medlem av grupper:");
		
		navn = new Text(person.getName());
		brukernavn = new Text(person.getUsername());
		addListElements(person.getGroups());
		
		pane.add(navnLabel, 0, 0);
		pane.add(brukernavnLabel, 0, 1);
		pane.add(grupperLabel, 0, 2);
		
		pane.add(navn, 1, 0);
		pane.add(brukernavn, 1, 1);
		pane.add(grupper, 1, 2);
		
		VBox mainBox = new VBox(5);
		mainBox.getChildren().addAll(header, pane);
		this.getChildren().add(mainBox);
		
	}
	
	private void addListElements(List<Group> groups) {
		for (Group group : groups) {
			items.add(group.getName());
		}
		grupper.setItems(items);
		grupper.setMaxWidth(150);
		grupper.setPrefHeight(75);
		
	}

}
