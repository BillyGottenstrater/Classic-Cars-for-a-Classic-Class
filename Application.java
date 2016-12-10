import javax.swing.*;        
import java.awt.*; //Includes GridLayout and Dimension
import java.awt.event.*;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.lang.*;

public class Application{

    private static Connection conn;

    public Application(){
        System.out.println("Created");
    }

    /***********
    
    SQL Stuff

    ************/

    public static void deleteTable (Connection conn, String table)
  {
    try{
      Statement s = conn.createStatement();
      String d = "DROP TABLE ";
      String sc = ";";
      String statement = d + table + sc;    
      s.executeUpdate(statement);
    }
    catch (Exception ee) {System.out.println("Error in deleting table (" + table + ") : " + ee);}
  }

  public static void deleteAllTables(Connection conn)
  {
    String[] tables = new String[]{"Payments1","Orders1","OrderDetails1","Products1","Offices1","Employees1","Customers1"};
    //String [] tables = new String[]{"Payments1"};
    for(String table: tables){
      deleteTable(conn, table);
    }
  }

/* Table Creation methods */

  public static void createAllTables(Connection conn)
  {
    createCustomers1(conn);
    createEmployees1(conn);
    createOffices1(conn);
    createProducts1(conn);
    createOrderDetails1(conn);
    createOrders1(conn);
    createPayments1(conn);
  }

  public static void initAllTables(Connection conn)
  {
    String[] tables = new String[]{"Customers1","Employees1","Offices1","Products1","OrderDetails1","Orders1","Payments1"};
    for(String table : tables){
      initTable(conn,table);
    }
  }

  public static void initAndCreateAllTables(Connection conn)
  {
    createAllTables(conn);
    initAllTables(conn);
  }

  public static void resetTables(Connection conn)
  {
    deleteAllTables(conn);
    initAndCreateAllTables(conn);
  }

  public static void createTable(Connection conn, String table)
  { 
    try{
      Statement s = conn.createStatement();
      s.executeUpdate(table);
    }
    catch (Exception ee) {System.out.println("Error in creating table (" + table + ") : " + ee);}
  }

  public static void createCustomers1(Connection conn)
  {
    String table = "CREATE TABLE Customers1 (";
    table += ("customerNumber INTEGER NOT NULL," 
       + "customerName VARCHAR(50) NOT NULL,"
       + "contactLastName VARCHAR(50) NOT NULL,"
       + "contactFirstName VARCHAR(50) NOT NULL,"
       + "phone VARCHAR(50) NOT NULL,"
       + "addressLine1 VARCHAR(50) NOT NULL,"
       + "addressLine2 VARCHAR(50) NULL,"
       + "city VARCHAR(50) NOT NULL,"
       + "state VARCHAR(50) NULL,"
       + "postalCode VARCHAR(15) NULL,"
       + "country VARCHAR(50) NOT NULL,"
       + "salesRepEmployeeNumber INTEGER NULL,"
       + "creditLimit DOUBLE NULL,");
    table += "PRIMARY KEY (customerNumber) )";
    createTable(conn, table);
  }

  public static void createEmployees1(Connection conn)
  {
    String table = "CREATE TABLE Employees1 (";
    table += ("employeeNumber INTEGER NOT NULL,"
    + "lastName VARCHAR(50) NOT NULL,"
    + "firstName VARCHAR(50) NOT NULL,"
    + "extension VARCHAR(10) NOT NULL,"
    + "email VARCHAR(100) NOT NULL,"
    + "officeCode VARCHAR(20) NOT NULL,"
    + "reportsTo INTEGER NULL,"
    + "jobTitle VARCHAR(50) NOT NULL,"
    + "PRIMARY KEY (employeeNumber) )");
    createTable(conn, table);
  }

  public static void createOffices1(Connection conn)
  {
    String table = ("CREATE TABLE Offices1 ("
    +"officeCode VARCHAR(50) NOT NULL,"
    +"city VARCHAR(50) NOT NULL,"
    +"phone VARCHAR(50) NOT NULL,"
    +"addressLine1 VARCHAR(50) NOT NULL,"
    +"addressLine2 VARCHAR(50) NULL,"
    +"state VARCHAR(50) NULL,"
    +"country VARCHAR(50) NOT NULL,"
    +"postalCode VARCHAR(10) NOT NULL,"
    +"territory VARCHAR(10) NOT NULL,"
    +"PRIMARY KEY (officeCode) )");
    createTable(conn, table);
  }

