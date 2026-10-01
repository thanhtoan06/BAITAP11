package com.laptrinhweb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.laptrinhweb.conn.DBConnect_24162129;
import com.laptrinhweb.dao.IUserDao_24162129;
import com.laptrinhweb.model.Users_24162129;

public class UserDaoImpl_24162129 implements IUserDao_24162129 {

    private static final String USER_COLUMNS = "userId, username, email, fullname, password, images, phone, "
            + "status, code, roleId, sellerId";

    @Override
    public Users_24162129 findByUsername(String username) {
        return findOne("SELECT " + USER_COLUMNS + " FROM Users WHERE username = ?", username);
    }

    @Override
    public Users_24162129 findByEmail(String email) {
        return findOne("SELECT " + USER_COLUMNS + " FROM Users WHERE email = ?", email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return exists("SELECT 1 FROM Users WHERE username = ?", username);
    }

    @Override
    public boolean checkExistEmail(String email) {
        return exists("SELECT 1 FROM Users WHERE email = ?", email);
    }

    @Override
    public void insert(Users_24162129 user) {
        String sql = "INSERT INTO Users (username, email, fullname, password, images, phone, status, code, roleId, sellerId) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnect_24162129.getConnection();
                PreparedStatement statement = connection == null ? null : connection.prepareStatement(sql)) {
            if (statement == null) {
                return;
            }
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getFullname());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getImages());
            statement.setString(6, user.getPhone());
            statement.setInt(7, user.getStatus());
            statement.setString(8, user.getCode());
            statement.setInt(9, user.getRoleId());
            if (user.getSellerId() > 0) {
                statement.setInt(10, user.getSellerId());
            } else {
                statement.setNull(10, java.sql.Types.INTEGER);
            }
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void updateStatus(String email, int status) {
        update("UPDATE Users SET status = ? WHERE email = ?", status, email);
    }

    @Override
    public void updateCode(String email, String code) {
        update("UPDATE Users SET code = ? WHERE email = ?", code, email);
    }

    @Override
    public Users_24162129 login(String username, String password) {
        String sql = "SELECT " + USER_COLUMNS + " FROM Users WHERE username = ? AND password = ? AND status = 1";
        return findOne(sql, username, password);
    }

