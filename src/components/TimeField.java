package components;


import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TextField;

public class TimeField extends TextField implements TimeFieldMain{

	private final List<FieldListener> listeners;
	private TimeFieldMain otherTime;
	private final boolean isFirst;
	
	public TimeField(boolean isFirst){
		super();
		this.setPromptText("00:00");
		listeners = new ArrayList<FieldListener>();
		this.isFirst = isFirst;
	}
	

	public void setOtherTime(TimeFieldMain otherTime){
		this.otherTime = otherTime;
	}
	
	public void addListener(FieldListener l){
		listeners.add(l);
	}
	
	public void removeListener(FieldListener l){
		listeners.remove(l);
	}
	
	
	private void checkInput(final boolean correct){
		for (FieldListener l: listeners){
			if (correct)
				l.setSuccess();
			else
				l.setError();
		}
	}
	
	
	
	private boolean isCorrectInput(){
		final String input = getText();
		if (input.contains(":")){
			final String[] time = input.split(":");
			if (time.length == 2){
				if ((time[0].matches("(0|1)[0-9]") || time[0].matches("2[0-3]")) && time[1].matches("[0-5][0-9]")){
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void replaceText(int start, int end, String text){
		
//		System.out.println(start + "  " + end + "  " + text);
		if (!text.matches("[0-9]*") || (super.getText().length() == 5 && !text.equals(""))){
			return;
		}
		final int length = super.getText().length();
//		if (length )
		if (length == 1 && !text.equals("")){
			super.replaceText(start, end, text + ":");
		}
		else if (super.getText().length() == 3 && text.equals("")){
			super.setText(super.getText().substring(0,1));
		}
		else
			super.replaceText(start, end, text);
//		super.setBlendMode(BlendMode.RED);
		if (!isFirst && super.getText().length() == 5){
			otherTime.requestTime();
		}else
			checkInput(isCorrectInput());
		
	}
	
	public boolean isValid(){
		return isCorrect;
	}
	
	@Override
	public void replaceSelection(String text){
		if ("".equals(text)){
			super.replaceSelection(text);
			checkInput(false);
		}
	}
	
	private boolean isCorrect;
	
	public void sendCurrentTime(int hour, int minutes) {
		if (isCorrectInput()){
			final String[] time = getText().split(":");
			int thisHour = Integer.parseInt(time[0]), thisMinutes = Integer.parseInt(time[1]);
			if (hour < thisHour || (hour == thisHour && minutes < thisMinutes)){
				checkInput(true);
				isCorrect = true;
				return;
			}
		}
		checkInput(false);
		isCorrect = false;
	}

	public void requestTime() {
		if (!isFirst){
			return;
		}
		if (isCorrectInput() && otherTime != null){
			final String[] time = getText().split(":");
			int hour = Integer.parseInt(time[0]), minutes = Integer.parseInt(time[1]);
			otherTime.sendCurrentTime(hour, minutes);
		}
	}
	
	
	public interface FieldListener {
		public void setSuccess();
		public void setError();
	}
	
}
