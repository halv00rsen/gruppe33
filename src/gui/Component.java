package gui;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public abstract class Component extends Pane{
	Pane parent;
	public Component(Pane parent){
		this.parent = parent;
		parent.getChildren().add(this);
		init(parent);
	}
	public void exitThisComponent(){
		parent.getChildren().remove(this);
		this.setVisible(false);
		
	}
	public void showThisComponent(){
		this.setVisible(true);
	}
	public void hideThisComponent(){
		this.setVisible(false);
	}
	/***
	 * init(mother) må implementeres av alle komponenter som skal ha noen spesifikasjoner
	 ***/
	public abstract void init(Node parent);
}


