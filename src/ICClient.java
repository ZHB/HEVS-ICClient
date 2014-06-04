import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import security.Security;

public class ICClient
{

    private Socket serverSocket;
    private String servIp = "127.0.0.1";
    private int servPort = 1096;
    private ObjectOutputStream outputObjectToServer = null;
    private ObjectInputStream inputObjectFromServer = null;
      
    private ChatGUI chatGUI;
    
    private User user;
    
    // Contains registred users with username as key
    private HashMap<String, User> registredUsers;
    
    public ICClient()
    {
        init();
    }

    /**
     * Init function
     */
    private void init()
    {
    	user = new User();
    	//registredUsers = new HashMap<String, User>();
    	
    	chatGUI = new ChatGUI();
        chatGUI.addObserver(new ClientNotification());
        chatGUI.setVisible(true);
        chatGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatGUI.pack(); 
    	
    	try 
        {
            serverSocket = new Socket(servIp, servPort);
            Thread inOutThread = new Thread(new InOutClient());
            inOutThread.start();

        }
    	catch (UnknownHostException e)
    	{
            e.printStackTrace();
        }
    	catch (IOException e)
    	{
            e.printStackTrace();
        }
    }

    /**
     * Internal class that handles input and output stream to server socket of the CLIENT
     * 
     *
     */
    public class InOutClient implements Runnable
    {
        public InOutClient()
        {
            try
            {
                outputObjectToServer = new ObjectOutputStream(serverSocket.getOutputStream());
                inputObjectFromServer = new ObjectInputStream(serverSocket.getInputStream());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        // Send/Recieve
        @Override
        public void run()
        {
            try 
            {
            	// Codes for action
				byte messageType;
				
				while(true) 
				{
            		messageType = inputObjectFromServer.readByte();
            		
					switch(messageType)
					{
					case 11: // on login
						chatGUI.setLoggedInButtons();
						chatGUI.setTitle("Welcome " + user.getLogin());
            			break;
            			
					case 12: // on logout
						chatGUI.setLoggedOffButtons();
						registredUsers.clear();
						chatGUI.updateUsersList(registredUsers);
            			break;
            			
					case 21: // on message reception
            			chatGUI.setTextarea(inputObjectFromServer.readUTF());
            			break;
            			
					case 100: // update the User Object on change
						try
						{
							user = (User) inputObjectFromServer.readObject();
						}
						catch (ClassNotFoundException e2)
						{
							e2.printStackTrace();
						}
            			break;
            			
					case 101: // update users list on change
						
						try
						{
							registredUsers = (HashMap<String, User>) inputObjectFromServer.readObject();

							// update GUI users list only for connected clients
							if(user.isConnected()) 
							{
								registredUsers.remove(user.getLogin());
								chatGUI.updateUsersList(registredUsers);
							}
						}
						catch (ClassNotFoundException e)
						{
							e.printStackTrace();
						}
            			break;
					case 120: // set the Client ID to the user 
						String idClient = inputObjectFromServer.readUTF();
						user.setId(idClient);
					
            			break;
					case 121:
						try {
							ArrayList<String> messages = (ArrayList<String>)inputObjectFromServer.readObject();
							chatGUI.updateTextArea(messages);
						} 
						catch (ClassNotFoundException e) 
						{
							e.printStackTrace();
						}
						
						break;
            		}
            	}
            }
            catch (IOException e)
            {
            	
            }
        }
    }
    
    public class ClientNotification implements ClientObserver
    {

		@Override
		public void notifyMessage(String s)
		{
			try
			{
				outputObjectToServer.writeByte(21); // Send message code
				outputObjectToServer.writeObject(new Message(s));
				outputObjectToServer.writeObject(user);
				outputObjectToServer.flush();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void login(String login, String pwd)
		{
			try 
			{
				
				// set the user with his login informations
				user.setLogin(login);
				user.setPwd(new Security().hashWithSha256(pwd));
				
				// send the user to the server for login process
				outputObjectToServer.writeByte(11);
				outputObjectToServer.writeObject(user);
				outputObjectToServer.flush();
				outputObjectToServer.reset();
			} 
			catch (NoSuchAlgorithmException | IOException e) 
			{
				e.printStackTrace();
			}
		}

		@Override
		public void register(String login, String pwd)
		{
			try 
			{
				// set the new user 
				user.setLogin(login);
				user.setPwd(new Security().hashWithSha256(pwd));
				
				// send the new user to the server for registration
				outputObjectToServer.writeByte(1); // register code
				outputObjectToServer.writeObject(user);
				outputObjectToServer.flush();
				outputObjectToServer.reset();
			}
			catch (NoSuchAlgorithmException | IOException e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void notifyUserSelection(User u)
		{
			try
			{
				outputObjectToServer.writeByte(111);
				outputObjectToServer.writeObject(u);
				outputObjectToServer.flush();
				outputObjectToServer.reset();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void notifyUnregister()
		{
			try 
			{
				outputObjectToServer.writeByte(2);
				outputObjectToServer.flush();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		@Override
		public void notifyLogout()
		{
			try
			{
				outputObjectToServer.writeByte(12); // logout code
				outputObjectToServer.flush();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		@Override
		public void notifyCloseChat()
		{
			try
			{
				outputObjectToServer.writeByte(13);
				outputObjectToServer.flush();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
    }
}
