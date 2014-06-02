import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

//import com.apple.dnssd.TXTRecord;


public class ChatGUI extends JFrame implements ClientObservable
{
	
	  private JTextArea txtarea;
	  private JTextField txtMessage;
	  private ArrayList<ClientObserver> observers = new ArrayList<ClientObserver>();
	  
	  private JPasswordField txPassword = new JPasswordField(10);
	  private JTextField txtLogin = new JTextField(15);
	  private JButton btnSendMessage = new JButton();
	  
	  private JLabel lblLogin = new JLabel("Login");
	  private JLabel lblPassword = new JLabel("Password");
	  
	  private JButton btnConnect = new JButton("Connect");
	  private JButton btnRegister = new JButton("Register"); 
	  
	  private JButton btnDisconnect = new JButton("Disconnect");
	  private JButton btnUnregister = new JButton("Unregister");
	
	  private HashMap<String, User> users = new HashMap<String, User>();
	  private JList usersList = new JList();
	  
	  public ChatGUI()
	  {
		  this.addWindowListener(new CustomWindowAdapter());
		  this.buildGUI();
	  }
	  
	  public void setTextarea(String string) 
	  {
		  final Object finalArg = string;

          SwingUtilities.invokeLater(new Runnable()
          {
	           public void run()
	           {
	               txtarea.append(finalArg.toString());
	               txtarea.append("\r\n");
	           }
	       });
	  }
	  
	  public void updateUsersList(HashMap<String, User> users) 
	  {
		  this.users = users;
		  usersList.setListData(this.users.keySet().toArray());
	  }
	  
	  public void setLoggedInButtons() 
	  {
		  btnConnect.setVisible(false);
		  btnRegister.setVisible(false);
		  
		  lblLogin.setVisible(false);
		  lblPassword.setVisible(false);
		  
		  txtLogin.setVisible(false);
		  txPassword.setVisible(false);
		  
		  btnDisconnect.setVisible(true);
		  btnUnregister.setVisible(true);
	  }
	  
	  public void setLoggedOffButtons() 
	  {
		  btnConnect.setVisible(true);
		  btnRegister.setVisible(true);
		  
		  lblLogin.setVisible(true);
		  lblPassword.setVisible(true);
		  
		  txtLogin.setVisible(true);
		  txPassword.setVisible(true);
		  
		  btnDisconnect.setVisible(false);
		  btnUnregister.setVisible(false);
	  }

