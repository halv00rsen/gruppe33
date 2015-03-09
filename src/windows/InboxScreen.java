package windows;

import components.Inbox;
import gui.Main.GoToEvent;
import gui.Window;

public class InboxScreen extends Window{

	private GoToEvent goToEvent;
	
	public InboxScreen(GoToEvent goToEvent){
		this.goToEvent = goToEvent;
		init();
	}
	
	@Override
	public void init() {
		Inbox inbox = new Inbox(this, goToEvent);
		getChildren().add(inbox);
	}

}