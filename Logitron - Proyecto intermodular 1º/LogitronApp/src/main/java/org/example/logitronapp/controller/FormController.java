package org.example.logitronapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.example.logitronapp.model.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

public class FormController implements Initializable {

    @FXML
    private BorderPane borderGeneral;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnComprobar;

    @FXML
    private Button btnVaciar;

    @FXML
    private CheckBox checkLista;

    @FXML
    private ComboBox<String> comboRol;
    private ObservableList<String> listaRols;

    @FXML
    private TextField editCorreo;

    @FXML
    private TextField editDireccion;

    @FXML
    private TextField editNombre;

    @FXML
    private TextField editClave;

    @FXML
    private RadioButton radioFemenino;

    @FXML
    private RadioButton radioMasculino;

    private ToggleGroup grupoGenero;

    @FXML
    private Spinner<Integer> spinnerEdad;
    private DropShadow shadow;
    // no se le asocia una lista
    private SpinnerValueFactory.IntegerSpinnerValueFactory modeloSpinner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actions();
    }

    private void actions() {
    }

}
