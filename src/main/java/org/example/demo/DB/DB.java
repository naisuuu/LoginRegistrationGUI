package org.example.demo.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DB {
    public void setupDB() throws SQLException {
            Connection conn = null;
try{
            String url = "jdbc:sqlite:users.db";

            String createUsersTableString = "CREATE TABLE IF NOT EXISTS users (" +
                    "user_id int PRIMARY KEY, " +
                    "username varchar(25) NOT NULL, " +
                    "password varchar(25) NOT NULL)";

            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            statement.executeUpdate(createUsersTableString);
            System.out.println("Users table created");

    } catch (SQLException e) {
    System.out.println(e.getMessage());
    throw new RuntimeException(e);
} finally {
    try{
        if (conn != null){
            conn.close();
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

    }
}
