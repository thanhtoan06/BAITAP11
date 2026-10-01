package com.laptrinhweb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.laptrinhweb.conn.DBConnect_24162129;
import com.laptrinhweb.dao.IProductDao_24162129;
import com.laptrinhweb.model.Product_24162129;

public class ProductDaoImpl_24162129 implements IProductDao_24162129 {

    private static final String PRODUCT_JOIN = "FROM Product p "
            + "LEFT JOIN Seller s ON p.sellerId = s.sellerId "
            + "LEFT JOIN Category c ON p.categoryId = c.categoryId ";

    private static final String PRODUCT_COLUMNS = "p.productId, p.productName, p.productCode, p.categoryId, "
            + "c.categoryName, p.price, p.amount, p.description, p.images, p.sellerId, s.sellerName, p.status ";

    @Override
    public List<Product_24162129> findAll() {
        return queryProducts("SELECT " + PRODUCT_COLUMNS + PRODUCT_JOIN
                + "WHERE p.status = 1 ORDER BY p.sellerId ASC, p.productId ASC");
    }

    @Override
    public List<Product_24162129> findAllGroupBySeller() {
        return findAll();
    }

    @Override
    public Product_24162129 findById(int id) {
        String sql = "SELECT " + PRODUCT_COLUMNS
                + PRODUCT_JOIN
            + "WHERE p.productId = ? AND p.status = 1";

        try (Connection connection = DBConnect_24162129.getConnection();
                PreparedStatement statement = connection == null ? null : connection.prepareStatement(sql)) {
            if (statement == null) {
                return null;
            }
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapProduct(resultSet) : null;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private List<Product_24162129> queryProducts(String sql) {
        List<Product_24162129> products = new ArrayList<>();
        try (Connection connection = DBConnect_24162129.getConnection();
                PreparedStatement statement = connection == null ? null : connection.prepareStatement(sql)) {
            if (statement == null) {
                return products;
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(mapProduct(resultSet));
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return products;
    }

    private Product_24162129 mapProduct(ResultSet resultSet) throws SQLException {
        Product_24162129 product = new Product_24162129();
        product.setProductId(resultSet.getInt("productId"));
        product.setProductName(resultSet.getString("productName"));
        product.setProductCode(resultSet.getLong("productCode"));
        product.setCategoryId(resultSet.getInt("categoryId"));
        product.setCategoryName(resultSet.getString("categoryName"));
        product.setPrice(resultSet.getDouble("price"));
        product.setAmount(resultSet.getInt("amount"));
        product.setDescription(resultSet.getString("description"));
        product.setImages(resultSet.getString("images"));
        product.setSellerId(resultSet.getInt("sellerId"));
        product.setSellerName(resultSet.getString("sellerName"));
        return product;
    }
}