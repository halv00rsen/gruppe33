package gui;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public abstract class Window extends Pane{
	
	protected BorderPane borderPane = new BorderPane();
	public Window(){
//		main.root.getChildren().add(main.root);
		this.setPrefHeight(Main.SCREENHEIGHT);
		this.setPrefWidth(Main.SCREENWIDTH);
		this.getChildren().add(borderPane);
		borderPane.translateXProperty().bind(Main.root.widthProperty().subtract(borderPane.widthProperty()).divide(2));
//		init();

//		this.setStyle("-fx-color-background: #FF0000");
//		this.setStyle("-fx-color-background: #FF0044");
		
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
	 * init(root) må implementeres av alle vindu go her legges til komponenter som skal være med
	 ***/
	public abstract void init();
}