  public static void createProducts1(Connection conn)
  {
    String table = "CREATE TABLE Products1 (";
    table += ("productCode VARCHAR(50) NOT NULL,"
    +"productName VARCHAR(70) NOT NULL,"
    +"productLine VARCHAR(50) NOT NULL,"
    +"productScale VARCHAR(10) NOT NULL,"
    +"productVendor VARCHAR(50) NOT NULL,"
    +"productDescription TEXT NOT NULL,"
    +"quantityInStock SMALLINT NOT NULL,"
    +"buyPrice DOUBLE NOT NULL,"
    +"MSRP DOUBLE NOT NULL,"
    +"PRIMARY KEY (productCode) )");
    createTable(conn, table);
  }

  public static void createOrderDetails1(Connection conn)
  {
    String table = ("CREATE TABLE OrderDetails1 ("
  + "orderNumber INTEGER NOT NULL,"
  + "productCode VARCHAR(50) NOT NULL,"
  + "quantityOrdered INTEGER NOT NULL,"
  + "priceEach DOUBLE NOT NULL,"
  + "orderLineNumber SMALLINT NOT NULL,"
  + "PRIMARY KEY (orderNumber, productCode),"
  + "FOREIGN KEY (productCode) REFERENCES Products1(productCode) )");
    createTable(conn, table);
  }

  public static void createOrders1(Connection conn)
  {
    String table = ("CREATE TABLE Orders1 ("
  + "orderNumber INTEGER NOT NULL,"
  + "orderDate DATETIME NOT NULL,"
  + "requiredDate DATETIME NOT NULL,"
  + "shippedDate DATETIME NULL,"
  + "status VARCHAR(15) NOT NULL,"
  + "comments TEXT NULL,"
  + "customerNumber INTEGER NOT NULL,"
  + "PRIMARY KEY (orderNumber),"
  + "FOREIGN KEY (customerNumber) REFERENCES Customers1(customerNumber),"
  + "FOREIGN KEY (orderNumber) REFERENCES OrderDetails1(orderNumber) )");
    createTable(conn, table);
  }

  public static void createPayments1(Connection conn)
  {
    String table = ("CREATE TABLE Payments1 ("
  + "customerNumber INTEGER NOT NULL,"
  + "checkNumber VARCHAR(50) NOT NULL,"
  + "paymentDate DATETIME NOT NULL,"
  + "amount DOUBLE NOT NULL,"
  + "PRIMARY KEY (customerNumber, checkNumber),"
  + "FOREIGN KEY (customerNumber) REFERENCES Customers1(customerNumber) )");
    createTable(conn, table);
  }


