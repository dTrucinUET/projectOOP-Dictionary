package com.example.dictionary.controller;

import javafx.animation.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import com.example.dictionary.Dictionary;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class HangmanController extends MainController {
    @FXML
    private AnchorPane winPane, done;




    @FXML
    private Text wordPlay;

    @FXML
    private Text meaning;

    @FXML
    private Text thinkChat;

    @FXML
    private Label timeLabel;
    private Timeline timeline;
    private int elapsedSeconds;

    @FXML
    private ImageView angry;

    @FXML
    private Text tell1;

    @FXML
    private ImageView tell2;

    @FXML
    private AnchorPane thinkPane;

    @FXML
    private Text tellChat;

    @FXML
    private AnchorPane tellPane;

    @FXML
    private TextField guess;

    @FXML
    private Text textForWord;

    @FXML
    private Text endOfGameText1;

    @FXML
    private AnchorPane hangman1, hangman2, hangman3, hangman4, hangman5, hangman6, hangman7, hangman8, hangman9, hangman10, hangman11;

    private static ArrayList<AnchorPane> hangManLives = new ArrayList<>(Arrays.asList());

    private String word;

    Dictionary dictionary = new Dictionary();

    private StringBuilder secretWord = new StringBuilder();

    private int livesPos = 0;

    @FXML
    Button play;

    @FXML
    Button exit;

    @FXML
    AnchorPane paneBackground;

    @FXML
    ImageView img;

    private int controlGame;

    private String[] overThingking = {
            "You're wrong" + "\n" + "I will die!", "I think you should choose ", "Better luck next time", "What about ",
            "My life is terrible", "I'm so stress", "Why don't you think it is ", "I feel lonely", "I guess it is ",
            "This is your last change", "Goodbye World!"
    };

    private String[] rightWord = {
            "Congratulation!", "You are so lucky!", "You are right!", "Nice going!", "Fantastic!", "Amazing!",
            "Good job!", "Wonderful!", "Perfect!", "Terrific!", "I feel like I am alive again", "You got it!",
            "I'm proud of you", "Smart answer!", "I knew you could do it", "Excellent!", "You are getting better"
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //hangmanTextArea.setText(hangManLives.get(livesPos));
        System.out.println("hangman" + hangman1);
        System.out.println("bg" + paneBackground);
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
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), paneBackground);

        winPane.setVisible(false);


        play.setOnMouseClicked(event -> {
            translateTransition.setByX(-1000);
            translateTransition.play();
        });
        hangManLives.add(hangman1);
        hangManLives.add(hangman2);
        hangManLives.add(hangman3);
        hangManLives.add(hangman4);
        hangManLives.add(hangman5);
        hangManLives.add(hangman6);
        hangManLives.add(hangman7);
        hangManLives.add(hangman8);
        hangManLives.add(hangman9);
        hangManLives.add(hangman10);
        hangManLives.add(hangman11);
        hangManLives.get(livesPos).setVisible(true);
        setupWord();
        guess.clear();
        hangManLives.get(livesPos).setVisible(true);
        thinkPane.setVisible(false);
        guess.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                getTextInput(new ActionEvent());
            }
        });
        tellChat.setText("");
        angry.setVisible(false);
        elapsedSeconds = 0;

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::updateTime));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void updateTime(ActionEvent event) {
        elapsedSeconds++;
        int minutes = elapsedSeconds / 60;
        int seconds = elapsedSeconds % 60;
        String formattedTime = String.format("Time: %02d:%02d", minutes, seconds);
        timeLabel.setText(formattedTime);
        timeLabel.focusWithinProperty();
      //  System.out.println(elapsedSeconds);
    }

    private void startGame() {
        // Start the timeline
        timeline.play();
    }

    private void endGame() {
        // Stop the timeline
        timeline.stop();
    }

    @FXML
    void getTextInput(ActionEvent event) {
        startGame();
        if (word == null) {
            word = dictionary.randomWord();
            setupWord();
            guess.clear();
            hangManLives.get(livesPos).setVisible(true);
        } else {
            playTurn();
        }
    }

    public void setupWord() {
        controlGame = 0;
        textForWord.setText(null);
        word = null;
        secretWord = new StringBuilder();
        word = dictionary.randomWord();
        int wordLength = word.length();

        secretWord.append("_ ".repeat(wordLength));
        textForWord.setText(String.valueOf(secretWord));

        hangManLives.get(livesPos).setVisible(true); // Hiển thị AnchorPane của livesPos
    }

    public void playTurn() {
        tell1.setText("Tell him...");
        tell2.setVisible(true);
        String guess1 = guess.getText();
        if (guess1.length() > 1) {
            thinkChat.setText("Don't talk" + "\n" + "to much!");
            angry.setVisible(true);
            return;
        }
        thinkChat.setText("Hmm...");
        angry.setVisible(false);
        tell1.setText("");
        tell2.setVisible(false);
        tellChat.setText("I think it is: " + guess1.toUpperCase());
        guess.clear();
        guess.setVisible(false);
        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(1), tellPane);
        fadeTransition1.setFromValue(0.0);
        fadeTransition1.setToValue(1.0);
        fadeTransition1.play();

        Task<Void> thinkingTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);
                if (word.contains(guess1)) {
                    ArrayList<Integer> positions = new ArrayList<>();
                    char[] wordChars = word.toCharArray();
                    char letterGuess = guess1.charAt(0);
                    for (int i = 0; i < word.length(); i++) {
                        if (wordChars[i] == letterGuess) {
                            positions.add(i);
                        }
                    }
                    positions.forEach(pos -> {
                        int random = (int) (Math.random() * 100 % 17);
                        secretWord.setCharAt(pos * 2, letterGuess);

                        thinkPane.setVisible(true);
                        thinkChat.setText(rightWord[random]);
                        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), thinkPane);
                        fadeTransition.setFromValue(0.0);
                        fadeTransition.setToValue(1.0);
                        fadeTransition.play();
                    });
                    textForWord.setText(String.valueOf(secretWord));
                } else {
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), hangManLives.get(livesPos));
                    fadeTransition.setFromValue(1.0);
                    fadeTransition.setToValue(0.0);
                    fadeTransition.play();

                    String hintRandom = dictionary.randomWord().toUpperCase();
                    hintRandom = "\"" + hintRandom + "\"";
                    String res = overThingking[livesPos];
                    if (livesPos == 1) {
                        res += hintRandom;
                    }
                    System.out.println(livesPos);
                    if (livesPos == 6 || livesPos == 8) {
                        int ran = (int) (Math.random() * 100 % word.length());
                        System.out.println(secretWord + "  " + word.substring(ran, ran + 1) + " " + secretWord.toString().contains(word.substring(ran, ran + 1)));
                        while (secretWord.toString().contains(word.substring(ran, ran + 1))) {
                            System.out.println(ran + " w " + word.substring(ran, ran + 1));

                            ran = (int) (Math.random() * 100 % word.length());
                        }
                      //  System.out.println(ran + "  " + word.substring(ran, ran + 1));
                        hintRandom = "\"" + word.substring(ran, ran + 1).toUpperCase() + "\"";
                        res += hintRandom;
                    }
                    if (livesPos == 3) {
                        hintRandom = dictionary.randomWord(word.length()).toUpperCase();
                        hintRandom = "\"" + hintRandom + "\"";
                        res += hintRandom;
                    }

                    thinkPane.setVisible(true);
                    thinkChat.setText(res);
                    FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(1), thinkPane);
                    fadeTransition1.setFromValue(0.0);
                    fadeTransition1.setToValue(1.0);
                    fadeTransition1.play();

                    livesPos++;
                    if (livesPos == 10) {
                        endOfGameText1.setText("You LOST!!");
                        controlGame = -1;

                    }
                }

                for (int i = 0; i < secretWord.length(); i++) {
                    if (secretWord.charAt(i) == '_') {
                        break;
                    }
                    if (i == secretWord.length() - 1) {
                        endOfGameText1.setText("You WON!!");
                        controlGame = 1;

                    }
                }
                guess.setVisible(false);
                guess.clear();
                System.out.println("word: " + word + " guess: " + secretWord);
                Thread.sleep(1000);
                if (controlGame == 0) {
                    tellChat.setText("I'm thinking");
                    FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(1), tellPane);
                    fadeTransition2.setFromValue(0.0);
                    fadeTransition2.setToValue(1.0);
                    fadeTransition2.play();
                    tell1.setText("Tell him...");
                    tell2.setVisible(true);
                }
                Thread.sleep(1000);
                tellChat.setText("");
                guess.setVisible(true);
                return null;
            }
        };
        System.out.println(controlGame);
        thinkingTask.setOnSucceeded(event -> {
            if (controlGame == 1){
                endGame();
                FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(1), tellPane);
                fadeTransition2.setFromValue(0.0);
                fadeTransition2.setToValue(1.0);
                fadeTransition2.play();
                tellChat.setText("I always right");
                done.setVisible(false);
                winPane.setVisible(true);

                wordPlay.setText(word);
                meaning.setText(dictionary.findWordHangman(word));

                FadeTransition fadeTransition3 = new FadeTransition(Duration.seconds(1), winPane);
                fadeTransition3.setFromValue(0.0);
                fadeTransition3.setToValue(1.0);
                fadeTransition3.play();


            } else if (controlGame == -1){
                FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(1), tellPane);
                fadeTransition2.setFromValue(0.0);
                fadeTransition2.setToValue(1.0);
                fadeTransition2.play();
                tellChat.setText("Better luck next life");
                endGame();
                done.setVisible(false);
            }


        });

        Thread thinkingThread = new Thread(thinkingTask);
        thinkingThread.start();
    }

    @FXML
    void reset(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Hangman.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    void endgame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