	  private void buildGUI()
	  {
    	// zone connexion top
        lblLogin.setForeground(Color.white);
        lblPassword.setForeground(Color.white);
        
        txtLogin.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtLogin.setBorder(BorderFactory.createCompoundBorder(
        		txtLogin.getBorder(), 
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
		txPassword.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txPassword.setBorder(BorderFactory.createCompoundBorder(
				txPassword.getBorder(), 
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		 
		
		btnConnect.setBorderPainted(false);
		btnConnect.setFocusPainted(false);
		btnConnect.setBackground(Color.WHITE);
		btnConnect.addMouseListener(new LoginAction());
		
		btnDisconnect.setVisible(false);
		btnDisconnect.setBorderPainted(false);
		btnDisconnect.setFocusPainted(false);
		btnDisconnect.setBackground(Color.WHITE);
		btnDisconnect.addMouseListener(new LogoutAction());
		
		btnRegister.setBorderPainted(false);
		btnRegister.setFocusPainted(false);
		btnRegister.setBackground(Color.WHITE);
		btnRegister.addMouseListener(new RegisterAction());
		
		btnUnregister.setVisible(false);
		btnUnregister.setBorderPainted(false);
		btnUnregister.setFocusPainted(false);
		btnUnregister.setBackground(Color.WHITE);
		btnUnregister.addMouseListener(new UnregisterAction());

        JPanel pnlNorth = new JPanel();
        pnlNorth.setLayout(new BoxLayout(pnlNorth, BoxLayout.X_AXIS));
        pnlNorth.setBorder(new EmptyBorder(10, 10, 10, 10) ); // set padding

        pnlNorth.add(lblLogin);
        pnlNorth.add(Box.createHorizontalStrut(5));
        pnlNorth.add(txtLogin);
        
        pnlNorth.add(Box.createHorizontalStrut(10));
        pnlNorth.add(lblPassword);
        pnlNorth.add(Box.createHorizontalStrut(5));
        pnlNorth.add(txPassword);
        
        pnlNorth.add(Box.createHorizontalStrut(10));
        pnlNorth.add(btnConnect);
        pnlNorth.add(btnDisconnect);
        pnlNorth.add(Box.createHorizontalStrut(5));
        pnlNorth.add(btnRegister);
        pnlNorth.add(btnUnregister);

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
        txtarea.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtarea.setBorder(BorderFactory.createCompoundBorder(
        		txtarea.getBorder(), 
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
        // always show the latest message to the textarea
        DefaultCaret caret = (DefaultCaret)txtarea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane scrollPaneTextarea = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneTextarea.setViewportView(txtarea);
        pnlCenter.add(scrollPaneTextarea);
        pnlCenter.add(Box.createHorizontalStrut(10));
        
        
        usersList.setFixedCellWidth(150);
        usersList.setVisibleRowCount(4);
        usersList.addMouseListener(new UserSelectionAction());
        usersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // limiter la selection à 1 user

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(usersList);
        
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
        
        txtMessage = new JTextField();
        txtMessage.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtMessage.setBorder(BorderFactory.createCompoundBorder(
        		txtMessage.getBorder(), 
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        txtMessage.addActionListener(new SendMessageOnKeyPressed());
        //txtMessage.addKeyListener(new SendMessageOnKeyPressed());
       
        btnSendMessage.setText("Send");
        btnSendMessage.setPreferredSize(new Dimension(170, 30));
        btnSendMessage.setBorderPainted(false);
        btnSendMessage.setFocusPainted(false);
        btnSendMessage.setBackground(Color.WHITE);
        
        btnSendMessage.addMouseListener(new SendMessageAction());
        
        pnlSouth.add(txtMessage);
        pnlSouth.add(Box.createHorizontalStrut(10));
        pnlSouth.add(btnSendMessage);
        
        pnlSouth.setBackground(new Color(48,138,226));
        southBox.add(pnlSouth);        
    }
	  
	  
	 private class SendMessageOnKeyPressed implements ActionListener
	 {
		@Override
		public void actionPerformed(ActionEvent e) {
			String str = txtMessage.getText();

            if (str != null && str.trim().length() > 0)
            {
                notifyMessage(str);
            }
            
            txtMessage.setText("");
            txtMessage.selectAll();
            txtMessage.requestFocus();
		} 
	 }

	 /**
	 * Action performed when a user click the login button
	 * 
	 * @author Vince
	 *
	 */
	private class SendMessageAction extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			String str = txtMessage.getText();

            if (str != null && str.trim().length() > 0)
            {
                notifyMessage(str);
            }
            txtMessage.setText("");
            txtMessage.selectAll();
            txtMessage.requestFocus();
		}
	}
	
	/**
	 * When a user is selected in the list
	 * @author Meon
	 *
	 */
	private class UserSelectionAction extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			txtarea.setText("Discussion avec " + usersList.getSelectedValue());
			notifyUserSelection(users.get(usersList.getSelectedValue()));
		}
	}
		
	/**
	 * Action performed when a user click the login button
	 * 
	 * @author Vince
	 *
	 */
	private class LoginAction extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if(txtLogin.getText().trim() != null && 
			   !txtLogin.getText().trim().isEmpty() && 
			   txPassword.getPassword() != null &&
			   txPassword.getPassword().length > 0
			)
			{
				char[] pass = txPassword.getPassword();  
				String passString = new String(pass); 
				
				// get login/password values and notify the Server class
				notifyLogin(txtLogin.getText(), passString);
			} else {
				setTextarea("Username and password must not be empty !");
			}
		}
	}
	
	private class LogoutAction extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			notifyLogout();
		}
	}
	
	/**
	 * Action performed when a user click the register button
	 * 
	 * @author Vince
	 *
	 */
	private class RegisterAction extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			// basics verifications
			if(txtLogin.getText().trim() != null && 
			   !txtLogin.getText().trim().isEmpty() && 
			   txPassword.getPassword() != null &&
			   txPassword.getPassword().length > 0
			)
			{
				// get login/password values and notify the Server class
				char[] pass = txPassword.getPassword();  
				String passString = new String(pass); 
				notifyRegistration(txtLogin.getText(), passString);
			} else {
				setTextarea("Username and password must not be empty !");
			}
		}
	}
	
	private class UnregisterAction extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			notifyUnregister();
		}
	}
	
	/**
	 * Action to perform on windows closing
	 * 
	 * @author Vince
	 *
	 */
	private class CustomWindowAdapter extends WindowAdapter 
	{
		// exit the application when window's close button is clicked
		public void windowClosing(WindowEvent e)
		{
			notifyDisconnection();
			System.exit(0);
		}
	}

	@Override
	public void addObserver(ClientObserver obs)
	{
		observers.add(obs);
	}

	@Override
	public void removeObserver(ClientObserver obs)
	{
		observers.remove(obs);
	}

	@Override
	public void notifyLogin(String login, String pwd) 
	{
		for(ClientObserver obs : observers) 
		{
			obs.login(login, pwd);
		}
	}

	@Override
	public void notifyRegistration(String login, String pwd)
	{
		for(ClientObserver obs : observers) 
		{
			obs.register(login, pwd);
		}
	}

	@Override
	public void notifyDisconnection()
	{
		for(ClientObserver obs : observers) 
		{
			obs.notifyDisconnection();
		}
	}

	@Override
	public void notifyMessage(String m)
	{
		for(ClientObserver obs : observers) 
		{
			obs.notifyMessage(m);
		}
	}

	@Override
	public void notifyLogout()
	{
		for(ClientObserver obs : observers) 
		{
			obs.notifyLogout();
		}
	}

	@Override
	public void notifyUnregister()
	{
		for(ClientObserver obs : observers) 
		{
			obs.notifyUnregister();
		}
	}

	@Override
	public void notifyUserSelection(User u)
	{
		for(ClientObserver obs : observers) 
		{
			obs.notifyUserSelection(u);
		}
	}
}
