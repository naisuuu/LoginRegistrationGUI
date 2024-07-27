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

    Register register = new Register();


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

        if(register.isPasswordSafe(password)){
            register.userRegister(username,password);
            logger.info("User" + username +"registered");
        }else {
            logger.info("User" + username +"registration failed");
        }
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage stage = (Stage) btnReturn.getScene().getWindow();
        stage.setScene(new Scene(root,600,400));
    }

}

