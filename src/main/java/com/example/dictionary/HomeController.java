package com.example.dictionary;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    // Declare the dictionaryButton instance variable
    @FXML
    private Button dictionaryButton;

    public void goDictionary(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dictionary.fxml"));
        Parent root = loader.load();
        DictionaryController dictionaryController = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.show();
    }


    public void initialize() {
        dictionaryButton = new Button("Dictionary");

        // Add the event handler to handle dictionaryButton clicks
        dictionaryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    goDictionary(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}