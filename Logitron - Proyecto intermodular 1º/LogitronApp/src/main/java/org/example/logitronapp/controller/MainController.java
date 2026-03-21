package org.example.logitronapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.logitronapp.HelloApplication;
import org.example.logitronapp.LoginAction;

import java.io.IOException;
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
                return;
            }

            // hacemos el control de login a través del metodo validarLogin en la clase LoginAction
            String rol = LoginAction.validarLogin(user, clave);

            if (rol != null) {
                txtFeedback.setText(String.format("Bienvenida %s", user));

                switch (rol) {
                    case "admin":
                        loadView("main-admin-view.fxml");
                        break;
                    case "vendedor":
                        loadView("main-sales-view.fxml");
                        break;
                    case "picker":
                        loadView("main-logistic-view.fxml");
                        break;
                    case "finanzas":
                        loadView("main-finanzas-view.fxml");
                        break;
                }

            } else {
                txtFeedback.setText("El usuario no existe o la clave es incorrecta");
            }

        });
    }

    private void loadView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/org/example/logitronapp/" + fxml));
            Parent root = loader.load();
            Stage stage = (Stage) btnAcceso.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
