package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.CreateUser;
import database.PersonInformation;

public class Program {

	public final static boolean DEBUG = true;
	private final List<ProgramListener> listeners;

	private Person currentPerson;
	private Calendar currentCalendar;
	
	public Program(){
		listeners = new ArrayList<ProgramListener>();
		//opprett kobling med database og/eller socketprogram
	}
	
	
	public void createUser(String username, String password, String name){
		if (username == null || password == null || name == null || currentPerson != null){
			callCreateUser(false);
			return;
		}
		if (username.length() < 6 || username.length() > 15 || password.length() < 6 || password.length() > 25){
			callCreateUser(false);
			return;
		}
		callCreateUser(CreateUser.isValidNewUser(username, Person.hashPassword(password), name));
	}
	
	public void personLogin(String username, String password){
		if (username == null || password == null || isLoggedIn()){
			loginFailListeners();
			return;
		}
		Map<String, String> info = PersonInformation.getPersonInformation(username, Person.hashPassword(password));
		String stringId = info.get("personid");
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
		int personid = Integer.parseInt(stringId);
		if (!Person.hashPassword(password).equals(passwordDatabase) || personid == -1 || username != usernameDatabase){
			if (DEBUG){
				System.out.println("Feil med passord");
			}
			for (ProgramListener l : listeners)
				l.loginFailed();
			return;
		}
		currentPerson = new Person(usernameDatabase, passwordDatabase, personid, name);
		currentCalendar = currentPerson.getPersonalCalendar();
		for (ProgramListener l : listeners)
			l.loginSuccess(username, name);
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
		currentCalendar = null;
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
	
	private boolean isLoggedIn(){
		return currentPerson != null;
	}
}
