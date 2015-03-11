package classes;

import java.util.List;

public interface ProgramListener {
	public void loginFailed();
	public void loginSuccess(String username, String name);
	public void logout();
	public void userCreated(final boolean isCreated);
	public void passwordChange(final boolean isChanged);
	public void sendMessage(Message msg);
	public void updateGroups(Group... groups);
	public void showEvent(Event event);
	public void updateCalendar(List<Calendar> cal);
	public void updateRoomNames(Room... rooms);
	public void sendNotification(String notif);
}
