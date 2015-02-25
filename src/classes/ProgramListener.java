package classes;

public interface ProgramListener {
	public void loginFailed();
	public void loginSuccess(String username, String name);
	public void logout();
	public void userCreated(final boolean isCreated);
	public void passwordChange(final boolean isChanged);
}
