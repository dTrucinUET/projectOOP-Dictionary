package com.example.dictionary.controller;

/**
 * Chỗ này t cần để dịch văn bản
 * Cần thêm cả cái button voice để đọc từ
 */


import com.dictionary.api.TranslateAPI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class TranslateController extends SearchController {
    @FXML
    private TextArea textField1;

    @FXML
    private TextArea textField2;

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private Button translateButton;

    private boolean isUsingImg1 = true; // Biến theo dõi trạng thái hiện tại

    private String from = "en"; // Ngôn ngữ nguồn mặc định là tiếng Anh
    private String to = "vi";   // Ngôn ngữ đích mặc định là tiếng Việt


    public void swap(ImageView imgView1, ImageView imgView2) {
        // Save the image from imgView1
        Image tempImage = imgView1.getImage();

        // Set imgView1 to display the image from imgView2
        imgView1.setImage(imgView2.getImage());

        // Set imgView2 to display the original image from imgView1
        imgView2.setImage(tempImage);
    }

    @FXML
    private void handleTranslateAction(KeyEvent event) {
        TranslateAPI obj1 = new TranslateAPI(from, to);
        String inputText = textField1.getText();
        // Gọi hàm dịch với inputText ở đây
        String translatedText = obj1.translateText(inputText);
        // Hiển thị kết quả nếu cần
        textField2.setText(translatedText);
    }

    @FXML
    private void handleToggleLanguageAction(ActionEvent event) {
        isUsingImg1 = !isUsingImg1;
        // Đảo ngược ngôn ngữ và ảnh khi nút được nhấn
        toggleLanguage();
        //   toggleImage();
    }

    private void toggleLanguage() {
        if (isUsingImg1) {
            from = "en";
            to = "vi";
        } else {
            from = "vi";
            to = "en";
        }
    }

    private void toggleImage() {
        swap(img1 , img2);
    }

    // Các phương thức khác nếu cần

}