package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.PersonInformation;

public class Program {

	private Person currentPerson;
	private final List<ProgramListener> listeners;
	
	public Program(){
		listeners = new ArrayList<ProgramListener>();
	}
	
	public void personLogin(String username, String password){
		Map<String, String> info = PersonInformation.getPersonInformation(username, password);
		String usernameDatabase, passwordDatabase, name;
		int personid = -1;
		try{
			usernameDatabase = info.get("username");
			passwordDatabase = info.get("password");
			name = info.get("name");
			personid = Integer.parseInt(info.get("personid"));
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("Feil med parsing fra database");
			loginFailListeners();
			return;
		}
		if (password != passwordDatabase || personid == -1 || username != usernameDatabase){
			for (ProgramListener l : listeners)
				l.loginFailed();
			return;
		}
		currentPerson = new Person(usernameDatabase, passwordDatabase, personid, name);
		for (ProgramListener l : listeners)
			l.loginSuccess();
	}
	
	private void loginFailListeners(){
		for (ProgramListener l : listeners)
			l.loginFailed();
	}
	
	public void logout(){
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
