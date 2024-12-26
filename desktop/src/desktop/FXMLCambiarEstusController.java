/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop;

import desktop.interfaz.INotificacionCambio;
import desktop.modelo.dao.EnviosDAO;
import desktop.modelo.pojo.Colaborador;
import desktop.modelo.pojo.Envio;
import desktop.modelo.pojo.Estatus;
import desktop.modelo.pojo.Mensaje;
import desktop.utilidades.Utilidades;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class FXMLCambiarEstusController implements Initializable {

    @FXML
    private ComboBox<Estatus> cbEstatus;
    @FXML
    private Label lcolaborador;
    @FXML
    private Label lTiempo;
private INotificacionCambio observador;
private Colaborador colaborador;
  private Envio envio;   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String tiempo = LocalDateTime.now().format(formateador);
            lTiempo.setText("Hora y fecha actual: "+tiempo);
    }   
    

    @FXML
    private void btncambiar(ActionEvent event) {
        Mensaje m= new Mensaje();
        if (cbEstatus.getValue()!=null) {
        m=EnviosDAO.hacerCambios(colaborador.getIdColaborador(),envio.getIdEnvio() ,cbEstatus.getValue().getIdEstatus());            
            if (!m.getError()) {
                Utilidades.mostrarAlertaSimple("cambio", "se cambio el estado", Alert.AlertType.INFORMATION);
                                observador.notificar();
                                cerrarVentana();
                                
                                

            }else{
                Utilidades.mostrarAlertaSimple("no", "no se cambio el estatus", Alert.AlertType.WARNING);
            }
        }
        else{
            Utilidades.mostrarAlertaSimple("error", "seleccione un estado", Alert.AlertType.WARNING);
        }
    }

       private void cargarEstatus() {
        ObservableList<Estatus> estatus = FXCollections.observableArrayList();
        List<Estatus> info = EnviosDAO.obtenerEstatuss();
        estatus.addAll(info);
        cbEstatus.setItems(estatus);
    }

    void inicializarValores(Colaborador colaborador, INotificacionCambio observador, Envio envioSeleccionado) {
   this.colaborador= colaborador;
   this.observador= observador;
   this.envio = envioSeleccionado;
   lcolaborador.setText("Colaborador en session: "+this.colaborador.getNombre()+" "+this.colaborador.getApellidoPaterno()+" "+this.colaborador.getApellidoMaterno());
   cargarEstatus();

        if (envio.getIdEstatus() != null) {
            for (Estatus estatus : cbEstatus.getItems()) {
                if (estatus.getIdEstatus() == envio.getIdEstatus()) {
                    cbEstatus.setValue(estatus);
                    break;
                }
            }
        }
   
    }
        /**
     * Funcion para cerrar la ventana actual.
     */
    private void cerrarVentana() {
        Stage escenario = (Stage) lcolaborador.getScene().getWindow();
        escenario.close();
    }
}
