package components;

import java.util.ArrayList;

import classes.Group;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GroupCheckBox extends ScrollPane{
		VBox box = new VBox(5);
		ObservableList<Group> items;
		public GroupCheckBox(){
			this.setContent(box);
			this.setPrefSize(170, 80);
			
		}
		ArrayList<Group> activeGroups = new ArrayList<Group>();
		ArrayList<Group> inactiveGroups = new ArrayList<Group>();

		ArrayList<ChangeListener<ArrayList<Group>>> listeners = new ArrayList<ChangeListener<ArrayList<Group>>>();
		public void addGroups(ObservableList<Group> items){
			this.items = items;
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
		public void updateGroups(ObservableList<Group> items){
			activeGroups.clear();
			inactiveGroups.clear();
			box.getChildren().clear();
			addGroups(items);
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
	}