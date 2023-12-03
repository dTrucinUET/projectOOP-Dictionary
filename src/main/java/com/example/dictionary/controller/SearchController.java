package com.example.dictionary.controller;

import com.example.dictionary.Dictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * m cần tạo ấy thứ như label với text ở đây để t còn css
 * Các chức năng
 * Button: voiceButton đọc từ
 * Button: favoriteButton thêm vào danh sách ưa thích
 * ...
 */
public class SearchController extends Controllers {
    // ... các biến khác

    Dictionary obj = new Dictionary();
    @FXML
    private Label nameLabel;

    @FXML
    private TextField toolSearch;

    @FXML
    private Button searchButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label meaningLabel;

    String searchText;
    public void displayName(String vocabulary) {
        nameLabel.setText(vocabulary);
        searchText = vocabulary;
        nameLabel.setWrapText(true);
    }

    public String getSearchResult() {
        return searchText;
    }

    @FXML
    private void initialize() {
        // Gắn sự kiện cho nút searchButton
        searchButton.setOnAction(event -> {
            try {
                handleSearchAction(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Đặt nội dung của Label vào ScrollPane
        if (scrollPane != null) {
            // Thêm Label vào ScrollPane
            scrollPane.setContent(meaningLabel);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
        }
    }


    public void handleSearchAction(ActionEvent event) throws Exception {
        // Lấy dữ liệu từ TextField khi nút được nhấn
        System.out.println("Search Button click");

        searchText = toolSearch.getText();

        String meaning = obj.findWord(searchText);

        // Ví dụ: In ra console để kiểm tra
        System.out.println("Searching for: " + searchText);
        displayMeaning(meaning);
        System.out.println("mean: " + meaning);

        // Hiển thị kết quả hoặc thực hiện các hành động khác tùy thuộc vào yêu cầu của ứng dụng
        // ...

        // Đặt dữ liệu vào Label hoặc thực hiện các hành động khác
        displayName(searchText);
    }

    private void displayMeaning(String meaning) {
        // Đặt nghĩa của từ lên Label meaningLabel
        meaningLabel.setText(meaning);
        meaningLabel.setWrapText(true);
    }

    public void search(ActionEvent event) throws Exception {

        String username = toolSearch.getText();
        Dictionary obj = new Dictionary();
        String mean = obj.findWord(username);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tratu.fxml"));
        root = loader.load();

        SearchController searchController = loader.getController();
        searchController.displayName(username);
        System.out.println("Searching: " + username);
        System.out.println("mean: " + mean);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}