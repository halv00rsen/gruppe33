package classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.MySQLConnection;

import components.CalendarBase;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import classes.Calendar.TypeOfCalendar;
import database.ConnectionMySQL;
import database.CreateUser;
import database.PersonInformation;

public class Program {

	public final static boolean DEBUG = true;
	private final List<ProgramListener> listeners;
	private final List<Calendar> activeCalendars, unactive;
	private final List<Person> allUsers;
	public UpdateThread updateThread;
	private Person currentPerson;
	ArrayList<MailInfo> mailInfo;
	public Program(){
		listeners = new ArrayList<ProgramListener>();
		activeCalendars = new ArrayList<Calendar>();
		unactive = new ArrayList<Calendar>();
		allUsers = new ArrayList<Person>();
		//opprett kobling med database og/eller socketprogram
	}
	public class UpdateThread{
		public boolean isLoggedin;
		
	    public UpdateThread() {
	    	isLoggedin = true;
	    	mailInfo = new ArrayList<MailInfo>();
	    }
	    public void callOnFinished(){
	    	ArrayList<HashMap<String, String>> email = ConnectionMySQL.getMessage(getCurrentUser().getUsername());
		       if(email != null){
		    	   System.out.println("email er ikke null!");
		    	   System.out.println("email"+email);
//		    	   
//		    	   System.out.println("email.get(0)"+email.get(0));
//		    	   System.out.println("email.get(0).get(username_from)"+email.get(0).get("username_from"));
		    	   	for (int i = 0; i < email.size(); i++) {
		    	   		String from = email.get(i).get("username_from");
						String to = email.get(i).get("username_to");
						String info = email.get(i).get("message");
						
			    	   	
						Message message = Message.Custom;
						HashMap<String,String> a = translateMail(from,to,info);
						if(! a.get("header").equals("null") && ! a.get("username_from").equals("null") && ! a.get("username_to").equals("null") && ! a.get("time").equals("null") && ! a.get("status").equals("null") ){
							MailInfo hei = mailInfoGenerator(a.get("header"),a.get("username_from"),a.get("username_to"),a.get("time"),a.get("status"),Integer.valueOf(a.get("event_id")));
					       	boolean exist = false;
					       	for (int j = 0; j < mailInfo.size(); j++) {
								
								if(mailInfo.get(j).from.equals(hei.from) && mailInfo.get(j).to.equals(hei.to) && mailInfo.get(j).status.equals(hei.status) && mailInfo.get(j).header.equals(hei.header) && mailInfo.get(j).date.equals(hei.date) && mailInfo.get(j).eventId == hei.eventId){
									exist = true;
								}
							}
					       	if(! exist){
					       		
					       		callMessage(message.customMessage(a.get("status")));
					       		createMail(hei);
					       	}
						}else{
//							System.out.println("want to delete message " + from + to + info);
							deleteMessage(from, to,info);
						}
				       	
				       	
		    	   	}
		       }
		    run();
	    }
		
		public void run() {
			if(isLoggedin){
				System.out.println("************************thread******************");
		        Timeline timeline = new Timeline();
		        timeline.setOnFinished( new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent arg0) {
						callOnFinished();
					}
					
				});
		        KeyFrame wait = new KeyFrame(Duration.millis(5000));
		    	timeline.getKeyFrames().add(wait);
		        
