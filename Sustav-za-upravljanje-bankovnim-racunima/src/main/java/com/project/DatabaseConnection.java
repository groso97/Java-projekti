package com.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static final String URL="jdbc:mysql://localhost:3306/bank-account-management-system";
    public static final String USER="root";
    public static final String PASSWORD="goranroso1997";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
