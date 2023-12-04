package com.example.dictionary.controller;

import com.example.dictionary.controller.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Scene đầu tiên hiện lên khi run App
 */

public class HomeController extends Controllers {
    public void switchToSignIn(ActionEvent event) throws IOException {
        System.out.println("Sign in Clicked");
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}