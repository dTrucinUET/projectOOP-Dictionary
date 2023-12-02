package com.example.dictionary;

import com.dictionary.Connection.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

class Word {
    public String wordname;
    public String mean;

    Word(String wordname, String mean) {
        this.mean = mean;
        this.wordname = wordname;
    }
}

public class Dictionary {

    public String findWord(String word) {
        JDBCConnection jdbcConnection = new JDBCConnection();
        String meaning = jdbcConnection.getMeaning(word);
        if(meaning != null) {
            meaning = meaning.replaceAll("<br />", "\n");
            meaning = meaning.replaceAll("<I><Q>@" + word , "");
            meaning = meaning.replaceAll("</Q></I>" , "");
        }
        return meaning;
    }

    public void addWord(Word word) {
        // Kiểm tra từ có tồn tại trong cơ sở dữ liệu không
        if (wordExistsInDatabase(word.wordname)) {
            System.out.println("Đã có từ này trong cơ sở dữ liệu!");
        } else {
            // Nếu không, thêm từ vào cơ sở dữ liệu
            addToDatabase(word);
            System.out.println("Thêm từ vào SQL thành công!");
        }
    }

    public void removeWord(String word) {
        // Kiểm tra từ có tồn tại trong cơ sở dữ liệu không
        if (wordExistsInDatabase(word)) {
            // Nếu có, xóa từ trong cơ sở dữ liệu
            removeFromDatabase(word);
            System.out.println("Xóa từ trong SQL thành công!");
        } else {
            System.out.println("Không tìm thấy từ để xóa!");
        }
    }

    private boolean wordExistsInDatabase(String word) {
        JDBCConnection jdbcConnection = new JDBCConnection();
        String meaning = jdbcConnection.getMeaning(word);
        return meaning != null;
    }

    private void addToDatabase(Word word) {
        JDBCConnection jdbcConnection = new JDBCConnection();
        Connection conn = jdbcConnection.getJDBCConnection();

        try {
            String sqlQuery = "INSERT INTO dictionary (target, definition) VALUES (?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, word.wordname);
            preparedStatement.setString(2, word.mean);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void removeFromDatabase(String word) {
        JDBCConnection jdbcConnection = new JDBCConnection();
        Connection conn = jdbcConnection.getJDBCConnection();

        try {
            String sqlQuery = "DELETE FROM dictionary WHERE target = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, word);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // ... (giữ nguyên phần code khởi tạo Dictionary và thực hiện thêm từ)

        Dictionary obj = new Dictionary();
        // Thử tìm từ trong Dictionary
        System.out.println(obj.findWord("run"));
        System.out.println(obj.findWord("hello"));
       // Word a = new Word("a00" ,"test" );
      //  obj.addWord(a);
      //  obj.removeWord("a00");
    }
}
