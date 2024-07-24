module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens org.example.demo to javafx.fxml;
    exports org.example.demo;
    exports org.example.demo.DB;
    opens org.example.demo.DB to javafx.fxml;
}