    @Override
    public List<Users_24162129> findAll(int page, int pageSize) {
        List<Users_24162129> users = new ArrayList<>();
        int safePage = Math.max(page, 1);
        int safePageSize = Math.max(pageSize, 1);
        String sql = "SELECT u.userId, u.username, u.email, u.fullname, u.password, u.images, u.phone, "
                + "u.status, u.code, u.roleId, u.sellerId, r.roleName "
                + "FROM Users u JOIN UserRoles r ON u.roleId = r.roleId "
                + "ORDER BY u.userId ASC LIMIT ? OFFSET ?";
        try (Connection connection = DBConnect_24162129.getConnection();
                PreparedStatement statement = connection == null ? null : connection.prepareStatement(sql)) {
            if (statement == null) {
                return users;
            }
            statement.setInt(1, safePageSize);
            statement.setInt(2, (safePage - 1) * safePageSize);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(mapUser(resultSet, true));
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return users;
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM Users";
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
    public Users_24162129 findById(int id) {
        String sql = "SELECT u.userId, u.username, u.email, u.fullname, u.password, u.images, u.phone, "
                + "u.status, u.code, u.roleId, u.sellerId, r.roleName "
                + "FROM Users u JOIN UserRoles r ON u.roleId = r.roleId WHERE u.userId = ?";
        try (Connection connection = DBConnect_24162129.getConnection();
                PreparedStatement statement = connection == null ? null : connection.prepareStatement(sql)) {
            if (statement == null) {
                return null;
            }
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapUser(resultSet, true) : null;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Users_24162129 user) {
        String sql = "UPDATE Users SET username = ?, email = ?, fullname = ?, password = ?, images = ?, "
                + "phone = ?, status = ?, roleId = ?, sellerId = ? WHERE userId = ?";
        try (Connection connection = DBConnect_24162129.getConnection();
                PreparedStatement statement = connection == null ? null : connection.prepareStatement(sql)) {
            if (statement == null) {
                return;
            }
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getFullname());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getImages());
            statement.setString(6, user.getPhone());
            statement.setInt(7, user.getStatus());
            statement.setInt(8, user.getRoleId());
            if (user.getSellerId() > 0) {
                statement.setInt(9, user.getSellerId());
            } else {
                statement.setNull(9, java.sql.Types.INTEGER);
            }
            statement.setInt(10, user.getUserId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String deleteCartItemsSql = "DELETE FROM CartItem WHERE cartId IN (SELECT cartId FROM Cart WHERE userId = ?)";
        String deleteCartsSql = "DELETE FROM Cart WHERE userId = ?";
        String deleteUserSql = "DELETE FROM Users WHERE userId = ?";
        try (Connection connection = DBConnect_24162129.getConnection()) {
            if (connection == null) {
                return;
            }
            connection.setAutoCommit(false);
            try (PreparedStatement deleteCartItems = connection.prepareStatement(deleteCartItemsSql);
                    PreparedStatement deleteCarts = connection.prepareStatement(deleteCartsSql);
                    PreparedStatement deleteUser = connection.prepareStatement(deleteUserSql)) {
                deleteCartItems.setInt(1, id);
                deleteCartItems.executeUpdate();
                deleteCarts.setInt(1, id);
                deleteCarts.executeUpdate();
                deleteUser.setInt(1, id);
                deleteUser.executeUpdate();
                connection.commit();
            } catch (SQLException exception) {
                connection.rollback();
                throw exception;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private Users_24162129 findOne(String sql, String... parameters) {
        try (Connection connection = DBConnect_24162129.getConnection();
                PreparedStatement statement = connection == null ? null : connection.prepareStatement(sql)) {
            if (statement == null) {
                return null;
            }
            setParameters(statement, parameters);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapUser(resultSet, false) : null;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private boolean exists(String sql, String parameter) {
        try (Connection connection = DBConnect_24162129.getConnection();
                PreparedStatement statement = connection == null ? null : connection.prepareStatement(sql)) {
            if (statement == null) {
                return false;
            }
            statement.setString(1, parameter);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    private void update(String sql, int status, String email) {
        try (Connection connection = DBConnect_24162129.getConnection();
                PreparedStatement statement = connection == null ? null : connection.prepareStatement(sql)) {
            if (statement == null) {
                return;
            }
            statement.setInt(1, status);
            statement.setString(2, email);
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void update(String sql, String code, String email) {
        try (Connection connection = DBConnect_24162129.getConnection();
                PreparedStatement statement = connection == null ? null : connection.prepareStatement(sql)) {
            if (statement == null) {
                return;
            }
            statement.setString(1, code);
            statement.setString(2, email);
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void setParameters(PreparedStatement statement, String... parameters) throws SQLException {
        for (int index = 0; index < parameters.length; index++) {
            statement.setString(index + 1, parameters[index]);
        }
    }

    private Users_24162129 mapUser(ResultSet resultSet, boolean includeRoleName) throws SQLException {
        Users_24162129 user = new Users_24162129();
        user.setUserId(resultSet.getInt("userId"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setFullname(resultSet.getString("fullname"));
        user.setPassword(resultSet.getString("password"));
        user.setImages(resultSet.getString("images"));
        user.setPhone(resultSet.getString("phone"));
        user.setStatus(resultSet.getInt("status"));
        user.setCode(resultSet.getString("code"));
        user.setRoleId(resultSet.getInt("roleId"));
        user.setSellerId(resultSet.getInt("sellerId"));
        if (includeRoleName) {
            user.setRoleName(resultSet.getString("roleName"));
        }
        return user;
    }
}