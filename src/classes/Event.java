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
	
	
	public Event(String eventName, String location, Room room,
			LocalDate startDate, LocalDate endDate, LocalDateTime startTime,
			LocalDateTime endTime, Integer freq, Person madeBy,
			Collection<EventAppliance> appliance, Priority priority, String info) {
		id = 2;
		this.eventName = eventName;
		this.location = location;
		this.room = room;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}


	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Integer getFreq() {
		return freq;
	}

	public void setFreq(Integer freq) {
		this.freq = freq;
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

	public void setInfo(String info) {
		this.info = info;
	}

	public Person getMadeBy() {
		return madeBy;
	}
	
}