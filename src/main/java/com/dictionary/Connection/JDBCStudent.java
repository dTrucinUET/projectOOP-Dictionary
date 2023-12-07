package com.dictionary.Connection;

import javafx.collections.ArrayChangeListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBCStudent extends  JDBCConnection {
    public ArrayList<String> getExam(int id) {
        ArrayList<String> res = new ArrayList<>();
        try {
            Connection conn = getJDBCConnection();
            String sqlQuery = "select question_text from exam where exam_number = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                res.add(resultSet.getString("question_text"));
            }

            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public ArrayList<String> getAnswer(int id) {
        ArrayList<String> res = new ArrayList<>();
        try {
            Connection conn = getJDBCConnection();
            String sqlQuery = "select correct_answer from exam where exam_number = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                res.add(resultSet.getString("correct_answer"));
            }

            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args) {
        ArrayList<String> res = new ArrayList<>();
        int id = 1;
        Integer i = 1;
        try {
            Connection conn = getJDBCConnection();
            String sqlQuery = "select question_text from exam where exam_number = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                res.add(i.toString() + ". " + resultSet.getString("question_text"));
                i++;
            }

            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(res);
    }
}
