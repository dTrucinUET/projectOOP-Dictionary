
package com.dictionary.Connection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.*;

public class JDBCConnection {
    public static Connection getJDBCConnection() {
        final String url = "jdbc:mysql://localhost:3306/dictionaries";
        final String user = "root";
        final String password = "06112003@tT";
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

    public String getMeaning(String word) {
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


    public static void main(String[] args) {
        Connection conn = JDBCConnection.getJDBCConnection();
        if (conn != null) {
            try {
                // Tìm từ trong cơ sở dữ liệu
                String targetToFind = "subject";
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

