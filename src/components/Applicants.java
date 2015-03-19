package components;

import java.time.LocalDate;
import java.util.ArrayList;

import components.CalendarGUI.CalendarGUIListener;
import classes.Event;
import classes.EventAppliance;
import classes.Person;
import gui.Component;
import gui.Main;
import gui.Main.UpdateAppliance;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import classes.Appliance;
public class Applicants extends Component implements CalendarGUIListener{
	ObservableList<EventAppliance> items = FXCollections.observableArrayList ();
	VBox elements;
	Label mainLabel;
	final Label label = new Label();
	ListView<EventAppliance> listView;
	Button btn1 = new Button("Meld ankomst");
	ChoiceBox<Appliance> choiceBox = new ChoiceBox<Appliance>();
	ObservableList<Appliance> listAppliance = FXCollections.observableArrayList(Appliance.values());
	private Person currentUser;
	private UpdateAppliance updateAppliance;
	Event currentEvent = null;
	public Applicants(Pane parent){
		super(parent);
		listView = new ListView<EventAppliance>();
//		items.addAll(
//				new EventAppliance(new Person("agnar123"," ","Bjarne","Agnar",false),Appliance.Attending),
//				new EventAppliance(new Person("peder123"," ","Peder","Pedersen",false),Appliance.Not_Attending),
//				new EventAppliance(new Person("stine123"," ","Stine","Stinesen",false),Appliance.Maybe),
//				new EventAppliance(new Person("grethe123"," ","Grethe","Grethesen",false),Appliance.Not_Answered),
//				new EventAppliance(new Person("synne123"," ","Synne","Synnesen",false),Appliance.Late),
//				new EventAppliance(new Person("lars123"," ","Lars","Jakobsen",false),Appliance.Attending)
//				);
		elements = new VBox(4);
		mainLabel = new Label();
		mainLabel.setText("Meldte ankomster");
		mainLabel.setFont(Main.header1);
		listView.setPrefSize(250, 80);
		listView.setMaxSize(300, 80);
        
		choiceBox.setItems(listAppliance);
		Label l = new Label("Din melding:");
		HBox hbox = new HBox(10);
		hbox.getChildren().addAll(l,choiceBox,btn1);
		elements.getChildren().addAll(mainLabel,listView,hbox);
		this.getChildren().addAll(elements);
		btn1.setOnAction(e -> btnCalled(e));
		
		
		

        items.sort(null);
        listView.setItems(items);
        listView.setCellFactory(new Callback<ListView<EventAppliance>, ListCell<EventAppliance>>() {
                @Override 
                public ListCell<EventAppliance> call(ListView<EventAppliance> list) {
                    return new ColorRectCell();
                }
            }
        );
        this.elements.setDisable(true);
        listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        listView.getSelectionModel().select(-1);

                    }
                });

            }
        });
    }
    
    private void btnCalled(ActionEvent e) {
    	if(currentEvent != null){
    		updateAppliance.update(currentEvent, new EventAppliance(currentUser, choiceBox.getSelectionModel().getSelectedItem()));
    		for (EventAppliance c : items){
    			if(c.getPerson().getUsername().equals(currentUser.username)){
    				c.setAppliance(choiceBox.getSelectionModel().getSelectedItem());
    				redraw();
    			}
    		}
    	}
    	
	}

	private void redraw() {
		for (int i = 0; i < items.size(); i++) {
			items.set(i, items.get(i));
		}
		
	}

	static class ColorRectCell extends ListCell<EventAppliance> {
        @Override
        public void updateItem(EventAppliance item, boolean empty) {
            super.updateItem(item, empty);

        	
            if (item != null) {
            	GridPane p = new GridPane();
            	Label label = new Label(item.getPerson().getFirstname() +  " " + item.getPerson().getLastname());
            	Label label2 = new Label(item.getAppliance().toString());
            	Circle circle = new Circle();
            	circle.setRadius(5);
            	circle.setFill(Color.web(item.getAppliance().getColor()));
            	ColumnConstraints cc = new ColumnConstraints();
            	ColumnConstraints c1 = new ColumnConstraints();
            	c1.setMaxWidth(15);
            	c1.setMinWidth(15);
            	cc.setMaxWidth(100);
            	cc.setMinWidth(100);
            	p.getColumnConstraints().addAll(c1,cc);
            	p.add(label, 1,0);

            	p.add(circle, 0,0);
            	p.add(label2, 2,0);

                
                setGraphic(p);
            }else{
            	setGraphic(null);
            }
        }
    }

	@Override
	public void dayIsHighligthed(LocalDate date, ArrayList<Event> events) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventIsHighligthed(Event event) {
		items.clear();
		if(event == null){

			this.elements.setDisable(true);
			currentEvent = null;
			return;
		}else{
			this.elements.setDisable(false);
			currentEvent = event;
			EventAppliance me = null;
			for (EventAppliance e : event.getAppliance()){
				if(e.getPerson().getUsername().equals(currentUser.username)){
					me = e;
					choiceBox.getSelectionModel().select(me.getAppliance());
				}
				items.add(e);
			}
			items.remove(me);
			items.sort(null);
			items.add(0, me);
		}
		
	}

	public void changePerson(Person p) {
		this.currentUser = p;
		
	}

	public void setEventApplianceCaller(UpdateAppliance updateAppliance) {
		this.updateAppliance = updateAppliance;
		
	}
	public void updateAppliance(Event event, EventAppliance eventAppliance){
		updateAppliance.update(event, eventAppliance);
	}
    
	
	
}
