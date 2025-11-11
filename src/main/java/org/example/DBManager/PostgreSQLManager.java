package org.example.DBManager;

import java.sql.*;
import java.util.ResourceBundle;

import lombok.Getter;
import lombok.Setter;
import org.example.IO.IO;

public class PostgreSQLManager {
    @Getter
    private static final PostgreSQLManager instance = new PostgreSQLManager();

    @Setter
    private static String resourceName = "application";

    @Getter
    private Connection connection;

    private PostgreSQLManager() {
        try {
            ResourceBundle rb = ResourceBundle.getBundle(resourceName);
            String url = rb.getString("DATABASE_URL");
            String user = rb.getString("DATABASE_USER");
            String password = rb.getString("DATABASE_PASSWORD");
            connection = DriverManager.getConnection(url, user, password);
            connection.createStatement().execute(
                            "CREATE TABLE IF NOT EXISTS book (" +
                                    "id BIGSERIAL PRIMARY KEY," +
                                    "name VARCHAR NOT NULL," +
                                    "path VARCHAR NOT NULL," +
                                    ");" +
                                    "CREATE TABLE IF NOT EXISTS bookmark (" +
                                    "id BIGSERIAL PRIMARY KEY," +
                                    "book_id BIGINT NOT NULL REFERENCES book(id) ON DELETE CASCADE," +
                                    "page INTEGER NOT NULL," +
                                    "date DATE NOT NULL," +
                                    ");"
                    );
        } catch (SQLException e) {
            IO.printError("Error while connecting to database and preparing tables");
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Exception during closing connection");
        }
    }
}
