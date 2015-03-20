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
import gui.Main.MessageDeleter;

public class Inbox extends Component{

	private final List<MailBox> mails;
	private final VBox box;
	private final GoToEvent goEvent;
	public MessageDeleter mesDel;
	
	public Inbox(Pane parent, GoToEvent goToEvent,MessageDeleter mesDel) {
		super(parent);
		mails = new ArrayList<MailBox>();
		box = new VBox(10);
		this.goEvent = goToEvent;
		this.mesDel = mesDel;
		ScrollPane scroll = new ScrollPane(box);
		scroll.setCenterShape(true);
		
		scroll.setPrefViewportHeight(Main.SCREENHEIGHT - 300);
		scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		getChildren().add(scroll);
		scroll.setLayoutX(100);
	}
	
	public void addMail(MailInfo mailInfo){
		MailBox mailbox = new MailBox(mailInfo, goEvent,mesDel,this);
		for (int i = 0; i < mails.size(); i++) {
			if(mails.get(i).equals(mailbox)){
				return;
			}
		}
		mails.add(mailbox);
		this.box.getChildren().add(0, mailbox);
		
	}
	public void removeMail(MailBox mailbox){
		mails.remove(mailbox);
		this.box.getChildren().remove(mailbox);
	}
	public void addAllMail(MailInfo... mailInfo){
		for (MailInfo mail: mailInfo){
			addMail(mail);
		}
	}
	
	

}
