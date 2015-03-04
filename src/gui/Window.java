package gui;
import components.*;
import windows.*;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class Window extends Pane{
	public final Main main;
	public Window(Main main){
		
		this.main = main;
//		main.root.getChildren().add(main.root);
		this.setPrefHeight(Main.SCREENHEIGHT);
		this.setPrefWidth(Main.SCREENWIDTH);
		init();
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
	public abstract void init();
}
