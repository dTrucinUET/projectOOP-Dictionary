package com.example.dictionary.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class LoadingController {
    @FXML
    private ProgressBar loadingBar;
    @FXML
    private Label loadingValue;

    public void updateProgress(double progress) {
        System.out.println(loadingValue.getText());
        System.out.println(loadingBar.getProgress());
        int percentage = (int) (progress * 100);
        loadingBar.setProgress(percentage);
        loadingValue.setText(String.format("%.0f%%", progress * 100));
    }
}