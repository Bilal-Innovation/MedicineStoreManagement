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

public class DeleteMedicineController implements Initializable {
    @FXML private TextField nameField;
    @FXML private TableView<Medicine> medicineTable;
    @FXML private TableColumn<Medicine, String> nameColumn;
    @FXML private TableColumn<Medicine, String> formulaColumn;
    @FXML private TableColumn<Medicine, Double> priceColumn;
    @FXML private TableColumn<Medicine, Integer> quantityColumn;
    @FXML private TableColumn<Medicine, String> typeColumn;
    @FXML private Button deleteButton;
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
                if (messageLabel != null) messageLabel.setText("");
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
    private void handleDelete() {
        if (selectedMedicine == null) {
            if (messageLabel != null) messageLabel.setText("Please select a medicine to delete!");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM medicines WHERE id = ?")) {
            pstmt.setInt(1, selectedMedicine.getId());
            pstmt.executeUpdate();
            if (messageLabel != null) messageLabel.setText("Medicine deleted successfully!");
            medicineTable.getItems().remove(selectedMedicine);
            selectedMedicine = null;
        } catch (SQLException e) {
            if (messageLabel != null) messageLabel.setText("Delete error: " + e.getMessage());
            System.out.println("Delete error: " + e.getMessage());
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