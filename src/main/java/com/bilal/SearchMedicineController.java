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

public class SearchMedicineController implements Initializable {
    @FXML private TextField nameField;
    @FXML private TextField formulaField;
    @FXML private TableView<Medicine> medicineTable;
    @FXML private TableColumn<Medicine, Integer> idColumn;
    @FXML private TableColumn<Medicine, String> nameColumn;
    @FXML private TableColumn<Medicine, String> formulaColumn;
    @FXML private TableColumn<Medicine, Double> priceColumn;
    @FXML private TableColumn<Medicine, String> typeColumn;
    @FXML private TableColumn<Medicine, Integer> quantityColumn;
    @FXML private Button adjustButton;
    @FXML private Label errorLabel;
    private Stage stage;
    private Medicine selectedMedicine;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (idColumn != null) {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            formulaColumn.setCellValueFactory(new PropertyValueFactory<>("formula"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            medicineTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                selectedMedicine = newSelection;
                if (errorLabel != null) errorLabel.setVisible(false);
                System.out.println("Selected Medicine: " + selectedMedicine);
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
        String formula = formulaField.getText().trim();
        if (medicineTable != null) medicineTable.getItems().clear();

        if (name.isEmpty() && formula.isEmpty()) {
            return;
        }

        String sql = "SELECT * FROM medicines WHERE 1=1";
        if (!name.isEmpty()) sql += " AND name LIKE ?";
        if (!formula.isEmpty()) sql += " AND formula LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int paramIndex = 1;
            if (!name.isEmpty()) pstmt.setString(paramIndex++, "%" + name + "%");
            if (!formula.isEmpty()) pstmt.setString(paramIndex++, "%" + formula + "%");
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
            }
        } catch (SQLException e) {
            System.out.println("Search error: " + e.getMessage());
        }
    }

    public void updateTableWithMedicine(Medicine updatedMedicine) {
        if (medicineTable != null && updatedMedicine != null) {
            boolean found = false;
            for (Medicine medicine : medicineTable.getItems()) {
                if (medicine.getId() == updatedMedicine.getId()) {
                    medicine.setQuantity(updatedMedicine.getQuantity());
                    found = true;
                    break;
                }
            }
            if (!found) {
                medicineTable.getItems().add(updatedMedicine); // Add if not found
            }
            medicineTable.refresh(); // Refresh to show updated data
            System.out.println("Table updated with: " + updatedMedicine);
        }
    }

    @FXML
    private void handleQuantityAdjust() {
        selectedMedicine = medicineTable.getSelectionModel().getSelectedItem();
        System.out.println("Adjust button clicked, selectedMedicine: " + selectedMedicine);

        if (selectedMedicine != null) {
            try {
                System.out.println("Loading quantity adjust for: " + selectedMedicine);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bilal/quantity_adjust.fxml"));
                loader.setControllerFactory(param -> new QuantityAdjustController());
                Parent adjustRoot = loader.load();
                QuantityAdjustController controller = loader.getController();
                controller.setStage(stage);
                controller.setMedicine(selectedMedicine);
                Scene scene = new Scene(adjustRoot);
                scene.getStylesheets().add(getClass().getResource("/com/bilal/quantity_adjust.css").toExternalForm());
                stage.setScene(scene);
                stage.setTitle("Medicine Store Management System App - Quantity Adjust");
                stage.show();
            } catch (Exception e) {
                System.out.println("Error loading Quantity Adjust: " + e.getMessage());
            }
        } else if (errorLabel != null) {
            errorLabel.setVisible(true);
            System.out.println("Error: No medicine selected. Please select a row first.");
        } else {
            System.out.println("Error: errorLabel is null, cannot display message.");
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
            System.out.println("Error returning to Dashboard: " + e.getMessage());
        }
    }

    @FXML
    private void handleSave() {
        if (selectedMedicine != null) {
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("UPDATE medicines SET quantity = ? WHERE id = ?")) {
                pstmt.setInt(1, selectedMedicine.getQuantity());
                pstmt.setInt(2, selectedMedicine.getId());
                pstmt.executeUpdate();
                System.out.println("Quantity updated for " + selectedMedicine);
            } catch (SQLException e) {
                System.out.println("Save error: " + e.getMessage());
            }
        }
    }
}