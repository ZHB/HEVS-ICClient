import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import security.Security;


public class ICClient {

    private Socket serverSocket;

    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;
    
    private ObjectOutputStream outputObjectToServer = null;
    private ObjectInputStream inputObjectFromServer = null;
      
    byte[] serverIP = {127, 0, 0, 1};
   
    
    public ICClient() {
        try {
        	 ChatGUI clientGUI = new ChatGUI();
             clientGUI.addObserver(new ClientNotification());
         

             clientGUI.setVisible(true);
             clientGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             clientGUI.pack(); 
             
            //serverSocket = new Socket("127.0.0.1", 1087);

            InetAddress serverAddress = InetAddress.getByAddress("",serverIP);
            serverSocket = new Socket(serverAddress, 1096);
            
           

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
                outputToServer = new PrintWriter(serverSocket.getOutputStream());
                inputFromServer = new BufferedReader( new InputStreamReader(serverSocket.getInputStream()));
                
                
                outputObjectToServer = new ObjectOutputStream(serverSocket.getOutputStream());
                outputObjectToServer.flush();
                inputObjectFromServer = new ObjectInputStream(serverSocket.getInputStream());
                
                //ObjectInputStream objectInputStream = new ObjectInputStream(serverSocket.getInputStream());
            } catch (IOException e) {
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
            		case 50:
            			System.out.println(inputObjectFromServer.readUTF());
            			break;
        			case 51:
        				System.out.println(inputObjectFromServer.readUTF());
            			break;
            		default:
            			done = true;
            		}
            	}
            	
                String line;

                while ((line = inputFromServer.readLine()) != null)
                {
                    final Object finalArg = line;

                      SwingUtilities.invokeLater(new Runnable() {
                       public void run() {
                           //txtarea.append(finalArg.toString());
                           //txtarea.append("\n");
                       }
                   });
                }

                System.out.println("While end");
                inputFromServer.close();
                outputToServer.close();
                
                outputObjectToServer.close();
                inputObjectFromServer.close();
                
                serverSocket.close();

            } catch (IOException ex) {

            }
        }
    }
    
    
    public class ClientNotification implements ClientObserver {

		@Override
		public void notifyMessage(String m) {
			System.out.println("Classe ICClient notifié de l'envoi de message");	
		}

		@Override
		public void login(String login, String pwd) {
			System.out.println("Classe ICClient notifié du login de " + login + " + " + pwd);
			
			// vérifier existsance login dans le système
			outputToServer.println();
			
			// si login n'existe pas, renvoyer message
			
			// si existe, crypter mot de passe et vérifier par rapport à la BDD
			
			// si OK, ouvrir la fenêtre de chat
			
			// charger la liste des clients
			
		}

		@Override
		public void register(String login, String pwd) {
			try 
			{
				Security sec = new Security();
				sec.hashWithSha256(pwd);

				// send data to the server
				outputObjectToServer.writeByte(1);
				outputObjectToServer.writeUTF(login.trim());
				outputObjectToServer.writeUTF(sec.hashWithSha256(pwd));
				outputObjectToServer.flush();
				
			} 
			catch (NoSuchAlgorithmException e1) 
			{
				e1.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			
			// vérifier que l'utilisateur n'existe pas déjà
			
			// Si n'existe pas, crypter message et sauvegarde en BDD.
			
			// Renvoyer message de confirmation
		}
    	
    }
}
