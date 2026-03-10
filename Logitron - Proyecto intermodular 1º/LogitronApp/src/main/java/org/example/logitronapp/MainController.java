package org.example.logitronapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button btnAcceso, btnNuevaClave;

    @FXML
    private TextField editUsuario, editClave;

    @FXML
    private VBox vBox;

    @FXML
    private HBox hBox;

    @FXML
    private Text txtFeedback;

    @Override
    public void initialize(URL location, ResourceBundle resources) { actions();}

    private void actions() {

        btnAcceso.setOnAction(actionEvent -> {

            String user = editUsuario.getText().toLowerCase().trim();
            String clave = editClave.getText();

            if (user.isEmpty()  || clave.isEmpty()) {
                txtFeedback.setText("Escribe un nombre de usuario y una clave");
            } else if (user.equals("patricia") && clave.equals("clave")) {
                txtFeedback.setText(String.format("Bienvenida %s", user));
            } else {
                txtFeedback.setText("El usuario no existe o la clave es incorrecta");
            }

        });
    }
}
