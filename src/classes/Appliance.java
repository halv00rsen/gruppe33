package classes;

import java.util.Comparator;

import javafx.scene.paint.Color;

public enum Appliance{
	Attending("Kommer!","#00FF00",4),
	Not_Attending("Kommer ikke","#FF0000",1),
	Maybe("Kommer kanskje","#FFFF00",2),
	Late("Kommer seint","#AAFF00",3),
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
	public String toString(){
		return norsk;
	}
	public String getColor(){
		return color;
	}
	

}
