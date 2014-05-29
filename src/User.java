import java.io.Serializable;
import java.util.ArrayList;


public class User implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7319912117333397675L;
	private String login;
	private String pwd;
	private boolean isConnected = false;
	private ArrayList<Discussion> discussions;
	
	public User()
	{

	}
	
	public User(String login, String pwd)
	{
		this.login = login;
		this.pwd = pwd;
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

	public void addDiscussion(Discussion disc)
	{
		discussions.add(disc);
	}
	
	public String getLogin()
	{
		return login;
	}
	
}
