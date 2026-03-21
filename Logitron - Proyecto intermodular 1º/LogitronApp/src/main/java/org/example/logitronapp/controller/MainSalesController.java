package org.example.logitronapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.logitronapp.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSalesController implements Initializable {
    @FXML
    private Button userSettings;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {actions();}

    public void actions() {
        userSettings.setOnAction(ActionEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        HelloApplication.class.getResource("main-view.fxml")
                );
                Parent root = loader.load();
                Stage stage = (Stage) userSettings.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.getStackTrace();
            }

        });
    }

}
