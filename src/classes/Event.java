package classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import database.PersonInformation;

public class Event implements Comparable<Event>{
	private final List<EventAppliance> appliance;
	
	private Person madeBy;
	private int id;
	private String eventName;
	private String location;
	private Room room;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private int freq;
	private Priority priority;
	private String info;
	private LocalDate freqEnd;
	private Event nextInSequence;
	private Event lastInSequence;

	private boolean monthlyRepeat;
	public Event(){
		id = (int)(Math.random()*100000000);
		this.madeBy = null;
		eventName = "";
		location = "";
		room = null;
		startDate = null;
		endDate = null;
		startTime = null;
		endTime = null;
		freq = 0;
		appliance = new ArrayList<EventAppliance>();
		priority = Priority.NOT_IMPORTANT;
		info = "";
		freqEnd = null;
		
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public Event nextEventInSequence(){
		return this.nextInSequence;
	}
	public Event lastEventInSequence(){
		return this.lastInSequence;
	}
	public void setNextEventInSequence(Event nextEvent){
		this.lastInSequence = nextEvent;
	}
	public void setLastEventInSequence(Event lastEvent){
		this.lastInSequence = lastEvent;
	}
	public Event(LocalDateTime startTime,LocalDateTime endTime,Person person){
		this();
		id = (int)(Math.random()*100000000);
		this.startDate = startTime.toLocalDate();
		this.endDate = endTime.toLocalDate();
		this.startTime = startTime;
		this.endTime = endTime;
		this.madeBy = person;
		priority = Priority.NOT_IMPORTANT;
	}
	public Event(String eventName, String location, Room room, LocalDateTime startTime,
		LocalDateTime endTime, Integer freq, Person madeBy,
		List<EventAppliance> appliance, Priority priority, String info) {
		id = (int)(Math.random()*100000000);
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
	
	public void overrideEvent(Event event){
		this.appliance.clear();
		for (EventAppliance ec: event.appliance)
			this.appliance.add(ec);
		eventName = event.eventName;
		location = event.location;
		room = event.room;
		startDate = event.startDate;
		endDate = event.endDate;
		startTime = event.startTime;
		endTime = event.endTime;
		freq = event.freq;
		priority = event.priority;
		info = event.info;
		freqEnd = event.freqEnd;
	}
	
	public LocalDate getFreqDate(){
		return freqEnd;
	}
	
	public boolean isMonthlyRepeat(){
		return this.monthlyRepeat;
	}
	public void setMadeBy(Person p){
		madeBy = p;
		//DEBUGFELT vvvvvvvvv
//			madeBy = PersonInformation.getPeople().get(1);
		//DEBUGFELT ^^^^^^^^^
		appliance.add(new EventAppliance(p, Appliance.Not_Answered));
	}
	public Person getMadeBy(Person p){
		return this.madeBy;
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
		if (startTime == null)
			startDate = null;
		else
			this.startDate = startTime.toLocalDate();
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
		if (endTime == null)
			endDate = null;
		else
			this.endDate = endTime.toLocalDate();
	}

	public Integer getFreq() {
		return freq;
	}

	public void setFreq(Integer freq,boolean monthlyRepeat, LocalDate freqEnd) {
		if(! monthlyRepeat){
			this.monthlyRepeat = false;
			this.freq = freq;
		}else if(monthlyRepeat){
			this.monthlyRepeat = true;
			this.freq = -1;
		}
		this.freqEnd = freqEnd;
		
	}
	public List<EventAppliance> getAppliance() {
		return appliance;
	}

	public void addAppliance(List<EventAppliance> appliance) {
		for (EventAppliance c : appliance)
			this.appliance.add(c);
	}

	public Priority getPriority() {
		if (priority == null)
			return Priority.NOT_IMPORTANT;
		return priority;
	}

	public void setPriority(Priority priority) {
		if (priority == null)
			this.priority = Priority.NOT_IMPORTANT;
		else
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
	
	public String toString(){
		return eventName;
	}
	
	public String debugString(){
		return "Name: " + eventName + ", madeBy: " + madeBy + ", id: " + id + ", startDate: " + startDate + 
				", endDate: " + endDate + ", startTime: " + startTime + ", endTime: " + endTime+ ", freq: " +
				freq + ", priority: " + priority + ", info: " + info + ", freqEnd: " + freqEnd;
	}
	@Override
	public int compareTo(Event other) {
		if(this.getStartTime().isBefore(other.getStartTime())){
			return -1;
		}else if(this.getStartTime().isEqual(other.getStartTime())){
			return 0;
		}else{
			return 1;
		}
	}
	
}
