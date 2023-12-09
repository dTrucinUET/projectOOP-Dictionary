package com.example.dictionary.controller;

import com.dictionary.Connection.JDBCConnection;
import com.dictionary.Connection.wordmean;
import com.dictionary.api.DictionaryApi;
import com.example.dictionary.Dictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

    @FXML
    private Button voice;

    String selectedWord;

    private String searchText;

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


    wordmean word;

    @FXML
    private Button exit;
    @FXML
    private AnchorPane pane1, pane2;
    @FXML
    private ImageView menu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        List<String> favoriteWords = getWord();
        if (!favoriteWords.isEmpty()) {
            displayWordDetails(favoriteWords.get(0));
        } else {
            System.out.println("Danh sách yêu thích trống.");
        }
        ObservableList<String> observableWordList = FXCollections.observableArrayList(favoriteWords);
        listWord.getItems().setAll(observableWordList);
        // Bắt sự kiện khi click vào một từ trong ListView
        listWord.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Lấy từ được chọn
                selectedWord = listWord.getSelectionModel().getSelectedItem();

                // Xử lý từ được chọn, ví dụ: hiển thị nó trên console
                System.out.println("Selected Word: " + selectedWord);

                // Gọi phương thức để hiển thị thông tin chi tiết về từ (thay thế bằng phương thức thích hợp của bạn)
                displayWordDetails(selectedWord);

            }
        });
    }

    public void removefavor(ActionEvent event){
        Dictionary obj = new Dictionary();
        obj.removeWord(selectedWord);
        List<String> favoriteWords = getWord();
        if (!favoriteWords.isEmpty()) {
            displayWordDetails(favoriteWords.get(0));
        } else {
            System.out.println("Danh sách yêu thích trống.");
        }
        ObservableList<String> observableWordList = FXCollections.observableArrayList(favoriteWords);
        listWord.getItems().setAll(observableWordList);
    }



    public void VoiceMute(ActionEvent event){
        DictionaryApi api = new DictionaryApi();
        String voi = api.getVoice(selectedWord);
        api.playAudio(voi);
    }
    private void displayWordDetails(String word) {
        // Viết code để hiển thị thông tin chi tiết về từ, có thể dựa vào cơ sở dữ liệu hoặc dữ liệu khác
        JDBCConnection jdbcConnection = new JDBCConnection();
        wordmean sol = new wordmean();
        sol = jdbcConnection.getMeaning(word);
        displayWordInformation(sol);
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
        synonymarea.getChildren().clear(); // Xóa các thành phần cũ trước khi thêm mới

        int max_num = 7;
        for (String synonym : synonyms) {
            if(max_num == 0) break;
            Label synonymLabel = new Label(synonym);
            synonymarea.getChildren().add(synonymLabel);
            max_num--;
        }
    }


    private void displayAntonyms(List<String> antonyms) {
        // Hiển thị các antonym lên antonymlabel (VBox)
        // Ví dụ: Nếu antonymlabel là một VBox
        antonymarea.getChildren().clear(); // Xóa các thành phần cũ trước khi thêm mới

        int max_num = 6;
        for (String antonym : antonyms) {
            if(max_num == 0) break;
            Label antonymLabel = new Label(antonym);
            antonymarea.getChildren().add(antonymLabel);
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
        webmean.getEngine().loadContent(htmlContent.toString());
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
        webmean.getEngine().loadContent(htmlContent.toString());
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

    public void handleSearchAction(ActionEvent event) {
        System.out.println("Search Button click");

        searchText = toolSearch.getText();
        word = obj.findWord(searchText);

        // Ví dụ: In ra console để kiểm tra
        System.out.println("Searching for: " + searchText);
        System.out.println("mean: " + word.getMeaning());

        // Đặt dữ liệu vào Label hoặc thực hiện các hành động khác
    }
}