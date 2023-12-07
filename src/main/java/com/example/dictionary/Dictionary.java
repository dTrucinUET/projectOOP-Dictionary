package com.example.dictionary;

import com.dictionary.Connection.JDBCConnection;
import com.dictionary.Connection.wordmean;

import java.sql.*;
import java.util.List;


public class Dictionary {

    public wordmean findWord(String word) {
        JDBCConnection jdbcConnection = new JDBCConnection();
        wordmean meaning = jdbcConnection.getMeaning(word);
        return meaning;
    }

    public void addWord(wordmean word) {
        // Kiểm tra từ có tồn tại trong cơ sở dữ liệu không
        //if (wordExistsInDatabase(word.getWordname())) {
        //    System.out.println("Đã có từ này trong cơ sở dữ liệu!");
       /// } else {
            // Nếu không, thêm từ vào cơ sở dữ liệu
            addToDatabase(word);
            System.out.println("Thêm từ vào SQL thành công!");
       // }
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
        wordmean meaning = jdbcConnection.getMeaning(word);
        return meaning != null;
    }

    private void addToDatabase(wordmean word) {
        JDBCConnection jdbcConnection = new JDBCConnection();
        Connection conn = jdbcConnection.getJDBCConnection();

        try {
            String sqlQuery = "INSERT INTO favorwwords ( word , phonetic ) VALUES ( ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, word.getWordname());
            preparedStatement.setString(2, word.getPhonetic());
            preparedStatement.executeUpdate();

            // Lấy ID của từ vừa thêm
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int wordId = generatedKeys.getInt(1);
                System.out.println(wordId);

                // Thêm thông tin từ vào các bảng khác

                addToFavoDefinition(conn, wordId, word.getMeaning() , word.getSpeech());
                if(!word.getAntonym().isEmpty()){
                    addToFavorAntonym(conn , wordId , word.getAntonym());
                }
                if(!word.getSynonym().isEmpty()){
                    addToFavorSynonym(conn, wordId, word.getSynonym());
                }
                // Thêm các thông tin khác tương tự
            }
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

    private void addToFavorSynonym(Connection conn, int wordId, List<String> synonyms) throws SQLException {
        String insertFavorWordSql = "INSERT INTO favorsynonyms (word_id, synonym) VALUES (?, ?)";
        PreparedStatement favorWordStatement = conn.prepareStatement(insertFavorWordSql);
        List<String> syn = synonyms;

        for (int i = 0; i < syn.size(); i++) {
            favorWordStatement.setInt(1, wordId);
            favorWordStatement.setString(2, syn.get(i));
            favorWordStatement.executeUpdate();
         }
        favorWordStatement.executeUpdate();
        favorWordStatement.close();
    }

    private void addToFavorAntonym(Connection conn, int wordId, List<String> antonyms) throws SQLException {
        String insertFavorWordSql = "INSERT INTO favoranotnyms (word_id, antonym) VALUES (?, ?)";
        PreparedStatement favorWordStatement = conn.prepareStatement(insertFavorWordSql);
        List<String> ant = antonyms;

        for (int i = 0; i < ant.size(); i++) {
            favorWordStatement.setInt(1, wordId);
            favorWordStatement.setString(2, ant.get(i));
            favorWordStatement.executeUpdate();
        }
        favorWordStatement.executeUpdate();
        favorWordStatement.close();
    }

    private void addToFavoDefinition(Connection conn, int wordId, List<String> definition , List<String> speech) throws SQLException {
        String insertFavoDefinitionSql = "INSERT INTO favordef (word_id, definition ,part_of_speech ) VALUES (?, ? , ?)";
        PreparedStatement favoDefinitionStatement = conn.prepareStatement(insertFavoDefinitionSql);
        List<String> def = definition;
        List<String> spe = speech;

        for (int i = 0; i < def.size(); i++) {
            favoDefinitionStatement.setInt(1, wordId);
            favoDefinitionStatement.setString(2, def.get(i));
            favoDefinitionStatement.setString(3, spe.get(i));
            favoDefinitionStatement.executeUpdate();
        }
        favoDefinitionStatement.executeUpdate();
        favoDefinitionStatement.close();
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

