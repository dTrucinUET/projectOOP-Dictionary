package com.example.dictionary;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

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
            Font.loadFont(getClass().getResourceAsStream("Poppins-Medium.ttf"), 14);

            Scene newScene = new Scene(loader.load(), 960, 640);

            // Get the stage for the current scene
            Stage currentStage = (Stage) backmain.getScene().getWindow();
            newScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            // Set the new scene on the stage
            currentStage.setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
