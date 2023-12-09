
package com.dictionary.Connection;

import com.example.dictionary.Student;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.*;

public class JDBCConnection {
    public static Connection getJDBCConnection() {
        final String url = "jdbc:mysql://localhost:3307/dictionaries";
        final String user = "root";
        final String password = "06112003@tT";
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        //   System.out.println("Kết nối thành công.");
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

    public String getMean(String word) {
        String meaning = "";

        try {
            Connection conn = getJDBCConnection();
            String sqlQuery = "select definition from definitions\n" +
                    "inner join words\n" +
                    "on definitions.word_id = words.id where word = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, word);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                meaning += "- " + resultSet.getString("definition") + "\n";
            }

            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("meaning: " + meaning);
        return meaning;
    }


    public String getRandomWord() {
        String randomWord = "";

        try {
            Connection conn = getJDBCConnection();
            String sqlQuery = "SELECT word FROM words ORDER BY RAND() LIMIT 1";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                randomWord = resultSet.getString("word");
            }

            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return randomWord;
    }

    public String getRandomWord(int len) {
        String res = "";
        try {
            Connection conn = getJDBCConnection();
            String sqlQuery = "select word from words where length(word) = ? ORDER BY RAND() LIMIT 1";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, len);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                res += resultSet.getString("word") + "\n";
            }

            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public String getMatchingRandomWord(String answer) {
        String res = "";
        try {
            Connection conn = getJDBCConnection();
            String sqlQuery = "select word from words where LEFT(word, 1) = ? ORDER BY RAND() LIMIT 1";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, String.valueOf(answer.charAt(answer.length() - 1)));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                res += resultSet.getString("word") + "\n";
            }

            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean isWord(String answer) {
        try {
            Connection conn = getJDBCConnection();
            String sqlQuery = "select word from words where word = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, answer);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return false;
            }

            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean isUser(String ID, String passWord) {
        String pass = "";
        try {
            Connection conn = getJDBCConnection();
            String sqlQuery = "select student_password from students where student_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return false;
            }
            if (!resultSet.getString("student_password").equals(passWord)) {
                return false;
            }

            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void insertUser(Student student) {
        String sql = "INSERT INTO students (student_id, student_name, student_password, " +
                "email, phone_number, address, date_of_birth) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getJDBCConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getPassWord());
            statement.setString(4, student.getEmail());
            statement.setString(5, student.getPhoneNumber());
            statement.setString(6, student.getAddress());
            statement.setString(7, student.getDateOfBirth());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Connection conn = JDBCConnection.getJDBCConnection();
        //System.out.println(getRandomWord());
/*
        if (conn != null) {
            try {
                // Tìm từ trong cơ sở dữ liệu

                String targetToFind = "maui";
                String sqlQuery = "select word, definition from words\n" +
                        "inner join definitions\n" +
                        "on words.id = definitions.word_id where word = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);

                preparedStatement.setString(1, targetToFind);
                //System.out.println("test: " + preparedStatement);
                ResultSet resultSet = preparedStatement.executeQuery();
                //System.out.println("test1: " + resultSet.next());
                // In ra màn hình thông tin từ và nghĩa (nếu có)
                String target = "";
                String definition = "";
                while (resultSet.next()) {
                    target = resultSet.getString("word");
                    definition += resultSet.getString("definition") + "\n";

                    System.out.println("Từ: " + target);
                    System.out.println("Nghĩa: " + definition);
                    Document document = Jsoup.parse(definition);

                    // Lấy thông tin từ thẻ <I> và <Q>
                    Elements iTags = document.select("I");
                    for (Element iTag : iTags) {
                        Elements qTags = iTag.select("Q");
                        for (Element qTag : qTags) {
                            // Lấy nội dung trong thẻ <Q>
                            String content = qTag.html();
                            System.out.println(content);
                        }
                    }
                    System.out.println("---------------------");
                }

                // Đóng tài nguyên
                resultSet.close();
                preparedStatement.close();
                conn.close();

              //  System.out.println(getRandomWord());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }*/
        /*
        int len = 4;
        String res = "";
        try {
            String sqlQuery = "select word from words where length(word) = ? ORDER BY RAND() LIMIT 1";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, len);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                res += resultSet.getString("word") + "\n";
            }

            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(res);

         */
        String res = "";
        try {
            String answer = "hello";
            //Connection conn = getJDBCConnection();
            String sqlQuery = "select word from words where LEFT(word, 1) = ? ORDER BY RAND() LIMIT 1";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, String.valueOf(answer.charAt(answer.length() - 1)));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
               // System.out.println(resultSet.getString("word"));
                res += resultSet.getString("word") + "\n";
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

