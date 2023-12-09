package com.example.dictionary;

import com.dictionary.Connection.JDBCConnection;
import com.dictionary.Connection.wordmean;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

class Word {
    public String wordname;
    public String mean;

    Word(String wordname, String mean) {
        this.mean = mean;
        this.wordname = wordname;
    }
}

public class Dictionary {

    public String randomWord() {
        JDBCConnection jdbcConnection = new JDBCConnection();
        return  jdbcConnection.getRandomWord();
    }

    public String randomWord(int len) {
        JDBCConnection jdbcConnection = new JDBCConnection();
        return  jdbcConnection.getRandomWord(len);
    }

    public String matchingWord(String answer) {
        JDBCConnection jdbcConnection = new JDBCConnection();
        return  jdbcConnection.getMatchingRandomWord(answer);
    }

    public wordmean findWord(String word) {
        JDBCConnection jdbcConnection = new JDBCConnection();
        wordmean meaning = jdbcConnection.getMeaning(word);
        return meaning;
    }


    public String findWordHangman(String word) {
        JDBCConnection jdbcConnection = new JDBCConnection();
        String meaning = jdbcConnection.getMean(word);
        return meaning;
    }

    public int checkAnswer(String answer, String check) {
        JDBCConnection jdbcConnection = new JDBCConnection();
        if (!jdbcConnection.isWord(answer)) {
            return -1;
        } else {
            System.out.println("check: " + check.charAt(check.length() - 1) + "!" + answer.charAt(0) + "!");
            if (check.charAt(check.length() - 1) != answer.charAt(0)) {
                return 0;
            }
        }
        return 1;
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

    public boolean addWord(wordmean word) {
        // Kiểm tra từ có tồn tại trong cơ sở dữ liệu không
        // if (wordExistsInDatabase(word.getWordname())) {
        //    System.out.println("Đã có từ này trong cơ sở dữ liệu!");
        //     return false;
        //  } else {
        // Nếu không, thêm từ vào cơ sở dữ liệu
        addToDatabase(word);
        System.out.println("Thêm từ vào SQL thành công!");
        return true;
        //     }
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
            // Lấy ID của từ cần xóa
            int wordId = getWordId(conn, word);

            if (wordId != -1) {
                // Xóa từ trong bảng favorwwords
                String deleteWordSql = "DELETE FROM favorwwords WHERE id = ?";
                PreparedStatement deleteWordStatement = conn.prepareStatement(deleteWordSql);
                deleteWordStatement.setInt(1, wordId);
                deleteWordStatement.executeUpdate();
                deleteWordStatement.close();

                // Xóa từ trong các bảng khác
                deleteFromFavorTables(conn, wordId);
            } else {
                System.out.println("Không tìm thấy từ để xóa!");
            }
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

    private int getWordId(Connection conn, String word) throws SQLException {
        String selectWordIdSql = "SELECT id FROM favorwwords WHERE word = ?";
        PreparedStatement selectWordIdStatement = conn.prepareStatement(selectWordIdSql);
        selectWordIdStatement.setString(1, word);

        ResultSet resultSet = selectWordIdStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("id");
        }

        return -1; // Trả về -1 nếu không tìm thấy từ
    }

    private void deleteFromFavorTables(Connection conn, int wordId) throws SQLException {
        // Xóa từ trong bảng favorsynonyms
        String deleteSynonymSql = "DELETE FROM favorsynonyms WHERE word_id = ?";
        PreparedStatement deleteSynonymStatement = conn.prepareStatement(deleteSynonymSql);
        deleteSynonymStatement.setInt(1, wordId);
        deleteSynonymStatement.executeUpdate();
        deleteSynonymStatement.close();

        // Xóa từ trong bảng favoranotnyms
        String deleteAntonymSql = "DELETE FROM favoranotnyms WHERE word_id = ?";
        PreparedStatement deleteAntonymStatement = conn.prepareStatement(deleteAntonymSql);
        deleteAntonymStatement.setInt(1, wordId);
        deleteAntonymStatement.executeUpdate();
        deleteAntonymStatement.close();

        // Xóa từ trong bảng favordef
        String deleteDefinitionSql = "DELETE FROM favordef WHERE word_id = ?";
        PreparedStatement deleteDefinitionStatement = conn.prepareStatement(deleteDefinitionSql);
        deleteDefinitionStatement.setInt(1, wordId);
        deleteDefinitionStatement.executeUpdate();
        deleteDefinitionStatement.close();
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

