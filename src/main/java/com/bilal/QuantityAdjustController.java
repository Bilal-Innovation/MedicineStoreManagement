package com.bilal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuantityAdjustController {
    @FXML private TextField quantityField;
    @FXML private Button saveButton;
    private Stage stage;
    private Medicine medicine;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
        quantityField.setText(String.valueOf(medicine.getQuantity()));
    }

    @FXML
    private void handleIncrease() {
        int quantity = Integer.parseInt(quantityField.getText()) + 1;
        quantityField.setText(String.valueOf(quantity));
        if (medicine != null) medicine.setQuantity(quantity);
    }

    @FXML
    private void handleDecrease() {
        int quantity = Integer.parseInt(quantityField.getText()) - 1;
        if (quantity >= 0) {
            quantityField.setText(String.valueOf(quantity));
            if (medicine != null) medicine.setQuantity(quantity);
        }
    }

    @FXML
    private void handleSave() {
        if (medicine != null) {
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("UPDATE medicines SET quantity = ? WHERE id = ?")) {
                pstmt.setInt(1, medicine.getQuantity());
                pstmt.setInt(2, medicine.getId());
                pstmt.executeUpdate();
                System.out.println("Quantity updated for " + medicine);
            } catch (SQLException e) {
                System.out.println("Save error: " + e.getMessage());
            }
        }
        handleBack();
    }

    @FXML
    private void handleBack() {
        try {
            // Load the existing SearchMedicineController instance
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bilal/search_medicine.fxml"));
            Parent searchRoot = loader.load();
            Scene scene = new Scene(searchRoot);
            scene.getStylesheets().add(getClass().getResource("/com/bilal/search_medicine.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Medicine Store Management System App - Search Medicine");
            SearchMedicineController controller = loader.getController();
            controller.setStage(stage);
            controller.updateTableWithMedicine(medicine); // Update the table with the modified medicine
            stage.show();
        } catch (Exception e) {
            System.out.println("Error returning to Search Medicine: " + e.getMessage());
        }
    }
}