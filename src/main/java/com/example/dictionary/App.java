package com.example.dictionary;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main Class để run
 */
public class App extends Application {
    public App() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        System.out.println("hi");
        AnchorPane root = new AnchorPane();
        Button button1 = new Button("(B1) Top + Left + Right");

        Button button2 = new Button("(B2) Top + Left + Right");
        Button button3 = new Button("(B3) Top + Left + Right");

        Button button4 = new Button("(B4) Top + Left + Right + Bottom");

        // (B1) Neo vào Top + Left + Right
        AnchorPane.setTopAnchor(button1, 40.0);
        AnchorPane.setLeftAnchor(button1, 50.0);
        AnchorPane.setRightAnchor(button1, 70.0);

        // (B2) Neo vào Top + Left + Right
        AnchorPane.setTopAnchor(button2, 90.0);
        AnchorPane.setLeftAnchor(button2, 10.0);
        AnchorPane.setRightAnchor(button2, 320.0);


        // (B3) Neo vào Top + Left + Right
        AnchorPane.setTopAnchor(button3, 100.0);
        AnchorPane.setLeftAnchor(button3, 250.0);
        AnchorPane.setRightAnchor(button3, 20.0);

        // (B4) Neo vào 4 cạnh của AnchorPane
        AnchorPane.setTopAnchor(button4, 150.0);
        AnchorPane.setLeftAnchor(button4, 40.0);
        AnchorPane.setRightAnchor(button4, 50.0);
        AnchorPane.setBottomAnchor(button4, 45.0);


        // Thêm vào AnchorPane
        root.getChildren().addAll(button1, button3, button2, button4);

        Scene scene = new Scene(root, 550, 250);

        stage.setTitle("AnchorPane Layout Demo (o7planning.org)");
        stage.setScene(scene);
        stage.show();
    }
}
