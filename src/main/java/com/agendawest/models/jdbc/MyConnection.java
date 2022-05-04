package com.agendawest.models.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private static Connection conn = null;

    // Datos de acceso a la BD
    private final String driver = "org.mariadb.jdbc.Driver";
    private final String dsn = "jdbc:mariadb://localhost:3306/agenda";
    private final String user = "admin";
    private final String pass = "admin";


    private MyConnection() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(dsn, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (conn == null) {
            new MyConnection();
        }
        return conn;
    }

    public static void close() {

        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
