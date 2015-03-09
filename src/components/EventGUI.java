package components;
import gui.Component;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
public class EventGUI extends Component {
	public EventGUI(Pane parent) {
		super(parent);
		addElements();
        addAction();
		// TODO Auto-generated constructor stub
	}
	AnchorPane root = new AnchorPane();
	Label repeteres_til = new Label();
    Label hver = new Label();
    Label dag = new Label();
	Button save = new Button();
	Button cancel = new Button();
	Button trash = new Button();
	TextField formalText = new TextField();
	TextField romText = new TextField();
	TextField freqText = new TextField();
	DatePicker datePicker = new DatePicker();
	DatePicker datePicker2 = new DatePicker();
	TextField fraKlokkenText = new TextField();
	TextField tilKlokkenText = new TextField();
	ChoiceBox split = new ChoiceBox();
	int freq = 0;

    private void addAction() {
    	split.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
    	      @Override
    	      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
    	    	  repeteresOn(split.getItems().get((Integer) number2));
    	      }
    	    });
    	cancel.setOnAction(e -> close(e));
    	trash.setOnAction(e -> trash(e));
    	save.setOnAction(e -> save(e));
    	fraKlokkenText.setOnAction(e -> clockValidate(e));
    	tilKlokkenText.setOnAction(e -> clockValidate(e));
    	freqText.setOnAction(e -> numberValidate(e));
    	
		
	}
    private void numberValidate(ActionEvent e) {
    	String t = ((TextField) e.getSource()).getText();
    	try{
        	int a = Integer.parseInt(t);
        	freq = a;
    	}catch(NumberFormatException error){
    		((TextField) e.getSource()).clear();
    	}
	}
	private void clockValidate(ActionEvent e) {
    	String t = ((TextField) e.getSource()).getText();
    	if (t.length() >5){
    		((TextField) e.getSource()).clear();
    	}
    	try{
    		int a = Integer.parseInt(t.substring(0,2));
    		int b = Integer.parseInt(t.substring(3,5));
    		System.out.println(a + "" + b);
    		if(a>=24 || a<=-1){
    			((TextField) e.getSource()).clear();
    			return;
    		}
    		if(b>=60 || b<=-1){
    			((TextField) e.getSource()).clear();
    			return;
    		}
    		
    	}catch(java.lang.StringIndexOutOfBoundsException error){
    		if(t.length() == 2){
    			int a = Integer.parseInt(t.substring(0,2));
    			if(!(a>=24 || a<=-1)){
    				((TextField) e.getSource()).clear();
    				((TextField) e.getSource()).setText(a+"-"+"00");
    			}else{
    				((TextField) e.getSource()).clear();
        			return;
    			}
    		}else{
    			((TextField) e.getSource()).clear();
    			return;
    		}
    	}catch(NumberFormatException error1){
    		((TextField) e.getSource()).clear();
    		return;
    	}
	}
	private void save(ActionEvent e) {
    	System.out.println(formalText.getText());
    	System.out.println(romText.getText());
    	System.out.println(datePicker.getValue());
    	System.out.println(fraKlokkenText.getText());
    	System.out.println(tilKlokkenText.getText());
    	System.out.println(datePicker2.getValue());
    	System.out.println("freq " + freq);
//    	PrintWriter writer;
    	
//		try {
//			writer = new PrintWriter("tid.txt", "UTF-8");
//			writer.println(formalText.getUserData());
//	    	writer.println("The second line");
//	    	writer.close();
//		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
    	
	}
	private void trash(ActionEvent e) {
    	formalText.clear();
    	romText.clear();
    	datePicker.setValue(null);
    	datePicker2.setValue(null);
    	fraKlokkenText.clear();
    	tilKlokkenText.clear();
    	split.getSelectionModel().selectFirst();  
	}
	private void close(ActionEvent e) {
    	Platform.setImplicitExit(true);
    	Platform.exit();
	}
	public void repeteresOn(Object object){
    	if(! object.equals("Aldri")){
    		freq = 0;
    		datePicker2.setDisable(false);
    		repeteres_til.setTextFill(Color.web("#000000"));
    		
    	}else{
    		datePicker2.setDisable(true);
    		repeteres_til.setTextFill(Color.web("#AAAAAA"));
    	}
    	if( object.equals("Månedlig")){
    		freq = 30;
    	}else if( object.equals("Ukentlig")){
    		freq = 7;
    	}
    	if( object.equals("Egendefinert")){
    		hver.setTextFill(Color.web("#000000"));
    		dag.setTextFill(Color.web("#000000"));
    		freqText.setDisable(false);
    	}else{
    		hver.setTextFill(Color.web("#AAAAAA"));
    		dag.setTextFill(Color.web("#AAAAAA"));
    		freqText.setDisable(true);
    	}
    }
	public void addElements(){
    	root.setStyle("-fx-background-color: #FFF");

        //formaal
        Label formaal = new Label();
        formaal.setText("Formål");
        VBox formalBox = new VBox(5);
        formalBox.getChildren().add(formaal);
        formalBox.getChildren().add(formalText);
        formalText.setPrefWidth(275);
//        setPos(formalBox,60,60);
//        root.getChildren().add(formalBox);
        
        //rom
        Label rom = new Label();
        rom.setText("Rom\t\t\t");
        HBox romBox = new HBox(20);
        romBox.getChildren().add(rom);
        romBox.getChildren().add(romText);
        romText.setPrefWidth(225);
//        setPos(romBox,60,150);
//        root.getChildren().add(romBox);
        
        //dato
        Label date = new Label();
        date.setText("Dato\t\t\t");
        HBox dateBox = new HBox(20);
        dateBox.getChildren().add(date);
        dateBox.getChildren().add(datePicker);
        datePicker.setPrefWidth(225);
//        setPos(dateBox,60,200);
//        root.getChildren().add(dateBox);
        
      //klokken
        Label fra_klokken = new Label();
        Label til = new Label();
        fra_klokken.setText("Fra klokken\t");
        til.setText("til");
        HBox klokkeBox = new HBox(20);
        klokkeBox.getChildren().add(fra_klokken);
        klokkeBox.getChildren().add(fraKlokkenText);
        klokkeBox.getChildren().add(til);
        klokkeBox.getChildren().add(tilKlokkenText);
        fraKlokkenText.setPrefWidth(60);
        tilKlokkenText.setPrefWidth(60);
//        setPos(klokkeBox,60,250);
//        root.getChildren().add(klokkeBox);
        
        //repeteres
        Label repeteres = new Label();
        repeteres.setText("Repeteres\t");
        HBox repeteresBox = new HBox(20);
        split.setPrefWidth(100);
        repeteresBox.getChildren().add(repeteres);
        repeteresBox.getChildren().add(split);
        split.getItems().addAll("Aldri","Ukentlig","Månedlig","Egendefinert");
        split.getSelectionModel().selectFirst();        
//        setPos(repeteresBox,60,300);
//        root.getChildren().add(repeteresBox);
        
      //repeteresTil
        repeteres_til.setText("Repeteres til\t");
		repeteres_til.setTextFill(Color.web("#AAAAAA"));
        HBox repeteresTilBox = new HBox(20);
        repeteresTilBox.getChildren().add(repeteres_til);
        repeteresTilBox.getChildren().add(datePicker2);

		datePicker2.setDisable(true);
        datePicker2.setPrefWidth(225);
//        setPos(repeteresTilBox,60,350);
//        root.getChildren().add(repeteresTilBox);
        
        //hver

        hver.setText("hver");
        dag.setText("dag");
		hver.setTextFill(Color.web("#AAAAAA"));
		dag.setTextFill(Color.web("#AAAAAA"));
		freqText.setPrefWidth(30);
        repeteresBox.getChildren().add(hver);
        repeteresBox.getChildren().add(freqText);
        repeteresBox.getChildren().add(dag);
		freqText.setDisable(true);
        
        //buttons
        cancel.setText("Avbryt");
        save.setText("Lagre endringer");
        trash.setText("Forkast");
        cancel.setCancelButton(true);
        buttonStyle(cancel,save,trash);
        HBox buttons = new HBox(20);
        buttons.getChildren().add(trash);
        buttons.getChildren().add(cancel);
        buttons.getChildren().add(save);
//        setPos(buttons,60,400);
//        root.getChildren().add(buttons);
        
        //set alle
        VBox rootBox = new VBox(30);
        rootBox.getChildren().add(formalBox);
        rootBox.getChildren().add(romBox);
        rootBox.getChildren().add(dateBox);
        rootBox.getChildren().add(klokkeBox);
        rootBox.getChildren().add(repeteresBox);
        rootBox.getChildren().add(repeteresTilBox);
        rootBox.getChildren().add(buttons);
        setPos(rootBox,50,30);
        root.getChildren().add(rootBox);
        
        
    }
    
    public void addTextToScene(Label... args ){
    	for(Label i : args){
    		i.setFont(Font.font("Verdana", 15));
    	}
    		
    }
    public void buttonStyle(Button... args ){
    	for(Button i : args){
    		i.setStyle("-fx-padding: 15px");
    	}
    		
    }
    public void setPos(Node a, double x, double y){
    	a.setLayoutX(x);
    	a.setLayoutY(y);
    	
    }

   
}
