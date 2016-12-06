TeamName: Solving Queries Lavishly
TeamMembers:
  William Gottenstrater wgottens@calpoly.edu
  Ruslan Adamovics radamovi@calpoly.edu
  Hans Schumann hschuman@calpoly.edu
Application Name: Classic Cars for a Classic Class 
Application Description: Basic analysis of a data sets regarding model car sales

Application Create Table Statements: 

CREATE TABLE Customers (
  customerNumber INTEGER NOT NULL,
  customerName VARCHAR(50) NOT NULL,
  contactLastName VARCHAR(50) NOT NULL,
  contactFirstName VARCHAR(50) NOT NULL,
  phone VARCHAR(50) NOT NULL,
  addressLine1 VARCHAR(50) NOT NULL,
  addressLine2 VARCHAR(50) NULL,
  city VARCHAR(50) NOT NULL,
  state VARCHAR(50) NULL,
  postalCode VARCHAR(15) NULL,
  country VARCHAR(50) NOT NULL,
  salesRepEmployeeNumber INTEGER NULL,
  creditLimit DOUBLE NULL,
  PRIMARY KEY (customerNumber)
);

CREATE TABLE Employees (
  employeeNumber INTEGER NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  firstName VARCHAR(50) NOT NULL,
  extension VARCHAR(10) NOT NULL,
  email VARCHAR(100) NOT NULL,
  officeCode VARCHAR(20) NOT NULL,
  reportsTo INTEGER NULL,
  jobTitle VARCHAR(50) NOT NULL,
  PRIMARY KEY (employeeNumber)
);

CREATE TABLE Offices (
  officeCode VARCHAR(50) NOT NULL,
  city VARCHAR(50) NOT NULL,
  phone VARCHAR(50) NOT NULL,
  addressLine1 VARCHAR(50) NOT NULL,
  addressLine2 VARCHAR(50) NULL,
  state VARCHAR(50) NULL,
  country VARCHAR(50) NOT NULL,
  postalCode VARCHAR(10) NOT NULL,
  territory VARCHAR(10) NOT NULL,
  PRIMARY KEY (officeCode)
);

CREATE TABLE Products (
  productCode VARCHAR(50) NOT NULL,
  productName VARCHAR(70) NOT NULL,
  productLine VARCHAR(50) NOT NULL,
  productScale VARCHAR(10) NOT NULL,
  productVendor VARCHAR(50) NOT NULL,
  productDescription TEXT NOT NULL,
  quantityInStock SMALLINT NOT NULL,
  buyPrice DOUBLE NOT NULL,
  MSRP DOUBLE NOT NULL,
  PRIMARY KEY (productCode)
);

CREATE TABLE OrderDetails (
  orderNumber INTEGER NOT NULL,
  productCode VARCHAR(50) NOT NULL,
  quantityOrdered INTEGER NOT NULL,
  priceEach DOUBLE NOT NULL,
  orderLineNumber SMALLINT NOT NULL,
  PRIMARY KEY (orderNumber, productCode),
  FOREIGN KEY (productCode) REFERENCES Products(productCode)
);

CREATE TABLE Orders (
  orderNumber INTEGER NOT NULL,
  orderDate DATETIME NOT NULL,
  requiredDate DATETIME NOT NULL,
  shippedDate DATETIME NULL,
  status VARCHAR(15) NOT NULL,
  comments TEXT NULL,
  customerNumber INTEGER NOT NULL,
  PRIMARY KEY (orderNumber),
  FOREIGN KEY (customerNumber) REFERENCES Customers(customerNumber),
  FOREIGN KEY (orderNumber) REFERENCES OrderDetails(orderNumber)
);

CREATE TABLE Payments (
  customerNumber INTEGER NOT NULL,
  checkNumber VARCHAR(50) NOT NULL,
  paymentDate DATETIME NOT NULL,
  amount DOUBLE NOT NULL,
  PRIMARY KEY (customerNumber, checkNumber),
  FOREIGN KEY (customerNumber) REFERENCES Customers(customerNumber)
);