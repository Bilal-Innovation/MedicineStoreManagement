package com.bilal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;

public class UpdateMedicineController implements Initializable {
    @FXML private TextField nameField;
    @FXML private TableView<Medicine> medicineTable;
    @FXML private TableColumn<Medicine, String> nameColumn;
    @FXML private TableColumn<Medicine, String> formulaColumn;
    @FXML private TableColumn<Medicine, Double> priceColumn;
    @FXML private TableColumn<Medicine, Integer> quantityColumn;
    @FXML private TableColumn<Medicine, String> typeColumn;
    @FXML private TextField formulaField;
    @FXML private TextField priceField;
    @FXML private TextField quantityField;
    @FXML private TextField typeField;
    @FXML private Button saveButton;
    @FXML private Label messageLabel;
    private Stage stage;
    private Medicine selectedMedicine;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (nameColumn != null) {
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            formulaColumn.setCellValueFactory(new PropertyValueFactory<>("formula"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            medicineTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                selectedMedicine = newSelection;
                if (selectedMedicine != null) {
                    formulaField.setText(selectedMedicine.getFormula());
                    priceField.setText(String.valueOf(selectedMedicine.getPrice()));
                    quantityField.setText(String.valueOf(selectedMedicine.getQuantity()));
                    typeField.setText(selectedMedicine.getType());
                    if (messageLabel != null) messageLabel.setText("");
                }
            });
        } else {
            System.out.println("Error: Table columns not initialized.");
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleSearch() {
        String name = nameField.getText().trim();
        if (medicineTable != null) medicineTable.getItems().clear();

        if (name.isEmpty()) {
            if (messageLabel != null) messageLabel.setText("Please enter a medicine name to search!");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM medicines WHERE name LIKE ?")) {
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();
            if (medicineTable != null) {
                while (rs.next()) {
                    Medicine medicine = new Medicine(
                            rs.getString("name"),
                            rs.getString("formula"),
                            rs.getDouble("price"),
                            rs.getString("type"),
                            rs.getInt("quantity")
                    );
                    medicine.setId(rs.getInt("id"));
                    medicineTable.getItems().add(medicine);
                }
                if (medicineTable.getItems().isEmpty()) {
                    if (messageLabel != null) messageLabel.setText("No medicines found!");
                }
            }
        } catch (SQLException e) {
            if (messageLabel != null) messageLabel.setText("Search error: " + e.getMessage());
            System.out.println("Search error: " + e.getMessage());
        }
    }

    @FXML
    private void handleSave() {
        if (selectedMedicine == null) {
            if (messageLabel != null) messageLabel.setText("Please select a medicine to update!");
            return;
        }

        if (formulaField.getText().trim().isEmpty() || priceField.getText().trim().isEmpty() ||
                quantityField.getText().trim().isEmpty() || typeField.getText().trim().isEmpty()) {
            if (messageLabel != null) messageLabel.setText("Please fill all fields!");
            return;
        }

        try {
            selectedMedicine.setFormula(formulaField.getText().trim());
            selectedMedicine.setPrice(Double.parseDouble(priceField.getText().trim()));
            selectedMedicine.setQuantity(Integer.parseInt(quantityField.getText().trim()));
            selectedMedicine.setType(typeField.getText().trim());

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(
                         "UPDATE medicines SET formula = ?, price = ?, quantity = ?, type = ? WHERE id = ?")) {
                pstmt.setString(1, selectedMedicine.getFormula());
                pstmt.setDouble(2, selectedMedicine.getPrice());
                pstmt.setInt(3, selectedMedicine.getQuantity());
                pstmt.setString(4, selectedMedicine.getType());
                pstmt.setInt(5, selectedMedicine.getId());
                pstmt.executeUpdate();
                if (messageLabel != null) messageLabel.setText("Record updated successfully!");

                // Update the table directly
                int selectedIndex = medicineTable.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    medicineTable.getItems().set(selectedIndex, selectedMedicine);
                    medicineTable.refresh();
                }
            } catch (SQLException e) {
                if (messageLabel != null) messageLabel.setText("Save error: " + e.getMessage());
                System.out.println("Save error: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            if (messageLabel != null) messageLabel.setText("Invalid number format for price or quantity!");
            System.out.println("Error: Invalid number format for price or quantity.");
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
            if (messageLabel != null) messageLabel.setText("Error returning to Dashboard: " + e.getMessage());
            System.out.println("Error returning to Dashboard: " + e.getMessage());
        }
    }
}