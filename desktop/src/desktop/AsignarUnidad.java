package desktop;

import desktop.interfaz.INotificacionCambio;
import desktop.modelo.dao.ColaboradorDAO;
import desktop.modelo.pojo.Colaborador;
import desktop.modelo.pojo.Mensaje;
import desktop.modelo.pojo.Unidad;
import desktop.utilidades.Utilidades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class AsignarUnidad {


    @FXML
    private Label lColaborador;

    @FXML
    private ComboBox<Unidad> cbUnidad;



    
    @FXML
    private Button btnAsignar;



    
    private Colaborador colaborador;
    private INotificacionCambio observador;

   
    public void inicializarInformacion(Colaborador colaborador, INotificacionCambio observador) {
        this.colaborador = colaborador;
        this.observador = observador;



      
        ObservableList<Unidad> unidadesDisponibles = FXCollections.observableArrayList(ColaboradorDAO.obtenerUnidadesActivas());

        
        if (unidadesDisponibles.isEmpty()) {

            Utilidades.mostrarAlertaSimple("Error", "No hay unidades disponibles para asignar", Alert.AlertType.ERROR);
            cerrarVentana();
        } else if (unidadesDisponibles.size() == 1 && unidadesDisponibles.get(0).getIdUnidad().equals(colaborador.getIdUnidad())) {
           
            Utilidades.mostrarAlertaSimple("Información", "La única unidad disponible es la que ya tiene asignada el colaborador", Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
           
            cbUnidad.setItems(unidadesDisponibles);

         
            if (colaborador.getIdUnidad() != null) {
                for (Unidad unidad : cbUnidad.getItems()) {
                    if (unidad.getIdUnidad().equals(colaborador.getIdUnidad())) {
                        cbUnidad.setValue(unidad);
                        break;
                    }
                }
            }
        }
    }

  
    @FXML
    void asignarUnidad(ActionEvent event) {
        Mensaje mensaje;
        Unidad unidadSeleccionada = cbUnidad.getValue();

       
        if (unidadSeleccionada == null) {
         
      Utilidades.mostrarAlertaSimple("por fava","por favor selecione unidad", Alert.AlertType.WARNING);
            return;
        }

      
        if (colaborador.getIdUnidad() == null) {
            colaborador.setIdUnidad(unidadSeleccionada.getIdUnidad());
            mensaje = ColaboradorDAO.asignarUnidadColaborador(colaborador);
        } else {
           
            colaborador.setIdUnidad(unidadSeleccionada.getIdUnidad());
            mensaje = ColaboradorDAO.cambiarUnidadColaborador(colaborador);
        }

      
        if (!mensaje.getError()) {
            Utilidades.mostrarAlertaSimple("Éxito", "Unidad asignada correctamente.", Alert.AlertType.INFORMATION);
            observador.notificar(); 
            cerrarVentana(); 
        } else {
            Utilidades.mostrarAlertaSimple("Error", mensaje.getMensaje(), Alert.AlertType.ERROR);
        }
    }

  
    @FXML
    void cancelar(ActionEvent event) {
        cerrarVentana(); // Cerrar la ventana
    }


    private void cerrarVentana() {
        Stage stage = (Stage) btnAsignar.getScene().getWindow();
        stage.close(); // Cerrar la ventana
    }
}
