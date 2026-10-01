CREATE DATABASE IF NOT EXISTS ltw_de06_24162129
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE ltw_de06_24162129;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS OrderItem;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS CartItem;
DROP TABLE IF EXISTS Cart;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS Category;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Seller;
DROP TABLE IF EXISTS UserRoles;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE UserRoles (
  roleId INT AUTO_INCREMENT PRIMARY KEY,
  roleName VARCHAR(50)
) ENGINE = InnoDB;

CREATE TABLE Seller (
  sellerId INT AUTO_INCREMENT PRIMARY KEY,
  sellerName VARCHAR(50),
  images VARCHAR(500),
  status INT
) ENGINE = InnoDB;

CREATE TABLE Users (
  userId INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50),
  email VARCHAR(100),
  fullname VARCHAR(50),
  password VARCHAR(50),
  images VARCHAR(500),
  phone VARCHAR(20),
  status INT,
  code VARCHAR(50),
  roleId INT,
  sellerId INT,
  FOREIGN KEY (roleId) REFERENCES UserRoles(roleId),
  FOREIGN KEY (sellerId) REFERENCES Seller(sellerId)
) ENGINE = InnoDB;

CREATE TABLE Category (
  categoryId INT AUTO_INCREMENT PRIMARY KEY,
  categoryName VARCHAR(200),
  images VARCHAR(500),
  status INT
) ENGINE = InnoDB;

CREATE TABLE Product (
  productId INT AUTO_INCREMENT PRIMARY KEY,
  productName VARCHAR(200),
  productCode BIGINT,
  categoryId INT,
  description VARCHAR(500),
  price DOUBLE,
  amount INT,
  stock INT,
  images VARCHAR(500),
  wishlist INT,
  status INT,
  createDate DATE,
  sellerId INT,
  FOREIGN KEY (categoryId) REFERENCES Category(categoryId),
  FOREIGN KEY (sellerId) REFERENCES Seller(sellerId)
) ENGINE = InnoDB;

CREATE TABLE Orders (
  orderId VARCHAR(50) PRIMARY KEY,
  userId INT,
  receiverName VARCHAR(100),
  receiverPhone VARCHAR(20),
  shippingAddress VARCHAR(500),
  totalAmount DOUBLE,
  paymentMethod VARCHAR(20),
  status VARCHAR(30),
  orderDate DATETIME,
  FOREIGN KEY (userId) REFERENCES Users(userId)
) ENGINE = InnoDB;

CREATE TABLE OrderItem (
  orderItemId VARCHAR(50) PRIMARY KEY,
  orderId VARCHAR(50),
  productId INT,
  quantity INT,
  unitPrice DOUBLE,
  FOREIGN KEY (orderId) REFERENCES Orders(orderId),
  FOREIGN KEY (productId) REFERENCES Product(productId)
) ENGINE = InnoDB;

CREATE TABLE Cart (
  cartId VARCHAR(50) PRIMARY KEY,
  userId INT,
  buyDate DATETIME,
  status INT,
  FOREIGN KEY (userId) REFERENCES Users(userId)
) ENGINE = InnoDB;

CREATE TABLE CartItem (
  cartItemId VARCHAR(50) PRIMARY KEY,
  quantity INT,
  unitPrice DOUBLE,
  productId INT,
  cartId VARCHAR(50),
  FOREIGN KEY (productId) REFERENCES Product(productId),
  FOREIGN KEY (cartId) REFERENCES Cart(cartId)
) ENGINE = InnoDB;

INSERT INTO UserRoles (roleId, roleName) VALUES
  (1, 'Admin'),
  (2, 'User'),
  (3, 'Seller');

INSERT INTO Seller (sellerId, sellerName, images, status) VALUES
  (1, 'Tech Store', 'tech-store.jpg', 1),
  (2, 'Home Living', 'home-living.jpg', 1);

INSERT INTO Users (userId, username, email, fullname, password, images, phone, status, code, roleId, sellerId) VALUES
  (1, 'admin', 'admin@example.com', 'System Administrator', 'admin123', 'admin.jpg', '0900000001', 1, NULL, 1, NULL),
  (2, 'user01', 'user01@example.com', 'Nguyen Van User', 'user123', 'user01.jpg', '0900000002', 1, NULL, 2, NULL);

INSERT INTO Category (categoryId, categoryName, images, status) VALUES
  (1, 'Electronics', 'electronics.jpg', 1),
  (2, 'Home Appliances', 'home-appliances.jpg', 1),
  (3, 'Accessories', 'accessories.jpg', 1);

INSERT INTO Product (productId, productName, productCode, categoryId, description, price, amount, stock, images, wishlist, status, createDate, sellerId) VALUES
  (1, 'Wireless Headphones', 1000001, 1, 'Bluetooth over-ear headphones', 129.99, 20, 20, 'wireless-headphones.jpg', 0, 1, '2026-01-10', 1),
  (2, 'Mechanical Keyboard', 1000002, 1, 'RGB mechanical keyboard for work and play', 89.50, 15, 15, 'mechanical-keyboard.jpg', 0, 1, '2026-01-12', 1),
  (3, 'USB-C Hub', 1000003, 3, 'Multi-port USB-C adapter', 35.00, 30, 30, 'usb-c-hub.jpg', 0, 1, '2026-01-15', 1),
  (4, 'Air Purifier', 2000001, 2, 'Compact air purifier for bedrooms', 159.00, 10, 10, 'air-purifier.jpg', 0, 1, '2026-02-01', 2),
  (5, 'Desk Lamp', 2000002, 2, 'Adjustable LED desk lamp', 42.75, 25, 25, 'desk-lamp.jpg', 0, 1, '2026-02-05', 2),
  (6, 'Ceramic Mug', 2000003, 3, 'Reusable ceramic coffee mug', 14.90, 40, 40, 'ceramic-mug.jpg', 0, 1, '2026-02-08', 2);