			    timeline.play();
		        }
	        	
	        }
		public void cancel(){
	    	isLoggedin = false;
	    }
	}
	public void createMail(MailInfo hei) {
		mailInfo.add(hei);
		for (ProgramListener l : listeners)
			l.createMail(hei);
		
	}
	public Person getCurrentUser(){
		return currentPerson;
	}
	public MailInfo mailInfoGenerator(String header, String from, String to, String time, String status, int event_id) {
		return new MailInfo(header, from, to,time,status, event_id);
	}
	public void createGroup(String name){
		int groupId = ConnectionMySQL.createGroup(name, 0);
		if (groupId == -1){
			if (DEBUG){
				System.out.println("Create group connection null");
				Group group = new Group(name, (int)( Math.random() * 10000));
				currentPerson.getGroups().add(group);
				group.addMembers(currentPerson);
				activeCalendars.add(group.getGroupCalendar());
				updateGroups();
			}
			callMessage(Message.GroupNotCreated);
			return;
		}else{
			Group group = new Group(name, groupId);
			currentPerson.getGroups().add(group);
			group.addMembers(currentPerson);
			ConnectionMySQL.addMembersToGroup(groupId, currentPerson.username);
			activeCalendars.add(group.getGroupCalendar());
		}
		updateGroups();
	}
	
	private void callAddedPerson(boolean added){
		for (ProgramListener l : listeners)
			l.personAdded(added);
	}
	
	private void callAddedGroup(boolean added){
		for (ProgramListener l : listeners)
			l.groupAdded(added);
	}
	
	public void addGroupTo(Group group, Group sub){
		if (group.getParent() == sub.id || sub.getParent() == group.id){
			callAddedGroup(false);
			return;
		}
		if (isIncest(sub, group.id)){
			System.out.println("Is incest!!! :)))))");
			callAddedGroup(false);
			return;
		}
		group.addSubGroups(sub);
		sub.setParent(group.id);
		ConnectionMySQL.addParent(sub.id, group.id);
		addSubEvents(group.getGroupCalendar().getEvents(), sub);
		callAddedGroup(true);
	}
	
	private boolean isIncest(Group sub, int origin){
		for (Group g : sub.getSubGroups()){
			if (g.id == origin || isIncest(g, origin))
				return true;
		}
		return false;
	}
	
	public void removeGroupFrom(Group group, Group sub){
		List<Event> events = group.getGroupCalendar().getEvents();
		if (ConnectionMySQL.removeParent(sub.id, group.id)){
			group.removeSubGroup(sub);
			sub.setParent(0);
			removeSubEvents(events, sub);
		}
	}
	
	private void addSubEvents(List<Event> events, Group group){
		for (Event e : events){
			group.getGroupCalendar().addEvent(e);
			ConnectionMySQL.addGroupsToEvent(e.getID(), group.id);
		}
		for (Group g : group.getSubGroups())
			addSubEvents(events, g);
	}
	
	private void removeSubEvents(List<Event> events, Group group){
		for (Event e : events){
			group.getGroupCalendar().removeEvent(e);
			ConnectionMySQL.removeGroupsFromEvent(e.getID(), group.id);
		}
		for (Group g : group.getSubGroups())
			removeSubEvents(events, g);
	}
	
	public void addPersonGroup(Group group, Person p){
		if (ConnectionMySQL.addMembersToGroup(group.id, p.username)){
			group.addMembers(p);
			List<Event> events = group.getGroupCalendar().getEvents();
			for (Event e : events){
				ConnectionMySQL.addMembersToEvent(e.getID(), p.username);
			}
			callAddedPerson(true);
			return;
		}
		callAddedPerson(false);
	}
	
	public void removePersonGroup(Group group, Person p){
		if (ConnectionMySQL.removeMembersFromGroup(group.id, p.username)){
			group.removePerson(p);
			for (Event e : group.getGroupCalendar().getEvents())
				ConnectionMySQL.removeMembersFromEvent(e.getID(), p.username);
		}
	}
	
	public void deleteGroup(int groupId){
		for (Calendar a : activeCalendars){
			System.out.println(a.debugString());
		}
		for (Calendar a : unactive)
			System.out.println(a.debugString());
		if (ConnectionMySQL.deleteGroup(groupId)){
			for (Group g : currentPerson.getGroups()){
				if (g.id == groupId){
					currentPerson.getGroups().remove(g);
					activeCalendars.remove(g.getGroupCalendar());
					unactive.remove(g.getGroupCalendar());
					for (Event e : g.getGroupCalendar().getEvents()){
						ConnectionMySQL.deleteEvent(e.getID());
					}
					break;
				}
			}
		}else if (DEBUG){
			System.out.println("delete group connection false");
			for (Group g : currentPerson.getGroups()){
				System.out.println(g.id + "   " + groupId);
				if (g.id == groupId){
					currentPerson.getGroups().remove(g);
					activeCalendars.remove(g.getGroupCalendar());
					unactive.remove(g.getGroupCalendar());
					break;
				}
			}
		}
		updateGroups();
		updateCalendars();
	}
	public void setHideEvent(Event event, Person person, boolean isHidden){
		ConnectionMySQL.hideEvent(event.getID(),person.getUsername(),isHidden);
		Calendar c = getCalendarFor(event.getID());
		c.removeEvent(event);
		updateCalendars();
	}
	public void changeEvent(int eventId, Calendar oldCal, Calendar newCal, Event event){
		Event oldEvent = null;
		if (newCal == null){
			newCal = currentPerson.getPersonalCalendar();
		}
		if (oldCal == null){
			oldCal = currentPerson.getPersonalCalendar();
		}
		for (Event e: oldCal.getEvents()){
			if (e.getID() == eventId){
				oldEvent = e;
				break;
			}
		}
		if (oldEvent == null){
			System.out.println("change event er null");
			return;
		}
		String[] from = event.getStartTime().toString().split("T");
		String[] to = event.getEndTime().toString().split("T");
		String start = from[0] + " " + from[1] + ":00";
		String end = to[0] + " " + to[1] + ":00";
		if (ConnectionMySQL.updateEvent("" + eventId, event.getEventName(), event.getLocation(), start, end, event.getPriority().pri, null, event.getFreq(), event.getInfo())){
			
			
			//
		}else{
			
			if (DEBUG){
				System.out.println("change event connection false");
			}
		}
		if (oldCal != newCal){
//			if (ConnectionMySQL.updateEvent(eventId, newEven, location, start, end, priority, lastChanged, frequency, info))
			System.out.println("changed cal");
			oldCal.removeEvent(oldEvent);
			newCal.addEvent(oldEvent);
		}
		for (EventAppliance e : oldEvent.getAppliance()){
			
			if(e.person.username != getCurrentUser().getUsername()){
				String info =  "header: Hendelse endret \ntime: "+LocalDateTime.now()+"\nstatus: En event har blitt endret p�! For � se n�rmere p� dette g� til innboksen og klikk p� hendelsen! \nevent_id: " + eventId;
				ConnectionMySQL.sendMessage(getCurrentUser().getUsername(), e.person.username,info);
				
			}
			
			ConnectionMySQL.removeMembersFromEvent(eventId, e.person.username);
		}
		if (event.getAppliance().isEmpty()){
			event.getAppliance().add(oldEvent.getAppliance().get(0));
		}else
			event.getAppliance().add(0, oldEvent.getAppliance().get(0));
		for (EventAppliance e : event.getAppliance()){
			ConnectionMySQL.addMembersToEvent(eventId, e.person.username);
			
		}
		oldEvent.overrideEvent(event);
		
		
//		createMail(new MailInfo("HEIHIG","UOBEG","PUP","bpi",oldEvent.getID()));
		updateCalendars();
	}
	
	private void updateGroups(){
		for (ProgramListener l : listeners)
			l.updateGroups(currentPerson.getGroups());
	}
	
	public Calendar getCalendarFor(int eventId){
		for (Calendar c : activeCalendars){
			for (Event e : c.getEvents()){
				System.out.println(e.getID() + "");
				if (e.getID() == eventId)
					return c;
			}
		}
		for (Calendar c : unactive){
			for (Event e : c.getEvents()){
				if (e.getID() == eventId)
					return c;
			}
		}return null;
	}
	
	public void createEvent(Event e, Calendar cal){
		//add events to server
//		for (Calendar cals: cal)
//			cals.addEvent(event);
//		callMessage(Message.EventAdded);
		e.setMadeBy(currentPerson);
		if (cal != null){
			for (Group g : currentPerson.getGroups()){
				if (cal.isOwner(g.id, TypeOfCalendar.Group)){
					for (Person p : g.getMembers()){
						e.getAppliance().add(new EventAppliance(p, Appliance.Not_Answered));
					}
					break;
				}
			}
		}
		
		if(e.getFreq() != null){
			
			if(e.getFreq()==0){
				createEventServer(e, (cal == null ? currentPerson.getPersonalCalendar(): cal));
				
			}else if(e.getFreq()>0 || e.isMonthlyRepeat()){
				int i = e.getFreq();
				Event lastEvent = e;
				Event nextEvent;
				while(true){
					nextEvent = new Event();
					nextEvent.overrideEvent(e); // m� ha ny ID da...
					if(e.isMonthlyRepeat()){
						nextEvent.setStartTime(lastEvent.getStartTime().plusMonths(1));
						nextEvent.setEndTime(lastEvent.getEndTime().plusMonths(1));	
					}else{
						nextEvent.setStartTime(lastEvent.getStartTime().plusDays(i));
						nextEvent.setEndTime(lastEvent.getEndTime().plusDays(i));	
					}
					
					nextEvent.setLastEventInSequence(lastEvent);
					lastEvent.setNextEventInSequence(nextEvent);
					createEventServer(lastEvent, (cal == null ? currentPerson.getPersonalCalendar(): cal));
					if(nextEvent.getStartDate().isAfter(e.getFreqDate())){
						break;
					}
					lastEvent = nextEvent;
				}
			}
		}
		
		updateCalendarListeners();
		callMessage(Message.EventAdded);
	}
	
	private void createEventServer(Event event, Calendar cal){
		String[] from = event.getStartTime().toString().split("T");
		String[] to = event.getEndTime().toString().split("T");
		String start = from[0] + " " + from[1] + ":00";
		String end = to[0] + " " + to[1] + ":00";
		int eventId = ConnectionMySQL.createEvent(event.getEventName(), event.getLocation(), start, end, event.getPriority().pri
				, event.getFreq(), event.getInfo());
		if(event.getRoom() != null){
			if(event.getRoom().getRoomNr() != 0){
				
				ConnectionMySQL.reserveRoom(eventId, event.getRoom().getRoomNr());
			
			}
		}
		
		
		if (eventId == -1){
			if (DEBUG){
				cal.addEvent(event);
				System.out.println("Create event server connection null");
				
			}
//			callMessage(Message.EventNotAdded);
			return;
		}
		if (!ConnectionMySQL.setEventCreator(eventId, currentPerson.username)){
			if (DEBUG){
				cal.addEvent(event);
			}
			callMessage(Message.EventNotAdded);
			return;
		}
		if (cal.type == TypeOfCalendar.Group){
			if (!ConnectionMySQL.addGroupsToEvent(eventId, ((GroupCalendar) cal).getOwnerGroup().id)){
				System.out.println("Group not saved createEvent");
			}
		}
		for (EventAppliance p : event.getAppliance()){
			ConnectionMySQL.addMembersToEvent(eventId, p.getPerson().getUsername());
//			updateAppliance(event, p);
		}
		event.setId(eventId);
		cal.addEvent(event);
	}
	
	public void updateAppliance(Event event, EventAppliance eventAppliance){
		for (EventAppliance e : event.getAppliance()){
			if(e.person.username != getCurrentUser().getUsername()){
				if(eventAppliance.getAppliance().equals(Appliance.Not_Attending)){
					String info =  "header: "+getCurrentUser().getUsername()+" meldte avbrudd! \ntime: "+LocalDateTime.now()+"\nstatus: Noen har meldt av brudd fra et arrangement. G� til innboks for � se hvilket arrangement! \nevent_id: " + event.getID();
					ConnectionMySQL.sendMessage(getCurrentUser().getUsername(), e.person.username,info);
					
				}
				
			}
		}
		ConnectionMySQL.setAppliance(event.getID(), eventAppliance.getPerson().getUsername(), eventAppliance.getAppliance().getStateName());
	}
	public void deleteEvent(Event event){
		//remove event from database/server
		Calendar cal = getCalendarFor(event.getID());
		cal.removeEvent(event);
		if (ConnectionMySQL.deleteEvent(event.getID()))
			callMessage(Message.EventDeleted);
		else
			callMessage(Message.EventNotDeleted);
			
		updateCalendarListeners();
//		for (Calendar cal: cals)
//			cal.removeEvent(event);
//		callMessage(Message.EventDeleted);
	}
	
	public void requestEvent(int eventId){
//		for (ProgramListener l : listeners){
//			l.showEvent(DebugMain.getEvents().get(3));
//		}
	}
	
	public void addCalendar(int id, TypeOfCalendar type){
		for (Calendar c : activeCalendars){
			if (c.isOwner(id, type)){
				return;
			}
		}
		for (Calendar c : unactive){
			if (c.isOwner(id, type)){
				activeCalendars.add(c);
				unactive.remove(c);
				updateCalendarListeners();
				return;
			}
		}
		//else, get calendar from database
	}
	
	public void removeCalendar(int id, TypeOfCalendar type){
		
		for (Calendar c : unactive){
			if (c.isOwner(id, type))
				return;
		}
		for (Calendar c : activeCalendars){
			if (c.isOwner(id, type)){
				unactive.add(c);
				activeCalendars.remove(c);
				updateCalendarListeners();
				return;
			}
		}
	}
	
	
	private void updateCalendarListeners(){
		Calendar[] c = new Calendar[activeCalendars.size()];
		for (int a = 0; a < c.length; a++){
			c[a] = activeCalendars.get(a);
		}
		for (ProgramListener l: listeners)
			l.updateCalendar(c);
	}
	
	private void sendOutPersons(){
		allUsers.clear();
		List<Person> list = new ArrayList<Person>();
		List<HashMap<String, String>> info = ConnectionMySQL.getUsers();
		if (info == null){
			System.out.println("sendOutPersons connection null");
			list = PersonInformation.getPeople();
		}else{
			for (Map<String, String> p : info){

				Person p1 = new Person(p.get("username"), null, p.get("firstName"), p.get("lastName"), false);
				if (!p1.username.equals(currentPerson.username))
					list.add(p1);
			}
		}
		for (Person p : list){
			allUsers.add(p);
		}
		for (ProgramListener l : listeners){
			l.setAllPersons(list);
		}
		allUsers.add(currentPerson);
	}
	
	
	private void callMessage(Message msg){
		for (ProgramListener l : listeners)
			l.sendMessage(msg);
	}
	
	
	public void createUser(String username, String password, String firstname, String lastname, 
			String phone, String email, boolean isAdmin){
		if (username == null || password == null || firstname == null || lastname == null){
			callCreateUser(false);
			return;
		}
		if (username.length() < 6 || username.length() > 15 || password.length() < 6 || password.length() > 25 || !currentPerson.admin){
			callCreateUser(false);
			return;
		}
		if (ConnectionMySQL.createUser(username, firstname, lastname, Person.hashPassword(password), email, phone, isAdmin)){
			callCreateUser(true);
		}else if (CreateUser.isValidNewUser(username, Person.hashPassword(password), firstname)){
			callCreateUser(true);
			System.out.println("Create user connection null");
		}else
			callCreateUser(false);
	}
	
	public void updateCurrentPerson(String firstname, String lastname, String email, String phone){
		if (isLoggedIn()){
			if (ConnectionMySQL.updateUser(currentPerson.username, firstname, lastname, 
					email, phone, currentPerson.admin)){
				callMessage(Message.PersonUpdated);
				currentPerson.setOtherInfo(phone, email);
				currentPerson.setFirstname(firstname);
				currentPerson.setLastname(lastname);
			}else{
				System.out.println("updateCurrentPerson conntection false");
				callMessage(Message.PersonNotUpdated);
			}
		}
	}
	
	public void updateCalendars(){
		updateCalendarListeners();
	}
	
	public void personLogin(String username, String password){
		if (username == null || password == null || isLoggedIn()){
			loginFailListeners();
			
			activeCalendars.add(currentPerson.getPersonalCalendar());
			for (ProgramListener l : listeners)
				l.loginSuccess(currentPerson);
				
			updateCalendarListeners();
			return;
		}
		if (username.toLowerCase().equals("admin") && password.toLowerCase().equals("admin")){
			currentPerson = new Person("admin", "admin", "Ola", "Nordmann", true);
			currentPerson.setOtherInfo("12345678", "olaNordmann@mail.com");
		}else {
			Map<String, String> info;
			info = ConnectionMySQL.getUserInfo(username);
			if (info == null){
				System.out.println("personLogin connection null");
				info = PersonInformation.getPersonInformation(username, Person.hashPassword(password));
			}
			
			String usernameDatabase = info.get("username");
			String passwordDatabase = info.get("password");
			String firstname = info.get("firstname"), lastname = info.get("lastname");
			System.out.println(usernameDatabase);
			System.out.println(passwordDatabase);
			System.out.println(username);
			System.out.println(Person.hashPassword(password));
			if (!Person.hashPassword(password).equals(passwordDatabase) || !username.equals(usernameDatabase)){
				if (DEBUG){
					System.out.println("Feil med passord");
				}
				for (ProgramListener l : listeners)
					l.loginFailed();
				return;
			}
			currentPerson = new Person(usernameDatabase, passwordDatabase, firstname, lastname, Boolean.getBoolean(info.get("isAdmin")));
			currentPerson.setOtherInfo(info.get("phone"), info.get("email"));
			
			updateThread = new UpdateThread();
			mailInfo = new ArrayList<MailInfo>();
			updateThread.run();
		}
		
		activeCalendars.add(currentPerson.getPersonalCalendar());
		for (ProgramListener l : listeners)
			l.loginSuccess(currentPerson);
		
		sendOutPersons();
		List<Integer> ev = initGroupsCurrentUser();
		getEventsFromServer(ev);
		updateCalendarListeners();
	}
	
	private void getEventsFromServer(List<Integer> taken){
		List<HashMap<String, String>> userEvents = ConnectionMySQL.getEvents(currentPerson.username);
		if (userEvents == null){
			System.out.println("get events from server connection null");
			return;
		}
		for (Map<String, String> ev: userEvents){
			int id = Integer.parseInt(ev.get("eventId"));
			if (contains(id, taken)){
				continue;
			}
			Event event = convertEventServer(ev);
			currentPerson.getPersonalCalendar().addEvent(event);
		}
	}
	
	private boolean contains(int id, List<Integer> l){
		for (Integer i : l){
			if (id == i)
				return true;
		}
		return false;
	}
	
	private List<Integer> initGroupsCurrentUser(){
		if (!isLoggedIn())
			return null;
		List<Integer> eventIds = new ArrayList<Integer>();
		List<Group> groups = new ArrayList<Group>();
		List<HashMap<String, String>> dbGroups = ConnectionMySQL.getGroups(currentPerson.getUsername());
		if (dbGroups == null){
			System.out.println("setGroups connection null");
			groups = PersonInformation.getGroups();
		}
		else{
			for (Map<String, String> g : dbGroups){
				Group group = new Group(g.get("groupName"), Integer.parseInt(g.get("groupId")));
				if (g.get("parent") != null){
					group.setParent(Integer.parseInt(g.get("parent")));
				}
				List<String> users = ConnectionMySQL.getGroupMembers(group.id);
				for (String user: users){
					for (Person p : allUsers){
						if (p.username.equals(user)){
							group.addMembers(p);
							break;
						}
					}
				}
				List<HashMap<String, String>> events = ConnectionMySQL.getEventsForGroup(group.id);
				for (Map<String, String> e: events){
					Event event = convertEventServer(e);
					group.getGroupCalendar().addEvent(event);
					eventIds.add(event.getID());
				}
				groups.add(group);
			}
			for (Group g1: groups){
				for (Group g2 : groups){
					if (g2.getParent() == g1.id){
						g1.addSubGroups(g2);
					}
				}
			}
		}
		for (Group g : groups){
			currentPerson.addGroup(g);
			activeCalendars.add(g.getGroupCalendar());
		}
		for (ProgramListener l : listeners){
			l.updateGroups(groups);
		}
		return eventIds;
//		groups.put("groupId", myRs.getString("groupId"));
//		groups.put("groupName", myRs.getString("groupName"));
	}
	
	private Person getPerson(String username){
		for (Person p: allUsers){
			if (p.username.equals(username))
				return p;
		}
		return null;
	}
	
	private Event convertEventServer(Map<String, String> e){
		Event event = new Event();
		event.setId(Integer.parseInt(e.get("eventId")));
		event.setEventName(e.get("eventName"));
		event.setLocation(e.get("location"));
		String[] stringStart = e.get("start").split(" "),
				stringEnd = e.get("end").split(" ");
//				stringSeen = e.get("lastSeen").split(" ");
		String[] dateStart = stringStart[0].split("-"),
				clockStart = stringStart[1].split(":");
		String[] dateEnd = stringEnd[0].split("-"),
				clockEnd = stringEnd[1].split(":");
		
		event.setStartTime(LocalDateTime.of(Integer.parseInt(dateStart[0]), Integer.parseInt(dateStart[1]), 
				Integer.parseInt(dateStart[2]), Integer.parseInt(clockStart[0]), Integer.parseInt(clockStart[1])));
		event.setEndTime(LocalDateTime.of(Integer.parseInt(dateEnd[0]), Integer.parseInt(dateEnd[1]), Integer.parseInt(dateEnd[2]), 
				Integer.parseInt(clockEnd[0]), Integer.parseInt(clockEnd[1])));
		event.setPriority(Priority.getPriority(Integer.parseInt(e.get("priority"))));
		int freq = Integer.parseInt(e.get("frequency"));
		if (e.get("freqDate") != null){
			String[] freqEnd = e.get("freqDate").split(" ");
			String[] freqDate = freqEnd[0].split("-"),
					freqClock = freqEnd[1].split(":");
			event.setFreq(freq, freq == -1, LocalDate.of(Integer.parseInt(freqDate[0]), Integer.parseInt(freqDate[1]), Integer.parseInt(freqDate[2])));
		}
		event.setInfo(e.get("info"));
		
		String creator = ConnectionMySQL.getEventCreator(event.getID());
		for (Person p : allUsers){
			if (p.username.equals(creator)){
				event.setMadeBy(p);
				break;
			}
		}
		
		List<HashMap<String, String>> applicants = ConnectionMySQL.getAppliances(event.getID());
		for (Map<String, String> a: applicants){
			String username = a.get("username");
			String appliance = a.get("appliance");
			Appliance app = null;
			if (appliance.equals("Waiting"))
				app = Appliance.Not_Answered;
			else if (appliance.equals("Attending"))
				app = Appliance.Attending;
			else if (appliance.equals("Not_Attending"))
				app = Appliance.Not_Attending;
			else if (appliance.equals("Maybe"))
				app = Appliance.Maybe;
			else
				app = Appliance.Late;
			Person p = getPerson(username);
			if (p != null)
				event.getAppliance().add(new EventAppliance(getPerson(username), app));
		}
//		events.put("lastChanged", myRs.getString("lastChanged"));
//		events.put("alarmId", myRs.getString("alarmId"));
//		events.put("lastSeen", myRs.getString("lastSeen"));
//		events.put("appliance", myRs.getString("appliance"));
//		events.put("isHidden", myRs.getString("isHidden"));
		return event;
	}
	
	public void changePasswordUser(String oldPassword, String newPassword){
		if (!isLoggedIn())
			return;
		if (currentPerson.isCorrectPassword(oldPassword)){
			if (ConnectionMySQL.changePassword(currentPerson.username, Person.hashPassword(newPassword))){
				currentPerson.changePassword(newPassword);
			}
			else {
				currentPerson.changePassword(newPassword);
				System.out.println("changePasswordUser connection false");
			}
			callChangePassword(true);
			callMessage(Message.PasswordChanged);
			return;
		}
		callMessage(Message.PasswordNotChanged);
		callChangePassword(false);
	}
	
	private void callChangePassword(boolean isChanged){
		for (ProgramListener l : listeners){
			l.passwordChange(isChanged);
		}
	}
	
	private void callCreateUser(boolean isCreated){
		for (ProgramListener l: listeners)
			l.userCreated(isCreated);
	}
	
	
	private void loginFailListeners(){
		for (ProgramListener l : listeners)
			l.loginFailed();
	}
	
	public void logout(){
		if (!isLoggedIn())
			return;
		currentPerson = null;
		activeCalendars.clear();
		if(updateThread != null){
			updateThread.cancel();
		}
		for (ProgramListener l : listeners)
			l.logout();
		
	}
	
	public void addListener(ProgramListener l){
		if (l != null)
			listeners.add(l);
	}
	
	public void removeListener(ProgramListener l){
		if (l != null)
			listeners.remove(l);
	}
	
	public boolean isLoggedIn(){
		return currentPerson != null;
	}
	
	public boolean isAdminLogIn(){
		return isLoggedIn() && currentPerson.admin;
	}
	public void deleteMessage(String username_from, String username_to, String info) {
		boolean exist = false;
       	for (int j = 0; j < mailInfo.size(); j++) {
       		String from2 = mailInfo.get(j).from;
			String to2 = mailInfo.get(j).to;
			String info2 = mailInfo.get(j).status;
			if(username_from.equals(from2) && username_to.equals(to2) && info2.equals(info)){
				mailInfo.remove(j);
			}
		}
		System.out.println("ATTEMPTING DELETE" + ConnectionMySQL.deleteMessage(username_from, username_to, info));
		
	}
	public HashMap<String,String> translateMail(String user_from,String user_to,String info){
		HashMap<String,String> a = new HashMap<String,String>();
		a.put("username_from", user_from);
		a.put("username_to", user_to);
		Pattern p0 = Pattern.compile("header:\\s(.*)\\n");
		Matcher m0 = p0.matcher(info);
		
		Pattern p1 = Pattern.compile("time:\\s(.*)\\n");
		Matcher m1 = p1.matcher(info);
		
		Pattern p2 = Pattern.compile("status:\\s(.*)\\n");
		Matcher m2 = p2.matcher(info);
		
		Pattern p3 = Pattern.compile("event_id:\\s(.*)");
		Matcher m3 = p3.matcher(info);
		
		if(m0.find()){
			System.out.println(m0.group(1));
			a.put("header", m0.group(1));
		}else{
			System.out.println("a.put(header, null);");
			a.put("header", "null");
		}
		if(m1.find()){
			System.out.println(m1.group(1));
			a.put("time", m1.group(1));
		}else{
			System.out.println("a.put(time, null);");
			a.put("time", "null");
		}
		if(m2.find()){
			System.out.println(m2.group(1));
			a.put("status", m2.group(1));
		}else{
			System.out.println("a.put(status, null);");
			a.put("status", "null");
		}
		if(m3.find()){
			System.out.println(m3.group(1));
			a.put("event_id", m3.group(1));
		}else{
			System.out.println("a.put(event_id, null);");
			a.put("event_id", "null");
		}
//		System.out.println("HER E HASH" + a);
//		System.out.println("header: Hendelse endret \ntime: "+LocalDateTime.now()+"\nstatus: En event har blitt endret p�! For � se n�rmere p� dette g� til innboksen og klikk p� hendelsen! \nevent_id: " + 26232);
		return a;
		
		
	}
}
