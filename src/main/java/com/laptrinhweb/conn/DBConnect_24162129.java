package com.laptrinhweb.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnect_24162129 {

    private static final String URL = "jdbc:mysql://localhost:3306/ltw_de06_24162129"
            + "?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";

    private DBConnect_24162129() {
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException exception) {
            System.err.println("Không tìm thấy MySQL JDBC Driver.");
            exception.printStackTrace();
        } catch (SQLException exception) {
            System.err.println("Không thể kết nối đến MySQL.");
            exception.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Kết nối MySQL thành công!");
            } else {
                System.err.println("Kết nối MySQL thất bại.");
            }
        } catch (SQLException exception) {
            System.err.println("Lỗi khi đóng kết nối MySQL.");
            exception.printStackTrace();
        }
    }
}