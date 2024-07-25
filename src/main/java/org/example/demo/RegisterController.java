package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.demo.DisplayAlert.showAlert;

public class RegisterController {

    private static final Logger logger = LogManager.getLogger(RegisterController.class);
    @FXML
    private Button btnRegister;

    @FXML
    private Button btnReturn;

    @FXML
    private TextField tfPass;

    @FXML
    private TextField tfUsername;


    @FXML
    void btnReturnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage stage = (Stage) btnReturn.getScene().getWindow();
        stage.setScene(new Scene(root,600,400));
    }

    @FXML
    void userRegisterAction(ActionEvent event) throws IOException {
        String username = tfUsername.getText();
        String password = tfPass.getText();

        if(isPasswordSafe(password)){
            userRegister(username,password);
            logger.info("User" + username +"registered");
        }else {
            logger.info("User" + username +"registration failed");
        }
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage stage = (Stage) btnReturn.getScene().getWindow();
        stage.setScene(new Scene(root,600,400));
    }

    public void userRegister(String username, String password){
        var url = "jdbc:sqlite:users.db";
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        String sql = "INSERT INTO users(username,password) VALUES (?,?)";

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

    private boolean isPasswordSafe(String password){ //https://www.geeksforgeeks.org/how-to-validate-a-password-using-regular-expressions-in-java/
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

