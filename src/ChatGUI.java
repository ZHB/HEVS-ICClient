import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.border.EmptyBorder;


public class ChatGUI extends JFrame {
	
	  private JTextArea txtarea;
	  private JTextField inputTextField;
	  
	  public ChatGUI() {
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
        
        
        String items[] = {"Sim�on", "Vincent", "Dany", "Jean", "Dominique", "Sylvain", "Pierre", "Christophe", "Ma�lle", "Vincent", "Dany", "Jean", "Dominique", "Sylvain", "Pierre", "Christophe", "Ma�lle", "Vincent", "Dany", "Jean", "Dominique", "Sylvain", "Pierre", "Christophe", "Ma�lle", "Vincent", "Dany", "Jean", "Dominique", "Sylvain", "Pierre", "Christophe", "Ma�lle", "Vincent", "Dany", "Jean", "Dominique", "Sylvain", "Pierre", "Christophe", "Ma�lle"};
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
}
