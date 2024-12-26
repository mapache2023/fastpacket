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
import desktop.modelo.pojo.Estatus;
import desktop.modelo.pojo.Mensaje;
import desktop.utilidades.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author berna
 */
public class FXMLAsignarCoductorEnvioController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private ObservableList<Colaborador> conductores;
        private INotificacionCambio observador;
    private Envio envio;
    @FXML
    private ComboBox<Colaborador> cbConductores;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTiposUnidades();
    } 
    
     private void cargarTiposUnidades(){
        conductores = FXCollections.observableArrayList();
        List<Colaborador> listaWS = UnidadDAO.obtenerTipoColaborador();
        if(listaWS != null){
            conductores.addAll(listaWS);
            cbConductores.setItems(conductores);
        }
    }

    @FXML
    private void btnAsignarConductor(ActionEvent event) {
          Mensaje mensaje;
        Colaborador colaboradorSeleccionada = cbConductores.getValue();

        // Verificar si se seleccionó una colaborador
        if (colaboradorSeleccionada == null) {
            // Si no se seleccionó ninguna colaborador, mostrar mensaje de error
          
            return;
        }

        // Si el colaborador no tiene una colaborador asignada, asignar la nueva colaborador
        if (envio.getIdColaborador() == null) {
            envio.setIdColaborador(colaboradorSeleccionada.getIdColaborador());
            mensaje = EnviosDAO.asignarColaborador(envio);
        } else {
            // Si el colaborador ya tiene una colaborador, cambiarla por la nueva colaborador seleccionada
            envio.setIdColaborador(colaboradorSeleccionada.getIdColaborador());
            mensaje = EnviosDAO.asignarColaborador(envio);
        }

        // Mostrar el mensaje correspondiente dependiendo del resultado de la asignación
        if (!mensaje.getError()) {
            Utilidades.mostrarAlertaSimple("Éxito", "Colaborador asignado correctamente.", Alert.AlertType.INFORMATION);
            observador.notificar(); // Notificar a la vista principal de que hubo un cambio
            cerrarVentana(); // Cerrar la ventana actual
        } else {
            Utilidades.mostrarAlertaSimple("Error", mensaje.getMensaje(), Alert.AlertType.ERROR);
        }
        }
    

    void inicializarValores(INotificacionCambio observador, Envio envio) {
        this.envio = envio;
        this.observador= observador;
          // Mostrar el rol del colaborador si está asignado
        if (envio.getIdColaborador() !=null) {
            for (Colaborador colaborador : cbConductores.getItems()) {
                if (colaborador.getIdColaborador() == envio.getIdColaborador()) {
                    cbConductores.setValue(colaborador);
                    break;
                }
            }
        }
        
    }
 private void cerrarVentana() {
       Stage stage = (Stage) cbConductores.getScene().getWindow();
        stage.close(); // Cerrar la ventana
    }

    void inicializarValores(Colaborador colaborador, FXMLAdminEnvioController aThis, Integer idEnvio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
