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

    // Etiqueta para mostrar el nombre del colaborador
    @FXML
    private Label lColaborador;

    // ComboBox para seleccionar una unidad
    @FXML
    private ComboBox<Unidad> cbUnidad;

    // Etiqueta para mostrar mensajes de error
    @FXML
    private Label lMensajeError;

    // Botones para asignar o cancelar
    @FXML
    private Button btnAsignar;

    @FXML
    private Button btnCancelar;

    // Variables para almacenar el colaborador y el observador
    private Colaborador colaborador;
    private INotificacionCambio observador;

    /**
     * Funcion para inicializar la información del colaborador y cargar las unidades disponibles.
     * @param colaborador El colaborador que se va a asignar a una unidad.
     * @param observador El objeto que notificará cuando se realice un cambio.
     */
    public void inicializarInformacion(Colaborador colaborador, INotificacionCambio observador) {
        this.colaborador = colaborador;
        this.observador = observador;

        // Mostrar el nombre completo del colaborador
        lColaborador.setText(colaborador.getNombre() + " " + colaborador.getApellidoPaterno());

        // Cargar las unidades activas disponibles
        ObservableList<Unidad> unidadesDisponibles = FXCollections.observableArrayList(ColaboradorDAO.obtenerUnidadesActivas());

        // Verificar si hay unidades disponibles para asignar
        if (unidadesDisponibles.isEmpty()) {
            // Si no hay unidades disponibles, mostrar un mensaje de error
            Utilidades.mostrarAlertaSimple("Error", "No hay unidades disponibles para asignar", Alert.AlertType.ERROR);
            cerrarVentana();
        } else if (unidadesDisponibles.size() == 1 && unidadesDisponibles.get(0).getIdUnidad().equals(colaborador.getIdUnidad())) {
            // Si solo hay una unidad disponible y ya está asignada al colaborador, mostrar mensaje de información
            Utilidades.mostrarAlertaSimple("Información", "La única unidad disponible es la que ya tiene asignada el colaborador", Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            // Si hay unidades disponibles, cargarlas en el ComboBox
            cbUnidad.setItems(unidadesDisponibles);

            // Si el colaborador ya tiene una unidad asignada, seleccionarla en el ComboBox
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

    /**
     * Funcion que asigna la unidad seleccionada al colaborador.
     * Se llama cuando el usuario hace clic en el botón "Asignar".
     * @param event El evento de clic en el botón.
     */
    @FXML
    void asignarUnidad(ActionEvent event) {
        Mensaje mensaje;
        Unidad unidadSeleccionada = cbUnidad.getValue();

        // Verificar si se seleccionó una unidad
        if (unidadSeleccionada == null) {
            // Si no se seleccionó ninguna unidad, mostrar mensaje de error
            lMensajeError.setText("Por favor, selecciona una unidad.");
            return;
        }

        // Si el colaborador no tiene una unidad asignada, asignar la nueva unidad
        if (colaborador.getIdUnidad() == null) {
            colaborador.setIdUnidad(unidadSeleccionada.getIdUnidad());
            mensaje = ColaboradorDAO.asignarUnidadColaborador(colaborador);
        } else {
            // Si el colaborador ya tiene una unidad, cambiarla por la nueva unidad seleccionada
            colaborador.setIdUnidad(unidadSeleccionada.getIdUnidad());
            mensaje = ColaboradorDAO.cambiarUnidadColaborador(colaborador);
        }

        // Mostrar el mensaje correspondiente dependiendo del resultado de la asignación
        if (!mensaje.getError()) {
            Utilidades.mostrarAlertaSimple("Éxito", "Unidad asignada correctamente.", Alert.AlertType.INFORMATION);
            observador.notificar(); // Notificar a la vista principal de que hubo un cambio
            cerrarVentana(); // Cerrar la ventana actual
        } else {
            Utilidades.mostrarAlertaSimple("Error", mensaje.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Funcion que cierra la ventana cuando el usuario hace clic en el botón "Cancelar".
     * @param event El evento de clic en el botón.
     */
    @FXML
    void cancelar(ActionEvent event) {
        cerrarVentana(); // Cerrar la ventana
    }

    /**
     * Funcion que cierra la ventana actual.
     */
    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close(); // Cerrar la ventana
    }
}
