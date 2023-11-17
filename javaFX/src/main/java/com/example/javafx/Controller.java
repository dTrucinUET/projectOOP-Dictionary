package com.example.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class Controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        try {
            // Load the FXML file for the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            Scene newScene = new Scene(loader.load(), 960, 640);
            // Get the stage for the current scene
            Stage currentStage = (Stage) welcomeText.getScene().getWindow();

            // Set the new scene on the stage
            currentStage.setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
