/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop;

import desktop.interfaz.INotificacionCambio;

import desktop.modelo.dao.EnviosDAO;
import desktop.modelo.dao.UnidadDAO;
import desktop.modelo.pojo.Colaborador;
import desktop.modelo.pojo.Envio;
import desktop.modelo.pojo.Unidad;
import desktop.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author berna
 */
public class FXMLAdminEnvioController implements Initializable, INotificacionCambio {

    private ObservableList<Envio> envios;
    private Colaborador colaborador;

    @FXML
    private TextField tfBuscar;
    @FXML
    private TableColumn<Envio,String> tcNombre;

    @FXML
    private TableColumn<Envio,String> tcGuia;
    @FXML
    private TableColumn<Envio,String> tcCosto;
    @FXML
    private TableView<Envio> tvEnvio;
    @FXML
    private ComboBox<String> cbEnvio;
    @FXML
    private TableColumn<Envio,String> tcColaborador;
    @FXML
    private TableColumn<Envio,String> tcEstatus;
    @FXML
    private TableColumn<Envio,String> tcDireccionOrigen;
    @FXML
    private TableColumn<?, ?> tcDirreccionDestino;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        ObservableList<String> opcionesBusqueda = FXCollections.observableArrayList(
                "Numero Guia"
        );
        cbEnvio.setItems(opcionesBusqueda);
        cbEnvio.getSelectionModel().selectFirst();
    }

    private void configurarTabla() {
        tcColaborador.setCellValueFactory(new PropertyValueFactory<>("colaborador"));
        tcEstatus.setCellValueFactory(new PropertyValueFactory<>("estadoActual"));
        tcGuia.setCellValueFactory(new PropertyValueFactory<>("numeroGuia"));
        tcCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        tcDireccionOrigen.setCellValueFactory(new PropertyValueFactory<>("direccionDestino"));
        tcDirreccionDestino.setCellValueFactory(new PropertyValueFactory<>("direccionOrigen"));
    }

    private void cargarInformacionTabla() {
        envios = FXCollections.observableArrayList();
        List<Envio> listaWS = EnviosDAO.obtenerEnvio();
        if (listaWS != null) {
            envios.addAll(listaWS);
            tvEnvio.setItems(envios);
        } else {
            Utilidades.mostrarAlertaSimple("", "Datos no disponibles ", Alert.AlertType.ERROR);
        }
    }

    private void irPantallaEnvio(INotificacionCambio observador, Envio envio) {
        try {
            Stage escenario = new Stage();
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLEnvioRegistro.fxml"));
            Parent vista = cargador.load();
            FXMLEnvioRegistroController controlador = cargador.getController();
            if (envio != null) {
                controlador.inicializarValores(observador, envio);

            } else {
                controlador.inicializarValores(observador, null);

            }
            Scene escenarioFormualario = new Scene(vista);
            escenario.setScene(escenarioFormualario);
            escenario.setTitle("Pantalla actualizar envios");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("error", "error", Alert.AlertType.ERROR);
        }
    }

    private void irPantallaConductorEnvio(INotificacionCambio observador, Envio envio) {
        try {
            Stage escenario = new Stage();
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLAsignarCoductorEnvio.fxml"));
            Parent vista = cargador.load();
            FXMLAsignarCoductorEnvioController controlador = cargador.getController();
            controlador.inicializarValores(observador, envio);
            Scene escenarioFormualario = new Scene(vista);
            escenario.setScene(escenarioFormualario);
            escenario.setTitle("Pantalla asignar conductor a un envio");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("eror", "error", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnIrFormulario(ActionEvent event) throws IOException {
        irPantallaEnvio(this, null);
    }

    @Override
    public void notificar() {
        envios.clear();
        cargarInformacionTabla();
    }

    @FXML
    private void btnIrAsignarConductor(ActionEvent event) throws IOException {
        Envio envioSeleccionado = tvEnvio.getSelectionModel().getSelectedItem();
        if (envioSeleccionado != null) {
            irPantallaConductorEnvio(this, envioSeleccionado);
        } else {
            Utilidades.mostrarAlertaSimple("Envio", "Por favor selecciona un envio", Alert.AlertType.WARNING);
        }

    }

    @FXML
    private void btnBuscar(ActionEvent event) {
        String textoBusqueda = tfBuscar.getText();
        String criterioBusqueda = cbEnvio.getValue();

        envios = FXCollections.observableArrayList();

        if (textoBusqueda.isEmpty()) {
            cargarInformacionTabla();
        } else {
            if (criterioBusqueda != null) {
                switch (criterioBusqueda) {
                    case "Numero Guia":
                        List<Envio> envioPorGuia = EnviosDAO.buscarPorNumero(textoBusqueda);
                        if (envioPorGuia != null) {
                            envios.clear();
                            envios.addAll(envioPorGuia);
                            tvEnvio.setItems(envios);
                        }
                        break;
                }
            }
        }
        tvEnvio.setItems(envios);
    }

    void inicializarInformacion(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    @FXML
    private void btnIrFormularioEditar(ActionEvent event) {
        Envio envioSeleccionado = tvEnvio.getSelectionModel().getSelectedItem();
        if (envioSeleccionado != null) {
            irPantallaEnvio(this, envioSeleccionado);
        } else {
            Utilidades.mostrarAlertaSimple("Envio", "Por favor selecciona un envio", Alert.AlertType.WARNING);
        }
    }


}
