package gui;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class Window extends Pane{
	public Window(){
		Main.root.getChildren().add(this);
		init(Main.root);
	}
	/***
	 * fjerner synlighet og fjerner seg selv fra parent
	 ***/
	public void exitThisWindow(){
		Main.root.getChildren().remove(this);
		this.setVisible(false);
	}
	/***
	 * viser synlighet av dette vinduet
	 ***/
	public void showThisWindow(){
		this.setVisible(true);
	}
	/***
	 * fjerner synlighet av dette vinduet
	 ***/
	public void hideThisWindow(){
		this.setVisible(false);
	}
	/***
	 * init(root) m� implementeres av alle vindu go her legges til komponenter som skal v�re med
	 ***/
	public abstract void init(Pane root);
}
