package org.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;


public class DB {

    private static final Logger logger = LogManager.getLogger(DB.class);

    public static void setupDB() throws SQLException {

        String url = "jdbc:sqlite:users.db";

        String createUsersTableString = "CREATE TABLE IF NOT EXISTS users (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username varchar(25) NOT NULL, " +
                "password varchar(25) NOT NULL)";

        try {
            logger.info("Connecting to database...");
            Connection conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            conn.setAutoCommit(false);
            statement.execute(createUsersTableString);

            String insertUser = "INSERT INTO users(username, password) VALUES (?, ?)";

            //Hardcoded users
            try(PreparedStatement preparedStatement = conn.prepareStatement(insertUser)){
                preparedStatement.setString(1, "admin");
                preparedStatement.setString(2, "4dm1nP455");
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "user");
                preparedStatement.setString(2, "u53rP455");
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "superadmin");
                preparedStatement.setString(2, "sup3r4dm1nP455");
                preparedStatement.executeUpdate();
            }
           conn.commit();
            logger.info("Connected to Database and Users table created");
        } catch (SQLException e) {
            logger.error("Error setting up DB" ,e);
        }
    }
}
