package com.example.dictionary.controller;

import com.dictionary.Connection.JDBCStudent;
import com.example.dictionary.Exams;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.ResourceBundle;

public class PracticeController extends MainControllers {
    int id = 1;

    @FXML
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14 ,b15, b16, b17, b18, b19, b20;

    @FXML
    private Label lNumberQuestion;

    @FXML
    private Text tQuestion;

    @FXML
    private TextField tfAnswer;

    private String answer;

    private Exams exam = new Exams();

    private Button button;

    @FXML
    private Label lScore;

    private int score;

    private int questionDone;

    private boolean[] answeredQuestions = new boolean[22]; // Sử dụng mảng boolean với số phần tử tương ứng với số câu hỏi

    private int timeLimit = 600;

    private Timeline timeline;

    @FXML
    private Label lTime;

    private DecimalFormat timeFormat = new DecimalFormat("00");

    @FXML
    private Button bSubmit;

    @FXML
    private Button t1, t2, t3, t4;

    @FXML
    private AnchorPane paneB;

    @FXML
    private Text time1, time2;

    @FXML Text core1, core2;

    @FXML
    private Text hint;
    private int examID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
    }

    private void handleTimeUp() {
        timeline.stop();
        showResultDialog();
    }

    private void showResultDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Kết quả");
        alert.setHeaderText("Bạn đã hoàn thành 20 câu hỏi.");
        alert.setContentText("Bạn đã hoàn thành " + questionDone + " câu hỏi\nĐiểm số của bạn: " + score);
        alert.showAndWait();
        paneB.setVisible(true);
        Integer integer = score;
        System.out.println(score);
        if (examID == 1) {
            int timeLimit = 600 - this.timeLimit;
            int minutes = timeLimit / 60;
            int seconds = timeLimit % 60;
            core1.setText("Core: " + integer.toString());
            time1.setText("Time: " + timeFormat.format(minutes) + ":" + timeFormat.format(seconds));
            t1.setStyle("-fx-background-color: #00FF00;");
        }
        if (examID == 2) {
            core2.setText("Core: " + integer.toString());
            int timeLimit = 600 - this.timeLimit;
            int minutes = timeLimit / 60;
            int seconds = timeLimit % 60;
            core2.setText("Core: " + integer.toString());
            time2.setText("Time: " + timeFormat.format(minutes) + ":" + timeFormat.format(seconds));
            t1.setStyle("-fx-background-color: #00FF00;");
        }

    }
    private void updateTimerLabel() {
        int minutes = timeLimit / 60;
        int seconds = timeLimit % 60;
        lTime.setText("Time: " + timeFormat.format(minutes) + ":" + timeFormat.format(seconds));
    }

    public void getTextInput() {
        System.out.println("--------------------");
        tfAnswer.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               // System.out.println(tfAnswer.getText());
                answer = tfAnswer.getText();
                System.out.println(answer + " " + exam.getCorrectAnswer().get(id - 1));
                if (exam.isCorrectAnswer(id, answer)) {
                    System.out.println("right");
                    button.getStyleClass().add("button-correct");
                    score++;
                    lScore.setText("Score: " + score);
                } else {
                    System.out.println("wrong");
                    button.getStyleClass().add("button-incorrect");
                }
                questionDone++;
                tfAnswer.clear();

                goToNextUnansweredQuestion();

                System.out.println(questionDone);
                if(questionDone == 20) {
                    handleTimeUp();
                    System.out.println("done!!!!!!!!!!!!!!!!!!!!!!!!1");
                }
            }

        });

    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        if (this.button != null) {
            this.button.getStyleClass().remove("button-selected");
        }
        this.button = clickedButton;
        this.button.getStyleClass().removeAll("button-correct", "button-incorrect");
        this.button.getStyleClass().add("button-selected");
        int questionNumber = Integer.parseInt(clickedButton.getText());
        System.out.println(questionNumber);
        // Hiển thị câu hỏi tương ứng
        answeredQuestions[id - 1] = true;
        this.button = clickedButton;
        lNumberQuestion.setText("Question: " + questionNumber);
        tQuestion.setText(exam.getQuestions().get(questionNumber - 1));
        hint.setText(exam.getCorrectAnswer().get(questionNumber - 1).substring(0, 1));
        id = questionNumber;
    }

    private void goToNextUnansweredQuestion() {
        int nextQuestionId = id + 1;
        while (answeredQuestions[nextQuestionId - 1] && questionDone <= 20) {
            nextQuestionId++;
            questionDone++;
            while (!answeredQuestions[nextQuestionId] && nextQuestionId<= 20) {
                nextQuestionId++;
            }
        }

        if (nextQuestionId <= 20) {
            this.button.getStyleClass().remove("button-selected");
            id = nextQuestionId;
            lNumberQuestion.setText("Question: " + id);
            tQuestion.setText(exam.getQuestions().get(id - 1));
            button = getButtonById(id);
            hint.setText(exam.getCorrectAnswer().get(id - 1).substring(0, 1));
            button.getStyleClass().add("button-selected"); // Đặt màu cho câu hỏi tiếp theo
        } else {
            nextQuestionId -= 20;
            while (answeredQuestions[nextQuestionId] && nextQuestionId<= 20) {
                nextQuestionId++;
            }
            this.button.getStyleClass().remove("button-selected");
            id = nextQuestionId;
            lNumberQuestion.setText("Question: " + id);
            tQuestion.setText(exam.getQuestions().get(id - 1));
            hint.setText(exam.getCorrectAnswer().get(id - 1).substring(0, 1));
            button = getButtonById(id);
            button.getStyleClass().add("button-selected");
        }
        System.out.println("next: " + nextQuestionId + answeredQuestions[nextQuestionId]);
    }

    private Button getButtonById(int questionId) {
        switch (questionId) {
            case 1:
                return b1;
            case 2:
                return b2;
            case 3:
                return b3;
            case 4:
                return b4;
            case 5:
                return b5;
            case 6:
                return b6;
            case 7:
                return b7;
            case 8:
                return b8;
            case 9:
                return b9;
            case 10:
                return b10;
            case 11:
                return b11;
            case 12:
                return b12;
            case 13:
                return b13;
            case 14:
                return b14;
            case 15:
                return b15;
            case 16:
                return b16;
            case 17:
                return b17;
            case 18:
                return b18;
            case 19:
                return b19;
            case 20:
                return b20;
            default:
                return b1;
        }
    }

    @FXML
    private void handleButtonSubmit(ActionEvent event) {
        submitExam();
    }

    private void submitExam() {
        timeline.stop();
        showResultDialog();
    }

    @FXML
    private void handleButtonTest() {
        questionDone = 0;
        JDBCStudent jdbcStudent = new JDBCStudent();
       // System.out.println(exam1.getQuestions());
        t1.setOnMouseClicked(event -> {
            Exams exam1 = new Exams(1, jdbcStudent.getExam(1), jdbcStudent.getAnswer(id));
            exam.setId(1);
            exam.setQuestions(exam1.getQuestions());
            exam.setCorrectAnswer(exam1.getCorrectAnswer());

            paneB.setVisible(false);
        System.out.println(exam.getQuestions());
        int questionCount = exam.getQuestions().size();
        if (questionCount > 0) {
            lNumberQuestion.setText("Question: " + "1");
            tQuestion.setText(exam.getQuestions().get(0));
            hint.setText(exam.getCorrectAnswer().get(0).substring(0, 1));
        } else {
            // Xử lý khi danh sách câu hỏi rỗng
            // Ví dụ: hiển thị thông báo lỗi, thực hiện các thay đổi khác, v.v.
            lNumberQuestion.setText("No questions available");
            tQuestion.setText("");
            return;  // Kết thúc phương thức để tránh tiếp tục thực hiện các thao tác khác
        }
        examID = 1;
        });

        t2.setOnMouseClicked(event -> {
            Exams exam1 = new Exams(2, jdbcStudent.getExam(2), jdbcStudent.getAnswer(id));
            exam.setId(2);
            exam.setQuestions(exam1.getQuestions());
            exam.setCorrectAnswer(exam1.getCorrectAnswer());

            paneB.setVisible(false);
            System.out.println(exam.getQuestions());
            int questionCount = exam.getQuestions().size();
            if (questionCount > 0) {
                lNumberQuestion.setText("Question: " + "1");
                tQuestion.setText(exam.getQuestions().get(0));
            } else {
                // Xử lý khi danh sách câu hỏi rỗng
                // Ví dụ: hiển thị thông báo lỗi, thực hiện các thay đổi khác, v.v.
                lNumberQuestion.setText("No questions available");
                tQuestion.setText("");
                return;  // Kết thúc phương thức để tránh tiếp tục thực hiện các thao tác khác
            }
            examID = 2;
        });

        score = 0;
        lScore.setText("Score: " + score);

        button = b1;
        button.getStyleClass().add("button-selected");

        Arrays.fill(answeredQuestions, false);

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), event -> {
                    timeLimit--;
                    if (timeLimit <= 0) {
                        timeline.stop();
                        handleTimeUp();
                    } else {
                        updateTimerLabel();
                    }
                })
        );

        timeline.play();
        updateTimerLabel();

    }
}
