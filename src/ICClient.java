import java.awt.BorderLayout;
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

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


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

        this.buildGUI();
    }

    private void buildGUI() {
        txtarea = new JTextArea(20, 50);
        txtarea.setEditable(false);
        this.add(new JScrollPane(txtarea), BorderLayout.WEST);
        

        String items[] = {"One", "Two", "Three"};
        JList combo = new JList(items);
        combo.setMinimumSize(new Dimension(50, 25));
        combo.setMaximumSize(new Dimension(150, 25));
        combo.setPreferredSize(new Dimension(100, 25));
        this.add(combo, BorderLayout.EAST);
       
         Box box = Box.createHorizontalBox();
         add(box, BorderLayout.SOUTH);
         inputTextField = new JTextField();
         JButton sendButton = new JButton("Send");
         box.add(inputTextField);
         box.add(sendButton);

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
    
    // get the connected client list
    public class ClientsList implements Runnable 
    {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			// ask the server for the client list every 10 sec

			
		}
    }
}
