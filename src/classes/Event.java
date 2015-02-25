package classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


public class Event {
	private String eventName;
	private String location;
	private Room room;
	private Date startDate;
	private Date endDate;
	private Integer freq;
	private final Person madeBy;
	private Collection<EventAppliance> appliance;
	private Boolean priority;
	private String info;
	
	public Event(String eventName, String location, Room room, Date startDate, Date stopDate, Integer freq,
			Person madeBy, Collection<EventAppliance> appliance,
			Boolean priority, String info) {
		
		this.eventName = eventName;
		this.location = location;
		this.room = room;
		this.startDate = startDate;
		this.endDate = stopDate;
		this.freq = freq;
		this.madeBy = madeBy;
		this.appliance = appliance;
		this.priority = priority;
		this.info = info;
	}
	
	public Collection<Person> getApplicants() {
		//Skal returnere de som kan (tror jeg)
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date stopDate) {
		this.endDate = stopDate;
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

	public Boolean getPriority() {
		return priority;
	}

	public void setPriority(Boolean priority) {
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
