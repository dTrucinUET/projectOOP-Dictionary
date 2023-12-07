package com.example.dictionary.controller;

import com.dictionary.Connection.JDBCStudent;
import com.example.dictionary.Exams;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class PracticeController extends MainControllers {
    int id = 1;

    @FXML
    private Button b1;

    @FXML
    private Label lNumberQuestion;

    @FXML
    private Text tQuestion;

    @FXML
    private TextField tfAnswer;

    private String answer;

    private Exams exam = new Exams();

    private Button button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        JDBCStudent jdbcStudent = new JDBCStudent();
        Exams exam1 = new Exams(1, jdbcStudent.getExam(1), jdbcStudent.getAnswer(id));
        exam.setId(1);
        exam.setQuestions(exam1.getQuestions());
        exam.setCorrectAnswer(exam1.getCorrectAnswer());

        lNumberQuestion.setText("Question: " + "1");
        tQuestion.setText(exam.getQuestions().get(0));

        button = b1;
    }

    public void getTextInput() {
        tfAnswer.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               // System.out.println(tfAnswer.getText());
                answer = tfAnswer.getText();
                System.out.println(answer + " " + exam.getCorrectAnswer().get(id));

                if (exam.isCorrectAnswer(id, answer)) {
                    System.out.println("right");
                    button.getStyleClass().add("button-correct");
                } else {
                    System.out.println("wrong");
                    button.getStyleClass().add("button-incorrect");
                }
                tfAnswer.clear();
            }
        });
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Button button = (Button) event.getSource();
        int questionNumber = Integer.parseInt(button.getText());
        System.out.println(questionNumber);
        // Hiển thị câu hỏi tương ứng
        this.button = button;
        lNumberQuestion.setText("Question: " + questionNumber);
        tQuestion.setText(exam.getQuestions().get(questionNumber - 1));
        id = questionNumber;
    }
}
