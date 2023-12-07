package com.example.dictionary.controller;

import com.example.dictionary.Dictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Lớp bố của Controllers.
 * Có các biến để load scene
 * Xử lý đóng cửa số
 */
public class Controllers implements Initializable {

    protected Stage stage;
    protected Scene scene;
    protected Parent root;

    @FXML
    private Button exit;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }
}