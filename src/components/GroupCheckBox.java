package components;

import gui.Component;
import gui.Main;
import gui.Main.AddGroupListener;
import gui.Main.GetGroupListener;

import java.util.ArrayList;
import java.util.List;

import classes.Group;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GroupCheckBox extends Component implements GetGroupListener{
		VBox box = new VBox(5);
		VBox elements;
		Label grupperLabel;
		ScrollPane scrollPane;
		private final ObservableList<Group> items;
		public GroupCheckBox(Pane parent, AddGroupListener l){
			super(parent);
			scrollPane = new ScrollPane();
			elements = new VBox(4);
			grupperLabel = new Label();
			grupperLabel.setText("Synlig grupper");
			grupperLabel.setFont(Main.header1);
			scrollPane.setContent(box);
			scrollPane.setPrefSize(300, 80);
			items = FXCollections.observableArrayList();
			elements.getChildren().addAll(grupperLabel,scrollPane);
			this.getChildren().add(elements);
			if (l != null)
				l.addListener(this);
			
		}
		ArrayList<Group> activeGroups = new ArrayList<Group>();
		ArrayList<Group> inactiveGroups = new ArrayList<Group>();

		ArrayList<ChangeListener<ArrayList<Group>>> listeners = new ArrayList<ChangeListener<ArrayList<Group>>>();
		public void addGroups(List<Group> items){
			this.items.clear();
			for (Group group : items){
				this.items.add(group);
			}
			for (Group group : items) {
				GroupElement p = new GroupElement(group);
				activeGroups.add(p.getGroup());
				p.getCheckBox().selectedProperty().addListener(new ChangeListener<Boolean>() {
					public void changed(ObservableValue ov,Boolean old_val, Boolean new_val) {
                        if(new_val){
                        	inactiveGroups.remove(p.getGroup());
                        	activeGroups.add(p.getGroup());
                        }else{
                        	inactiveGroups.add(p.getGroup());
                        	activeGroups.remove(p.getGroup());
                        }
                        alertListeners();
					}
				});
				box.getChildren().add(p);
			}
			
		}
		public void alertListeners(){
			for (ChangeListener<ArrayList<Group>> listener : listeners) {
				listener.changed(null, null, activeGroups);
			}
		}
		class GroupElement extends HBox{
			Group group;
			CheckBox check;
			GroupElement(Group group){
				this.group = group;
				check = new CheckBox();

				check.selectedProperty().set(true);
				Label l = new Label();
				l.setText(group.getName());
				this.getChildren().addAll(check,l);
				
			}
			public Group getGroup() {
				return this.group;
			}
			public CheckBox getCheckBox(){
				return check;
			}
		}
		@Override
		public void setGroups(List<Group> groups) {
			// TODO Auto-generated method stub
			activeGroups.clear();
			inactiveGroups.clear();
			box.getChildren().clear();
			addGroups(groups);
		}
	}