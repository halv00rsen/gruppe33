package gui;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class Window extends Pane{
	public Window(){
		Main.root.getChildren().add(this);
		init(Main.root);
	}
	public void exitThisWindow(){
		Main.root.getChildren().remove(this);
		this.setVisible(false);
	}
	public void showThisWindow(){
		this.setVisible(true);
	}
	public void hideThisWindow(){
		this.setVisible(false);
	}
	/***
	 * init(root) må implementeres av alle vindu go her legges til komponenter som skal være med
	 ***/
	public abstract void init(Pane root);
}
