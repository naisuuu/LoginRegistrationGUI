package org.example.demo;

import java.sql.*;

public class Register {
    public void userRegister(String username, String password) {

        var url = "jdbc:sqlite:users.db";
        String sql = "INSERT INTO users (username, password) VALUES ('"+username+"', '"+password+"');";

        try(Connection conn =DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Register Error");
        }
    }
}

