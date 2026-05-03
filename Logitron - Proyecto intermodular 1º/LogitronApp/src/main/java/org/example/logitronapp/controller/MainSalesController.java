package org.example.logitronapp.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.logitronapp.HelloApplication;
import org.example.logitronapp.model.Articulo;
import org.example.logitronapp.model.Cliente;
import org.example.logitronapp.model.LineaPedido;
import org.example.logitronapp.repository.ArticulosRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainSalesController implements Initializable {
    @FXML
    private Button userLogout;

    @FXML
    private GridPane gridArticulos;

    @FXML
    private Circle totalCircle;

    @FXML
    private Text totalText;

    @FXML
    private ComboBox<Articulo> editArticulo;

    @FXML
    private Spinner<Integer> udsSpinner;

    @FXML
    private Spinner<Integer> dtoSpinner;

    @FXML private TableView<LineaPedido> tablaPedido;
    @FXML private TableColumn<LineaPedido, String> colArticulo;
    @FXML private TableColumn<LineaPedido, Double> colPrecioUnitario;
    @FXML private TableColumn<LineaPedido, Integer> colUnidades;
    @FXML private TableColumn<LineaPedido, Integer> colDescuento;
    @FXML private TableColumn<LineaPedido, Double> colTotalLinea;

    private final ObservableList<LineaPedido> lineasPedido =
            FXCollections.observableArrayList();

    private Integer idPedidoActual = null;


    @FXML
    private Button botonAgregar;

    @FXML
    private Button botonLanzarPedido;

    private double totalPedido = 0.0;

    private final ArticulosRepository repoArt = new ArticulosRepository();
    private List<Articulo> listArticulos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        actions();
        instances();

    }

    private void instances() {

        // Buscar todos los articulos existentes para el dropdown
        List<Articulo> listaArt = repoArt.findAll();

        editArticulo.setItems(FXCollections.observableArrayList(listaArt));

        // Configurar los Spinner
        udsSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999, 1)
        );

        dtoSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0)
        );

        // Armar la tabla con los atributos de cada columna
        colArticulo.setCellValueFactory(data ->
                new SimpleStringProperty((data.getValue().getNombreArticulo())));
        colPrecioUnitario.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getPrecioUnitario()).asObject());
        colUnidades.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getUnidades()).asObject());
        colDescuento.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getDescuento()).asObject());
        colTotalLinea.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getTotalLinea()).asObject());

        tablaPedido.setItems(lineasPedido);

    }

    public void actions() {
        userLogout.setOnAction(ActionEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        HelloApplication.class.getResource("main-view.fxml")
                );
                Parent root = loader.load();
                Stage stage = (Stage) userLogout.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.getStackTrace();
            }

        });

        botonAgregar.setOnAction(e -> {
            idPedidoActual = 1;
            agregarLineaPedido();


        });

        totalCircle.setOnMouseClicked(e -> mostrarDetallePedido());

        botonLanzarPedido.setOnAction(e -> abrirDialogoCliente());
    }

    private void actualizarTotal() {
        totalPedido = lineasPedido.stream()
                .mapToDouble(LineaPedido::getTotalLinea)
                .sum();

        totalText.setText(String.format("%.2f €", totalPedido));
    }

    private void limpiarFormulario() {
        editArticulo.setValue(null);
        udsSpinner.getValueFactory().setValue(1);
        dtoSpinner.getValueFactory().setValue(0);
    }

    private void limpiarTablaPedido() {
        lineasPedido.clear();
    }

    private void agregarLineaPedido() {

        Articulo articulo = editArticulo.getValue();

        if (articulo == null) {
            System.out.println("Selecciona un artículo");
            return;
        }

        int unidades = (int) udsSpinner.getValue();
        int descuento = (int) dtoSpinner.getValue();

        LineaPedido linea = new LineaPedido(articulo, unidades, descuento, idPedidoActual);
        lineasPedido.add(linea);

        actualizarTotal();
        limpiarFormulario();
    }

    private void mostrarDetallePedido() {

        if (lineasPedido.isEmpty()) {
            System.out.println("No hay articulos añadidos");
            return;
        }

        StringBuilder detalle = new StringBuilder();

        for (LineaPedido linea : lineasPedido) {
            detalle.append(linea.toString()).append("\n");
        }

        detalle.append("\nTOTAL: ")
                .append(String.format("%.2f €",totalPedido));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalle del pedido");
        alert.setHeaderText("Articulos agregados");
        alert.setContentText(detalle.toString());
        alert.showAndWait();
    }

    private void abrirDialogoCliente() {

        Dialog<Cliente> dialog = new Dialog<>();
        dialog.setTitle("Datos del cliente");
        dialog.setHeaderText("Introduce los datos del cliente");

        ButtonType botonAceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(botonAceptar, ButtonType.CANCEL);

        TextField inputCif = new TextField();
        inputCif.setPromptText("CIF");

        TextField inputNombreJuridico = new TextField();
        inputNombreJuridico.setPromptText("Nombre jurídico");

        DatePicker inputFechaAlta = new DatePicker();
        inputFechaAlta.setPromptText("Fecha alta");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("CIF:"), 0, 0);
        grid.add(inputCif, 1, 0);

        grid.add(new Label("Nombre jurídico:"), 0, 1);
        grid.add(inputNombreJuridico, 1, 1);

        grid.add(new Label("Fecha alta:"), 0, 2);
        grid.add(inputFechaAlta, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
            if (button == botonAceptar) {
                return new Cliente(
                        generarIdCliente(),
                        inputFechaAlta.getValue().toString(),
                        inputCif.getText(),
                        inputNombreJuridico.getText()
                );
            }
            return null;
        });

        dialog.showAndWait().ifPresent(cliente -> {
            System.out.println("Cliente creado:");
            System.out.println(cliente.getCif());
            System.out.println(cliente.getNombreJuridico());

            // Aquí luego guardarías pedido + cliente
            lanzarPedido(cliente);
        });
    }

    private int generarIdCliente() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    private void lanzarPedido(Cliente cliente) {

        if (lineasPedido.isEmpty()) {
            System.out.println("No puedes lanzar un pedido vacío");
            return;
        }

        System.out.println("Pedido lanzado para: " + cliente.getNombreJuridico());

        for (LineaPedido linea : lineasPedido) {
            System.out.println(linea);
        }

        System.out.println("TOTAL: " + totalPedido);

        limpiarTablaPedido();
    }


}
