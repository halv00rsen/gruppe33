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
	
	public Program(){
		listeners = new ArrayList<ProgramListener>();
	}
	
	public void createUser(String username, String password, String name){
		if (username == null || password == null || name == null || currentPerson != null){
			createUserFailListeners();
			return;
		}
		if (username.length() < 6 || username.length() > 15 || password.length() < 6 || password.length() > 25){
			createUserFailListeners();
			return;
		}
		if (CreateUser.isValidNewUser(username, password, name)){
			for (ProgramListener l : listeners)
				l.userCreated();
		}else
			createUserFailListeners();
	}
	
	public void personLogin(String username, String password){
		if (username == null || password == null || isLoggedIn()){
			loginFailListeners();
			return;
		}
		Map<String, String> info = PersonInformation.getPersonInformation(username, password);
		String stringId = info.get("personid");
		if (stringId == null){
			loginFailListeners();
			if (DEBUG){
				System.out.println("Stringid er null");
			}
			return;
		}
		for (char a : stringId.toCharArray()){
			if ("0123456789".indexOf(a) == -1){
				loginFailListeners();
				if (DEBUG){
					System.out.println(stringId + " kan ikke parses");
				}
				return;
			}
		}
		String usernameDatabase = info.get("username");
		String passwordDatabase = info.get("password");
		String name = info.get("name");
		int personid = Integer.parseInt(stringId);
		if (password != passwordDatabase || personid == -1 || username != usernameDatabase){
			for (ProgramListener l : listeners)
				l.loginFailed();
			return;
		}
		currentPerson = new Person(usernameDatabase, passwordDatabase, personid, name);
		for (ProgramListener l : listeners)
			l.loginSuccess(username, name);
	}
	
	private void createUserFailListeners(){
		for (ProgramListener l : listeners)
			l.userNotCreated();
	}
	
	private void loginFailListeners(){
		for (ProgramListener l : listeners)
			l.loginFailed();
	}
	
	public void logout(){
		if (!isLoggedIn())
			return;
		currentPerson = null;
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
