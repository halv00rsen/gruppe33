package classes;

public interface ProgramListener {
	public void loginFailed();
	public void loginSuccess(String username, String name);
	public void logout();
	public void userCreated();
	public void userNotCreated();
}
