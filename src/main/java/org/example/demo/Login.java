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
import java.util.Objects;

public class Login {

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnUser;

    @FXML
    private TextField tfPass;

    @FXML
    private TextField tfUser;

    public boolean userLogin(String username, String password) {

        var url = "jdbc:sqlite:users.db";
        var sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(SQLException e){
            System.out.println("Login Error");}
        return false;
    }

    @FXML
    void userLoginAction(ActionEvent event) throws IOException {

        if(userLogin(tfUser.getText(), tfPass.getText())) {
            Stage stage = (Stage) tfUser.getScene().getWindow();
            Parent root;
            if(tfUser.getText().equals("admin")) { //omitted check for pass to save complexity
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("src/main/resources/org/example/demo/admin.fxml")));
            } else{
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("src/main/resources/org/example/demo/user.fxml")));
            }
            stage.setScene(new Scene(root,600,400));
        } else {
            System.out.println("Login failed");
        }
    }

    @FXML
    void userRegisterAction(ActionEvent event) {

    }



}
