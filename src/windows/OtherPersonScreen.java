package windows;

import java.util.ArrayList;
import java.util.List;

import classes.Person;
import classes.PersonCalendar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import components.SchedulingGUI;
import gui.FxUtil;
import gui.GetPersonListener;
import gui.Main.AddGroupListener;
import gui.Main.AddPersonListener;
import gui.Main.ChangeTab;
import gui.Main.SchedulingGuiMethods;
import gui.Window;

public class OtherPersonScreen extends Window implements GetPersonListener{
	VBox mainBox;
	GridPane pane;
	Label chosePersonLabel;
	
	SchedulingGUI schedulingGUI;
	ComboBox<Person> peopleComboBox;
	
	PersonCalendar cal;
	
	private final ChangeTab tab;
	private ObservableList<Person> items;
	SchedulingGuiMethods mainMethods;
	
	public OtherPersonScreen(SchedulingGuiMethods mainMethods) {
		this.mainMethods = mainMethods;
		mainMethods.getAddPersonListener().addListener(this);
		
		this.tab = mainMethods.getChangeTab();
		init();
	}
	
	@Override
	public void init() {
		mainBox = new VBox(10);
		pane = new GridPane();
		pane.setHgap(20);
		
		chosePersonLabel = new Label("Velg person å se kalender til:");
		
		peopleComboBox = new ComboBox<Person>();
		peopleComboBox.setPromptText("Velg person");
		FxUtil.autoCompleteComboBox(peopleComboBox, FxUtil.AutoCompleteMode.CONTAINING);
		items = peopleComboBox.getItems();
		
//		Person examplePerson = (DebugMain.getPerson());
//		cal = new PersonCalendar(examplePerson);
//		schedulingGUI = new SchedulingGUI(this, examplePerson, tab,cal);
		
		Button button = new Button("Vis");
	
		button.setOnAction(e -> {
			int p = peopleComboBox.getSelectionModel().getSelectedIndex();
			if (p == -1)
				return;
	        mainBox.getChildren().remove(schedulingGUI);
	        cal = new PersonCalendar(items.get(p));
	        schedulingGUI = new SchedulingGUI(this,mainMethods,cal);
	        schedulingGUI.changePerson(items.get(p));
	        mainBox.getChildren().add(schedulingGUI);
		});
//		peopleComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {
//
//			public void changed(ObservableValue<? extends Person> observable, Person oldValue, Person newValue) {
//				if (newValue == null)
//					return;
//				System.out.println("Hiodgjioerjgioe");
//		        mainBox.getChildren().remove(schedulingGUI);
//		        cal = new PersonCalendar(newValue);
//		        schedulingGUI = new SchedulingGUI(getThis(), newValue,tab,cal);
//		        mainBox.getChildren().add(schedulingGUI);
//			}
//		});
		cal = new PersonCalendar(null);
		pane.add(chosePersonLabel, 0, 0);
		pane.add(peopleComboBox, 1, 0);
		pane.add(button, 2,0);
		schedulingGUI = new SchedulingGUI(this, mainMethods,  cal);
		
		mainBox.getChildren().addAll(pane, schedulingGUI);
		
//		borderPane.setMargin(inbox, new Insets(20));
		borderPane.setCenter(mainBox);
	}

	private OtherPersonScreen getThis() {
		return this;
	}

	@Override
	public void updatePersons(List<Person> persons) {
		// TODO Auto-generated method stub
		for (Person person : persons) {
			items.add(person);
		}
		peopleComboBox.getSelectionModel().selectFirst();
		int index = peopleComboBox.getSelectionModel().getSelectedIndex();
		if (index == -1)
			return;
		Person p = items.get(index);
		cal = new PersonCalendar(p);
		mainBox.getChildren().remove(schedulingGUI);
		schedulingGUI = new SchedulingGUI(this, mainMethods,cal);
		schedulingGUI.changePerson(p);
		mainBox.getChildren().add(schedulingGUI);
		
	}

	@Override
	public void personAdded(boolean added) {
		// TODO Auto-generated method stub
		
	}
}
