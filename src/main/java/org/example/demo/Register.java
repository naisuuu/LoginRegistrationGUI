package org.example.demo;

import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.demo.DisplayAlert.showAlert;

public class Register {

    private static final Logger logger = LogManager.getLogger(RegisterController.class);

    public void userRegister(String username, String password){
        var url = "jdbc:sqlite:users.db";
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        String sql = "INSERT INTO users(username,password) VALUES (?,?)"; // adsl;fhasd;jlfhasd;fjkhasdfjk;hadsf;jklh

        logger.info("Connecting to Database");
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql)){

            logger.info("Connected to database");

            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ps.executeUpdate();
        }catch(SQLException e){
            logger.error(e);
        }
    }

    boolean isPasswordSafe(String password){ //https://www.geeksforgeeks.org/how-to-validate-a-password-using-regular-expressions-in-java/
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);

        if (password == null) {
            logger.warn("Registration failed");
            return false;
        }
        Matcher m = p.matcher(password);

        if(!m.matches()){
            logger.warn("Registration failed");
            showAlert(Alert.AlertType.ERROR, "Password does not meet minimum security requirements");
        }

        return m.matches();
    }

}
