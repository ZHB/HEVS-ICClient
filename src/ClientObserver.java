
public interface ClientObserver {
	
	public void notifyMessage(String m);
	
	public void login(String login, String pwd);
	
	public void notifyRegistration();
	
}
