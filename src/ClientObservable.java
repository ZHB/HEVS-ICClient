import java.util.List;


public interface ClientObservable {
	
	public void addObserver(ClientObserver obs);
	
	public void removeObserver(ClientObserver obs);
	
	public void notifyCloseChat();
	
	public void notifyLogin(String login, String pwd);
	
	public void notifyLogout();
	
	public void notifyUnregister();
	
	public void notifyRegistration(String login, String pwd);
	
	public void notifyMessage(String m);
	
	public void notifyUserSelection(User u);
}
