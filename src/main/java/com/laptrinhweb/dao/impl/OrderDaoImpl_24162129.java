package com.laptrinhweb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.laptrinhweb.conn.DBConnect_24162129;
import com.laptrinhweb.dao.IOrderDao_24162129;

public class OrderDaoImpl_24162129 implements IOrderDao_24162129 {

    @Override
    public String placeCodOrder(int userId, String receiverName, String receiverPhone, String shippingAddress) {
        String findCartSql = "SELECT cartId FROM Cart WHERE userId = ? AND status = 0 LIMIT 1 FOR UPDATE";
        String findItemsSql = "SELECT ci.productId, ci.quantity, ci.unitPrice, p.amount "
                + "FROM CartItem ci JOIN Product p ON ci.productId = p.productId "
                + "WHERE ci.cartId = ? FOR UPDATE";
        String insertOrderSql = "INSERT INTO Orders (orderId, userId, receiverName, receiverPhone, shippingAddress, "
            + "totalAmount, paymentMethod, status, orderDate) VALUES (?, ?, ?, ?, ?, ?, 'COD', 'NEW', CURRENT_TIMESTAMP)";
        String insertItemSql = "INSERT INTO OrderItem (orderItemId, orderId, productId, quantity, unitPrice) "
                + "VALUES (?, ?, ?, ?, ?)";
        String decreaseAmountSql = "UPDATE Product SET amount = amount - ? WHERE productId = ? AND amount >= ?";
        String closeCartSql = "UPDATE Cart SET status = 1, buyDate = CURRENT_TIMESTAMP WHERE cartId = ?";

        try (Connection connection = DBConnect_24162129.getConnection()) {
            if (connection == null) {
                return null;
            }
            connection.setAutoCommit(false);
            try {
                String cartId;
                try (PreparedStatement statement = connection.prepareStatement(findCartSql)) {
                    statement.setInt(1, userId);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (!resultSet.next()) {
                            connection.rollback();
                            return null;
                        }
                        cartId = resultSet.getString("cartId");
                    }
                }

                String orderId = UUID.randomUUID().toString();
                double totalAmount = 0;
                int itemCount = 0;
                try (PreparedStatement items = connection.prepareStatement(findItemsSql)) {
                    items.setString(1, cartId);
                    try (ResultSet resultSet = items.executeQuery()) {
                        while (resultSet.next()) {
                            int quantity = resultSet.getInt("quantity");
                            int availableAmount = resultSet.getInt("amount");
                            if (quantity < 1 || availableAmount < quantity) {
                                connection.rollback();
                                return null;
                            }
                            totalAmount += resultSet.getDouble("unitPrice") * quantity;
                            itemCount++;
                        }
                    }
                }
                if (itemCount == 0) {
                    connection.rollback();
                    return null;
                }

                try (PreparedStatement order = connection.prepareStatement(insertOrderSql)) {
                    order.setString(1, orderId);
                    order.setInt(2, userId);
                    order.setString(3, receiverName);
                    order.setString(4, receiverPhone);
                    order.setString(5, shippingAddress);
                    order.setDouble(6, totalAmount);
                    order.executeUpdate();
                }

                try (PreparedStatement items = connection.prepareStatement(findItemsSql);
                        PreparedStatement insertItem = connection.prepareStatement(insertItemSql);
                        PreparedStatement decreaseAmount = connection.prepareStatement(decreaseAmountSql)) {
                    items.setString(1, cartId);
                    try (ResultSet resultSet = items.executeQuery()) {
                        while (resultSet.next()) {
                            int productId = resultSet.getInt("productId");
                            int quantity = resultSet.getInt("quantity");
                            insertItem.setString(1, UUID.randomUUID().toString());
                            insertItem.setString(2, orderId);
                            insertItem.setInt(3, productId);
                            insertItem.setInt(4, quantity);
                            insertItem.setDouble(5, resultSet.getDouble("unitPrice"));
                            insertItem.executeUpdate();

                            decreaseAmount.setInt(1, quantity);
                            decreaseAmount.setInt(2, productId);
                            decreaseAmount.setInt(3, quantity);
                            if (decreaseAmount.executeUpdate() != 1) {
                                connection.rollback();
                                return null;
                            }
                        }
                    }
                }

                try (PreparedStatement closeCart = connection.prepareStatement(closeCartSql)) {
                    closeCart.setString(1, cartId);
                    closeCart.executeUpdate();
                }
                connection.commit();
                return orderId;
            } catch (SQLException exception) {
                connection.rollback();
                throw exception;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}