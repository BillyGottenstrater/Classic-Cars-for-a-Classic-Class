//     ---------------------
import java.sql.*;
// -----------------------


import java.util.*;
import java.io.*;
import java.lang.*;

public class JDBCTestMysql1 {
    
  private static Connection conn;
  private static int target = 1;

//////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////CREATE CONNECTION///////////////////////


/* Table Deletion Methods */

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
    String[] tables = new String[]{"Payments1","Orders1","OrderDetails1","Products1","Offices1","Employees1","Customers1"};
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
    String str = "LOAD DATA LOCAL INFILE '" + tableName + ".txt' INTO TABLE \n"
    + tableName + "FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES \n" 
    + "TERMINATED BY '\n'";
    try{
      Statement s = conn.createStatement();
      s.executeUpdate(str);
    }
    catch (Exception ee) {System.out.println("Error in filling table (" + tableName + ") : " + ee);}
  }
    
  public static void main(String args[]) {
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
      //deleteAllTables(conn); 
      Statement s1 = conn.createStatement();
      deleteTable(conn, "Customers1");
      createCustomers1(conn);
      // String table = "CREATE TABLE Customers1 (";
      // table += ("customerNumber INTEGER NOT NULL," 
      //   + "customerName VARCHAR(50) NOT NULL,"
      //   + "contactLastName VARCHAR(50) NOT NULL,"
      //   + "contactFirstName VARCHAR(50) NOT NULL,"
      //   + "phone VARCHAR(50) NOT NULL,"
      //   + "addressLine1 VARCHAR(50) NOT NULL,"
      //   + "addressLine2 VARCHAR(50) NULL,"
      //   + "city VARCHAR(50) NOT NULL,"
      //   + "state VARCHAR(50) NULL,"
      //   + "postalCode VARCHAR(15) NULL,"
      //   + "country VARCHAR(50) NOT NULL,"
      //   + "salesRepEmployeeNumber INTEGER NULL,"
      //   + "creditLimit DOUBLE NULL,");
      // table += "PRIMARY KEY (customerNumber) )";

      //System.out.println(table);
      // s1.executeUpdate("use dekhtyar");
      //s1.executeUpdate(table);
    } 
    catch (Exception ee) {System.out.println("Error in creating tables : " + ee);}
   

    try {
      Statement s2 = conn.createStatement();
      s2.executeUpdate("LOAD DATA LOCAL INFILE 'Customers1.txt' INTO TABLE Customers1 FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\n'");
      //s2.executeUpdate("INSERT INTO Books VALUES(1, 'Database Systems','Ullman')");
      //s2.executeUpdate("INSERT INTO Books VALUES(2, 'Artificial Intelligence', 'Russel, Norvig')");   
      //s2.executeUpdate("INSERT INTO Books VALUES(3, 'Problem Solving in C', 'Hanly, Koffman')");   
      ResultSet result = s2.executeQuery("SELECT * FROM Customers1 LIMIT 10");
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
    catch (Exception ee) {System.out.println("Error in adding data to Customers1 or selecting from Customers1 : " + ee);}

    /*
    try {  
      String psText = "INSERT INTO Books VALUES(?,?,?)";
      PreparedStatement ps = conn.prepareStatement(psText);

      ps.setInt(1, 4);
      ps.setString(2, "A Guide to LaTeX");
      ps.setString(3, "Kopka, Daly");
      ps.executeUpdate();
    }
    catch (Exception e03) {System.out.println(e03);}
    
    try { 
      Statement s4 = conn.createStatement();
      ResultSet result = s4.executeQuery("SELECT Title, Author FROM Books");
      boolean f = result.next(); 
      while (f)
      {
        String s = result.getString(1);
        String a = result.getString(2);
        System.out.println(s+", "+ a);
        f=result.next();
      }

      s4.executeUpdate("DROP TABLE Books");
    }
    catch (Exception ee) {System.out.println(ee);}
    */
    try {
      conn.close();
    }
    catch (Exception ex)
    {
      System.out.println("Unable to close connection");
    };
          
  }  

}
