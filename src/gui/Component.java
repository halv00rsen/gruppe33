package gui;
import javafx.scene.layout.Pane;

public abstract class Component extends Pane{
	public Pane parent;

	public Component(Pane parent){
		
		this.parent = parent;
		
	}
	/***
	 * fjerner synlighet og fjerner seg selv fra parent
	 ***/
	public void exitThisComponent(){
		parent.getChildren().remove(this);
		this.setVisible(false);
		
	}
	/***
	 * viser bare synligheten av komponenten
	 ***/
	public void showThisComponent(){
		this.setVisible(true);
	}
	/***
	 * fjerner bare synligheten av komponenten
	 ***/
	public void hideThisComponent(){
		this.setVisible(false);
	}
}


