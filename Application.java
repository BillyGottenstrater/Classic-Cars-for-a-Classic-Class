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
                frame.setPreferredSize(new Dimension(600, 150));
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
                    String output = "";
                    while (f)
                    {
                        output = cb2val + " " + result.getString(1) + " has spent a total of $" + result.getString(2);
                      System.out.println(output);
                      f = result.next();
                    }
                    JLabel querylabel = new JLabel(output);
                    panel.add(querylabel);
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
        
        int numModels = 0;
        try{
            Statement s = conn.createStatement();
            ResultSet result = s.executeQuery("SELECT COUNT(DISTINCT EmployeeNumber) FROM Employees1");
            // ResultSetMetaData rsmd = result.getMetaData();
            boolean f = result.next();
            while (f)
            {
                numModels = Integer.parseInt(result.getString(1));
                //if result.getString = 
                f = result.next();
            }
        }
        catch (Exception ee) {System.out.println("Error in getting number of employees : " + ee);}

        String[] allModels = new String[numModels];
        
        try{
            Statement s = conn.createStatement();
            ResultSet result = s.executeQuery("SELECT FirstName, LastName FROM Employees1");
            // ResultSetMetaData rsmd = result.getMetaData();
            boolean f = result.next();
            int count = 0;
            while (f)
            {
                allModels[count] = result.getString(1) + " " + result.getString(2);
                f = result.next();
                count++;
            }
        }
        catch (Exception ee) {System.out.println("Error in filling AllModels Dropdown : " + ee);}

        JPanel panelDelete = new JPanel();
        JLabel deleteLabel1 = new JLabel("Delete ");
        JComboBox<String> modelDropdown = new JComboBox<String>(allModels);
        JLabel deleteLabel2 = new JLabel(" from the database");
        JButton deleteBtn = new JButton("Delete");
        panelDelete.add(deleteLabel1);
        panelDelete.add(modelDropdown);
        panelDelete.add(deleteLabel2);
        panelDelete.add(deleteBtn);

        deleteBtn.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numModels2 = 0;
                String modelName = (String)modelDropdown.getSelectedItem();
                JFrame frame = new JFrame("Result");
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setPreferredSize(new Dimension(600, 150));
                JPanel panelAfterDelete = new JPanel();
                try{
                Statement deleteStatement = conn.createStatement();
                deleteStatement.executeUpdate("DELETE FROM Employees1 WHERE FirstName = '" + modelName.split(" ")[0] + "' AND LastName = '" + modelName.split(" ")[1] + "'");
                }
                catch(Exception ee){System.out.println("Error in deleting from Employees1 : " + ee);}
                modelDropdown.removeItem(modelName);
            }
        });


        // office functionality

        int numOffices = 0;
        try{
            Statement s = conn.createStatement();
            ResultSet result = s.executeQuery("SELECT COUNT(DISTINCT officeCode) FROM Offices1");
            // ResultSetMetaData rsmd = result.getMetaData();
            boolean f = result.next();
            while (f)
            {
                numOffices = Integer.parseInt(result.getString(1));
                //if result.getString = 
                f = result.next();
            }
        }
        catch (Exception ee) {System.out.println("Error in getting number of offices : " + ee);}

        String[] allOffices = new String[numOffices];
        
        try{
            Statement s = conn.createStatement();
            ResultSet result = s.executeQuery("SELECT city FROM Offices1");
            // ResultSetMetaData rsmd = result.getMetaData();
            boolean f = result.next();
            int count = 0;
            while (f)
            {
                allOffices[count] = result.getString(1);
                f = result.next();
                count++;
            }
        }
        catch (Exception ee) {System.out.println("Error in filling AllOffices Dropdown : " + ee);}
        JPanel panelOffice = new JPanel();
        JButton officeBtn = new JButton("Find info");
        JLabel officeLabel = new JLabel("Find information about the office in ");
        JComboBox<String> officesDropdown = new JComboBox<String>(allOffices);
        panelOffice.add(officeLabel);
        panelOffice.add(officesDropdown);
        panelOffice.add(officeBtn);

        officeBtn.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = (String)officesDropdown.getSelectedItem();
                JFrame frame = new JFrame(city+ " Office information");
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setPreferredSize(new Dimension(600, 150));
                JPanel panelAfterOffice = new JPanel();
                String query = ("SELECT addressLine1, addressLine2, State, postalCode, country, phone "
                                +"FROM Offices1 " 
                                +"WHERE city = '"+city+"'");
                try{
                    Statement s = conn.createStatement();
                    ResultSet result = s.executeQuery(query);
                    ResultSetMetaData rsmd = result.getMetaData();
                    boolean f = result.next();
                    String output = "";
                    while (f)
                    {
                        String line1 = result.getString(1) != null ? result.getString(1) : "";
                        String line2 = result.getString(2) != null ? result.getString(2) : "";
                        String state = result.getString(3) != null ? result.getString(3) : "";
                        String postalCode = result.getString(4) != null ? result.getString(4) : "";
                        String country = result.getString(5) != null ? result.getString(5) : "";
                        String phone = result.getString(6) != null ? result.getString(6) : "";
                        output = "<html><pre><strong>Address</strong>: " + line1 + "<br>         " + line2 + (line2 == "" ? "" :"<br>         ") + city + ", " + state + " " + postalCode + ", " + country + "<br><strong>Phone</strong>:   " + phone +"</pre></html>";
                      System.out.println(output);
                      f = result.next();
                    }
                    JLabel querylabel = new JLabel(output);
                    JPanel queryPanel = new JPanel();
                    queryPanel.add(querylabel);
                    System.out.println("Button clicked.");
                    frame.getContentPane().add(queryPanel);
                    frame.pack();
                    frame.setVisible(true);
                }
                catch (Exception ee) {System.out.println("Error in getting data from Database : " + ee);}
            }
        });

        // Reset functionality
        JPanel panelReset = new JPanel();
        JButton resetButton = new JButton("Restore Tables");
        panelReset.add(resetButton);
        resetButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Result");
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setPreferredSize(new Dimension(600, 150));
                // JPanel panelAfterReset = new JPanel();
                resetTables(conn);
                // JLabel words = new JLabel("Tables are restored.");
                // panelAfterReset.add(words);
                // frame.getContentPane().add(panelAfterReset);
                frame.pack();
                frame.setVisible(false);
                createAndShowGUI(conn);
            }
        });
        bigPanel.add(panel);
        bigPanel.add(panelDelete);
        bigPanel.add(panelOffice);
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
          //resetTables(conn);
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
