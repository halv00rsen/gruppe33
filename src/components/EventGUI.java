package components;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import gui.Component;
import gui.DebugMain;
import gui.FxUtil;
import gui.Main.AddNewEvent;
import gui.Main.ChangeTab;
import classes.Appliance;
import classes.EventAppliance;
import classes.Person;
import classes.Priority;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class EventGUI extends Component{
	
	private final TimeField start, end;
	private final NumberField freqText;
	private final AddNewEvent eventCall;
	private final ChangeTab changeTab;
	
	Label repeatTo = new Label();
    Label every = new Label();
    Label day = new Label();

	Button avaibleRoomsBtn = new Button();
	Button save = new Button();
	Button cancel = new Button();
	Button trash = new Button();
	TextField purposeText = new TextField();
	TextField romText = new TextField();
//	TextField freqText = new TextField();
	BorderPane pane = new BorderPane();
	TextArea infoText = new TextArea();
	DatePicker startDate = new DatePicker(), endDate = new DatePicker(),
			repDate = new DatePicker();
//	TextField fromClockText = new TextField();
//	TextField toClockText = new TextField();
	ComboBox<Picker> split = new ComboBox<Picker>();
	int freq = 0;
	
	private Priority priority;
	private ComboBox<Person> searchPeople;
	private ObservableList<Person> comboPeople, listPeople;
	private ListView<Person> invited = new ListView<Person>();
	
	private boolean state = false;
	
	public EventGUI(Pane parent, AddNewEvent eventCall, ChangeTab changeTab) {
		super(parent);
		this.eventCall = eventCall;
		this.changeTab = changeTab;
		start = new TimeField(true);
		end = new TimeField(false);
		freqText = new NumberField();
		startDate.setOnAction(new EventHandler<ActionEvent>(){
			
			public void handle(ActionEvent event){
				if (startDate.getValue() == null)
					return;
				if (startDate.getValue().isBefore(LocalDate.now()))
					startDate.setValue(LocalDate.now());
				if (endDate.getValue() != null){
					if (startDate.getValue().isAfter(endDate.getValue())){
						endDate.setValue(startDate.getValue());
					}
				}else
					endDate.setValue(startDate.getValue());
			}
		});
		endDate.setOnAction(new EventHandler<ActionEvent>(){
			
			public void handle(ActionEvent e){
				if (startDate.getValue() == null || endDate.getValue() == null)
					return;
				if (startDate.getValue().isAfter(endDate.getValue()))
					endDate.setValue(startDate.getValue());
			}
		});
		start.setOtherTime(end);
		end.setOtherTime(start);
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
    	trash.setOnAction(e -> trash());
    	save.setOnAction(e -> save(e));
	}
    
    
	private void save(ActionEvent e) {
		if (!start.isCorrectInput() || !end.isCorrectInput())
    		return;
    	LocalDate start = startDate.getValue(), end = endDate.getValue();
    	if (start == null || end == null)
    		return;
    	else if (end.isBefore(start))
    		return;
    	
    	int sh = this.start.getHour(), sm = this.start.getMinutes(),
    			eh = this.end.getHour(), em = this.end.getMinutes();
    	if (sh == -1 || sm == -1 || eh == -1 || em == -1)
    		return;
    	
    	LocalDateTime t1 = LocalDateTime.of(start, LocalTime.of(sh, sm)),
    			t2 = LocalDateTime.of(end, LocalTime.of(eh, em));
    	classes.Event event = new classes.Event(t1, t2, null);
    	event.setEventName(purposeText.getText());
    	Picker splitStuff = split.getValue();
    	LocalDate freqEnd = repDate.getValue();
    	if (freqEnd == null)
    		return;
    	if (splitStuff == Picker.Daglig || splitStuff == Picker.Ukentlig)
    		event.setFreq(splitStuff.freq, false, freqEnd);
    	else if (splitStuff == Picker.Aldri)
    		event.setFreq(0, false, freqEnd);
    	else if (splitStuff == Picker.Egendefinert){
    		int ting = freqText.getNumDays();
    		if (ting == -1)
    			return;
    		event.setFreq(ting, false, freqEnd);
    	}else
    		event.setFreq(0, true, freqEnd);
    	List<EventAppliance> persons = new ArrayList<EventAppliance>();
    	
    	for (Person p : listPeople){
    		persons.add(new EventAppliance(p, Appliance.Not_Answered));
    	}
    	event.addAppliance(persons);
    	event.setInfo(infoText.getText());
    	
    	eventCall.addEvent(event);
    	trash();
    	changeTab.goToHomeScreen();
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
	private void trash() {
    	purposeText.clear();
    	romText.clear();
    	startDate.setValue(null);
    	endDate.setValue(null);
    	repDate.setValue(null);
    	start.clear();
    	end.clear();
    	split.getSelectionModel().selectFirst();
    	for (Person p : listPeople){
    		comboPeople.add(p);
    	}
    	listPeople.clear();
    	infoText.clear();
    	freqText.clear();
    	state = false;
	}
	
	private void close(ActionEvent e) {
//    	Platform.setImplicitExit(true);
//    	Platform.exit();
	}
	
	public void repeteresOn(Object object){
    	if(! object.equals("Aldri")){
    		freq = 0;
    		repDate.setDisable(false);
    		repeatTo.setTextFill(Color.web("#000000"));
    		
    	}else{
    		repDate.setDisable(true);
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
        HBox heisann = new HBox(5);
        heisann.getChildren().addAll(startDate, endDate);
        dateBox.getChildren().add(heisann);
//        datePicker.setPrefWidth(225);
//        setPos(dateBox,60,200);
//        root.getChildren().add(dateBox);
        
      //klokken
        Label fra_klokken = new Label();
        Label til = new Label();
        fra_klokken.setText("Fra klokken\t");
        til.setText("til");
        HBox klokkeBox = new HBox(20);
        klokkeBox.getChildren().add(fra_klokken);
        klokkeBox.getChildren().add(start);
        klokkeBox.getChildren().add(til);
        klokkeBox.getChildren().add(end);
        start.setPrefWidth(60);
        end.setPrefWidth(60);
        
        //repeteres
        Label repeteres = new Label();
        repeteres.setText("Repeteres\t");
        HBox repeteresBox = new HBox(20);
       
//        split.setEditable(true);
        split.setPrefWidth(100);
        repeteresBox.getChildren().add(repeteres);
        repeteresBox.getChildren().add(split);
        split.getItems().addAll(Picker.Aldri, Picker.Daglig, Picker.Ukentlig, Picker.Månedlig, Picker.Egendefinert);
        split.getSelectionModel().selectFirst(); 
   
//        split.show();
  
        
        
        //repeteresTil
        repeatTo.setText("Repeteres til\t");
		repeatTo.setTextFill(Color.web("#AAAAAA"));
        HBox repeteresTilBox = new HBox(20);
        repeteresTilBox.getChildren().add(repeatTo);
        repeteresTilBox.getChildren().add(repDate);

		repDate.setDisable(true);
        repDate.setPrefWidth(225);
        
        //hver

        every.setText("hver");
        day.setText("dag");
		every.setTextFill(Color.web("#AAAAAA"));
		day.setTextFill(Color.web("#AAAAAA"));
		freqText.setPrefWidth(50);
        repeteresBox.getChildren().add(every);
        repeteresBox.getChildren().add(freqText);
        repeteresBox.getChildren().add(day);
		freqText.setDisable(true);
        
        //buttons
        cancel.setText("Avbryt");
        save.setText("Lag event");
        trash.setText("Forkast");
        cancel.setCancelButton(true);
        buttonStyle(cancel,save,trash);
        HBox buttons = new HBox(20);
        buttons.getChildren().add(trash);
        buttons.getChildren().add(cancel);
        buttons.getChildren().add(save);
        

        searchPeople = new ComboBox<Person>();
        searchPeople.setPrefWidth(200);
        FxUtil.autoCompleteComboBox(searchPeople, FxUtil.AutoCompleteMode.CONTAINING); 
        //ListView
        comboPeople = searchPeople.getItems();
        listPeople = FXCollections.observableArrayList();
        invited.setItems(listPeople);
        Button addPerson = new Button("Legg til"), removeButton = new Button("Fjern");
        for (Person p : DebugMain.getPeople())
        	comboPeople.add(p);
        addPerson.setOnAction(new EventHandler<ActionEvent>(){
        	
        	public void handle(ActionEvent e){
        		int val = searchPeople.getSelectionModel().getSelectedIndex();
				if (val == -1)
					return;
				Person selected = comboPeople.remove(val);
				if (selected == null)
					return;
				listPeople.add(selected);
				searchPeople.getSelectionModel().clearSelection();
        	}
        });
        
        removeButton.setOnAction(new EventHandler<ActionEvent>(){
        	
        	public void handle(ActionEvent e){
        		Person selected = invited.getSelectionModel().getSelectedItem();
				if (selected == null)
					return;
//				choices.add(selected);
				comboPeople.add(selected);
				listPeople.remove(selected);
        	}
        });
        
        HBox buttonsBox = new HBox(5);
        buttonsBox.getChildren().addAll(searchPeople, addPerson);
        
        Pane blueBox = new Pane();
        BorderPane.setMargin(blueBox,new Insets(20));
        VBox listBox = new VBox(20);
        listBox.getChildren().add(buttonsBox);
        listBox.getChildren().addAll(invited, removeButton);
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
						if (p.isActive()){
							p.turnOff();
							priority = null;
						}
						else{
							p.turnOn();
							priority = p;
						}
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
	
	private enum Picker{
		Ukentlig(7), Daglig(1), Månedlig(-1), Aldri(0), Egendefinert(-1);
		
		private final int freq;
		
		Picker(int freq){
			this.freq = freq;
		}
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

	public void showEvent(classes.Event event) {
		trash();
		start.setTime(event.getStartTime().getHour(), event.getStartTime().getMinute());
		end.setTime(event.getStartTime().getHour(), event.getStartTime().getMinute());
		Integer freq = event.getFreq();
		if (freq == null)
			split.getSelectionModel().select(Picker.Månedlig);
		else if (freq == 7)
			split.getSelectionModel().select(Picker.Ukentlig);
		else if (freq == 1)
			split.getSelectionModel().select(Picker.Daglig);
		else if (freq == 0)
			split.getSelectionModel().select(Picker.Aldri);
		else{
			split.getSelectionModel().select(Picker.Egendefinert);
			freqText.setText("" + freq);
		}
		purposeText.setText(event.getEventName());
		if (event.getRoom() != null)
			romText.setText(event.getRoom().getRoomName());
		infoText.setText(event.getInfo());
		startDate.setValue(event.getStartDate());
		endDate.setValue(event.getEndDate());
		if (event.getFreqDate() != null)
			repDate.setValue(event.getFreqDate());
		priority = event.getPriority();
		for (EventAppliance e : event.getAppliance()){
			Person p = e.person;
			int index = comboPeople.indexOf(p);
			if (index != -1){
				comboPeople.remove(index);
				listPeople.add(p);
			}
		}
	}
}
