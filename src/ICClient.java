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


public class ICClient extends JFrame {

    private Socket serverSocket;

    private JTextArea txtarea;
    private JTextField inputTextField;

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
              
        this.buildGUI();

    }

    private void buildGUI() {
    	
    	
    	// zone connexion top
        JLabel lblLogin = new JLabel("Login");
        lblLogin.setForeground(Color.white);
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.white);
        
        JTextField txfUserWest = new JTextField(15);
        txfUserWest.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        txfUserWest.setBorder(BorderFactory.createCompoundBorder(
        		txfUserWest.getBorder(), 
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
        JPasswordField txfPwdWest = new JPasswordField(10);
		txfPwdWest.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txfPwdWest.setBorder(BorderFactory.createCompoundBorder(
				txfPwdWest.getBorder(), 
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		 
		JButton btnConnectWest = new JButton("Connect");
		btnConnectWest.setBorderPainted(false);
		btnConnectWest.setFocusPainted(false);
		btnConnectWest.setBackground(Color.WHITE);
		
		JButton btnRegisterEast = new JButton("Register");
		btnRegisterEast.setBorderPainted(false);
		btnRegisterEast.setFocusPainted(false);
		btnRegisterEast.setBackground(Color.WHITE);
        
        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BoxLayout(pnlNorth, BoxLayout.X_AXIS));
        pnlNorth.setBorder(new EmptyBorder(10, 10, 10, 10) ); // set padding

        pnlNorth.add(lblLogin);
        pnlNorth.add(Box.createHorizontalStrut(5));
        pnlNorth.add(txfUserWest);
        
        pnlNorth.add(Box.createHorizontalStrut(10));
        pnlNorth.add(lblPassword);
        pnlNorth.add(Box.createHorizontalStrut(5));
        pnlNorth.add(txfPwdWest);
        
        pnlNorth.add(Box.createHorizontalStrut(10));
        pnlNorth.add(btnConnectWest);
        pnlNorth.add(Box.createHorizontalStrut(5));
        pnlNorth.add(btnRegisterEast);

        pnlNorth.setBackground(new Color(48,138,226));
        
        
        Box northBox = Box.createHorizontalBox();
        northBox.add(pnlNorth);
        add(northBox, BorderLayout.NORTH);
	
        
        
        
        // zone centrale
        JPanel pnlCenter = new JPanel();
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.X_AXIS));
        pnlCenter.setBorder(new EmptyBorder(10, 10, 10, 10) ); // set padding
        
        pnlCenter.setBackground(new Color(25,162,232));
        
        txtarea = new JTextArea(20, 50);
        txtarea.setEditable(false);
        pnlCenter.add(txtarea);
        pnlCenter.add(Box.createHorizontalStrut(10));
        
        
        String items[] = {"Siméon", "Vincent", "Dany", "Jean", "Dominique", "Sylvain", "Pierre", "Christophe", "Maëlle", "Vincent", "Dany", "Jean", "Dominique", "Sylvain", "Pierre", "Christophe", "Maëlle", "Vincent", "Dany", "Jean", "Dominique", "Sylvain", "Pierre", "Christophe", "Maëlle", "Vincent", "Dany", "Jean", "Dominique", "Sylvain", "Pierre", "Christophe", "Maëlle", "Vincent", "Dany", "Jean", "Dominique", "Sylvain", "Pierre", "Christophe", "Maëlle"};
        JList<String> combo = new JList<String>(items);
        combo.setFixedCellWidth(150);
        combo.setVisibleRowCount(4);

        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(combo);
        
        pnlCenter.add(scrollPane);
        
        
        Box centeredBox = Box.createHorizontalBox();
        centeredBox.add(pnlCenter);
        add(centeredBox, BorderLayout.CENTER);
        
        
       

     
        // zone basse
        JPanel pnlSouth = new JPanel();
        pnlSouth.setLayout(new BoxLayout(pnlSouth, BoxLayout.X_AXIS));
        pnlSouth.setBorder(new EmptyBorder(10, 10, 10, 10) ); // set padding
        
        
        Box southBox = Box.createHorizontalBox();
        add(southBox, BorderLayout.SOUTH);
        inputTextField = new JTextField();
        inputTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        JButton sendButton = new JButton("Send");
        sendButton.setPreferredSize(new Dimension(170, 30));
        sendButton.setBorderPainted(false);
        sendButton.setFocusPainted(false);
        sendButton.setBackground(Color.WHITE);
        
        pnlSouth.add(inputTextField);
        pnlSouth.add(Box.createHorizontalStrut(10));
        pnlSouth.add(sendButton);
        
        pnlSouth.setBackground(new Color(48,138,226));
        
        
        southBox.add(pnlSouth);
         
         
        

         // Action for the inputTextField and the goButton
         ActionListener sendListener = new ActionListener() {
             public void actionPerformed(ActionEvent e) {

                 String str = inputTextField.getText();

                 if (str != null && str.trim().length() > 0)
                 {
                     outputToServer.println(str);
                     outputToServer.flush();
                 }

                 inputTextField.selectAll();
                 inputTextField.requestFocus();
             }
         };
         inputTextField.addActionListener(sendListener);
         sendButton.addActionListener(sendListener);
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
                           txtarea.append(finalArg.toString());
                           txtarea.append("\n");
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
