import java.util.ArrayList;

/**
 * One discussion contains messages
 * @author SB
 *
 */
public class Discussion
{
	
	ArrayList<Message> messages;
	
	public Discussion()
	{
		
	}
	
	public void addMessage(Message msg)
	{
		messages.add(msg);
	}
}
