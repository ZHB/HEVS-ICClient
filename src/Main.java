import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {
		
		//LoginGUI gui = new LoginGUI();
		JFrame client = new ICClient();
		
        client.setVisible(true);
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.pack(); 
        
        
	}

}
