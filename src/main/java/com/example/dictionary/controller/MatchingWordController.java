package com.example.dictionary.controller;

import com.example.dictionary.Dictionary;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.SetChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MatchingWordController implements Initializable {
    @FXML
    private Button exit;

    @FXML
    private Button buttonSend;

    @FXML
    private TextField tfMessage;

    @FXML
    private VBox vBoxMessage = new VBox();

    @FXML
    private ScrollPane spMain;

    private Dictionary dictionary = new Dictionary();

    @FXML
    private String input;

    private String check = "";

    private String answer = "";

    private List<String> result = new ArrayList<>();

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

        tfMessage.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                getTextInput(new ActionEvent());
            }
        });
    }

    @FXML
    public void getTextInput(ActionEvent event) {
        input = tfMessage.getText().trim();
        playTurn();
    }

    public void playTurn() {
        // spMain.setVvalue(spMain.getVmax());

        if (!input.isEmpty()) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 5, 5, 10));

            Text text = new Text(input.toUpperCase());
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-color: rgb(239, 242, 255);" + "-fx-background-color:  rgb(98,98,105);" + "-fx-background-radius: 20px;");
            textFlow.setPadding(new Insets(5, 5, 5, 10));
            text.setFill(Color.color(0.934, 0.945, 0.966));

            hBox.getChildren().add(textFlow);
            vBoxMessage.getChildren().add(hBox);

            tfMessage.clear();
            if (!answer.contains("This") && !answer.contains("before")) {
                check = answer.toLowerCase().trim();
            }
            answer = dictionary.matchingWord(input);
            if (result.contains(input)) {
                answer = "You answer that word before!";
            } else if (!check.isEmpty()) {
                System.out.println("..." + check + check.length() + " | " + input + "###");
                int checkAnswer = dictionary.checkAnswer(input.toLowerCase(), check.toLowerCase());
                if (checkAnswer == -1) {
                    answer = "This word doesn't exist!";
                } else if (checkAnswer == 0) {
                    answer = "This word is incorrect!";
                } else {
                    result.add(input.trim());
                    answer = "The next is: " + answer.toUpperCase();
                    answer = answer.trim();
                }
            }
        } else {
            answer = "You must say something";
        }
       // spMain.setVvalue(spMain.getVmax());


        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER_LEFT);
        hBox1.setPadding(new Insets(5, 10, 5, 5));

        Text text1 = new Text(answer);
        TextFlow textFlow1 = new TextFlow(text1);
        textFlow1.setStyle("-fx-color: rgb(239, 242, 255);" + "-fx-background-color:  rgb(79,72,248);" + "-fx-background-radius: 20px;");
        textFlow1.setPadding(new Insets(5, 10, 5, 5));
        text1.setFill(Color.color(0.934, 0.945, 0.966));

        hBox1.getChildren().add(textFlow1);
        vBoxMessage.getChildren().add(hBox1);
        spMain.setVvalue(spMain.getVmax());
    }

    public void endgame(ActionEvent event) {

    }



}
