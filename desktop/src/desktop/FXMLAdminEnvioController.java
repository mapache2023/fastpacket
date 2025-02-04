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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author berna
 */
public class FXMLAdminEnvioController implements Initializable, INotificacionCambio {
    @FXML
    public Label etiquetaColaborador;
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
    private ComboBox<String> cbEnvio;
    @FXML
    private TableColumn<Envio,String> tcColaborador;
    @FXML
    private TableColumn<Envio,String> tcEstatus;
    @FXML
    private TableColumn<Envio,String> tcDireccionOrigen;
    @FXML
    private TableColumn<Envio,String> tcDirreccionDestino;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        ObservableList<String> opcionesBusqueda = FXCollections.observableArrayList(
                "Numero Guia"
        );
       // cbEnvio.setItems(opcionesBusqueda);
        //cbEnvio.getSelectionModel().selectFirst();
        //cbEnvio.setDisable(true);
    }
private void inicializarBusqueda(){
     ObservableList<Envio> lista = envios;
        if(lista != null){
            FilteredList<Envio> filtro = new FilteredList<>(lista, p -> true); 
            tfBuscar.textProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtro.setPredicate(busqueda ->{
                    
                        if(newValue == null || newValue.isEmpty()){
                            return true;                        
                        } 
                        String lowerFilter = newValue.toLowerCase();
                  
                        if(busqueda.getNumeroGuia().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        return false;
                    
                    });
                }
            });
            SortedList<Envio> listaOrdenados = new SortedList(filtro);
            listaOrdenados.comparatorProperty().bind(tvEnvio.comparatorProperty());
            tvEnvio.setItems(listaOrdenados);
        }
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
            inicializarBusqueda();
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
                controlador.inicializarValores(observador, envio,this.colaborador);

            } else {
                controlador.inicializarValores(observador, null,this.colaborador);

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

        String nombre = colaborador.getNombre() != null ? colaborador.getNombre() : "N/A";
        String apellidoPaterno = colaborador.getApellidoPaterno() != null ? colaborador.getApellidoPaterno() : "";
        etiquetaColaborador.setText( nombre + " " + apellidoPaterno);
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

    @FXML
    private void cambiarEstatus(ActionEvent event) {
        
         Envio envioSeleccionado = tvEnvio.getSelectionModel().getSelectedItem();
        if (envioSeleccionado != null) {
             try {
            Stage escenario = new Stage();
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLCambiarEstus.fxml"));
            Parent vista = cargador.load();
            FXMLCambiarEstusController controlador = cargador.getController();
            controlador.inicializarValores(this.colaborador,this,envioSeleccionado);
            Scene escenarioFormualario = new Scene(vista);
            escenario.setScene(escenarioFormualario);
            escenario.setTitle("Pantalla asignar conductor a un envio");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("error", "error", Alert.AlertType.ERROR);
        }
        } else {
            Utilidades.mostrarAlertaSimple("Envio", "Por favor selecciona un envio", Alert.AlertType.WARNING);
        }
          
        
    }


}
