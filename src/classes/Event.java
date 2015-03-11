package classes;

import gui.Main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Event {
	private final Person madeBy;
	public  final int id;
	private String eventName;
	private String location;
	private Room room;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Integer freq;
	private Collection<EventAppliance> appliance;
	private Priority priority;
	private String info;
	private LocalDateTime freqEnd;

	public Event(){
		id = (int)Math.random()*100000000;
		this.madeBy = null;
		
	}
	public Event(LocalDateTime startTime,LocalDateTime endTime,Person person){
		id = (int)Math.random()*100000000;
		this.startDate = startTime.toLocalDate();
		this.endDate = endTime.toLocalDate();
		this.madeBy = person;
		
	}
	public Event(String eventName, String location, Room room, LocalDateTime startTime,
			LocalDateTime endTime, Integer freq, Person madeBy,
			Collection<EventAppliance> appliance, Priority priority, String info) {
		id = (int)Math.random()*100000000;
		this.eventName = eventName;
		this.location = location;
		this.room = room;
		this.startDate = startTime.toLocalDate();
		this.endDate = endTime.toLocalDate();
		this.startTime = startTime;
		this.endTime = endTime;
		this.freq = freq;
		this.madeBy = madeBy;
		this.appliance = appliance;
		this.priority = priority;
		this.info = info;
	}

	public Collection<Person> getApplicants() {
		// Skal returnere de som kan (tror jeg)
		return new ArrayList<Person>();
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public LocalDate getStartDate() {
		return startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
		this.startDate = startTime.toLocalDate();
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
		this.endDate = endTime.toLocalDate();
	}

	public Integer getFreq() {
		return freq;
	}

	public void setFreq(Integer freq,boolean monthlyRepeat) {
		if(! monthlyRepeat){
			monthlyRepeat = false;
			this.freq = freq;
		}else if(monthlyRepeat){
			monthlyRepeat = true;
			this.freq = null;
		}
	}
	public void setFreqEndTime(LocalDateTime endTime) {
		this.freqEnd = endTime;
	}
	public Collection<EventAppliance> getAppliance() {
		return appliance;
	}

	public void setAppliance(Collection<EventAppliance> appliance) {
		this.appliance = appliance;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String getInfo() {
		return info;
	}
	public int getID() {
		return id;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Person getMadeBy() {
		return madeBy;
	}
	
	
	
	
}
