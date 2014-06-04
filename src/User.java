import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class User implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7319912117333397675L;
	private String login;
	private String pwd;
	private String clientId = null;
	private boolean isConnected = false;
	private HashMap<String, ArrayList<String>> conversations = new HashMap<String, ArrayList<String>>();
	

	public User()
	{

	}
	
	public User(String login, String pwd)
	{
		this.login = login;
		this.pwd = pwd;
	}
	
	public void createConversation(String key) {
		ArrayList<String> messages = new ArrayList<String>();
		conversations.put(key, messages);
	}
	
	public ArrayList<String> getConversation(String key) {
		return conversations.get(key);
	}

	public void setConversation(String key, String message) {
		conversations.get(key).add(message);
	}
	
	public void removeConversation(String key) {
		conversations.remove(key);
	}
	
	public HashMap<String, ArrayList<String>> getConversations() {
		return conversations;
	}
	
	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	
	
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin()
	{
		return login;
	}	
	
	public void setId(String id)
	{
		this.clientId = id;
	}
	
	public String getId()
	{
		return clientId;
	}
	
	public String toString() {
		return "Login: " + login + " IsConnected: " + isConnected + " ClientID: " + clientId;
		
	}
}
