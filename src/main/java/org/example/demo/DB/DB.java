package org.example.demo.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DB {
    public void setupDB() throws SQLException {
            Connection conn = null;

            String url = "jdbc:sqlite:users.db";

            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "user_id int PRIMARY KEY, " +
                    "username varchar(25) NOT NULL, " +
                    "password varchar(25) NOT NULL)";

            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            statement.executeUpdate(createUsersTable);
            System.out.println("Users table created");
    }
}
