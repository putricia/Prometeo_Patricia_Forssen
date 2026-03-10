module org.example.logitronapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.logitronapp to javafx.fxml;
    exports org.example.logitronapp;
}