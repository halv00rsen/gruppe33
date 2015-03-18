package classes;

public enum Appliance {
	Attending("Kommer!"),
	Not_Attending("Kommer ikke"),
	Maybe("Kommer kanskje"),
	Late("Kommer seint"),
	Not_Answered("Venter på svar..");
	String norsk;
	Appliance(String string){
		this.norsk = string;
	}
	public String toString(){
		return norsk;
	}
}
