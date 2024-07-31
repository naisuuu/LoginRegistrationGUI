package org.example.demo;

import javafx.scene.control.Alert;

public class DisplayAlert {

    public static void showAlert(Alert.AlertType alertType, String message){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
