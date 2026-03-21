module org.example.logitronapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.xml;
    requires javafx.base;
    requires static lombok;


    opens org.example.logitronapp to javafx.fxml;
    exports org.example.logitronapp;
    exports org.example.logitronapp.controller;
    opens org.example.logitronapp.controller to javafx.fxml;
}