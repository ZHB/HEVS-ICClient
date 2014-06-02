import java.awt.List;


public interface ClientObserver {
	
	public void login(String login, String pwd);
	
	public void notifyLogout();
	
	public void register(String login, String pwd);
	
	public void notifyDisconnection();
	
	public void notifyUnregister();
	
	public void notifyMessage(String m);

	public void notifyUserSelection(User u);
}
