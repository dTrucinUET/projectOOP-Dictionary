package com.example.dictionary.controller;

import com.dictionary.Connection.wordmean;
import com.example.dictionary.Dictionary;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Giao diện chính của App, bao gồm các ha switchTo để chuyển giữa các Scene
 * Nếu m cần code liên quan đến thanh search thì sang class SearchController
 */
public class MainController extends Controllers {
    private final Dictionary obj = new Dictionary();

    @FXML
    private TextField toolSearch;

    @FXML
    private Button searchButton;

    @FXML
    private WebView wordexplain;

    @FXML
    private VBox antonymlabel;

    @FXML
    private VBox synonymlabel;

    String searchText;

    wordmean word;

    @FXML
    private Button exit;
    @FXML
    private AnchorPane pane1, pane2;
    @FXML
    private ImageView menu;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
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

    public void handleSearchAction(ActionEvent event) {
        System.out.println("Search Button click");

        searchText = toolSearch.getText();
        word = obj.findWord(searchText);

        // Ví dụ: In ra console để kiểm tra
        System.out.println("Searching for: " + searchText);
        System.out.println("mean: " + word.getMeaning());

        // Đặt dữ liệu vào Label hoặc thực hiện các hành động khác
    }

    public void switchToGame(ActionEvent event) throws IOException {
        System.out.println("Sign in Clicked");
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
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

    public void switchToFavorite(ActionEvent event) throws IOException {
        System.out.println("Favorite Clicked");
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


    public void search_tool(ActionEvent event) throws Exception {

        String username = toolSearch.getText();
        Dictionary obj = new Dictionary();
        String mean = obj.findWord(username).getMeaning().toString();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Search.fxml"));
        root = loader.load();

        SearchController searchController = loader.getController();
        searchController.displayName(username);
        System.out.println("Searching: " + username);
        System.out.println("mean: " + mean);

        //root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
