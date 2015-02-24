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
		if (password != info.get("password")){
			for (ProgramListener l : listeners)
				l.logginFailed();
			return;
		}
		currentPerson = new Person(info.get("username"), info.get("password"), Integer.parseInt(info.get("personid")), info.get("name"));
		for (ProgramListener l : listeners)
			l.logginSuccess();
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
