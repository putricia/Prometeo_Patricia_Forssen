module org.example.logitronapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.xml;


    opens org.example.logitronapp to javafx.fxml;
    exports org.example.logitronapp;
}