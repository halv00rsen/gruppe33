package classes;

public enum Message {
	EventAdded("Eventen ble lagt til."), EventNotAdded("Eventen ble ikke lagt til"), EventDeleted("Eventen ble slettet");
	
	public final String info;
	
	Message(String info){
		this.info = info;
	}
}
