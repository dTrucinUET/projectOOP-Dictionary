package com.example.dictionary.controller;

import com.example.dictionary.Dictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * m cần tạo ấy thứ như label với text ở đây để t còn css
 * Các chức năng
 * Button: voiceButton đọc từ
 * Button: favoriteButton thêm vào danh sách ưa thích
 * ...
 */
public class SearchController extends MainControllers {
    // ... các biến khác

    Dictionary obj = new Dictionary();
    @FXML
    protected Label nameLabel;

    @FXML
    protected TextField toolSearch;

    @FXML
    protected Button searchButton;

    @FXML
    protected ScrollPane scrollPane;

    @FXML
    protected Label meaningLabel;


    String searchText;
    public void displayName(String vocabulary) {
        nameLabel.setText(vocabulary + "\n");
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
            System.out.println("run");
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
        System.out.println("done handle event");
    }

    private void displayMeaning(String meaning) {
        // Đặt nghĩa của từ lên Label meaningLabel
        meaningLabel.setText(meaning);
        meaningLabel.setWrapText(true);
        scrollPane.setFitToWidth(true);
        meaningLabel.setAlignment(Pos.TOP_LEFT);
        scrollPane.setContent(meaningLabel);
    }
}