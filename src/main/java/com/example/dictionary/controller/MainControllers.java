package com.example.dictionary.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainControllers extends Controllers{
    @FXML
    private Button exit;
    @FXML
    private AnchorPane pane1, pane2;
    @FXML
    private ImageView menu;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exit.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirm Exit");
            alert.setContentText("Are you sure you want to exit?");

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    System.exit(0);
                }
            });
        });
        System.out.println(pane1);
        pane1.setVisible(false);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition.setByX(-400);
        translateTransition.play();

        final boolean[] menuClick = {false};

        menu.setOnMouseClicked(event -> {
            if (!menuClick[0]) {
                pane1.setVisible(true);

                FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
                fadeTransition1.setFromValue(0);
                fadeTransition1.setToValue(0.15);
                fadeTransition1.play();

                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
                translateTransition1.setByX(+400);
                translateTransition1.play();

                menuClick[0] = true;
            }
            else {
                FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
                fadeTransition1.setFromValue(0.15);
                fadeTransition1.setToValue(0);
                fadeTransition1.play();

                fadeTransition1.setOnFinished(event1 -> {
                    pane1.setVisible(false);
                });

                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
                translateTransition1.setByX(-400);
                translateTransition1.play();
                menuClick[0] = false;
            }
        });
    }

    public void switchToGame(ActionEvent event) throws IOException {
        System.out.println("Sign in Clicked");
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

        public void switchToProfile(ActionEvent event) throws IOException {
        System.out.println("Sign in Clicked");
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Profile.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToHome(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToTranslate(ActionEvent event) throws IOException {
        System.out.println("Sign in Clicked");
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Translate.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToPractice(ActionEvent event) throws IOException {
        System.out.println("Practice Clicked");
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Practice.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

        public void switchToFavorite(ActionEvent event) throws IOException {
        System.out.println("Sign in Clicked");
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Favorite.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMain(ActionEvent event) throws IOException {
        System.out.println("Sign in Clicked");
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
