/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop;

import desktop.interfaz.INotificacionCambio;
import desktop.modelo.dao.ClienteDAO;
import desktop.modelo.dao.ColaboradorDAO;
import desktop.modelo.dao.EnviosDAO;
import desktop.modelo.pojo.*;
import desktop.utilidades.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author berna
 */
public class FXMLEnvioRegistroController implements Initializable {
    private Colaborador colaborador;
    @FXML
    public Label etiquetaColaborador;
    @FXML
    private TextField tfCalleOrigen;
    @FXML
    private TextField tfNumeroOrigen;
    @FXML
    private TextField tfcoloniaOrigen;
    @FXML
    private TextField tfCodigoPOrigen;
    @FXML
    private TextField tfCiudadOrigen;
    @FXML
    private TextField tfEstadoOrigen;
    @FXML
    private TextField tfCalleDestino;
    @FXML
    private TextField tfnumeroDestino;
    @FXML
    private TextField tfcoloniaDestino;
    @FXML
    private TextField tfCodigoPDestino;
    @FXML
    private TextField tfciudadDestino;
    @FXML
    private TextField tfEstadoDestino;
    @FXML
    private TextField tfnuemroGuia;
    @FXML
    private TextField tfCosto;
    @FXML
    private ComboBox<Cliente> cbCliente;
    private INotificacionCambio observador;
    private Envio envio;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnEditar;

  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
        
    }

    @FXML
    private void guardar(ActionEvent event) {
        if (Validador()) {
            Envio envio = new Envio();
            envio.setNumeroGuia(tfnuemroGuia.getText());
            envio.setCalleOrigen(tfCalleOrigen.getText());
            envio.setNumeroOrigen(tfNumeroOrigen.getText());
            envio.setColoniaOrigen(tfcoloniaOrigen.getText());
            envio.setCodigoPostalOrigen(tfCodigoPOrigen.getText());
            envio.setCiudadOrigen(tfCiudadOrigen.getText());
            envio.setEstadoOrigen(tfEstadoOrigen.getText());
            envio.setCalleDestino(tfCalleDestino.getText());
            envio.setNumeroDestino(tfnumeroDestino.getText());
            envio.setColoniaDestino(tfcoloniaDestino.getText());
            envio.setCodigoPostalDestino(tfCodigoPDestino.getText());
            envio.setCiudadDestino(tfciudadDestino.getText());
            envio.setEstadoDestino(tfEstadoDestino.getText());
            envio.setCosto(tfCosto.getText());
            if (cbCliente.getValue() != null) {
                envio.setIdCliente(cbCliente.getValue().getIdCliente()); 
            }
guardarEnvio(envio);
        } 
    }

    private void guardarEnvio(Envio envio) {
        Mensaje msj = EnviosDAO.registrarEnvio(envio);
        if (!msj.getError()) {
            Utilidades.mostrarAlertaSimple("Colaborador registrado", msj.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notificar();
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        if (envio != null) {
            if(Validador()){
             
            envio.setCalleOrigen(tfCalleOrigen.getText());
            envio.setNumeroOrigen(tfNumeroOrigen.getText());
            envio.setColoniaOrigen(tfcoloniaOrigen.getText());
            envio.setCodigoPostalOrigen(tfCodigoPOrigen.getText());
            envio.setCiudadOrigen(tfCiudadOrigen.getText());
            envio.setEstadoOrigen(tfEstadoOrigen.getText());
            envio.setCalleDestino(tfCalleDestino.getText());
            envio.setNumeroDestino(tfnumeroDestino.getText());
            envio.setColoniaDestino(tfcoloniaDestino.getText());
            envio.setCodigoPostalDestino(tfCodigoPDestino.getText());
            envio.setCiudadDestino(tfciudadDestino.getText());
            envio.setEstadoDestino(tfEstadoDestino.getText());
            envio.setCosto(tfCosto.getText());
            if (cbCliente.getValue() != null) {
                envio.setIdCliente(cbCliente.getValue().getIdCliente());
            }
            editarEnvio(envio);   
            }
        } else {
            Utilidades.mostrarAlertaSimple("Error", "No hay un envío cargado para editar.", AlertType.ERROR);
        }
    }

    private void editarEnvio(Envio envio) {
        Mensaje msj = EnviosDAO.editarEnvio(envio);
        if (!msj.getError()) {
            Utilidades.mostrarAlertaSimple("Envio editado", msj.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notificar();
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error al editar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

   
    private void cerrarVentana() {
        Stage escenario = (Stage) tfNumeroOrigen.getScene().getWindow();
        escenario.close();
    }


    private boolean Validador() {
 
        if (tfnuemroGuia.getText().isEmpty() ||
            tfCalleOrigen.getText().isEmpty() ||
            tfNumeroOrigen.getText().isEmpty() ||
            tfCodigoPOrigen.getText().isEmpty() ||
            tfciudadDestino.getText().isEmpty() ||
            tfCosto.getText().isEmpty() ||
            cbCliente.getValue() == null) {
            Utilidades.mostrarAlertaSimple("Error", "Por favor, completa todos los campos.", AlertType.ERROR);
            return false;
        }
        if(!esNumero(tfNumeroOrigen.getText()) || !esNumero(tfnumeroDestino.getText())){
            Utilidades.mostrarAlertaSimple("Error", "El Numero de direccion  debe ser un número válido.", AlertType.ERROR);
            return false;
        }
     
        if (!esNumero(tfCosto.getText())) {
            Utilidades.mostrarAlertaSimple("Error", "El costo debe ser un número válido.", AlertType.ERROR);
            return false;
        }

        
        if (!esNumero(tfCodigoPOrigen.getText()) || !esNumero(tfCodigoPDestino.getText())) {
            Utilidades.mostrarAlertaSimple("Error", "Los códigos postales deben ser números enteros.", AlertType.ERROR);
            return false;
        }

        return true;
    }

    void inicializarValores(INotificacionCambio observador, Envio envio, Colaborador colaborador) {
        this.observador = observador;
cargarCliente();
        if (envio != null) {
            this.envio = envio;
            tfnuemroGuia.setText(envio.getNumeroGuia());
            tfCalleOrigen.setText(envio.getCalleOrigen());
            tfNumeroOrigen.setText(envio.getNumeroOrigen());
            tfcoloniaOrigen.setText(envio.getColoniaOrigen());
            tfCodigoPOrigen.setText(envio.getCodigoPostalOrigen());
            tfCiudadOrigen.setText(envio.getCiudadOrigen());
            tfEstadoOrigen.setText(envio.getEstadoOrigen());
            tfCalleDestino.setText(envio.getCalleDestino());
            tfnumeroDestino.setText(envio.getNumeroDestino());
            tfcoloniaDestino.setText(envio.getColoniaDestino());
            tfCodigoPDestino.setText(envio.getCodigoPostalDestino());
            tfciudadDestino.setText(envio.getCiudadDestino());
            tfEstadoDestino.setText(envio.getEstadoDestino());
            tfCosto.setText(String.valueOf(envio.getCosto()));
            if (envio.getIdCliente() != 0) {
                for (Cliente cliente : cbCliente.getItems()) {
                    if (cliente.getIdCliente() == envio.getIdCliente()) {
                        cbCliente.setValue(cliente);
                        break;
                    }
                }
            }
            btnGuardar.setVisible(false);
            tfnuemroGuia.setDisable(true);
        }
        else {
            btnEditar.setVisible(false);
        }
        this.colaborador = colaborador;

        String nombre = colaborador.getNombre() != null ? colaborador.getNombre() : "N/A";
        String apellidoPaterno = colaborador.getApellidoPaterno() != null ? colaborador.getApellidoPaterno() : "";
        etiquetaColaborador.setText( nombre + " " + apellidoPaterno);
    }

    private void cargarCliente() {
        ObservableList<Cliente> clientes = FXCollections.observableArrayList();
        List<Cliente> info = ClienteDAO.obtenerClientes();
        clientes.addAll(info);
        cbCliente.setItems(clientes);
    }
 private boolean esNumero(String texto) {
        try {
            Double.parseDouble(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }   
    }

