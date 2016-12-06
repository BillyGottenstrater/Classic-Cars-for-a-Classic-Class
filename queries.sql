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


