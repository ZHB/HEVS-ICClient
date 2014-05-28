import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;


public class ChatGUI extends JFrame implements ClientObservable {
	
	  private JTextArea txtarea;
	  private JTextField inputTextField;
	  private ArrayList<ClientObserver> observers = new ArrayList<ClientObserver>();
	  
	  private JPasswordField txPassword = new JPasswordField(10);
	  private JTextField txtLogin = new JTextField(15);
	  
	  public ChatGUI() {
		  this.buildGUI();
	  }
	  
	  public void setTextarea(String string) {
		  final Object finalArg = string;

          SwingUtilities.invokeLater(new Runnable() {
	           public void run() {
	               txtarea.append(finalArg.toString());
	               txtarea.append("\n");
	           }
	       });
	  }

	  private void buildGUI() {
    	
    	
    	// zone connexion top
        JLabel lblLogin = new JLabel("Login");
        lblLogin.setForeground(Color.white);
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.white);
        

        txtLogin.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        txtLogin.setBorder(BorderFactory.createCompoundBorder(
        		txtLogin.getBorder(), 
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
       
		txPassword.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txPassword.setBorder(BorderFactory.createCompoundBorder(
				txPassword.getBorder(), 
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		 
		JButton btnConnectWest = new JButton("Connect");
		btnConnectWest.setBorderPainted(false);
		btnConnectWest.setFocusPainted(false);
		btnConnectWest.setBackground(Color.WHITE);
		btnConnectWest.addMouseListener(new LoginAction());
		
		JButton btnRegisterEast = new JButton("Register");
		btnRegisterEast.setBorderPainted(false);
		btnRegisterEast.setFocusPainted(false);
		btnRegisterEast.setBackground(Color.WHITE);
		btnRegisterEast.addMouseListener(new RegisterAction());
        
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
        txtLogin.setBorder(javax.swing.BorderFactory.createEmptyBorder());
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
        inputTextField.setBorder(BorderFactory.createCompoundBorder(
        		inputTextField.getBorder(), 
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
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
                     //outputToServer.println(str);
                     //outputToServer.flush();
                 }

                 inputTextField.selectAll();
                 inputTextField.requestFocus();
             }
         };
         inputTextField.addActionListener(sendListener);
         sendButton.addActionListener(sendListener);
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
			   txPassword.getText().trim() != null &&
			   !txPassword.getText().trim().isEmpty()
			)
			{
				// get login/password values and notify the Server class
				notifyLogin(txtLogin.getText(), txPassword.getText());
			} else {
				setTextarea("Username and password must not be empty !");
			}
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
			   txPassword.getText().trim() != null &&
			   !txPassword.getText().trim().isEmpty()
			)
			{
				// get login/password values and notify the Server class
				notifyRegistration(txtLogin.getText(), txPassword.getText());
			} else {
				setTextarea("Username and password must not be empty !");
			}
		}
	}

	@Override
	public void addObserver(ClientObserver obs) {
		observers.add(obs);
	}

	@Override
	public void removeObserver(ClientObserver obs) {
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
	public void notifyRegistration(String login, String pwd) {
		for(ClientObserver obs : observers) 
		{
			obs.register(login, pwd);
		}
	}
}
