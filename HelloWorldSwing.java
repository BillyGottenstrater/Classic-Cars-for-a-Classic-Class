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
        String[] choices = { "Highest","Lowest"};
        JComboBox<String> cb = new JComboBox<String>(choices);
        cb.setVisible(true);
        panel.add(cb);
        JLabel label2 = new JLabel("spending");
        panel.add(label2);
        String[] choices2 = {"Country", "CustomerName"};
        JComboBox<String> cb2 = new JComboBox<String>(choices2);
        cb2.setVisible(true);
        panel.add(cb2);
        JButton btn = new JButton("Query");
        panel.add(btn);
        btn.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Result");
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setPreferredSize(new Dimension(600, 300));
                JPanel panel = new JPanel();
                String groupBy = "Customers1." + (String)cb2.getSelectedItem();
                String cbval = (String)cb.getSelectedItem();
                String cbsign = (cbval == "Highest") ? ">=" : "<=";
                String cbsignhtml = (cbval == "Highest") ? "&gt;=" : "&lt;=";
                String query = ("SELECT " + groupBy + ", ROUND(SUM(P1.amount),2) AS Total_Amount \n"+
                                "FROM Customers1 JOIN Payments1 \n"+
                                "ON Customers1.customerNumber = Payments1.customerNumber \n"+
                                "GROUP BY \n" + groupBy + "\n" +
                                " HAVING SUM(P1.amount) "+cbsign+" ALL (SELECT SUM(Payments1.amount) \n"+
                                "FROM Customers1 JOIN Payments1 \n"+
                                "ON Customers1.customerNumber = Payments1.customerNumber \n"+ 
                                "GROUP BY " + groupBy + ");");

                 String htmlquery = ("<html>SELECT " + groupBy + ", ROUND(SUM(P1.amount),2) AS Total_Amount <br>"+
                                "FROM Customers1 JOIN Payments1 <br>"+
                                "ON Customers1.customerNumber = Payments1.customerNumber <br>"+
                                "GROUP BY \n" + groupBy + "<br>" +
                                " HAVING SUM(P1.amount) "+cbsignhtml+" ALL (SELECT SUM(Payments1.amount) <br>"+
                                "FROM Customers1 JOIN Payments1 <br>"+
                                "ON Customers1.customerNumber = Payments1.customerNumber <br>"+ 
                                "GROUP BY " + groupBy + ");</html>");
                JLabel label = new JLabel(htmlquery);
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
