import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;


public class ICClient {

    private Socket serverSocket;

    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;
      
    byte[] serverIP = {127, 0, 0, 1};
   
    
    public ICClient() {
        try {
			
            //serverSocket = new Socket("127.0.0.1", 1087);

            InetAddress serverAddress = InetAddress.getByAddress("",serverIP);
            serverSocket = new Socket(serverAddress, 1089);

            Thread inOutThread = new Thread(new InOutClient());
            inOutThread.start();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        //LoginGUI gui = new LoginGUI();
        
        //gui.addObserver(new ClientNotification());
              
        
        JFrame client = new ChatGUI();
		
        client.setVisible(true);
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.pack(); 

    }

    


    public class InOutClient implements Runnable {


        public InOutClient()
        {
            try {
                outputToServer = new PrintWriter(serverSocket.getOutputStream());
                inputFromServer = new BufferedReader( new InputStreamReader(serverSocket.getInputStream()));
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
			
			// si login n'existe pas, renvoyer message
			
			// si existe, crypter mot de passe et vérifier par rapport à la BDD
			
			// si OK, ouvrir la fenêtre de chat
		}

		@Override
		public void notifyRegistration() {
			System.out.println("Classe ICClient notifiée de l'inscription");
			
			// vérifier que l'utilisateur n'existe pas déjà
			
			// Si n'existe pas, crypter message et sauvegarde en BDD.
			
			// Renvoyer message de confirmation
		}
    	
    }
}
