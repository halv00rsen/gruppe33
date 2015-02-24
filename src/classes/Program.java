package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.PersonInformation;

public class Program {

	public final static boolean DEBUG = true;
	private Person currentPerson;
	private final List<ProgramListener> listeners;
	
	public Program(){
		listeners = new ArrayList<ProgramListener>();
	}
	
	public void personLogin(String username, String password){
		if (username == null || password == null){
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
	
	private void loginFailListeners(){
		for (ProgramListener l : listeners)
			l.loginFailed();
	}
	
	public void logout(){
		if (currentPerson == null)
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
}
