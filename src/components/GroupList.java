package components;

import java.util.List;



import components.GroupGUI.FieldListener;

import classes.Group;
import classes.Person;
import gui.FxUtil;
import gui.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GroupList<Value> extends VBox{
	
	public static final int width = 150, height = 200;
	
	private final ListView<Value> list;
	private final ObservableList<Value> items, valueList;
	private final Button delete, addButton;
	private final ToggleButton addValue;
	private final ComboBox<Value> valueChooser;
	private final FieldListener l;
	
	
	enum Fancy{
		Group, Person;
	}
	
	
	public GroupList(String header, Fancy fancy, FieldListener l){
		super(10);
		this.l = l;
		list = new ListView<Value>();
		items = FXCollections.observableArrayList();
//		choices = FXCollections.observableArrayList();
		list.setPrefHeight(height);
		list.setPrefWidth(width);
		list.setItems(items);
		Label head = new Label(header);
		head.setFont(Main.header1);
		
		HBox buttons = new HBox(10);
		delete = new Button("Fjern");
		delete.setPrefWidth(width / 2 - 5);
		delete.setOnAction(new EventHandler<ActionEvent>(){
			
			public void handle(ActionEvent event){
				Value selected = list.getSelectionModel().getSelectedItem();
				if (selected == null)
					return;
//				choices.add(selected);
				if (fancy == Fancy.Group){
					l.removeGroup((Group) selected);
				}else if (fancy == Fancy.Person){
					l.removeMember((Person) selected);
				}
				valueList.add(selected);
				items.remove(selected);
			}
		});
		addValue = new ToggleButton("Legg til ...");
		addValue.setPrefWidth(width / 2 - 5);
		addValue.setOnAction(new EventHandler<ActionEvent>(){
			
			public void handle(ActionEvent event){
				final boolean pressed = addValue.isSelected();
				valueChooser.setVisible(pressed);
				addButton.setVisible(pressed);
				
			}
		});
		
		buttons.getChildren().addAll(addValue, delete);
		
		valueChooser = new ComboBox<Value>();
		FxUtil.autoCompleteComboBox(valueChooser, FxUtil.AutoCompleteMode.CONTAINING);
		valueList = valueChooser.getItems();
		valueChooser.setPrefWidth(width);
//		valueChooser.setItems(choices);
		
		addButton = new Button("Legg til");
		addButton.setPrefWidth(width);
		
		addButton.setOnAction(new EventHandler<ActionEvent>(){
			
			
			public void handle(ActionEvent event){
//				Value selected = valueChooser.getValue();
				int val = valueChooser.getSelectionModel().getSelectedIndex();
				if (val == -1)
					return;
				Value selected = valueList.remove(val);
				if (selected == null)
					return;
				items.add(selected);
//				valueChooser.getItems().clear();
//				if (valueChooser.getItems().isEmpty()){
//					System.out.println("slettet!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//				}
//				valueChooser.getSelectionModel().
//				valueChooser.getSelectionModel().clearSelection();
//				choices.remove(selected);
				valueChooser.getSelectionModel().clearSelection();
				if (fancy == Fancy.Group){
					l.addGroup((Group) selected);
				}else if (fancy == Fancy.Person){
					l.addMember((Person) selected);
				}
//				valueChooser.getEditor().setText("");
				
				
			}
		});
		
		valueChooser.setVisible(false);
		addButton.setVisible(false);
		
		getChildren().addAll(head, list, buttons, valueChooser, addButton);
	}
	
	public void setValues(List<Value> values){
		for (Value v : items){
			valueList.add(v);
		}
		items.clear();
		for (Value v : values){
			items.add(v);
			valueList.remove(v);
		}
	}
	
	public void addChoices(List<Value> choices){
		for (Value v : choices){
			valueChooser.getItems().add(v);
//			this.choices.add(v);
		}
	}
	
	public void clearChoices(){
		valueChooser.getItems().clear();
//		choices.clear();
	}
	
	public void removeChoices(List<Value> choices){
		for (Value v : choices)
			valueChooser.getItems().remove(v);
//			this.choices.remove(v);
	}
	
	public void addItems(List<Value> items){
		for (Value item : items)
			addItem(item);
	}
	
	public void removeAllItems(){
		items.clear();
	}
	
	public void removeItems(List<Value> items){
		for (Value item: items){
			removeItem(item);
		}
	}
	
	public void removeItem(Value item){
		this.items.remove(item);
	}
	
	public void addItem(Value item){
		this.items.add(item);
	}

}