  /* General purpose table initializer */
  public static void initTable(Connection conn, String tableName)
  {
    String str = "LOAD DATA LOCAL INFILE 'dataFiles/" + tableName + ".txt' INTO TABLE \n"
    + tableName + " FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES \n" 
    + "TERMINATED BY '\n'";
    try{
      Statement s = conn.createStatement();
      s.executeUpdate(str);
    }
    catch (Exception ee) {System.out.println("Error in filling table (" + tableName + ") : " + ee);}
  }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI(Connection conn) {
        //Create and set up the window.
        JFrame frame = new JFrame("Queries for Classic Cars Sales");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 500));
        
        JPanel bigPanel = new JPanel();
        bigPanel.setLayout(new BoxLayout(bigPanel,BoxLayout.Y_AXIS));

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Construct your query : ");
        label.setVisible(true); 
        panel.add(label);
        String[] choices = { "Highest","Lowest"};
        final JComboBox<String> cb = new JComboBox<String>(choices);
        cb.setVisible(true);
        panel.add(cb);
        JLabel label2 = new JLabel("spending");
        panel.add(label2);
        String[] choices2 = {"Country", "Customer"};
        final JComboBox<String> cb2 = new JComboBox<String>(choices2);
        cb2.setVisible(true);
        panel.add(cb2);
        JButton btn = new JButton("Query");
        panel.add(btn);
        btn.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Result");
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setPreferredSize(new Dimension(600, 300));
                JPanel panel = new JPanel();
                String cb2val = (String)cb2.getSelectedItem();
                String groupBy = "Customers1." + ((cb2val == "Customer") ? "CustomerName" : cb2val); 
                String cbval = (String)cb.getSelectedItem();
                String cbsign = (cbval == "Highest") ? ">=" : "<=";
                String cbsignhtml = (cbval == "Highest") ? "&gt;=" : "&lt;=";
                String query = ("SELECT " + groupBy + ", ROUND(SUM(Payments1.amount),2) AS Total_Amount \n"+
                                "FROM Customers1 JOIN Payments1 \n"+
                                "ON Customers1.customerNumber = Payments1.customerNumber \n"+
                                "GROUP BY \n" + groupBy + "\n" +
                                " HAVING SUM(Payments1.amount) "+cbsign+" ALL (SELECT SUM(Payments1.amount) \n"+
                                "FROM Customers1 JOIN Payments1 \n"+
                                "ON Customers1.customerNumber = Payments1.customerNumber \n"+ 
                                "GROUP BY " + groupBy + ");");
                try{
                    Statement s = conn.createStatement();
                    ResultSet result = s.executeQuery(query);
                    ResultSetMetaData rsmd = result.getMetaData();
                    boolean f = result.next();
                    while (f)
                    {
                        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                          System.out.print(result.getString(i) + ", ");  
                      }
                      System.out.println();
                      f = result.next();
                    }
                }
                catch (Exception ee) {System.out.println("Error in getting data from Database : " + ee);}

                // String htmlquery = ("<html>SELECT " + groupBy + ", ROUND(SUM(P1.amount),2) AS Total_Amount <br>"+
                //                 "FROM Customers1 JOIN Payments1 <br>"+
                //                 "ON Customers1.customerNumber = Payments1.customerNumber <br>"+
                //                 "GROUP BY \n" + groupBy + "<br>" +
                //                 " HAVING SUM(P1.amount) "+cbsignhtml+" ALL (SELECT SUM(Payments1.amount) <br>"+
                //                 "FROM Customers1 JOIN Payments1 <br>"+
                //                 "ON Customers1.customerNumber = Payments1.customerNumber <br>"+ 
                //                 "GROUP BY " + groupBy + ");</html>");
                // JLabel label = new JLabel(htmlquery);
                // panel.add(label);
                System.out.println("Button clicked.");
                frame.getContentPane().add(panel);
                frame.pack();
                frame.setVisible(true);
            }
        });

        JPanel panelReset = new JPanel();
        JButton resetButton = new JButton("Restore Tables");
        panelReset.add(resetButton);
        resetButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Result");
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setPreferredSize(new Dimension(600, 300));
                JPanel panelAfterReset = new JPanel();
                JLabel words = new JLabel("Tables are restored.");
                panelAfterReset.add(words);
                frame.getContentPane().add(panelAfterReset);
                frame.pack();
                frame.setVisible(true);
            }
        });
        bigPanel.add(panel);
        bigPanel.add(panelReset);
        frame.getContentPane().add(bigPanel);

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

        try{
          Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        catch (Exception ex) {
          System.out.println("Driver not found");
          System.out.println(ex);
        };

      String url = "jdbc:mysql://cslvm74.csc.calpoly.edu/testuser?";
      conn = null;

      try { 
    // conn = DriverManager.getConnection(url, "LOGIN_ID", "PASSWORD");
        conn = DriverManager.getConnection(url +"user=testuser&password=abc123&");
    }
    catch (Exception ex)
    {
      System.out.println("Could not open connection");
      System.out.println(ex);
    };

    System.out.println("Connected");
    try {
      resetTables(conn);
      //System.out.println(table);
      // s1.executeUpdate("use dekhtyar");
      //s1.executeUpdate(table);
    } 
    catch (Exception ee) {System.out.println("Error in creating tables : " + ee);}

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Application h = new Application();
                createAndShowGUI(conn);
            }
        });
    }
}
