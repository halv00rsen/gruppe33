package tests;

import classes.Person;
import classes.Program;
import classes.ProgramListener;

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
		ProgramTest t = new ProgramTest();
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
}
