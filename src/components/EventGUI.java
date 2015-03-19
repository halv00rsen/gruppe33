package components;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import gui.Component;
import gui.FxUtil;
import gui.GetPersonListener;
import gui.Main.AddGroupListener;
import gui.Main.AddNewEvent;
import gui.Main.AddPersonListener;
import gui.Main.ChangeTab;
import gui.Main.GetGroupListener;
import classes.Appliance;
import classes.Calendar;
import classes.EventAppliance;
import classes.Group;
import classes.Person;
import classes.PersonCalendar;
import classes.Priority;
import classes.Calendar.TypeOfCalendar;
import classes.Room;
import database.ConnectionMySQL;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class EventGUI extends Component implements GetPersonListener, GetGroupListener{
	
	private final TimeField start, end;
	private final NumberField freqText;
	private final AddNewEvent eventCall;
	private final ChangeTab changeTab;
	Priority[] pList;
	Label repeatTo = new Label();
    Label every = new Label();
    Label day = new Label();

	Button avaibleRoomsBtn = new Button();
	Button save = new Button();
	Button cancel = new Button();
	Button trash = new Button();
	TextField purposeText = new TextField();
	ComboBox<String> romChoice = new ComboBox();
	ComboBox<Calendar> calendarChoice = new ComboBox<Calendar>();
	ObservableList<Calendar> calendarItems;
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
	private ObservableList<String> items;
	
	private ListView<Person> invited = new ListView<Person>();
	
	private classes.Event currentEvent;
	
	public EventGUI(Pane parent, AddNewEvent eventCall, ChangeTab changeTab, AddPersonListener l, AddGroupListener gl) {
		super(parent);
		this.eventCall = eventCall;
		this.changeTab = changeTab;
		gl.addListener(this);
		l.addListener(this);
//		calendarItems = FXCollections.observableArrayList();
		start = new TimeField(true);
		end = new TimeField(false);
		freqText = new NumberField();
		currentEvent = null;
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
				if (repDate.getValue() != null  && repDate.getValue().isBefore(endDate.getValue())){
					repDate.setValue(endDate.getValue().plusDays(1));
				}
			}
		});
		repDate.setOnAction(new EventHandler<ActionEvent>(){
			
			public void handle(ActionEvent e){
				if (endDate.getValue() != null){
					if (endDate.getValue().isAfter(repDate.getValue()))
						repDate.setValue(endDate.getValue().plusDays(1));
				}
			}
		});
		start.setOtherTime(end);
		end.setOtherTime(start);
		addElements();
        addAction();
        this.getChildren().add(pane);
		
		romChoice.setEditable(false);
        romChoice.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				if(startDate.getValue() != null && endDate.getValue() != null && endDate.getValue() != null && endDate.getValue() != null
						&& startDate.getValue().equals(endDate.getValue())){
					
					romChoice = showAvailableRooms(LocalDateTime.of(startDate.getValue(), LocalTime.of(start.getHour(), start.getMinutes())), 
							LocalDateTime.of(endDate.getValue(), LocalTime.of(end.getHour(), start.getMinutes())));
					
				}
				else{
					
					System.out.println("velg verdier");
					
				}
				
			}
        	
        	
        });
     
	}
	
	private ComboBox<String> showAvailableRooms(LocalDateTime fromTime, LocalDateTime toTime) {
		
		String[] from = fromTime.toString().split("T");
		String[] to = toTime.toString().split("T");
		String start = from[0] + " " + from[1] + ":00";
		String end = to[0] + " " + to[1] + ":00";
		
		ArrayList<String> availableRooms = ConnectionMySQL.getAvailableRooms(start, end);
		ListView<String> availableRoomsListView = new ListView<String>();
		ObservableList<String> items =FXCollections.observableArrayList ();
		
		for (String room: availableRooms){
			
			items.add(room);
			
		}
		
		romChoice.setItems(items);
		romChoice.setPrefHeight(20);
		return romChoice;
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
//		if (!start.isCorrectInput() || !end.isCorrectInput())
//    		return;
		if (calendarChoice.getSelectionModel().getSelectedIndex() == -1)
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
    	classes.Event newevent = new classes.Event(t1, t2, null);
    	newevent.setEventName(purposeText.getText());
    	Picker splitStuff = split.getValue();
    	LocalDate freqEnd = repDate.getValue();
    	if (freqEnd == null && splitStuff != Picker.Aldri)
    		return;
    	if (splitStuff == Picker.Daglig || splitStuff == Picker.Ukentlig)
    		newevent.setFreq(splitStuff.freq, false, freqEnd);
    	else if (splitStuff == Picker.Aldri)
    		newevent.setFreq(0, false, freqEnd);
    	else if (splitStuff == Picker.Egendefinert){
    		int ting = freqText.getNumDays();
    		if (ting == -1)
    			return;
    		newevent.setFreq(ting, false, freqEnd);
    	}else
    		newevent.setFreq(0, true, freqEnd);
    	int index = calendarChoice.getSelectionModel().getSelectedIndex();
    	Calendar calendar = calendarChoice.getItems().get(index);
    	List<EventAppliance> persons = new ArrayList<EventAppliance>();
    	if (calendar.type == TypeOfCalendar.Personal){
    		for (Person p : listPeople){
    			persons.add(new EventAppliance(p, Appliance.Not_Answered));
    		}
    	}
    	newevent.addAppliance(persons);
    	
    	if(romChoice.getValue() != null){
    		System.out.println(romChoice.getValue());
    		newevent.setRoom(new Room(Integer.parseInt(romChoice.getValue())));
    		
    	}
    	newevent.setInfo(infoText.getText());
    	newevent.setPriority(priority);
    	
    	if (currentEvent == null){
    		eventCall.addEvent(newevent, (calendar.type == TypeOfCalendar.Personal ? null: calendar) );
    		changeTab.showEventInHomeScreen(newevent);
    	}else{
    		//skal sendes til server.
    		eventCall.changeEvent(currentEvent.getID(), currentCal, (calendar.type == TypeOfCalendar.Personal ? null: calendar) , newevent);
//    		currentEvent.overrideEvent(newevent);
    		changeTab.showEventInHomeScreen(currentEvent);
    	}
   
    	trash();
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
		if (currentEvent != null){
			resetButtons();
		}
    	purposeText.clear();
    	romChoice.selectionModelProperty().set(null);
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
    	currentEvent = null;
    	for (Priority p : pList){
				p.turnOff();
		}
    	pList[0].turnOn();
//    	changeTab.goToHomeScreen();
	}
	
	private void resetButtons(){
		cancel.setText("Avbryt");
        save.setText("Lag event");
        trash.setText("Forkast");
	}
	
	private void close(ActionEvent e) {
		if (currentEvent != null){
			trash();
//			changeTab.goToHomeScreen();
		}
//    	Platform.setImplicitExit(true);
//    	Platform.exit();
	}
	
	public void repeteresOn(Object object){
    	if(! object.equals(Picker.Aldri)){
    		freq = 0;
    		repDate.setDisable(false);
    		repeatTo.setTextFill(Color.web("#000000"));
    		
    	}else{
    		repDate.setDisable(true);
    		repeatTo.setTextFill(Color.web("#AAAAAA"));
    	}
    	if( object.equals(Picker.Månedlig)){
    		freq = 30;
    	}else if( object.equals(Picker.Ukentlig)){
    		freq = 7;
    	}else if (object.equals(Picker.Daglig)){
    		freq = 1;
    	}
    	if( object.equals(Picker.Egendefinert)){
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
//    	pane.setStyle("-fx-background-color: #FFF");

		//calendar
        Label calendarLabel = new Label();
        calendarLabel.setText("Kalender\t\t\t");
        FxUtil.autoCompleteComboBox(calendarChoice, FxUtil.AutoCompleteMode.CONTAINING);
        calendarItems = calendarChoice.getItems();
        VBox calendarBox = new VBox(2);
        calendarBox.getChildren().add(calendarLabel);
        calendarBox.getChildren().add(calendarChoice);
        calendarChoice.setPrefWidth(200);
       
		
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
        FxUtil.autoCompleteComboBox(romChoice, FxUtil.AutoCompleteMode.CONTAINING);
        HBox romBox = new HBox(20);
        romBox.getChildren().add(rom);
        romBox.getChildren().add(romChoice);
        romBox.getChildren().add(avaibleRoomsBtn);
        romChoice.setPrefWidth(225);
       
        
        //dato
        Label til1 = new Label();
        til1.setText("\t\ttil\t\t");
        Label date = new Label();
        date.setText("\tFra dato\t\t");
        HBox dateBox = new HBox(0);
        dateBox.getChildren().add(date);
        HBox heisann = new HBox(0);
        heisann.getChildren().addAll(startDate, til1,endDate);
        dateBox.getChildren().add(heisann);
        startDate.setPrefWidth(100);
        endDate.setPrefWidth(100);
//        datePicker.setPrefWidth(225);
//        setPos(dateBox,60,200);
//        root.getChildren().add(dateBox);
        
      //klokken
        Label fra_klokken = new Label();
        Label til = new Label();
        fra_klokken.setText("Fra klokken\t\t");
        til.setText("\t\t\ttil\t\t");
        HBox klokkeBox = new HBox(5);
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
        searchPeople.setPrefWidth(180);
        
        FxUtil.autoCompleteComboBox(searchPeople, FxUtil.AutoCompleteMode.CONTAINING); 
        //ListView
        comboPeople = searchPeople.getItems();
        
        invited.setMaxHeight(300);
        listPeople = FXCollections.observableArrayList();
        invited.setItems(listPeople);
        VBox listButtons = new VBox(3);
        Button addPerson = new Button("Legg til"), removeButton = new Button("  Fjern  ");
    	removeButton.setDisable(true);
        listButtons.getChildren().addAll(addPerson,removeButton);
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
        invited.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>(){

			
			@Override
			public void changed(ObservableValue<? extends Person> arg0,Person arg1, Person arg2) {
				Person selected = invited.getSelectionModel().getSelectedItem();
				if (selected != null){
					removeButton.setDisable(false);
				}else{
					removeButton.setDisable(true);
				}
				
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
        BorderPane buttonsBox = new BorderPane();
        
        buttonsBox.setLeft(searchPeople);
        BorderPane.setAlignment(searchPeople, Pos.CENTER_LEFT);
        buttonsBox.setRight(listButtons);
        BorderPane.setAlignment(listButtons, Pos.CENTER_RIGHT);
        
        Pane blueBox = new Pane();
        BorderPane.setMargin(blueBox,new Insets(20));
        VBox listBox = new VBox(20);
        listBox.getChildren().add(buttonsBox);
        listBox.getChildren().addAll(invited);
        listBox.setPadding(new Insets(30));
        blueBox.setStyle("-fx-background-color: #AAF");
        blueBox.setPrefHeight(500);
        blueBox.setPrefWidth(300);
        blueBox.getChildren().add(listBox);
        
        calendarChoice.setOnAction(new EventHandler<ActionEvent>(){
        	
        	public void handle(ActionEvent e){
        		int index = calendarChoice.getSelectionModel().getSelectedIndex();
        		if (index == -1)
        			return;
        		Calendar cal = calendarItems.get(index);
        		if (cal == null)
        			return;
        		if (cal.type == TypeOfCalendar.Personal){
        			buttonsBox.setDisable(false);
        			invited.setDisable(false);
        		}else{
        			buttonsBox.setDisable(true);
        			invited.setDisable(true);
        		}
//        		if (calendarChoice.getValue().type == TypeOfCalendar.Personal){
//        			
//        		}
        	}
        });
        
      //Priority
        BorderPane prioThings = new BorderPane();
        Label prio = new Label();
        prio.setText("Prioritet:\t");
        HBox priorityList = new HBox(5);

//        priorityList.setStyle("-fx-background-color: #FFFFFF;");
        prioThings.setLeft(prio);
        BorderPane.setAlignment(prio, Pos.CENTER);
        prioThings.setCenter(priorityList);
        BorderPane.setAlignment(priorityList, Pos.CENTER);
        priority = Priority.NOT_IMPORTANT;
        pList = new Priority[3];
        pList[0] = Priority.NOT_IMPORTANT;
        pList[0].turnOn();
        pList[1] = Priority.IMPORTANT;
        pList[2] = Priority.VERY_IMPORTANT;

        priorityList.getChildren().add(pList[0].getVisualization());
        priorityList.getChildren().add(pList[1].getVisualization());
        priorityList.getChildren().add(pList[2].getVisualization());
        listBox.getChildren().add(prioThings);
        
        EventHandler<Event> event = new EventHandler<Event>(){

			@Override
			public void handle(Event event) {
				for (Priority p : pList){
					if (p.getVisualization() == event.getSource()){
//						if (p.isActive()){
//							p.turnOff();
//							priority = null;
//						}
//						else{
							p.turnOn();
							priority = p;
//						}
					}else
						p.turnOff();
				}
			}
        	
        };
        for (Priority p : pList)
        	p.getVisualization().setOnMouseClicked(event);
        
        //set alle
        VBox rootBox = new VBox(25);
        BorderPane.setMargin(rootBox,new Insets(20));
        rootBox.getChildren().add(calendarBox);
        rootBox.getChildren().add(formalBox);
        rootBox.getChildren().add(infoBox);
        rootBox.getChildren().add(dateBox);
        rootBox.getChildren().add(klokkeBox);
        rootBox.getChildren().add(repeteresBox);
        rootBox.getChildren().add(repeteresTilBox);
        rootBox.getChildren().add(romBox);
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
    
    private Calendar currentCal;

	public void showEvent(classes.Event event, Calendar cal) {
		trash();
		if (event == null)
			return;
		currentCal = cal;
		int calIndex = calendarItems.indexOf(cal);
		if (calIndex == -1){
//			currentCal = calendarChoice.getSelectionModel().getSelectedItem();
//			currentCal = calendarItems.get(0);
			currentCal = null;
		}else{
			calendarChoice.getSelectionModel().select(calIndex);
		}
		currentEvent = event;
		start.setTime(event.getStartTime().getHour(), event.getStartTime().getMinute());
		end.setTime(event.getEndTime().getHour(), event.getEndTime().getMinute());
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
		if (event.getRoom() != null){
			
		}
			//////Må fikses på
			////romChoice.set(event.getRoom().getRoomName());
		infoText.setText(event.getInfo());
		startDate.setValue(event.getStartDate());
		endDate.setValue(event.getEndDate());
		if (event.getFreqDate() != null)
			repDate.setValue(event.getFreqDate());
				for (EventAppliance e : event.getAppliance()){
			Person p = e.person;
			int index = comboPeople.indexOf(p);
			if (index != -1){
				comboPeople.remove(index);
				listPeople.add(p);
			}
		}
		priority = event.getPriority();

		for (Priority p : pList){
			if (p.getVisualization() == event.getPriority().getVisualization()){
					p.turnOn();
					priority = p;
			}else
				p.turnOff();
		}
	
		save.setText("Lagre");
		trash.setText("Slett");
		cancel.setText("Avbryt");
	}

	@Override
	public void updatePersons(List<Person> persons) {
		// TODO Auto-generated method stub
		for (Person p : persons)
        	comboPeople.add(p);
	}

	@Override
	public void setGroups(List<Group> groups) {
		calendarItems.clear();
		calendarItems.add(new PersonCalendar(null));
		for (Group g : groups){
			calendarItems.add(g.getGroupCalendar());
		}
		calendarChoice.getSelectionModel().selectFirst();
	}

	@Override
	public void groupAdded(boolean added) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void personAdded(boolean added) {
		// TODO Auto-generated method stub
		
	}
}
