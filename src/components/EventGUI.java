package components;
import gui.AutoCompleteComboBoxListener;
import gui.Component;
import gui.FxUtil;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import classes.Priority;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
public class EventGUI extends Component {
	Label repeatTo = new Label();
    Label every = new Label();
    Label day = new Label();

	Button avaibleRoomsBtn = new Button();
	Button save = new Button();
	Button cancel = new Button();
	Button trash = new Button();
	TextField purposeText = new TextField();
	TextField romText = new TextField();
	TextField freqText = new TextField();
	BorderPane pane = new BorderPane();
	TextArea infoText = new TextArea();
	DatePicker datePicker = new DatePicker();
	DatePicker datePicker2 = new DatePicker();
	TextField fromClockText = new TextField();
	TextField toClockText = new TextField();
	ComboBox<String> split = new ComboBox<String>();
	int freq = 0;
	ListView invited = new ListView();
	
	private Priority priority;
	
	public EventGUI(Pane parent) {
		super(parent);
		addElements();
        addAction();
        this.getChildren().add(pane);
		// TODO Auto-generated constructor stub
	}
	
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
    	fromClockText.setOnAction(e -> clockValidate(e));
    	toClockText.setOnAction(e -> clockValidate(e));
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
    	System.out.println(purposeText.getText());
    	System.out.println(romText.getText());
    	System.out.println(datePicker.getValue());
    	System.out.println(fromClockText.getText());
    	System.out.println(toClockText.getText());
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
    	purposeText.clear();
    	romText.clear();
    	datePicker.setValue(null);
    	datePicker2.setValue(null);
    	fromClockText.clear();
    	toClockText.clear();
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
    		repeatTo.setTextFill(Color.web("#000000"));
    		
    	}else{
    		datePicker2.setDisable(true);
    		repeatTo.setTextFill(Color.web("#AAAAAA"));
    	}
    	if( object.equals("Månedlig")){
    		freq = 30;
    	}else if( object.equals("Ukentlig")){
    		freq = 7;
    	}
    	if( object.equals("Egendefinert")){
    		every.setTextFill(Color.web("#000000"));
    		day.setTextFill(Color.web("#000000"));
    		freqText.setDisable(false);
    	}else{
    		every.setTextFill(Color.web("#AAAAAA"));
    		day.setTextFill(Color.web("#AAAAAA"));
    		freqText.setDisable(true);
    	}
    }
	public void addElements(){
    	pane.setStyle("-fx-background-color: #FFF");

        //formaal
        Label formaal = new Label();
        formaal.setText("Formål");
        VBox formalBox = new VBox(5);
        formalBox.getChildren().add(formaal);
        formalBox.getChildren().add(purposeText);
        purposeText.setPrefWidth(275);
        
      //info
        Label info = new Label();
        info.setText("Informasjon\t");
        HBox infoBox = new HBox(20);
        infoBox.getChildren().add(info);
        infoBox.getChildren().add(infoText);
        infoText.setPrefWidth(335);
        infoText.setPrefHeight(120);
        
        //rom
        Label rom = new Label();
        avaibleRoomsBtn.setText("Se ledige rom");
        rom.setText("Rom\t\t\t");
        HBox romBox = new HBox(20);
        romBox.getChildren().add(rom);
        romBox.getChildren().add(romText);
        romBox.getChildren().add(avaibleRoomsBtn);
        romText.setPrefWidth(225);
       
        
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
        klokkeBox.getChildren().add(fromClockText);
        klokkeBox.getChildren().add(til);
        klokkeBox.getChildren().add(toClockText);
        fromClockText.setPrefWidth(60);
        toClockText.setPrefWidth(60);
        
        //repeteres
        Label repeteres = new Label();
        repeteres.setText("Repeteres\t");
        HBox repeteresBox = new HBox(20);
       
//        split.setEditable(true);
        split.setPrefWidth(100);
        repeteresBox.getChildren().add(repeteres);
        repeteresBox.getChildren().add(split);
        split.getItems().addAll("Aldri","Aldrg","Ukentlig","Månedlig","Egendefinert");
        split.getSelectionModel().selectFirst(); 
        FxUtil.autoCompleteComboBox(split, FxUtil.AutoCompleteMode.CONTAINING);    
//        split.show();
//        AutoCompleteComboBoxListener<String> hei = new AutoCompleteComboBoxListener<String>(split);
  
        
        
        //repeteresTil
        repeatTo.setText("Repeteres til\t");
		repeatTo.setTextFill(Color.web("#AAAAAA"));
        HBox repeteresTilBox = new HBox(20);
        repeteresTilBox.getChildren().add(repeatTo);
        repeteresTilBox.getChildren().add(datePicker2);

		datePicker2.setDisable(true);
        datePicker2.setPrefWidth(225);
        
        //hver

        every.setText("hver");
        day.setText("dag");
		every.setTextFill(Color.web("#AAAAAA"));
		day.setTextFill(Color.web("#AAAAAA"));
		freqText.setPrefWidth(30);
        repeteresBox.getChildren().add(every);
        repeteresBox.getChildren().add(freqText);
        repeteresBox.getChildren().add(day);
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
        
        //ListView
        Pane blueBox = new Pane();
        BorderPane.setMargin(blueBox,new Insets(20));
        VBox listBox = new VBox(20);
        listBox.getChildren().add(invited);
        listBox.setPadding(new Insets(30));
        blueBox.setStyle("-fx-background-color: #AAF");
        blueBox.setPrefHeight(500);
        blueBox.setPrefWidth(300);
        blueBox.getChildren().add(listBox);
        
      //Priority
        Label prio = new Label();
        prio.setText("Prioritet:\t");
        HBox priorityList = new HBox(5);
        priority = null;
        Priority[] pList = new Priority[3];
        pList[0] = Priority.NOT_IMPORTANT;
        pList[0].turnOn();
        pList[1] = Priority.IMPORTANT;
        pList[2] = Priority.VERY_IMPORTANT;

        priorityList.getChildren().add(prio);
        priorityList.getChildren().add(pList[0].getVisualization());
        priorityList.getChildren().add(pList[1].getVisualization());
        priorityList.getChildren().add(pList[2].getVisualization());
        priorityList.setAlignment(Pos.BASELINE_CENTER);
        listBox.getChildren().add(priorityList);
        
        EventHandler<Event> event = new EventHandler<Event>(){

			@Override
			public void handle(Event event) {
				for (Priority p : pList){
					if (p.getVisualization() == event.getSource()){
						if (p.isActive())
							p.turnOff();
						else
							p.turnOn();
					}else
						p.turnOff();
				}
			}
        	
        };
        for (Priority p : pList)
        	p.getVisualization().setOnMouseClicked(event);
        
        //set alle
        VBox rootBox = new VBox(30);
        BorderPane.setMargin(rootBox,new Insets(20));
        rootBox.getChildren().add(formalBox);
        rootBox.getChildren().add(infoBox);
        rootBox.getChildren().add(romBox);
        rootBox.getChildren().add(dateBox);
        rootBox.getChildren().add(klokkeBox);
        rootBox.getChildren().add(repeteresBox);
        rootBox.getChildren().add(repeteresTilBox);
        setPos(rootBox,50,30);
        pane.setLeft(rootBox);
        pane.setRight(blueBox);
        buttons.setAlignment(Pos.BASELINE_CENTER);
        pane.setBottom(buttons);
        
        
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
