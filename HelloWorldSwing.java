import javax.swing.*;        
import java.awt.*; //Includes GridLayout and Dimension
import java.awt.event.*;

public class HelloWorldSwing{

    public HelloWorldSwing(){
        System.out.println("Created");
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 500));

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Construct your query : ");
        label.setVisible(true); 
        panel.add(label);
        String[] choices = { "Highest","Lowest", "Average"};
        JComboBox<String> cb = new JComboBox<String>(choices);
        cb.setVisible(true);
        panel.add(cb);
        JLabel label2 = new JLabel("spending");
        panel.add(label2);
        String[] choices2 = {"Country", "Customer"};
        JComboBox<String> cb2 = new JComboBox<String>(choices2);
        cb2.setVisible(true);
        panel.add(cb2);
        JButton btn = new JButton("Query");
        panel.add(btn);
        btn.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Result");
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setPreferredSize(new Dimension(600, 50));
                JPanel panel = new JPanel();
                JLabel label = new JLabel("The answer to your query");
                panel.add(label);
                System.out.println("Button clicked.");
                frame.getContentPane().add(panel);
                frame.pack();
                frame.setVisible(true);
            }
        });
        frame.getContentPane().add(panel);

        //Add the ubiquitous "Hello World" label.
        //JLabel label2 = new JLabel("Hello World");
        //frame.getContentPane().add(label2);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //HelloWorldSwing h = new HelloWorldSwing();
                createAndShowGUI();
            }
        });
    }
}
