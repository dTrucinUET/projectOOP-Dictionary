package com.example.dictionary.controller;

import com.dictionary.Connection.wordmean;
import com.example.dictionary.Dictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SearchController extends MainController {

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

    public void displayName(String vocabulary) {
        searchText = vocabulary;
    }

    public String getSearchResult() {
        return searchText;
    }

    @FXML
    private void initialize() {
        // Gắn sự kiện cho nút searchButton
        searchButton.setOnAction(this::handleSearchAction);
    }

    public void handleSearchAction(ActionEvent event) {
        System.out.println("Search Button click");

        searchText = toolSearch.getText();
        word = obj.findWord(searchText);

        // Ví dụ: In ra console để kiểm tra
        System.out.println("Searching for: " + searchText);
        displayWordInformation(word);
        System.out.println("mean: " + word.getMeaning());

        // Đặt dữ liệu vào Label hoặc thực hiện các hành động khác
        displayName(searchText);
    }

    public void handleFavorAction(ActionEvent event){
        System.out.println("Click insert favorite");
        Dictionary obj = new Dictionary();
        obj.addWord(word);
    }

    private void displayWordInformation(wordmean word) {
        // Hiển thị thông tin từ lên các thành phần tương ứng
        displayWord(word.getWordname() , word.getPhonetic());
        displayMeaning(word.getMeaning(),word.getSpeech());
        displaySynonyms(word.getSynonym());
        displayAntonyms(word.getAntonym());
    }

    private void displaySynonyms(List<String> synonyms) {
        // Hiển thị các synonym lên synonymlabel (VBox)
        // Ví dụ: Nếu synonymlabel là một VBox
        synonymlabel.getChildren().clear(); // Xóa các thành phần cũ trước khi thêm mới

        int max_num = 7;
        for (String synonym : synonyms) {
            if(max_num == 0) break;
            Label synonymLabel = new Label(synonym);
            synonymlabel.getChildren().add(synonymLabel);
            max_num--;
        }
    }

    private void displayAntonyms(List<String> antonyms) {
        // Hiển thị các antonym lên antonymlabel (VBox)
        // Ví dụ: Nếu antonymlabel là một VBox
        antonymlabel.getChildren().clear(); // Xóa các thành phần cũ trước khi thêm mới

        int max_num = 6;
        for (String antonym : antonyms) {
            if(max_num == 0) break;
            Label antonymLabel = new Label(antonym);
            antonymlabel.getChildren().add(antonymLabel);
            max_num--;
        }
    }


    StringBuilder htmlContent = new StringBuilder("<html>");

    private void displayWord(String word, String phenot) {
        // Giữ lại phần "<html>" và xóa nội dung bên trong
        htmlContent.setLength(6);

        // Thêm nội dung header mới
        htmlContent.append("<header><p>").append(word).append("  /").append(phenot).append("/").append("</p></header>");

        // Load nội dung HTML vào WebView
        wordexplain.getEngine().loadContent(htmlContent.toString());
    }

    private void displayMeaning(List<String> meanings, List<String> speeches) {
        // Giữ lại phần "<html>" và xóa nội dung bên trong

        // Thêm phần body mới
        htmlContent.append("<body>");

        for (int i = 0; i < meanings.size(); i++) {
            String meaning = meanings.get(i);
            String speech = speeches.get(i);

            // Thêm nghĩa và loại từ vào nội dung HTML
            htmlContent.append("<p><b>").append(speech).append(":</b> ").append(meaning).append("</p>");
        }

        htmlContent.append("</body></html>");

        // Load nội dung HTML vào WebView
        wordexplain.getEngine().loadContent(htmlContent.toString());
    }




    public void switchToHome(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToGame(ActionEvent event) throws IOException {
        System.out.println("Sign in Clicked");
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
