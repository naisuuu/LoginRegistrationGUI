package org.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class Login {

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    public boolean userLogin(String username, String password) {

        var url = "jdbc:sqlite:users.db";
        var sql = "SELECT * FROM users WHERE username = ?";

        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String passwordHash = rs.getString("password");
                return BCrypt.checkpw(password, passwordHash);
            }
        }catch(SQLException e){
            logger.error(e);
        }
        return false;
    }


}
