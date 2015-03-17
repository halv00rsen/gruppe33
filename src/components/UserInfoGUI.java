package components;

import java.util.List;

import classes.Group;
import classes.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import gui.Component;
import gui.Main;

public class UserInfoGUI extends Component{
	ListView<String> grupper = new ListView<String>();
	////EKSEMPEL UNDER
	ObservableList<String> itemsss = FXCollections.observableArrayList(
            "chocolate", "salmon", "gold", "coral", "darkorchid",
            "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
            "blueviolet", "brown");
	private final GridPane pane;
	private final Label title, navnLabel, brukernavnLabel, grupperLabel;
	
	private final Text navn, brukernavn;
	
	
//	private final ObservableList<String> items =FXCollections.observableArrayList ();

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
		grupperLabel = new Label("Grupper synlig:");
		navn = new Text("");
		brukernavn = new Text("");
		pane.add(navn, 1, 0);
		pane.add(brukernavn, 1, 1);
		pane.add(grupper, 0, 3);
		
		pane.add(navnLabel, 0, 0);
		pane.add(brukernavnLabel, 0, 1);
		pane.add(grupperLabel, 0, 2);
		
		grupper.setItems(itemsss);
		
		grupper.setMaxWidth(150);
		grupper.setPrefHeight(75);
		grupper.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		grupper.getSelectionModel().selectAll();
//		grupper.setCellFactory(new Callback<ListView<String>, 
//	            ListCell<String>>() {
//            @Override 
//            public ListCell<String> call(ListView<String> list) {
//                return new ColorRectCell();
//            }
//        }
//    );
		
		VBox mainBox = new VBox(5);
		mainBox.getChildren().addAll(title, pane);
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
