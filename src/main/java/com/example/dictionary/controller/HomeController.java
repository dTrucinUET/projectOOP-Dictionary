package com.example.dictionary.controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.events.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Scene đầu tiên hiện lên khi run App
 */

public class HomeController extends Controllers {

    @FXML
    private Button logIn;

    @FXML
    private Button signUp;

    protected static boolean loginS = false;
    @FXML
    private void switchToLogIn(ActionEvent event) throws Exception {
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.display((Stage) logIn.getScene().getWindow());
        System.out.println(loginS);

        if (loginS) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void switchToSignUp(ActionEvent event) {
        SignUpWindow signUpWindow = new SignUpWindow();
        signUpWindow.display((Stage) signUp.getScene().getWindow());
    }

}