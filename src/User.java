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
	private String id = null;
	private boolean isConnected = false;
	private HashMap<String, ArrayList<Message>> conversations = new HashMap<String, ArrayList<Message>>();
	

	public User()
	{

	}
	
	public User(String login, String pwd)
	{
		this.login = login;
		this.pwd = pwd;
	}
	
	public void createConversation(String key) {
		ArrayList<Message> messages = new ArrayList<Message>();
		conversations.put(key, messages);
	}
	
	public ArrayList<Message> getConversation(String key) {
		return conversations.get(key);
	}

	public void setConversation(String key, Message message) {
		conversations.get(key).add(message);
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
		this.id = id;
	}
	
	public String getId()
	{
		return id;
	}
	
	public String toString() {
		return "Login: " + login + " IsConnected: " + isConnected + " ClientID: " + id;
		
	}
}
