package com.example.dictionary.controller;

import com.dictionary.Connection.JDBCConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import org.apache.commons.io.LineIterator;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FavoriteController extends MainController {

    @FXML
    private ListView<String> listWord = new ListView<>();

    @FXML
    private WebView webmean;

    @FXML
    private VBox synonymarea;

    @FXML
    private VBox antonymarea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        List<String> favoriteWords = getWord();
        ObservableList<String> observableWordList = FXCollections.observableArrayList(favoriteWords);
        listWord.getItems().setAll(observableWordList);
    }

    public List<String> getWord() {
        List<String> word = FXCollections.observableArrayList();
        JDBCConnection jdbcConnection = new JDBCConnection();
        try (Connection con = jdbcConnection.getJDBCConnection()) {
            String sqlQue = "select word from favorwwords;";
            try (PreparedStatement preparedStatement = con.prepareStatement(sqlQue)) {
                ResultSet result = preparedStatement.executeQuery();

                while (result.next()) {
                    String word_ss = result.getString("word");
                    word.add(word_ss);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return word;
    }

    public void displayFavoriteWords() {
        List<String> favoriteWords = getWord();
        ObservableList<String> observableWordList = FXCollections.observableArrayList(favoriteWords);
        listWord.getItems().setAll(observableWordList);
    }


}
