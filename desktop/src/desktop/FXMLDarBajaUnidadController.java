/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop;

import desktop.interfaz.INotificacionCambio;
import desktop.modelo.dao.UnidadDAO;
import desktop.modelo.pojo.Mensaje;
import desktop.modelo.pojo.Unidad;
import desktop.utilidades.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author berna
 */
public class FXMLDarBajaUnidadController implements Initializable {

    @FXML
    private TextArea taMotivo;

    private Unidad unidad;
    private INotificacionCambio observador;
    private Integer idColaborador;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    

    @FXML
    private void btnGuardar(ActionEvent event) {
        Mensaje m;
        if(taMotivo.getText().isEmpty()){
            Utilidades.mostrarAlertaSimple("motivo obligatorio","el motivo es obligatorio", Alert.AlertType.WARNING);
        }
        else{
           Unidad unidad=new Unidad();
           unidad.setIdUnidad(this.unidad.getIdUnidad());
           unidad.setMotivo(taMotivo.getText());
           m=UnidadDAO.darBaja(unidad);
           if(!m.getError()){
              Utilidades.mostrarAlertaSimple("Mensajje","Se dio de baja con exito", Alert.AlertType.INFORMATION);
               observador.notificar();
               cerrarVentana();

           }
        }

    }


    private void cerrarVentana() {
        Stage escenario = (Stage) taMotivo.getScene().getWindow();
        escenario.close();
    }


    public void inicializarValores(INotificacionCambio observador, Unidad unidad) {
        this.unidad = unidad;
        this.observador= observador;
    }
}
