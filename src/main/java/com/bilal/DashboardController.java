package com.bilal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardController {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleAddMedicine() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bilal/add_medicine.fxml"));
            Parent addRoot = loader.load();
            Scene scene = new Scene(addRoot);
            scene.getStylesheets().add(getClass().getResource("/com/bilal/add_medicine.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Medicine Store Management System App - Add Medicine");
            AddMedicineController controller = loader.getController();
            controller.setStage(stage);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error loading Add Medicine: " + e.getMessage());
        }
    }

    @FXML
    private void handleSearchMedicine() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bilal/search_medicine.fxml"));
            Parent searchRoot = loader.load();
            Scene scene = new Scene(searchRoot);
            scene.getStylesheets().add(getClass().getResource("/com/bilal/search_medicine.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Medicine Store Management System App - Search Medicine");
            SearchMedicineController controller = loader.getController();
            controller.setStage(stage);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error loading Search Medicine: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdateMedicine() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bilal/update_medicine.fxml"));
            Parent updateRoot = loader.load();
            Scene scene = new Scene(updateRoot);
            scene.getStylesheets().add(getClass().getResource("/com/bilal/update_medicine.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Medicine Store Management System App - Update Medicine");
            UpdateMedicineController controller = loader.getController();
            controller.setStage(stage);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error loading Update Medicine: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteMedicine() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bilal/delete_medicine.fxml"));
            Parent deleteRoot = loader.load();
            Scene scene = new Scene(deleteRoot);
            scene.getStylesheets().add(getClass().getResource("/com/bilal/delete_medicine.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Medicine Store Management System App - Delete Medicine");
            DeleteMedicineController controller = loader.getController();
            controller.setStage(stage);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error loading Delete Medicine: " + e.getMessage());
        }
    }

    @FXML
    private void handleExit() {
        System.out.println("Exiting application...");
        stage.close();
    }
}