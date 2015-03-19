package components;

import java.util.ArrayList;
import java.util.List;

import classes.MailInfo;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import gui.Component;
import gui.Main;
import gui.Main.GoToEvent;

public class Inbox extends Component{

	private final List<MailBox> mails;
	private final VBox box;
	private final GoToEvent goEvent;
	
	public Inbox(Pane parent, GoToEvent goToEvent) {
		super(parent);
		mails = new ArrayList<MailBox>();
		box = new VBox(10);
		this.goEvent = goToEvent;
		
		ScrollPane scroll = new ScrollPane(box);
		scroll.setCenterShape(true);
		
		scroll.setPrefViewportHeight(Main.SCREENHEIGHT - 300);
		scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		getChildren().add(scroll);
//		addMail(new MailInfo("Invitasjon til event", "Jon Jonsen", "23.2.2015", "Du er invitert til en ny event klokken 12.", 1));
//		addMail(new MailInfo("Invitasjon til event", "Jon Jonsen", "23.2.2015", "Du er invitert til en ny event klokken 12.", 1));
//		addMail(new MailInfo("Invitasjon til event", "Jon Jonsen", "23.2.2015", "Du er invitert til en ny event klokken 12.", 1));
//		addMail(new MailInfo("Invitasjon til event", "Jon Jonsen", "23.2.2015", "Du er invitert til en ny event klokken 12.", 1));
//		addMail(new MailInfo("Invitasjon til event", "Jon Jonsen", "23.2.2015", "Du er invitert til en ny event klokken 12.", 1));
//		addMail(new MailInfo("Invitasjon til event", "Jon Jonsen", "23.2.2015", "Du er invitert til en ny event klokken 12.", 1));
//		addMail(new MailInfo("Invitasjon til event", "Jon Jonsen", "23.2.2015", "Du er invitert til en ny event klokken 12.", 1));
//		addMail(new MailInfo("Invitasjon til event", "Jon Jonsen", "23.2.2015", "Du er invitert til en ny event klokken 12.", 1));
//		addMail(new MailInfo("Invitasjon til event", "Jon Jonsen", "23.2.2015", "Du er invitert til en ny event klokken 12.", 1));
//		addMail(new MailInfo("Invitasjon til event", "Jon Jonsen", "23.2.2015", "Du er invitert til en ny event klokken 12.", 1));
//		addMail(new MailInfo("Invitasjon til event", "Jon Jonsen", "23.2.2015", "Du er invitert til en ny event klokken 12.", 1));
		
		scroll.setLayoutX(100);
	}
	
	public void addMail(MailInfo mailInfo){
		MailBox box = new MailBox(mailInfo, goEvent);
		mails.add(box);
		this.box.getChildren().add(0, box);
		
	}
	
	public void addAllMail(MailInfo... mailInfo){
		for (MailInfo mail: mailInfo){
			addMail(mail);
		}
	}
	
	

}
