package GUI;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginGUI extends JFrame
{
	private JPanel pnlMain;
	private JPanel pnlWest;
	private JPanel pnlEast;
	private JPanel pnlSouth;
	
	private JTextField txfUserWest;
	private JTextField txfPwdWest;
	private JButton btnConnectWest;
	
	private JTextField txfUserEast;
	private JTextField txfPwdEast;
	private JButton btnRegisterEast;
	
	private JLabel lblInfo;
	
	private boolean firstClickWest; // SB / Help to manage DeleteFieldWestAction
	private boolean firstClickEast; // SB / Help to manage DeleteFieldEastAction
	
	public LoginGUI()
	{
		init();
	}
	
	/**
	 * This method set standard things for this object (directly called in constructor)
	 * @author SB
	 */
	public void init()
	{
		pnlMain = new JPanel();
		pnlWest = new JPanel();
		pnlEast = new JPanel();
		pnlSouth = new JPanel();
		
		txfUserWest = new JTextField("User", 15);
		txfPwdWest = new JTextField("Pass", 15);
		btnConnectWest = new JButton("CONNECT");
		
		txfUserEast = new JTextField("User", 15);
		txfPwdEast = new JTextField("Pass", 15);
		btnRegisterEast = new JButton("REGISTER");
		
		lblInfo = new JLabel("Informations...");
		
		firstClickWest = true;
		DeleteFieldWestAction deleteFieldWest = new DeleteFieldWestAction();
		firstClickEast = true;
		DeleteFieldEastAction deleteFieldEast = new DeleteFieldEastAction();
		
		// SB / Frame parameters
		setTitle("Chat client");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 180);
		setVisible(true);
		
		// SB / WEST PART
		pnlWest.setBorder(BorderFactory.createTitledBorder("Login"));
		pnlWest.setLayout(new BoxLayout(pnlWest, BoxLayout.Y_AXIS));
		pnlWest.add(txfUserWest);
		pnlWest.add(txfPwdWest);
		btnConnectWest.setAlignmentX(CENTER_ALIGNMENT);
		pnlWest.add(btnConnectWest);
		// SB / West side listeners
		txfUserWest.addMouseListener(deleteFieldWest);
		txfPwdWest.addMouseListener(deleteFieldWest);
		btnConnectWest.addMouseListener(new ConnectAction());
		
		// SB / EAST PART
		pnlEast.setBorder(BorderFactory.createTitledBorder("New member"));
		pnlEast.setLayout(new BoxLayout(pnlEast, BoxLayout.Y_AXIS));
		pnlEast.add(txfUserEast);
		pnlEast.add(txfPwdEast);
		btnRegisterEast.setAlignmentX(CENTER_ALIGNMENT);
		pnlEast.add(btnRegisterEast);
		// SB / East side listeners
		txfUserEast.addMouseListener(deleteFieldEast);
		txfPwdEast.addMouseListener(deleteFieldEast);
		btnRegisterEast.addMouseListener(new RegisterAction());
		
		// SB / SOUTH PART
		pnlSouth.add(lblInfo);
		
		// SB / Adding differents panels to the frame
		pnlMain.add(pnlWest, BorderLayout.WEST);
		pnlMain.add(pnlEast, BorderLayout.EAST);
		pnlMain.add(pnlSouth, BorderLayout.SOUTH);
		add(pnlMain);
	}
	
	private class ConnectAction extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			lblInfo.setText("Connecting...");
		}
	}
	
	private class RegisterAction extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			lblInfo.setText("Registration complete !");
		}
	}
	
	private class DeleteFieldWestAction extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if (firstClickWest)
			{
				txfUserWest.setText("");
				txfPwdWest.setText("");
				firstClickWest = false;
			}	
		}
	}
	
	private class DeleteFieldEastAction extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if (firstClickEast)
			{
				txfUserEast.setText("");
				txfPwdEast.setText("");
				firstClickEast = false;
			}	
		}
	}
}
