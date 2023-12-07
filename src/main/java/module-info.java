module com.example.dictionary {
    requires javafx.controls;
    requires javafx.fxml;

    // Các cấu hình khác nếu cần

    exports com.example.dictionary.controller;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;

    requires jsoup;
    requires mysql.connector.j;
    requires json.simple;
    requires org.apache.commons.io;
    requires org.apache.commons.text;
    requires javafx.media;
    requires org.json;
    requires javafx.web;

    opens com.example.dictionary.controller;
    opens com.example.dictionary to javafx.fxml;
}
