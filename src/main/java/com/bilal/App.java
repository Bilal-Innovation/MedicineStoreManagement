package com.bilal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/bilal/login.fxml"));
        Scene scene = new Scene(root, 450, 250);
        scene.getStylesheets().clear(); // Clear any cached styles
        scene.getStylesheets().add(getClass().getResource("/com/bilal/login.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/com/bilal/dashboard.css").toExternalForm());
        primaryStage.setTitle("Medicine Store Management System App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}