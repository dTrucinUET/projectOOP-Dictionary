package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class homeController {
    @FXML
    private ImageView imageView;

    @FXML
    private Label backmain;

    @FXML
    private void initialize() {
        // Initialize the ImageView here
        imageView = new ImageView();
    }

    @FXML
    protected void onhomeButtonClick() {
        try {
            // Load the FXML file for the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainpage.fxml"));
            Scene newScene = new Scene(loader.load(), 960, 640);

            // Get the stage for the current scene
            Stage currentStage = (Stage) backmain.getScene().getWindow();

            // Set the new scene on the stage
            currentStage.setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
