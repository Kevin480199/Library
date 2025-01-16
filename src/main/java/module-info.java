module org.example.library {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens org.example.library to javafx.fxml;
    exports org.example.library;
}