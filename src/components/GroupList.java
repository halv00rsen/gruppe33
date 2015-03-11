package components;

import java.util.List;

import gui.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GroupList<Value> extends VBox{
	
	public static final int width = 150, height = 200;
	
	private final ListView<Value> list;
	private final ObservableList<Value> items, choices;
	private final Button delete, addButton;
	private final ToggleButton addValue;
	private final ChoiceBox<Value> valueChooser;
	
	public GroupList(String header){
		super(10);
		list = new ListView<Value>();
		items = FXCollections.observableArrayList();
		choices = FXCollections.observableArrayList();
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
				choices.add(selected);
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
		
		valueChooser = new ChoiceBox<Value>();
		valueChooser.setPrefWidth(width);
		valueChooser.setItems(choices);
		
		addButton = new Button("Legg til");
		addButton.setPrefWidth(width);
		
		addButton.setOnAction(new EventHandler<ActionEvent>(){
			
			private Value last = null;
			
			public void handle(ActionEvent event){
				Value selected = valueChooser.getSelectionModel().getSelectedItem();
				if (selected == null || last == selected)
					return;
				items.add(selected);
				choices.remove(selected);
				last = selected;
				
			}
		});
		
		valueChooser.setVisible(false);
		addButton.setVisible(false);
		
		getChildren().addAll(head, list, buttons, valueChooser, addButton);
	}
	
	public void addChoices(List<Value> choices){
		for (Value v : choices){
			this.choices.add(v);
		}
	}
	
	public void clearChoices(){
		choices.clear();
	}
	
	public void removeChoices(List<Value> choices){
		for (Value v : choices)
			this.choices.remove(v);
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
