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
import java.util.Objects;

import static org.example.demo.DisplayAlert.showAlert;

public class LoginController {

    private static final Logger logger = LogManager.getLogger(LoginController.class);

   @FXML
    private Button btnRegister;
    @FXML
    private TextField tfPass;

    @FXML
    private TextField tfUser;

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

    @FXML
    void userLoginAction(ActionEvent event) throws IOException {
        String username = tfUser.getText();
        String password = tfPass.getText();

        logger.info("Logging in user {}", username);
        if(userLogin(username, password)) {

            Stage stage = (Stage) tfUser.getScene().getWindow();
            Parent root;

            if("admin".equals(username)) {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin.fxml")));
            } else {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("user.fxml")));
            }
            stage.setScene(new Scene(root,600,400));
            logger.info("Logged in user {}", username);
        } else {
            showAlert(Alert.AlertType.ERROR,"Login Failed");
            logger.info("Log in failed for user {}", username);
        }
    }

    @FXML
    void userRegisterAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
        Stage stage = (Stage) btnRegister.getScene().getWindow();
        stage.setScene(new Scene(root,600,400));
    }
}
