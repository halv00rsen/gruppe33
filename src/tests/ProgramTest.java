package tests;

import java.util.List;

import classes.Calendar;
import classes.Event;
import classes.Group;
import classes.Message;
import classes.Program;
import classes.ProgramListener;
import classes.Room;

public class ProgramTest implements ProgramListener{
	
	public ProgramTest(){
		
		Program p = new Program();
		p.addListener(this);
		p.personLogin("halvor1", "123456");
		p.changePasswordUser("112345", "heisann");
		p.changePasswordUser("123456", "heisann");
		p.changePasswordUser("heisann", "hadebra");
	}

	public static void main(String[] args){
//		ProgramTest t = new ProgramTest();
		System.out.println("true: " + num(1));
		System.out.println("false: " + num(2));
		System.out.println("true: " + string("user"));
		System.out.println("false: " + string("Uas"));
	}
	
	public static boolean num(Object nr){
//		System.out.println(nr.toString());
		return ("" + 1).equals(nr.toString());
	}
	
	public static boolean string(Object str){
		return "user".equals(str);
	}

	public void loginFailed() {
		System.out.println("feil login");
	}

	public void loginSuccess(String username, String name) {
		System.out.println("login suksess");
		System.out.println("Username: " + username + ", name: " + name);
	}

	public void logout() {
		System.out.println("logget ut");
	}

	public void userCreated(final boolean isCreated) {
		System.out.println((isCreated?"Bruker laget": "Bruker ikke laget"));
	}

	public void passwordChange(final boolean isChanged) {
		System.out.println((isChanged? "Passord byttet":"Passord ikke byttet"));
	}
	
	public void setEvents(Event... events){
		System.out.println("Events er satt");
	}
	
	@Override
	public void sendMessage(Message msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGroups(Group... groups) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRoomNames(Room... rooms) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendNotification(String notif) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCalendar(List<Calendar> cal) {
		// TODO Auto-generated method stub
		
	}
	
	
}
