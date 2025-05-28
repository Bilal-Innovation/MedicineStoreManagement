package com.bilal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;

public class AddMedicineController implements Initializable {
    @FXML private TextField nameField;
    @FXML private TextField formulaField;
    @FXML private TextField priceField;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField quantityField;
    @FXML private Label messageLabel;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeComboBox.getItems().addAll("Tablet", "Syrup");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleAddMedicine() {
        String name = nameField.getText();
        String formula = formulaField.getText();
        String priceStr = priceField.getText();
        String type = typeComboBox.getValue();
        String quantityStr = quantityField.getText();

        if (name.isEmpty() || formula.isEmpty() || priceStr.isEmpty() || type == null || quantityStr.isEmpty()) {
            messageLabel.setText("Please fill all fields!");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);
            if (price < 0 || quantity < 0) {
                messageLabel.setText("Price and Quantity must be positive!");
                return;
            }

            // Use DatabaseConnection to get connection
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO medicines (name, formula, price, type, quantity) VALUES (?, ?, ?, ?, ?)")) {
                pstmt.setString(1, name);
                pstmt.setString(2, formula);
                pstmt.setDouble(3, price);
                pstmt.setString(4, type);
                pstmt.setInt(5, quantity);
                pstmt.executeUpdate();
                messageLabel.setText("Medicine Added Successfully!");
                nameField.clear();
                formulaField.clear();
                priceField.clear();
                typeComboBox.setValue(null);
                quantityField.clear();
            } catch (SQLException e) {
                messageLabel.setText("Database error: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid price or quantity format!");
        }
    }

    @FXML
    private void handleBack() {
        try {
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
            messageLabel.setText("Error returning to Dashboard!");
            e.printStackTrace();
        }
    }
}