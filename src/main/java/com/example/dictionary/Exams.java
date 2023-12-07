package com.example.dictionary;

import com.dictionary.Connection.JDBCConnection;
import com.dictionary.Connection.JDBCStudent;

import java.util.ArrayList;
import java.util.List;

public class Exams {
    private int id;
    private ArrayList<String> questions = new ArrayList<>();
    private ArrayList<String> correctAnswer = new ArrayList<>();

    private JDBCStudent jdbcStudent = new JDBCStudent();

    private int numQuestion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public ArrayList<String> getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(ArrayList<String> correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Exams(int id, ArrayList<String> questions, ArrayList<String> correctAnswer) {
        this.id = id;
        this.questions = questions;
        this.correctAnswer = correctAnswer;
    }

    public void createExam(int id) {
        this.questions = jdbcStudent.getExam(id);
    }

    public boolean isCorrectAnswer(int number_question, String answer) {
        return correctAnswer.get(number_question - 1).equals(answer);
    }

    public Exams() {
    }
}
