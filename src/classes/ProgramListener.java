package classes;

public interface ProgramListener {
	public void loginFailed();
	public void loginSuccess(String username, String name);
	public void logout();
}
