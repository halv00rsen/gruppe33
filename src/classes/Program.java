package classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import classes.Calendar.TypeOfCalendar;
import database.ConnectionMySQL;
import database.CreateUser;
import database.PersonInformation;

public class Program {

	public final static boolean DEBUG = true;
	private final List<ProgramListener> listeners;
	private final List<Calendar> activeCalendars, unactive;
	private final List<Person> allUsers;

	private Person currentPerson;
	
	public Program(){
		listeners = new ArrayList<ProgramListener>();
		activeCalendars = new ArrayList<Calendar>();
		unactive = new ArrayList<Calendar>();
		allUsers = new ArrayList<Person>();
		//opprett kobling med database og/eller socketprogram
	}
	
	public void createGroup(String name){
		int groupId = ConnectionMySQL.createGroup(name, 0);
		if (groupId == -1){
			if (DEBUG){
				System.out.println("Create group connection null");
				currentPerson.getGroups().add(new Group(name, 1));
				updateGroups();
			}
			callMessage(Message.GroupNotCreated);
			return;
		}else{
			currentPerson.getGroups().add(new Group(name, groupId));
		}
		updateGroups();
	}
	
	private void updateGroups(){
		for (ProgramListener l : listeners)
			l.updateGroups(currentPerson.getGroups());
	}
	
	public void createEvent(Event e, Calendar cal){
		//add events to server
//		for (Calendar cals: cal)
//			cals.addEvent(event);
//		callMessage(Message.EventAdded);
		e.setMadeBy(currentPerson);
		
		if(e.getFreq() != null){
			
			if(e.getFreq()==0){
				createEventServer(e, (cal == null ? currentPerson.getPersonalCalendar(): cal));
				
			}else if(e.getFreq()>0 || e.isMonthlyRepeat()){
				int i = e.getFreq();
				Event lastEvent = e;
				Event nextEvent;
				while(true){
					nextEvent = new Event();
					nextEvent.overrideEvent(e); // må ha ny ID da...
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
			updateAppliance(event, p);
		}
		event.setId(eventId);
		cal.addEvent(event);
	}
	
	public void updateAppliance(Event event, EventAppliance eventAppliance){
		ConnectionMySQL.setAppliance(event.getID(), eventAppliance.getPerson().getUsername(), eventAppliance.getAppliance().getStateName());
	}
	public void deleteEvent(Event event, Calendar...cals){
		//remove event from database/server
		currentPerson.getPersonalCalendar().removeEvent(event);
		updateCalendarListeners();
		callMessage(Message.EventDeleted);
//		for (Calendar cal: cals)
//			cal.removeEvent(event);
//		callMessage(Message.EventDeleted);
	}
	
	public void requestEvent(int eventId){
//		for (ProgramListener l : listeners){
//			l.showEvent(DebugMain.getEvents().get(3));
//		}
	}
	
	public void addCalendar(Object id, TypeOfCalendar type){
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
	
	public void removeCalendar(Object id, TypeOfCalendar type){
		for (Calendar c : unactive){
			if (c.isOwner(id, type))
				return;
		}
		for (Calendar c : activeCalendars){
			if (c.isOwner(c, type)){
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
				Person p1 = new Person(p.get("username"), null, p.get("firstname"), p.get("lastname"), false);
				list.add(p1);
			}
		}
		for (Person p : list){
			allUsers.add(p);
		}
		for (ProgramListener l : listeners){
			l.setAllPersons(list);
		}
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
			if (!Person.hashPassword(password).equals(passwordDatabase) || username != usernameDatabase){
				if (DEBUG){
					System.out.println("Feil med passord");
				}
				for (ProgramListener l : listeners)
					l.loginFailed();
				return;
			}
			currentPerson = new Person(usernameDatabase, passwordDatabase, firstname, lastname, Boolean.getBoolean(info.get("isAdmin")));
			currentPerson.setOtherInfo(info.get("phone"), info.get("email"));
		}
		
		activeCalendars.add(currentPerson.getPersonalCalendar());
		for (ProgramListener l : listeners)
			l.loginSuccess(currentPerson);
		
		updateCalendarListeners();
		sendOutPersons();
		initGroupsCurrentUser();
	}
	
	private void initGroupsCurrentUser(){
		if (!isLoggedIn())
			return;
		List<Group> groups = new ArrayList<Group>();
		List<HashMap<String, String>> dbGroups = ConnectionMySQL.getGroups(currentPerson.getUsername());
		if (dbGroups == null){
			System.out.println("setGroups connection null");
			groups = PersonInformation.getGroups();
		}
		else{
			for (Map<String, String> g : dbGroups){
				Group group = new Group(g.get("groupName"), Integer.parseInt(g.get("groupId")));
				if (!g.get("parent").equals("null")){
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
		for (Group g : groups)
			currentPerson.addGroup(g);
		for (ProgramListener l : listeners){
			l.updateGroups(groups);
		}
//		groups.put("groupId", myRs.getString("groupId"));
//		groups.put("groupName", myRs.getString("groupName"));
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
	
	public static ArrayList<Room> getRooms() {
		Room r1 = new Room(0, "Det gule");
		Room r2 = new Room(1, "Det andre");
		
		ArrayList<Room> list = new ArrayList<Room>(); 
		list.addAll(Arrays.asList(r1, r2));
		return list;
		
	}
}
