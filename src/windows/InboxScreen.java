package windows;

import classes.MailInfo;
import javafx.geometry.Insets;
import components.Inbox;
import gui.Main.GoToEvent;
import gui.Main.MessageDeleter;
import gui.Window;

public class InboxScreen extends Window{

	private GoToEvent goToEvent;
	MessageDeleter mesDel;
	public InboxScreen(GoToEvent goToEvent,MessageDeleter mesDel){
		this.mesDel = mesDel;
		this.goToEvent = goToEvent;
		init();
	}
	Inbox inbox;
	@Override
	public void init() {
		inbox = new Inbox(this, goToEvent,mesDel);
		borderPane.setMargin(inbox, new Insets(20));
		borderPane.setCenter(inbox);
	}

	public void createMail(MailInfo mailInfo) {
		inbox.addMail(mailInfo);
		
	}

}
