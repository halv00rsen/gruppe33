package components;

import classes.EventAppliance;
import gui.Component;
import gui.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Applicants extends Component{
	ObservableList<String> items =FXCollections.observableArrayList (
		    "Janne Pettersen\t\t\tKommer ikke", "Kristian Olsen\t\t\tKommer", "Grethe Ingebjørg\t\t\tVenter svar..", "Ole Jensen\t\t\tKommer kanskje...");
	VBox elements;
	Label grupperLabel;
	ListView<String> listView;
	public Applicants(Pane parent){
		super(parent);
		listView = new ListView<String>();
		listView.setItems(items);
		
		
		elements = new VBox(4);
		grupperLabel = new Label();
		grupperLabel.setText("Meldt ankomst");
		grupperLabel.setFont(Main.header1);
//		listView.setItems();
		listView.setPrefSize(300, 80);
		elements.getChildren().addAll(grupperLabel,listView);
		this.getChildren().add(elements);
		
	}
	String stringMaker(EventAppliance e){
		return e.person.getFirstname() + " " + e.person.getLastname() + "\t\t" + e.appliance;
	}

}
