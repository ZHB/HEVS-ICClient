
public interface ClientObservable {
	
	public void addObserver(ClientObserver obs);
	
	public void removeObserver(ClientObserver obs);
	
	public void notifyDisconnection();
	
	public void notifyLogin(String login, String pwd);
	
	public void notifyRegistration(String login, String pwd);
	
}
