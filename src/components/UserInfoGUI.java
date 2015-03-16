package components;

import java.util.List;

import classes.Group;
import classes.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import gui.Component;
import gui.Main;

public class UserInfoGUI extends Component{
	
	private final GridPane pane;
	private final Label title, navnLabel, brukernavnLabel, grupperLabel;
	
	private final Text navn, brukernavn;
	
	private final ListView<String> grupper = new ListView<String>();
	private final ObservableList<String> items =FXCollections.observableArrayList ();

	public UserInfoGUI(Pane parent) {
		super(parent);
		title = new Label("Brukerinformasjon:");
		title.setFont(Main.header1);
		title.setPadding(new Insets(10, 0, 0, 0));
		pane = new GridPane();
		pane.setHgap(30);
		pane.setVgap(5);
		
		navnLabel = new Label("Navn:");
		brukernavnLabel = new Label("Brukernavn:");
		grupperLabel = new Label("Medlem av grupper:");
		navn = new Text("");
		brukernavn = new Text("");
		pane.add(navn, 1, 0);
		pane.add(brukernavn, 1, 1);
		pane.add(grupper, 1, 2);
		
		pane.add(navnLabel, 0, 0);
		pane.add(brukernavnLabel, 0, 1);
		pane.add(grupperLabel, 0, 2);
		
		grupper.setItems(items);
		grupper.setMaxWidth(150);
		grupper.setPrefHeight(75);
		
		VBox mainBox = new VBox(5);
		mainBox.getChildren().addAll(title, pane);
		this.getChildren().add(mainBox);
	}

	public void changePerson(Person p){
		
		if (p != null){
			System.out.println("p is not null!!");
			System.out.println(p.getName() + "   " + p.getUsername() + "   " + p.getGroups());
			navn.setText(p.getName());
			brukernavn.setText(p.getUsername());
			addListElements(p.getGroups());
		}else
			System.out.println("p is null!!");
	}
	
	private void addListElements(List<Group> groups) {
		items.clear();
		for (Group group : groups) {
			items.add(group.getName());
		}
	}
}
