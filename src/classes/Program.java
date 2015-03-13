package classes;

import gui.DebugMain;

import java.util.ArrayList;
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

	private Person currentPerson;
	
	public Program(){
		listeners = new ArrayList<ProgramListener>();
		activeCalendars = new ArrayList<Calendar>();
		unactive = new ArrayList<Calendar>();
		//opprett kobling med database og/eller socketprogram
	}
	
	public void createEvent(Event event, Calendar... cal){
		//add events to server
//		for (Calendar cals: cal)
//			cals.addEvent(event);
//		callMessage(Message.EventAdded);
		System.out.println(activeCalendars);
		currentPerson.getPersonalCalendar().addEvent(event);
		event.setMadeBy(currentPerson);
		updateCalendarListeners();
		callMessage(Message.EventAdded);
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
		System.out.println("Event " + eventId + " was requested");
		for (ProgramListener l : listeners){
			l.showEvent(DebugMain.getEvents().get(3));

			
		}
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
	
	
	private void callMessage(Message msg){
		for (ProgramListener l : listeners)
			l.sendMessage(msg);
	}
	
	
	public void createUser(String username, String password, String name){
		if (username == null || password == null || name == null || currentPerson == null){
			callCreateUser(false);
			return;
		}
		if (username.length() < 6 || username.length() > 15 || password.length() < 6 || password.length() > 25 || !currentPerson.admin){
			callCreateUser(false);
			return;
		}
		callCreateUser(CreateUser.isValidNewUser(username, Person.hashPassword(password), name));
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
			currentPerson = new Person("admin", "admin", "Ola Nordmann", true);
			return;
		}
		Map<String, String> info = PersonInformation.getPersonInformation(username, Person.hashPassword(password));

		// Map<String, String> infoFromDatabase = ConnectionMySQL.getUserInfo(username);
		String stringId = info.get("personid");
		// String infoUsername = infoFromDatabase.get("username") + ", " + infoFromDatabase.get("password");
		System.out.println(stringId);
		if (stringId == null){
			if (DEBUG){
				System.out.println("Stringid er null");
			}
			loginFailListeners();
			return;
		}
		for (char a : stringId.toCharArray()){
			if ("0123456789".indexOf(a) == -1){
				if (DEBUG){
					System.out.println(stringId + " kan ikke parses");
				}
				loginFailListeners();
				return;
			}
		}

		String usernameDatabase = info.get("username");
		String passwordDatabase = info.get("password");
		String name = info.get("name");
		if (!Person.hashPassword(password).equals(passwordDatabase) || username != usernameDatabase){
			if (DEBUG){
				System.out.println("Feil med passord");
			}
			for (ProgramListener l : listeners)
				l.loginFailed();
			return;
		}
		currentPerson = new Person(usernameDatabase, passwordDatabase, name, false);
		activeCalendars.add(currentPerson.getPersonalCalendar());
		for (ProgramListener l : listeners)
			l.loginSuccess(currentPerson);
		updateCalendarListeners();
	}
	
	public void changePasswordUser(String oldPassword, String newPassword){
		if (!isLoggedIn())
			return;
		if (currentPerson.isCorrectPassword(oldPassword)){
			if (PersonInformation.changePassword(currentPerson.username, Person.hashPassword(oldPassword), Person.hashPassword(newPassword))){
				currentPerson.changePassword(newPassword);
				callChangePassword(true);
				return;
			}
		}
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
}
