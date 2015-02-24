package tests;

import classes.Program;
import classes.ProgramListener;

public class ProgramTest implements ProgramListener{
	
	public ProgramTest(){
		
		Program p = new Program();
		p.addListener(this);
		p.personLogin("kåre", "1234");
		p.logout();
		p.createUser("halo123", "12345", "Jørgen Halvorsen");
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

	public void userCreated() {
		System.out.println("Brukeren ble opprettet");
	}

	public void userNotCreated() {
		System.out.println("Brukeren ble ikke opprettet");
	}
}
