
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

    public String getMeaning(String word) {
        String meaning = null;

        try {
            Connection conn = getJDBCConnection();
            String sqlQuery = "SELECT definition FROM dictionary WHERE target = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, word);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                meaning = resultSet.getString("definition");
            }

            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return meaning;
    }


    public static void main(String[] args) {
        Connection conn = JDBCConnection.getJDBCConnection();

        if (conn != null) {
            try {
                // Tìm từ trong cơ sở dữ liệu
                String targetToFind = "hello";
                String sqlQuery = "SELECT target, definition FROM dictionary WHERE target = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
                preparedStatement.setString(1, targetToFind);

                ResultSet resultSet = preparedStatement.executeQuery();

                // In ra màn hình thông tin từ và nghĩa (nếu có)
                while (resultSet.next()) {
                    String target = resultSet.getString("target");
                    String definition = resultSet.getString("definition");

                    System.out.println("Từ: " + target);
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

