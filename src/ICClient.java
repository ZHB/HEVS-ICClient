import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class ICClient extends JFrame {

    private Socket serverSocket;

    private JPanel panel;
    private JTextArea txtarea;
    private JTextField inputTextField;

    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;

    public ICClient() {
        try {
            serverSocket = new Socket("192.168.0.12", 1078);

            //InetAddress serverAddress = InetAddress.getByAddress("",myIp);
            //System.out.println("Get the address of the server : "+ serverAddress);

            //try to connect to the server
            //serverSocket = new Socket(serverAddress, 1057);

            // System.out.println("We got the connexion to  "+ serverAddress);


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
        this.add(new JScrollPane(txtarea), BorderLayout.CENTER);


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

            try {

                String line;



                System.out.println("befor read line");
                while ((line = inputFromServer.readLine()) != null)
                {



                    System.out.println(line);

                    // update textarea
                    //this.notifyObservers(line);
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
}
