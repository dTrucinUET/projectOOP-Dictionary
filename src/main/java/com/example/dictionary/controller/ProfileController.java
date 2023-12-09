package com.example.dictionary.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController extends MainControllers {
    private final SignUpWindow info = new SignUpWindow();

    @FXML
    private Text tname;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        //tname.setText(info.nameField.getText());
    }
}
