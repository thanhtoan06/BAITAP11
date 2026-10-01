package com.laptrinhweb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.laptrinhweb.conn.DBConnect_24162129;
import com.laptrinhweb.dao.ICategoryDao_24162129;
import com.laptrinhweb.model.Category_24162129;

public class CategoryDaoImpl_24162129 implements ICategoryDao_24162129 {

    private static final String SELECT_COLUMNS = "categoryId, categoryName, images, status";

    @Override
    public List<Category_24162129> findAll() {
        return queryList("SELECT " + SELECT_COLUMNS + " FROM Category ORDER BY categoryId ASC", null);
    }

    @Override
    public List<Category_24162129> findAll(int page, int pageSize) {
        int safePage = Math.max(page, 1);
        int safePageSize = Math.max(pageSize, 1);
        String sql = "SELECT " + SELECT_COLUMNS + " FROM Category ORDER BY categoryId ASC LIMIT ? OFFSET ?";
        return queryList(sql, statement -> {
            statement.setInt(1, safePageSize);
            statement.setInt(2, (safePage - 1) * safePageSize);
        });
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM Category";
        try (Connection connection = DBConnect_24162129.getConnection();
                PreparedStatement statement = connection == null ? null : connection.prepareStatement(sql)) {
            if (statement == null) {
                return 0;
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? resultSet.getInt(1) : 0;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    @Override
    public Category_24162129 findById(int id) {
        String sql = "SELECT " + SELECT_COLUMNS + " FROM Category WHERE categoryId = ?";
        try (Connection connection = DBConnect_24162129.getConnection();
                PreparedStatement statement = connection == null ? null : connection.prepareStatement(sql)) {
            if (statement == null) {
                return null;
            }
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapCategory(resultSet) : null;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void insert(Category_24162129 category) {
        String sql = "INSERT INTO Category (categoryName, images, status) VALUES (?, ?, ?)";
        executeUpdate(sql, statement -> {
            statement.setString(1, category.getCategoryName());
            statement.setString(2, category.getImages());
            statement.setInt(3, category.getStatus());
        });
    }

    @Override
    public void update(Category_24162129 category) {
        String sql = "UPDATE Category SET categoryName = ?, images = ?, status = ? WHERE categoryId = ?";
        executeUpdate(sql, statement -> {
            statement.setString(1, category.getCategoryName());
            statement.setString(2, category.getImages());
            statement.setInt(3, category.getStatus());
            statement.setInt(4, category.getCategoryId());
        });
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Category WHERE categoryId = ?";
        executeUpdate(sql, statement -> statement.setInt(1, id));
    }

    private List<Category_24162129> queryList(String sql, StatementSetter setter) {
        List<Category_24162129> categories = new ArrayList<>();
        try (Connection connection = DBConnect_24162129.getConnection();
                PreparedStatement statement = connection == null ? null : connection.prepareStatement(sql)) {
            if (statement == null) {
                return categories;
            }
            if (setter != null) {
                setter.setParameters(statement);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    categories.add(mapCategory(resultSet));
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return categories;
    }

    private void executeUpdate(String sql, StatementSetter setter) {
        try (Connection connection = DBConnect_24162129.getConnection();
                PreparedStatement statement = connection == null ? null : connection.prepareStatement(sql)) {
            if (statement == null) {
                return;
            }
            setter.setParameters(statement);
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private Category_24162129 mapCategory(ResultSet resultSet) throws SQLException {
        Category_24162129 category = new Category_24162129();
        category.setCategoryId(resultSet.getInt("categoryId"));
        category.setCategoryName(resultSet.getString("categoryName"));
        category.setImages(resultSet.getString("images"));
        category.setStatus(resultSet.getInt("status"));
        return category;
    }

    @FunctionalInterface
    private interface StatementSetter {
        void setParameters(PreparedStatement statement) throws SQLException;
    }
}