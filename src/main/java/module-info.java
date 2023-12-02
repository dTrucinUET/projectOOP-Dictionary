module com.example.dictionary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;

    requires mysql.connector.j;
    requires jsoup;

    opens com.example.dictionary to javafx.fxml;
    exports com.example.dictionary;
}