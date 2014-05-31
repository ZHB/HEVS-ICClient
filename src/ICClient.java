import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import security.Security;

public class ICClient {

    private Socket serverSocket;

    private ObjectOutputStream outputObjectToServer = null;
    private ObjectInputStream inputObjectFromServer = null;
      
    byte[] serverIP = {127, 0, 0, 1};
    
    private ChatGUI clientGUI;
    private User user = new User();
    
    
    private HashMap<String, User> registredUsers = new HashMap<String, User>();
    
    public ICClient() {
        try 
        {
            serverSocket = new Socket("192.168.0.12", 1096);

            //InetAddress serverAddress = InetAddress.getByAddress("",serverIP);
            //serverSocket = new Socket(serverAddress, 1096);
            
            clientGUI = new ChatGUI();
            clientGUI.addObserver(new ClientNotification());
            clientGUI.setVisible(true);
            clientGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            clientGUI.pack(); 
           
            Thread inOutThread = new Thread(new InOutClient());
            inOutThread.start();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    public class InOutClient implements Runnable {

        public InOutClient()
        {
            try {
                outputObjectToServer = new ObjectOutputStream(serverSocket.getOutputStream());
                inputObjectFromServer = new ObjectInputStream(serverSocket.getInputStream());
            } catch (IOException e) {
            	System.out.println("dsdd");
                e.printStackTrace();
            }
        }

        // receiving from server
        @Override
        public void run() {

            try 
            {
            	boolean done = false;
				byte messageType;
				
				while(!done) 
				{
            		messageType = inputObjectFromServer.readByte();
            		
					switch(messageType) {
					case 11: // login
						clientGUI.setLoggedInButtons();
            			break;
					case 12: // logout
						clientGUI.setLoggedOffButtons();
						
						// clear registered users list
						registredUsers.clear();
						clientGUI.updateUsersList(registredUsers);
            			break;
					case 21: // messages
            			clientGUI.setTextarea(inputObjectFromServer.readUTF());
            			break;
					case 100: // update the User Object
						try {
							user = (User) inputObjectFromServer.readObject();
						} catch (ClassNotFoundException e2) {
							e2.printStackTrace();
						}
            			break;
					case 101: // update users list
						
						try {
							registredUsers = (HashMap<String, User>) inputObjectFromServer.readObject();

							// update GUI users list only for connected clients
							if(user.isConnected()) 
							{
								clientGUI.updateUsersList(registredUsers);
							}
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
            			break;
        			case 13: // updtate user status as connected = true
        				try {
							user = (User) inputObjectFromServer.readObject();
							
							System.out.println("Login : " + user.getLogin() + user.isConnected());
							if(user.isConnected()) {
								System.out.println("taille : " +registredUsers.size());
								clientGUI.updateUsersList(registredUsers);
							}
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
        				break;
        			case 60: // create user
        				try {
							user = (User) inputObjectFromServer.readObject();
							//users.put(user.getLogin(), user);
							
							System.out.println(user.getLogin() + user.isConnected());
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
        				break;
            		default:
            			done = true;
            		}
            	}
            } 
            catch (IOException e) {}
        }
    }
    
    
    public class ClientNotification implements ClientObserver {

		@Override
		public void notifyMessage(String s) {
			
			// create a new conversation for the user
			//user.createConversation("conv1");
			
			// add a message to a conversation
			//user.setConversation("conv1",  new Message(s));
			
			try {
				outputObjectToServer.writeByte(21); // Send message code
				outputObjectToServer.writeObject(new Message(s));
				outputObjectToServer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void login(String login, String pwd) {
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
		public void register(String login, String pwd) {
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
			} catch (NoSuchAlgorithmException | IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void notifyDisconnection() {
			try {
				outputObjectToServer.close();
				inputObjectFromServer.close();
	            serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void notifyUserSelection(List selectedUsers) {
			try {
				outputObjectToServer.writeByte(111);
				outputObjectToServer.writeObject(selectedUsers);
				outputObjectToServer.flush();
				outputObjectToServer.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
			

			// stocker la liste des tilisateurs sélectionnés
			
			// vérifier si fichier de conversation existe
			
			// s'il existe, charger le fichier de conversation
			
		}

		@Override
		public void notifyLogout() {
			try {
				outputObjectToServer.writeByte(12); // logout code
				outputObjectToServer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void notifyUnregister() {
			try 
			{
				outputObjectToServer.writeByte(2);
				outputObjectToServer.writeObject(user);
				outputObjectToServer.flush();
				outputObjectToServer.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	
    }
}
