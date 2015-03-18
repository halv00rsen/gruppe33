package classes;

import java.util.Comparator;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

public enum Appliance{
	Attending("Kommer!","#00FF00",4),
	Not_Attending("Kommer ikke","#FF0000",1),
	Maybe("Kommer kanskje","FFAA00",2),
	Late("Kommer seint","#DDFF33",3),
	Not_Answered("Venter på svar..","#FFFFFF",0);
	String norsk;
	String color;
	int value;
	Appliance(String string,String color,int value){
		this.norsk = string;
		this.color = color;
		this.value = value;
	}
	public int getValue(){
		return this.value;
	}
	public String getStateName(){
		if(this.equals(Appliance.Attending)){
			return "Attending";
		}
		if(this.equals(Appliance.Not_Attending)){
			return "Not_Attending";
		}
		if(this.equals(Appliance.Maybe)){
			return "Maybe";
		}
		if(this.equals(Appliance.Late)){
			return "Late";
		}
		if(this.equals(Appliance.Not_Answered)){
			return "Not_Answered";
		}
		return "";

	}
	public String toString(){
		return norsk;
	}
	public String getColor(){
		return color;
	}
	

}
