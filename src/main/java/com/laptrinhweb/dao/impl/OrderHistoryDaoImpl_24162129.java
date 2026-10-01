package com.laptrinhweb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.laptrinhweb.conn.DBConnect_24162129;
import com.laptrinhweb.dao.IOrderHistoryDao_24162129;
import com.laptrinhweb.model.Order_24162129;

public class OrderHistoryDaoImpl_24162129 implements IOrderHistoryDao_24162129 {

    private static final String[] STATUSES = {
            "NEW", "CONFIRMED", "PACKING", "SHIPPING", "DELIVERING", "DELIVERED", "CANCELLED", "RETURNED"
    };

    @Override
    public List<Order_24162129> findByUserId(int userId, String status) {
        List<Order_24162129> orders = new ArrayList<>();
        boolean filterByStatus = isValidStatus(status);
        String sql = "SELECT o.orderId, o.userId, o.receiverName, o.receiverPhone, o.shippingAddress, "
                + "o.totalAmount, o.paymentMethod, o.status, o.orderDate, COUNT(oi.orderItemId) AS itemCount "
                + "FROM Orders o LEFT JOIN OrderItem oi ON o.orderId = oi.orderId "
                + "WHERE o.userId = ? " + (filterByStatus ? "AND o.status = ? " : "")
                + "GROUP BY o.orderId, o.userId, o.receiverName, o.receiverPhone, o.shippingAddress, "
                + "o.totalAmount, o.paymentMethod, o.status, o.orderDate ORDER BY o.orderDate DESC";
        try (Connection connection = DBConnect_24162129.getConnection();
                PreparedStatement statement = connection == null ? null : connection.prepareStatement(sql)) {
            if (statement == null) {
                return orders;
            }
            statement.setInt(1, userId);
            if (filterByStatus) {
                statement.setString(2, status);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order_24162129 order = new Order_24162129();
                    order.setOrderId(resultSet.getString("orderId"));
                    order.setUserId(resultSet.getInt("userId"));
                    order.setReceiverName(resultSet.getString("receiverName"));
                    order.setReceiverPhone(resultSet.getString("receiverPhone"));
                    order.setShippingAddress(resultSet.getString("shippingAddress"));
                    order.setTotalAmount(resultSet.getDouble("totalAmount"));
                    order.setPaymentMethod(resultSet.getString("paymentMethod"));
                    order.setStatus(resultSet.getString("status"));
                    order.setOrderDate(resultSet.getTimestamp("orderDate"));
                    order.setItemCount(resultSet.getInt("itemCount"));
                    orders.add(order);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return orders;
    }

    private boolean isValidStatus(String status) {
        for (String validStatus : STATUSES) {
            if (validStatus.equals(status)) {
                return true;
            }
        }
        return false;
    }
}