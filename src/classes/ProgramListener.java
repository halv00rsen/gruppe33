package classes;

public interface ProgramListener {
	public void loginFailed();
	public void loginSuccess(String username, String name);
	public void logout();
	public void userCreated(final boolean isCreated);
	public void passwordChange(final boolean isChanged);
	public void changeView(View view);
	public void sendMessage(Message msg);
	public void updateGroups(Group... groups);
	public void updateCalendar(Calendar... cal);
	public void updateRoomNames(Room... rooms);
	public void sendNotification(String notif);
}
