package com.lc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/db_pbol_tb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection createMySQLConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        connection.setAutoCommit(false);
        return connection;
    }
}
