package com.laptrinhweb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.laptrinhweb.conn.DBConnect_24162129;
import com.laptrinhweb.dao.ICartDao_24162129;
import com.laptrinhweb.model.CartItem_24162129;

public class CartDaoImpl_24162129 implements ICartDao_24162129 {

    private static final String ITEM_COLUMNS = "ci.cartItemId, ci.quantity, ci.unitPrice, ci.productId, ci.cartId, "
            + "p.productName, p.images, p.amount";

    @Override
    public List<CartItem_24162129> findItemsByUserId(int userId) {
        List<CartItem_24162129> items = new ArrayList<>();
        String sql = "SELECT " + ITEM_COLUMNS + " FROM CartItem ci "
                + "JOIN Cart c ON ci.cartId = c.cartId JOIN Product p ON ci.productId = p.productId "
                + "WHERE c.userId = ? AND c.status = 0 ORDER BY ci.cartItemId";
        try (Connection connection = DBConnect_24162129.getConnection();
                PreparedStatement statement = connection == null ? null : connection.prepareStatement(sql)) {
            if (statement == null) {
                return items;
            }
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    CartItem_24162129 item = new CartItem_24162129();
                    item.setCartItemId(resultSet.getString("cartItemId"));
                    item.setQuantity(resultSet.getInt("quantity"));
                    item.setUnitPrice(resultSet.getDouble("unitPrice"));
                    item.setProductId(resultSet.getInt("productId"));
                    item.setCartId(resultSet.getString("cartId"));
                    item.setProductName(resultSet.getString("productName"));
                    item.setProductImages(resultSet.getString("images"));
                    item.setAvailableAmount(resultSet.getInt("amount"));
                    items.add(item);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return items;
    }

    @Override
    public boolean addItem(int userId, int productId, int quantity) {
        if (quantity < 1) {
            return false;
        }
        String productSql = "SELECT price, amount FROM Product WHERE productId = ? AND status = 1 FOR UPDATE";
        try (Connection connection = DBConnect_24162129.getConnection()) {
            if (connection == null) {
                return false;
            }
            connection.setAutoCommit(false);
            try (PreparedStatement productStatement = connection.prepareStatement(productSql)) {
                productStatement.setInt(1, productId);
                try (ResultSet productResult = productStatement.executeQuery()) {
                    if (!productResult.next() || productResult.getInt("amount") < quantity) {
                        connection.rollback();
                        return false;
                    }
                    double price = productResult.getDouble("price");
                    int availableAmount = productResult.getInt("amount");
                    String cartId = getOrCreateCart(connection, userId);
                    String existingId = null;
                    int existingQuantity = 0;
                    String findItemSql = "SELECT cartItemId, quantity FROM CartItem WHERE cartId = ? AND productId = ? FOR UPDATE";
                    try (PreparedStatement findItem = connection.prepareStatement(findItemSql)) {
                        findItem.setString(1, cartId);
                        findItem.setInt(2, productId);
                        try (ResultSet itemResult = findItem.executeQuery()) {
                            if (itemResult.next()) {
                                existingId = itemResult.getString("cartItemId");
                                existingQuantity = itemResult.getInt("quantity");
                            }
                        }
                    }
                    if (existingId != null) {
                        if (existingQuantity + quantity > availableAmount) {
                            connection.rollback();
                            return false;
                        }
                        try (PreparedStatement update = connection.prepareStatement(
                                "UPDATE CartItem SET quantity = ?, unitPrice = ? WHERE cartItemId = ?")) {
                            update.setInt(1, existingQuantity + quantity);
                            update.setDouble(2, price);
                            update.setString(3, existingId);
                            update.executeUpdate();
                        }
                    } else {
                        try (PreparedStatement insert = connection.prepareStatement(
                                "INSERT INTO CartItem (cartItemId, quantity, unitPrice, productId, cartId) VALUES (?, ?, ?, ?, ?)")) {
                            insert.setString(1, UUID.randomUUID().toString());
                            insert.setInt(2, quantity);
                            insert.setDouble(3, price);
                            insert.setInt(4, productId);
                            insert.setString(5, cartId);
                            insert.executeUpdate();
                        }
                    }
                }
            }
            connection.commit();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateItem(int userId, String cartItemId, int quantity) {
        if (quantity < 1 || cartItemId == null || cartItemId.isBlank()) {
            return false;
        }
        String sql = "UPDATE CartItem ci JOIN Cart c ON ci.cartId = c.cartId JOIN Product p ON ci.productId = p.productId "
                + "SET ci.quantity = ? WHERE ci.cartItemId = ? AND c.userId = ? AND c.status = 0 AND p.status = 1 AND ? <= p.amount";
        return executeUpdate(sql, quantity, cartItemId, userId, quantity);
    }

    @Override
    public boolean deleteItem(int userId, String cartItemId) {
        if (cartItemId == null || cartItemId.isBlank()) {
            return false;
        }
        String sql = "DELETE ci FROM CartItem ci JOIN Cart c ON ci.cartId = c.cartId "
                + "WHERE ci.cartItemId = ? AND c.userId = ? AND c.status = 0";
        return executeUpdate(sql, cartItemId, userId);
    }

    private boolean executeUpdate(String sql, Object... parameters) {
        try (Connection connection = DBConnect_24162129.getConnection();
                PreparedStatement statement = connection == null ? null : connection.prepareStatement(sql)) {
            if (statement == null) {
                return false;
            }
            for (int index = 0; index < parameters.length; index++) {
                if (parameters[index] instanceof Integer) {
                    statement.setInt(index + 1, (Integer) parameters[index]);
                } else {
                    statement.setString(index + 1, String.valueOf(parameters[index]));
                }
            }
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    private String getOrCreateCart(Connection connection, int userId) throws SQLException {
        String selectSql = "SELECT cartId FROM Cart WHERE userId = ? AND status = 0 LIMIT 1";
        try (PreparedStatement select = connection.prepareStatement(selectSql)) {
            select.setInt(1, userId);
            try (ResultSet resultSet = select.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("cartId");
                }
            }
        }
        String cartId = UUID.randomUUID().toString();
        try (PreparedStatement insert = connection.prepareStatement(
                "INSERT INTO Cart (cartId, userId, buyDate, status) VALUES (?, ?, CURRENT_TIMESTAMP, 0)")) {
            insert.setString(1, cartId);
            insert.setInt(2, userId);
            insert.executeUpdate();
        }
        return cartId;
    }
}