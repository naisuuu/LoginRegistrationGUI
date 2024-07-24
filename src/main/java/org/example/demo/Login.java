package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.*;

public class Login {

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnUser;

    @FXML
    private TextField tfPass;

    @FXML
    private TextField tfUser;

    @FXML
    void userLoginAction(ActionEvent event) {

    }

    @FXML
    void userRegisterAction(ActionEvent event) {

    }

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

}
