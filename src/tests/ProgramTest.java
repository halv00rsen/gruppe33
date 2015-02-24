package tests;

import classes.Program;
import classes.ProgramListener;

public class ProgramTest implements ProgramListener{
	
	public ProgramTest(){
		
		Program p = new Program();
		p.addListener(this);
		p.personLogin("k�re", "1234");
		p.logout();
	}

	public static void main(String[] args){
		ProgramTest t = new ProgramTest();
	}

	public void loginFailed() {
		System.out.println("feil login");
	}

	public void loginSuccess() {
		System.out.println("login suksess");
	}

	public void logout() {
		System.out.println("logget ut");
	}
}
