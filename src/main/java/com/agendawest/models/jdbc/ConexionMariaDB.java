package com.agendawest.models.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMariaDB {

    private Connection connection;

    private final String url = "jdbc:mariadb://localhost:3306/agenda";
    private final String usr = "admin";
    private final String pass = "admin";

    public ConexionMariaDB(){}

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void establishConnection() {
        try {

            connection = DriverManager.getConnection(url, usr, pass);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
