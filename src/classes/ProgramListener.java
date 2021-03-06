package classes;

import java.util.List;

public interface ProgramListener {
	public void loginFailed();
	public void loginSuccess(Person person);
	public void logout();
	public void userCreated(final boolean isCreated);
	public void passwordChange(final boolean isChanged);
	public void sendMessage(Message msg);
	public void updateGroups(List<Group> groups);
	public void showEvent(Event event);
	public void updateRoomNames(Room... rooms);
	public void sendNotification(String notif);
	public void updateCalendar(Calendar[] cal);
	public void setAllPersons(List<Person> persons);
	public void updatePersonInformation(Person person);
	public void groupAdded(boolean added);
	public void personAdded(boolean added);
	public void createMail(MailInfo mailInfo);
}
