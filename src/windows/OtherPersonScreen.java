package windows;

import java.util.ArrayList;

import classes.Person;
import classes.PersonCalendar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import components.SchedulingGUI;
import gui.DebugMain;
import gui.Window;

public class OtherPersonScreen extends Window{
	VBox mainBox;
	GridPane pane;
	Label chosePersonLabel;
	
	SchedulingGUI schedulingGUI;
	ComboBox<String> peopleComboBox;
	
	PersonCalendar cal;
	ArrayList<Person> persons;
	
	
	public OtherPersonScreen(ArrayList<Person> persons) {
		this.persons = persons;
		init();
	}
	
	@Override
	public void init() {
		mainBox = new VBox(10);
		pane = new GridPane();
		pane.setHgap(20);
		
		chosePersonLabel = new Label("Velg person å se kalender til:");
		
		peopleComboBox = new ComboBox<String>();
		peopleComboBox.setPromptText("Velg person");
		peopleComboBox.setEditable(false); //Om man skal kunne skrive selv
		addPeople();
		
		Person examplePerson = (DebugMain.getPerson());
		cal = new PersonCalendar(examplePerson);
		schedulingGUI = new SchedulingGUI(this, examplePerson,cal);
		
	
		peopleComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        mainBox.getChildren().remove(schedulingGUI);
		        Person person = findPerson(newValue);
		        cal = new PersonCalendar(person);
		        schedulingGUI = new SchedulingGUI(getThis(), cal, person);
		        mainBox.getChildren().add(schedulingGUI);
			}
		});
		
		pane.add(chosePersonLabel, 0, 0);
		pane.add(peopleComboBox, 1, 0);
		
		
		mainBox.getChildren().addAll(pane, schedulingGUI);
		
		this.getChildren().add(mainBox);
	}

	private void addPeople() {
		for (Person person : persons) {
			String name = person.getName();
			peopleComboBox.getItems().add(name);
		}
	}
	
	private Person findPerson(String name) {
		for (Person person : persons) {
			if (name.equals(person.getName())) {
				return person;
			}
		}
		return null;
	}
	
	private OtherPersonScreen getThis() {
		return this;
	}
}
