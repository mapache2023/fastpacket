/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop;

import desktop.modelo.dao.EnviosDAO;
import static desktop.modelo.dao.EnviosDAO.obtenerEnvios;
import desktop.modelo.dao.PaqueteDAO;
import desktop.modelo.pojo.Colaborador;
import desktop.modelo.pojo.Envio;
import desktop.modelo.pojo.Mensaje;
import desktop.modelo.pojo.Paquete;
import desktop.utilidades.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Carla
 */
public class FXMLPaqueteRegistroController implements Initializable {

    @FXML
    private Text etiquetaColaborador;

    private Colaborador colaborador;
   
    private boolean modoEdicion = false; 
    private Paquete paqueteEdicion; 
  
    @FXML
    private TextField tfDescripcion;
    @FXML
    private TextField tfPeso;
    @FXML
    private TextField tfAltura;
    @FXML
    private TextField tfAncho;
    @FXML
    private TextField tfProfundidad;
  
     @FXML  
    private Button btnGuardar;
    @FXML
    private ComboBox<Envio> cbEnvios;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarCombo();
    }

  public void inicializarCombo() {
    // Obtener la lista de envíos desde el servicio web
    List<Envio> envios = obtenerEnvios();

    // Verificar que la lista no sea nula ni vacía
    if (envios != null && !envios.isEmpty()) {
        // Imprimir los envíos para verificar que los objetos tienen los valores correctos
        for (Envio envio : envios) {
            System.out.println("Envio ID: " + envio.getIdEnvio());
        }

        // Crear una lista observable de los envíos
        ObservableList<Envio> envioOptions = FXCollections.observableArrayList(envios);

        // Asignar la lista observable al ComboBox
        cbEnvios.setItems(envioOptions);

        // Establecer un convertidor para mostrar solo el ID del envío en el ComboBox
        cbEnvios.setCellFactory(param -> new ListCell<Envio>() {
            @Override
            protected void updateItem(Envio envio, boolean empty) {
                super.updateItem(envio, empty);
                if (envio != null && !empty) {
                    // Mostrar solo el ID del envío
                    setText("Número de Guía: " + envio.getNumeroGuia());
                } else {
                    setText(null);
                }
            }
        });

        // Establecer cómo se debe mostrar el valor seleccionado
        cbEnvios.setButtonCell(new ListCell<Envio>() {
            @Override
            protected void updateItem(Envio envio, boolean empty) {
                super.updateItem(envio, empty);
                if (envio != null) {
                    // Mostrar solo el ID del envío
                    setText("Número de Guía: " + envio.getNumeroGuia());
                } else {
                    setText(null);
                }
            }
        });

        // Establecer el comportamiento cuando se selecciona un elemento
        cbEnvios.setOnAction(event -> {
            Envio selectedEnvio = cbEnvios.getSelectionModel().getSelectedItem();
            if (selectedEnvio != null) {
                // Obtener el idEnvio del elemento seleccionado
                int idEnvio = selectedEnvio.getIdEnvio();
                System.out.println("ID seleccionado: " + idEnvio);
            }
        });
    } else {
        // En caso de que no haya envíos disponibles, mostrar un mensaje o manejar el error
        Utilidades.mostrarAlertaSimple("Error", "No se pudieron cargar los envíos.", Alert.AlertType.ERROR);
    }
}


    public void inicializarInformacion(Colaborador colaborador) {
        this.colaborador = colaborador;
        if (colaborador != null) {
            String nombre = colaborador.getNombre() != null ? colaborador.getNombre() : "N/A";
            String apellidoPaterno = colaborador.getApellidoPaterno() != null ? colaborador.getApellidoPaterno() : "";
            etiquetaColaborador.setText(nombre + " " + apellidoPaterno);
        } else {
            etiquetaColaborador.setText("Colaborador no especificado.");
        }
    }

    private void guardarDatosPaquete(Paquete paquete) {
        // Usar PaqueteDAO para registrar el paquete
        Mensaje msj = PaqueteDAO.registrarPaquete(paquete);
        if (!msj.getError()) {
            // Si el registro fue exitoso, mostrar un mensaje de éxito
            Utilidades.mostrarAlertaSimple("Paquete registrado",
                    "La información del paquete " + paquete.getDescripcion() + " se registró correctamente",
                    Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            // Si hubo un error al registrar, mostrar el mensaje de error
            Utilidades.mostrarAlertaSimple("Error al guardar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editarDatosPaquete(Paquete paquete) {
        // Usar PaqueteDAO para modificar los datos del paquete
        Mensaje msj = PaqueteDAO.actualizarPaquete(paquete);
        if (!msj.getError()) {
            // Si la actualización fue exitosa, mostrar un mensaje de éxito
            Utilidades.mostrarAlertaSimple("Paquete actualizado",
                    "La información del paquete " + paquete.getDescripcion() + " ha sido actualizada correctamente",
                    Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            // Si hubo un error al modificar, mostrar el mensaje de error
            Utilidades.mostrarAlertaSimple("Error al modificar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private boolean sonCamposValidosPaquete(Paquete paquete) {
        // Verificar que los campos no estén vacíos
        if (paquete.getDescripcion().isEmpty() || paquete.getAlto().isEmpty()
                || paquete.getAncho().isEmpty() || paquete.getPeso().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "Por favor, llena todos los campos.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void cerrarVentana() {
        // Obtener la ventana (stage) actual y cerrarla
        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();
    }
  private int idEnvio;  // Variable interna para almacenar el ID de Envio
    
  void PaqueteEdicion(Paquete paquete) {
    this.paqueteEdicion = paquete;
    this.modoEdicion = true;

    // Pre-llenar los campos con los datos del paquete a editar
    tfDescripcion.setText(paquete.getDescripcion());
    tfAltura.setText(paquete.getAlto());
    tfAncho.setText(paquete.getAncho());
    tfPeso.setText(paquete.getPeso());
    tfProfundidad.setText(paquete.getProfundidad());

    // Asignar el idEnvio al Paquete
    this.idEnvio = paquete.getIdEnvio();

    // Deshabilitar el ComboBox para evitar cambios en el Envio
    cbEnvios.setDisable(true);

    // Si ya existe un Envio en el paquete, preseleccionar el combo con ese Envio
    for (Envio envio : cbEnvios.getItems()) {
        if (envio.getIdEnvio() == this.idEnvio) {
            cbEnvios.getSelectionModel().select(envio);
            break;
        }
    }
}

@FXML
private void clickGuardarPaquete(ActionEvent event) {
    System.out.println("Iniciando método clickGuardarPaquete");

    // Obtener los valores de los campos
    String descripcion = tfDescripcion.getText();
    String altura = tfAltura.getText();
    String ancho = tfAncho.getText();
    String peso = tfPeso.getText();
    String profundidad = tfProfundidad.getText();

    // Validar el idEnvio en modo agregar
    if (cbEnvios.getSelectionModel().getSelectedItem() == null) {
        Utilidades.mostrarAlertaSimple("Error de selección", "Debe seleccionar un Envio válido.", Alert.AlertType.ERROR);
        return; // Detener el proceso si no se selecciona un Envio
    }
    int idEnvio = cbEnvios.getSelectionModel().getSelectedItem().getIdEnvio();

    // Crear el objeto Paquete con los valores obtenidos
    Paquete paquete = new Paquete();
    paquete.setDescripcion(descripcion);
    paquete.setAlto(altura);
    paquete.setAncho(ancho);
    paquete.setPeso(peso);
    paquete.setProfundidad(profundidad);
    paquete.setIdEnvio(idEnvio); // Asignar el idEnvio seleccionado

    // Si estamos en modo edición, actualizar el Paquete existente
    if (modoEdicion && paqueteEdicion != null) {
        paquete.setIdPaquete(paqueteEdicion.getIdPaquete()); // Mantener el idPaquete para la edición
    }

    // Validar campos
    System.out.println("Validando campos...");
    if (sonCamposValidosPaquete(paquete)) {
        System.out.println("Campos válidos. Procesando...");
        if (modoEdicion) {
            editarDatosPaquete(paquete); // Actualizar el paquete si estamos en modo edición
        } else {
            guardarDatosPaquete(paquete); // Guardar el nuevo paquete si estamos en modo creación
        }
    } else {
        System.out.println("Campos no válidos.");
        Utilidades.mostrarAlertaSimple("Error de validación", "Algunos campos tienen valores inválidos.", Alert.AlertType.ERROR);
    }
}

}
