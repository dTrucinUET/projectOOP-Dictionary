package com.example.dictionary;

import com.dictionary.Connection.JDBCConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MatchingWord {
    private final JDBCConnection jdbcConnection = new JDBCConnection();

    public String getMatchingWord (String word) {
        return jdbcConnection.getMatchingRandomWord(word);
    }
}
