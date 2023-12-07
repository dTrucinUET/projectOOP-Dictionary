package com.dictionary.Connection;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class JDBCConnection {
    public static Connection getJDBCConnection() {
        final String url = "jdbc:mysql://localhost:3306/dictionaries";
        final String user = "root";
        final String password = "07122003";
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Kết nối thành công.");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    public wordmean getMeaning(String word) {
        wordmean result = new wordmean();

        try (Connection conn = getJDBCConnection()) {
            // Query for meaning and part of speech
            String sqlQuery = "SELECT definition, part_of_speech FROM definitions " +
                    "INNER JOIN words ON definitions.word_id = words.id WHERE word = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, word);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    // Gán giá trị cho danh sách definition
                    String definition = resultSet.getString("definition");
                    if (definition != null) {
                        result.addMeaning(definition);
                    }

                    // Gán giá trị cho danh sách part of speech
                    String speech = resultSet.getString("part_of_speech");
                    if (speech != null) {
                        result.addSpeech(speech);
                    }
                }
            }

            String sql_1 = "SELECT phonetic FROM words " +
                    "WHERE word = ?";
            try (PreparedStatement sql = conn.prepareStatement(sql_1)) {
                sql.setString(1, word);
                ResultSet result1 = sql.executeQuery();

                if (result1.next()) {
                    String phonetics = result1.getString("phonetic");
                    if (phonetics != null) {
                        result.phonetic = phonetics;
                        result.wordname = word;
                    }
                }
            }

            // Query for synonyms
            String sqlSyn = "SELECT synonym FROM synonyms " +
                    "INNER JOIN words ON synonyms.word_id = words.id WHERE word = ?";
            try (PreparedStatement syn = conn.prepareStatement(sqlSyn)) {
                syn.setString(1, word);
                ResultSet result1 = syn.executeQuery();

                while (result1.next()) {
                    String synonyms = result1.getString("synonym");
                    if (synonyms != null) {
                        result.addSynonym(synonyms);
                    }
                }
            }

            String sqlAnt = "SELECT antonym FROM antonyms " +
                    "INNER JOIN words ON antonyms.word_id = words.id WHERE word = ?";
            try (PreparedStatement ant = conn.prepareStatement(sqlAnt)) {
                ant.setString(1, word);
                ResultSet result1 = ant.executeQuery();

                while (result1.next()) {
                    String antonyms = result1.getString("antonym");
                    if (antonyms != null) {
                        result.addAntonym(antonyms);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public static void main(String[] args) {
        JDBCConnection dictionaryService = new JDBCConnection();
        String word = "long"; // Replace with the word you want to search
        wordmean result = dictionaryService.getMeaning(word);
        System.out.println("Từ: " + result.wordname);
        System.out.println("Phát âm: /" + result.phonetic + "/");

        for(int i = 0; i < result.meaning.size(); i++)
        {
            System.out.println("Speech: " + result.speech.get(i));
            System.out.println("Meaning: " + result.meaning.get(i));
        }
        for(int i = 0; i < result.synonym.size(); i++)
        {
            System.out.println("Đồng nghĩa: " + result.synonym.get(i));
        }

        for(int i = 0; i < result.antonym.size(); i++)
        {
            System.out.println("Trái nghĩa: " + result.antonym.get(i));
        }
    }
}
