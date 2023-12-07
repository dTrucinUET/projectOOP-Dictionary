package com.example.dictionary.controller;

import com.dictionary.Connection.JDBCConnection;
import com.example.dictionary.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SignUpWindow {
    @FXML
    private TextField studentIdField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField nameField;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    private JDBCConnection jdbcConnection = new JDBCConnection();

    @FXML
    private void signUpButtonClicked() {
        // Lấy thông tin từ các trường nhập liệu
        String studentId = studentIdField.getText();
        String phoneNumber = phoneNumberField.getText();
        String address = addressField.getText();
        String name = nameField.getText();
        String dob = dobPicker.getValue().toString();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        Student student = new Student(studentId, name, password, phoneNumber, address, "", dob);
        jdbcConnection.insertUser(student);

        // Lưu thông tin vào cơ sở dữ liệu ở đây
        // ...

        // Xử lý các bước khác sau khi lưu thông tin thành công
        // ...
    }

    @FXML
    private void cancelButtonClicked() {
        // Xử lý khi người dùng bấm nút Cancel
        // ...
    }
    public void display(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
