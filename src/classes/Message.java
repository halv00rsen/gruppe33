package classes;

public enum Message {
	EventAdded("Eventen ble lagt til."), EventNotAdded("Eventen ble ikke lagt til"), EventDeleted("Eventen ble slettet"),
	PersonUpdated("Din bruker ble oppdatert."), PersonNotUpdated("Din bruker ble ikke oppdatert."), PasswordChanged("Passordet ble endret"),
	PasswordNotChanged("Passordet ble ikke endret"), GroupCreated("Gruppen ble laget"), GroupNotCreated("Gruppen ble ikke laget"), EventNotDeleted("Eventen ble ikke slettet");
	
	public final String info;
	
	Message(String info){
		this.info = info;
	}
}
