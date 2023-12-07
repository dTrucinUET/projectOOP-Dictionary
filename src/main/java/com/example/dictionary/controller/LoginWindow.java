package com.example.dictionary.controller;

import com.dictionary.Connection.JDBCConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.events.Event;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginWindow extends HomeController{
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Button signIn;
    @FXML
    private Button exit;

    JDBCConnection jdbcConnection = new JDBCConnection();
    public void display(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignIn.fxml"));
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

    public void signInButtonClicked(ActionEvent event) throws Exception{
            String id = usernameField.getText();
            String pass = passwordField.getText();
            if (jdbcConnection.isUser(id, pass)) {
                System.out.println("ok");
                showSuccessDialog(); // Hiển thị thông báo đăng nhập thành công
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                loginS = true;
                stage.close();
            } else {
                System.out.println("?");
                showErrorDialog();
            }
    }

    private void showErrorDialog() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Login failed!");

        alert.showAndWait();
    }

    private void showSuccessDialog() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Login successful!");

        alert.showAndWait();
    }

}