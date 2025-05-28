package com.bilal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.equals("admin") && password.equals("hello")) {
            messageLabel.setText("Login Successful!");
            try {
                Stage stage = (Stage) usernameField.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bilal/dashboard.fxml"));
                Parent dashboardRoot = loader.load();
                Scene scene = new Scene(dashboardRoot);
                scene.getStylesheets().add(getClass().getResource("/com/bilal/dashboard.css").toExternalForm());
                stage.setScene(scene);
                stage.setTitle("Medicine Store Management System App - Dashboard");
                DashboardController controller = loader.getController();
                controller.setStage(stage);
                stage.show();
            } catch (Exception e) {
                messageLabel.setText("Error loading Dashboard!");
                e.printStackTrace();
            }
        } else {
            messageLabel.setText("Invalid credentials. Try again.");
        }
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}