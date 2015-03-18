package components;

import java.util.List;

import classes.Group;
import classes.Person;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import gui.Component;
import gui.Main;

public class UserInfoGUI extends Component{
	////EKSEMPEL UNDER
	
	private final GridPane pane;
	private final Label title, navnLabel, brukernavnLabel;
	
	private final Text navn, brukernavn;
	
	
//	private final ObservableList<String> items =FXCollections.observableArrayList ();

	public UserInfoGUI(Pane parent) {
		super(parent);
		brukernavnLabel = new Label("Brukernavn:");
		title = new Label("Brukerinformasjon:");
		title.setFont(Main.header1);
		title.setPadding(new Insets(10, 0, 0, 0));
		pane = new GridPane();
		pane.setHgap(0);
		pane.setVgap(0);
		navnLabel = new Label("Navn:");
		navn = new Text("");
		brukernavn = new Text("");
		pane.add(title, 0, 0);
		pane.add(navn, 1, 1);
		pane.add(brukernavn, 1, 2);
		
		pane.add(navnLabel, 0, 1);
		pane.add(brukernavnLabel, 0, 2);
		
		
		VBox mainBox = new VBox(5);
		mainBox.getChildren().addAll(pane);
		this.getChildren().add(mainBox);
	}
	public void changePerson(Person p){
		
		if (p != null){
			navn.setText(p.getFirstname() + p.getLastname());
			brukernavn.setText(p.getUsername());
			addListElements(p.getGroups());
		}else
			System.out.println("p is null!!");
	}
	
	private void addListElements(List<Group> groups) {
//		items.clear();
//		for (Group group : groups) {
//			items.add(group.getName());
//		}
	}
}
