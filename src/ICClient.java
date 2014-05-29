import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import javax.swing.JFrame;
import security.Security;

public class ICClient {

    private Socket serverSocket;

    private ObjectOutputStream outputObjectToServer = null;
    private ObjectInputStream inputObjectFromServer = null;
      
    byte[] serverIP = {127, 0, 0, 1};
    
    private ChatGUI clientGUI;
   // private HashMap<String, User> users = new HashMap<String, User>();
    private User user;
    
    
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
                //outputObjectToServer.flush();
                inputObjectFromServer = new ObjectInputStream(serverSocket.getInputStream());
                
                //ObjectInputStream objectInputStream = new ObjectInputStream(serverSocket.getInputStream());
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
            		case 50: // successfully registered
            			clientGUI.setTextarea(inputObjectFromServer.readUTF());
            			break;
        			case 51: // user already exists
        				clientGUI.setTextarea(inputObjectFromServer.readUTF());
            			break;
        			case 52: // user already exists
        				clientGUI.setTextarea(inputObjectFromServer.readUTF());
            			break;
        			case 53: // user login success
        				clientGUI.setTextarea(inputObjectFromServer.readUTF());
            			break;
        			case 54: // update users list

						try 
						{
							//clientGUI.updateUsersList((HashMap<String, User>) inputObjectFromServer.readObject());
							//HashMap<String, User> users = (HashMap<String, User>) inputObjectFromServer.readObject();
							
							System.out.println("update list");
							
							registredUsers = (HashMap<String, User>) inputObjectFromServer.readObject();
							
							//if(user.isConnected()) {
								clientGUI.updateUsersList(registredUsers);
							//}
								//System.out.println(user.isConnected() + " L'utilisateur est connecté. Update liste !");
								
							
							//users = (HashMap<String, User>) inputObjectFromServer.readObject();
							
							
						} 
						catch (ClassNotFoundException e) 
						{
							e.printStackTrace();
						}
						
            			break;
        			case 55: // create user
        				try {
							user = (User) inputObjectFromServer.readObject();
							
							System.out.println("Login : " + user.getLogin() + user.isConnected());
							if(user.isConnected()) {
								System.out.println("sent list");
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
    				case 70: // create user
    					System.out.println("liste recue");
        				try {
        					registredUsers = (HashMap<String, User>) inputObjectFromServer.readObject();
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
		public void notifyMessage(String m) {
			System.out.println("Classe ICClient notifié de l'envoi de message");	
		}

		@Override
		public void login(String login, String pwd) {
			try 
			{
				Security sec = new Security();
				sec.hashWithSha256(pwd);
				
				// send data to the server
				outputObjectToServer.writeByte(2); // login code
				
				outputObjectToServer.writeUTF(login.trim());
				outputObjectToServer.writeUTF(sec.hashWithSha256(pwd));
				outputObjectToServer.flush();
				
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
				Security sec = new Security();
				sec.hashWithSha256(pwd);
				
				// send data to the server
				outputObjectToServer.writeByte(1); // register code
				outputObjectToServer.writeUTF(login.trim());
				outputObjectToServer.writeUTF(sec.hashWithSha256(pwd));
				outputObjectToServer.flush();
				
			} 
			catch (NoSuchAlgorithmException | IOException e) 
			{
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
    	
    }
}
