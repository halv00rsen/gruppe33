package components;

import java.util.ArrayList;
import java.util.List;

import components.TimeField.FieldListener;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class NumberField extends TextField{
	
	private final List<FieldListener> listeners;
	
	public NumberField(){
//		setPromptText("Eks: 115");
		listeners = new ArrayList<FieldListener>();
		setOnKeyTyped(new EventHandler<KeyEvent>(){
			
			public void handle(KeyEvent event){
				checkInput();
			}
		});
	}
	
	private boolean isValid(){
		final String text = getText();
		return !text.matches("[0-9]*");
	}
	
	private void checkInput(){
		if (isValid()){
			for (FieldListener l : listeners)
				l.setError();
		}else{
			for (FieldListener l : listeners)
				l.setSuccess();
		}
	}
	
	public int getNumDays(){
		if (isValid()){
			int num = Integer.parseInt(getText());
			if (num < 365)
				return num;
		}
		return -1;
	}
	
	@Override
	public void replaceText(int start, int end, String text){
		if (getText().length() == 3 && !"".equals(text)){
			return;
		}
		if (text.matches("[0-9]*")){
			super.replaceText(start, end, text);
		}
	}
	
	@Override
	public void replaceSelection(String text){
		if (text.length() > 3)
			return;
		if (text.matches("[0-9]*")){
			super.replaceSelection(text);
		}
	}
	
	public void addListener(FieldListener l){
		listeners.add(l);
	}
	
	public void removeListener(FieldListener l){
		listeners.remove(l);
	}
}
