module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires jbcrypt;

    opens org.example.demo to javafx.fxml;
    exports org.example.demo;
}