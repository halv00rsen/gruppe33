package components;

import classes.EventAppliance;
import classes.Person;
import gui.Component;
import gui.Main;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class Applicants extends Component{
	ObservableList<EventAppliance> items =FXCollections.observableArrayList (
			new EventAppliance(new Person("agnar123"," ","Bjarne","Agnar",false),Appliance.Attending),
			new EventAppliance(new Person("peder123"," ","Peder","Pedersen",false),Appliance.Not_Attending),
			new EventAppliance(new Person("stine123"," ","Stine","Stinesen",false),Appliance.Maybe),
			new EventAppliance(new Person("grethe123"," ","Grethe","Grethesen",false),Appliance.Not_Answered),
			new EventAppliance(new Person("synne123"," ","Synne","Synnesen",false),Appliance.Late),
			new EventAppliance(new Person("lars123"," ","Lars","Jakobsen",false),Appliance.Attending)
			);
	VBox elements;
	Label grupperLabel;
	final Label label = new Label();
	ListView<EventAppliance> listView;
	public Applicants(Pane parent){
		super(parent);
		listView = new ListView<EventAppliance>();
		
		elements = new VBox(4);
		grupperLabel = new Label();
		grupperLabel.setText("Meldt ankomst");
		grupperLabel.setFont(Main.header1);
		listView.setPrefSize(250, 80);
		elements.getChildren().addAll(grupperLabel,listView);
		this.getChildren().add(elements);
		
		
		
		

        items.sort(null);
        listView.setItems(items);
        
        listView.setCellFactory(new Callback<ListView<EventAppliance>, ListCell<EventAppliance>>() {
                @Override 
                public ListCell<EventAppliance> call(ListView<EventAppliance> list) {
                    return new ColorRectCell();
                }
            }
        );
 
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
            }
        }
    }
    
	String stringMaker(EventAppliance e){
		return e.person.getFirstname() + " " + e.person.getLastname() + "\t\t" + e.appliance;
	}
	
	
}
