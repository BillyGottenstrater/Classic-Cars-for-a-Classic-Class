SELECT Customers1.customerName AS CustomerName, Customers1.country AS Country, 
ROUND(SUM(Payments1.amount),2) AS Total_Amount 
FROM Customers1 JOIN Payments1 
ON Customers1.customerNumber = Payments1.customerNumber 
GROUP BY Customers1.customerName ORDER BY Total_Amount DESC;


SELECT Customers1.country AS Country, 
ROUND(SUM(Payments1.amount),2) AS Total_Amount
FROM Customers1 JOIN Payments1 
ON Customers1.customerNumber = Payments1.customerNumber 
GROUP BY Customers1.country ORDER BY Total_Amount DESC;


SELECT Products1.productName AS ProductName, 
COUNT(OrderDetails1.productCode) AS Quantity_Sold 
FROM Products1 INNER JOIN OrderDetails1 
ON Products1.productCode = OrderDetails1.productCode 
GROUP BY ProductName ORDER BY Quantity_Sold DESC;


SELECT productName, buyPrice, MSRP, ROUND((MSRP - buyPrice),2) AS Diff 
FROM Products1;


SELECT Customers1.customerName, Products1.productName, Orders1.status, 
Customers1.creditLimit, OrderDetails1.priceEach, Orders1.orderDate 
FROM Customers1, Products1, Orders1, OrderDetails1 
WHERE OrderDetails1.OrderNumber = Orders1.OrderNumber 
AND Orders1.CustomerNumber = Customers1.CustomerNumber 
AND OrderDetails1.ProductCode = Products1.ProductCode 
AND Customers1.country = "USA" AND Customers1.state = "CA";


SELECT Customers1.CustomerName, SUM(OrderDetails1.priceEach), 
Customers1.creditLimit, 
(Customers1.creditLimit - SUM(OrderDetails1.priceEach)) AS Diff 
FROM OrderDetails1, Orders1, Customers1 
WHERE OrderDetails1.orderNumber = Orders1.orderNumber 
AND Orders1.customerNumber = Customers1.customerNumber 
GROUP BY Customers1.customerName;


SELECT Customers1.customerName AS Customer, MONTHNAME(Orders1.orderDate) AS Month, 
COUNT(Orders1.orderNumber) AS 'Orders Count' FROM Customers1, Orders1, Payments1 
WHERE Payments1.customerNumber = Customers1.customerNumber 
AND Customers1.customerNumber = Orders1.customerNumber 
GROUP BY Customers1.customerName, MONTHNAME(Orders1.orderDate) 
HAVING COUNT(Orders1.orderNumber) >= 5 
ORDER BY Customers1.customerName, COUNT(Orders1.orderNumber) DESC;


SELECT Customers1.customerName AS 'Customer Name', Products1.productName AS 'Product Name', 
MAX(Products1.MSRP) AS 'MAX MSRP of Product Per Customer Name' 
FROM Customers1, Products1, OrderDetails1, Orders1  
WHERE Customers1.customerNumber = Orders1.customerNumber 
AND Orders1.orderNumber = OrderDetails1.orderNumber 
AND OrderDetails1.productCode = Products1.productCode 
GROUP BY Customers1.customerName;


SELECT Customers1.customerName, Orders1.status AS 'Status Level', 
COUNT(Orders1.status) AS 'Total Count' FROM Customers1, Orders1, OrderDetails1 
WHERE Orders1.orderNumber = OrderDetails1.orderNumber 
AND Customers1.customerNumber = Orders1.customerNumber 
GROUP BY Customers1.customerName, Orders1.status;


SELECT Orders1.status AS 'Status Level', 
COUNT(Orders1.status) AS 'Total Count' FROM Orders1, OrderDetails1 
WHERE Orders1.orderNumber = OrderDetails1.orderNumber 
GROUP BY Orders1.status;

