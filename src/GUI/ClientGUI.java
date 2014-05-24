package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientGUI extends JFrame
{
	
	private JTextArea txtarea;
    private JTextField inputTextField;
	
	public ClientGUI()
	{
		init();
	}
	
	public void init()
	{
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
//         ActionListener sendListener = new ActionListener() {
//             public void actionPerformed(ActionEvent e) {
//
//                 String str = inputTextField.getText();
//
//                 if (str != null && str.trim().length() > 0)
//                 {
//                     outputToServer.println(str);
//                     outputToServer.flush();
//                 }
//
//                 inputTextField.selectAll();
//                 inputTextField.requestFocus();
//             }
//         };
//         inputTextField.addActionListener(sendListener);
//         sendButton.addActionListener(sendListener);
	}
}
