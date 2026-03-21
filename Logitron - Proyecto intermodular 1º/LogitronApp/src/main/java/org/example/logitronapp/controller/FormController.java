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
        instances();
        initGUI();
        actions();
    }

    private void actions() {
        btnComprobar.setOnAction(event -> {

        });
        btnVaciar.setOnAction(event -> {
            vaciarCampos();
        });
        btnAgregar.setOnAction(event -> {
            // comprobar que todos los campos estan rellenos
            String nombre = editNombre.getText();
            String correo = editCorreo.getText();
            String direccion = editDireccion.getText();
            String clave = editClave.getText();
            String rol = comboRol.getSelectionModel().getSelectedItem();
            String genero = ((RadioButton) (grupoGenero.getSelectedToggle())).getText();
            Usuario usuario = new Usuario(nombre, correo, direccion, clave, rol, genero);
            System.out.println("Usuario creado con exito: " + usuario.getNombre());
            vaciarCampos();
        });
        btnComprobar.setOnMouseEntered(new ManejoRaton());
        btnVaciar.setOnMouseEntered(new ManejoRaton());
        btnAgregar.setOnMouseEntered(new ManejoRaton());
        btnAgregar.setOnMouseExited(new ManejoRaton());
        btnVaciar.setOnMouseExited(new ManejoRaton());
        btnComprobar.setOnMouseExited(new ManejoRaton());

    }

    private void vaciarCampos() {
        editNombre.clear();
        editCorreo.clear();
        editClave.clear();
        editDireccion.clear();
        spinnerEdad.increment(-90);
        comboRol.getSelectionModel().select(0);
        grupoGenero.selectToggle(null);
    }

    private void initGUI() {
        grupoGenero.getToggles().addAll(radioFemenino, radioMasculino);
        comboRol.setItems(listaRols);
    }

    private void instances() {
        shadow = new DropShadow();
        grupoGenero = new ToggleGroup();
        listaRols = FXCollections.observableArrayList("Administrador", "Finanzas", "Logistica", "Comercial");
    }

    class ManejoRaton implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            // System.out.println("Raton por encima");
            if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
                System.out.println("Saliendo");
                ((Button) (event.getSource())).setEffect(null);
            } else if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
                System.out.println("Entrando");
                ((Button) (event.getSource())).setEffect(shadow);
            }
        }
    }
}
