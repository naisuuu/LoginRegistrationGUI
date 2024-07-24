package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {
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
    void userRegisterAction(ActionEvent event){
        String username = tfUsername.getText();
        String password = tfPass.getText();

        if(isPasswordSafe(password)){
            userRegister(username,password);
            System.out.println("User registered");
        }else {
            System.out.println("Register failed" + username + " " + password);
        }
    }

    public void userRegister(String username, String password){
        var url = "jdbc:sqlite:users.db";
        String sql = "INSERT INTO users(username,password) VALUES (?,?)";
        System.out.println("TEST");
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            System.out.println("Reached");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        System.out.println("ASDFADSFSADF");
    }

    private boolean isPasswordSafe(String password){ //https://www.geeksforgeeks.org/how-to-validate-a-password-using-regular-expressions-in-java/
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }

}

