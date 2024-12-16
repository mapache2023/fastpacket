package desktop;

import desktop.modelo.pojo.Colaborador;
import desktop.modelo.pojo.Paquete;
import desktop.modelo.dao.PaqueteDAO;
import desktop.modelo.pojo.Mensaje;
import static desktop.utilidades.Utilidades.mostrarAlertaSimple;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLListaPaqueteController {

    private ObservableList<Paquete> listaPaquetes;
    private TextField txtBuscarPaquete;
    @FXML
    private Label etiquetaColaborador;
 
    private Colaborador colaborador;
    @FXML
    private TextField tfBusqueda;
    @FXML
    private Button btnEditar;

    private TableView<Paquete> tablaPaquetes;
    
    @FXML
    private TableColumn<Paquete, String> tcDescripcion;
    
    @FXML
    private TableColumn<Paquete, String> tcPeso;
    
    @FXML
    private TableColumn<Paquete, String> tcAlto;
    
    @FXML
    private TableView<Paquete> tvPaquete;
    @FXML
    private TableColumn<Paquete, String> tcAncho;
    @FXML
    private TableColumn<Paquete, String> tcProfundidad;
    
  public void initialize() {
        // Configurar las columnas de la tabla con los tipos de datos correspondientes
        tcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tcPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        tcAlto.setCellValueFactory(new PropertyValueFactory<>("alto"));
        tcAncho.setCellValueFactory(new PropertyValueFactory<>("ancho"));
        tcProfundidad.setCellValueFactory(new PropertyValueFactory<>("profundidad"));
         
         // Cargar los paquetes de la base de datos (esta función debe implementarse)
        cargarPaquetes();
        inicializarBusqueda();
    }


// Método para cargar todos los paquetes desde la base de datos
 // Método para cargar todos los paquetes desde la base de datos
    private void cargarPaquetes() {
        // Obtener los paquetes desde el DAO
        List<Paquete> paquetes = PaqueteDAO.obtenerPaquetes();  // Ajusta el nombre de la clase según sea necesario
        

        // Verificar que la lista no esté vacía o nula
        if (paquetes != null && !paquetes.isEmpty()) {
            // Crear un ObservableList para la tabla
            listaPaquetes = FXCollections.observableArrayList(paquetes);
            
            // Asignar la lista a la tabla
            tvPaquete.setItems(listaPaquetes);
            inicializarBusqueda();
        } else {
            // Si no se encuentran paquetes, mostrar una alerta
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No hay paquetes");
            alert.setHeaderText(null);
            alert.setContentText("No se encontraron paquetes para mostrar.");
            alert.showAndWait();
        }
    }

private void inicializarBusqueda(){
        if(listaPaquetes != null){
            FilteredList<Paquete> filtroPaquetes = new FilteredList<>(listaPaquetes, p -> true); //Filtro, se le manda un observable list y un predicado, el cual determina qué se hará dependiedo si este es true o false
            tfBusqueda.textProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroPaquetes.setPredicate(busqueda ->{
                        //Si no hay nada en el tf (es vacío), mete todo los valores
                        if(newValue == null || newValue.isEmpty()){
                            return true;                        
                        } 
                        String lowerFilter = newValue.toLowerCase();
                        //-- Reglas de filtrado --\\
                        //Por Descripcion
                        if(busqueda.getDescripcion().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        return false;
                    
                    });
                }
            });//Modifica las porpiedades de un TF, para saber qué se escribe caracter por caracter y poder hacer algo con respecto a esto
            SortedList<Paquete> paquetesOrdenados = new SortedList(filtroPaquetes);
            paquetesOrdenados.comparatorProperty().bind(tvPaquete.comparatorProperty());
            tvPaquete.setItems(paquetesOrdenados);
        }
    }
 
    // Método para registrar un nuevo paquete
@FXML
public void registrarPaquete(ActionEvent event) {
    // Cargar la nueva pantalla "PaqueteRegistro.fxml"
    try {
        // Cargar el FXML de la pantalla de registro de paquetes
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPaqueteRegistro.fxml"));
        Parent root = loader.load();
        FXMLPaqueteRegistroController controladorVista = loader.getController();
            if (this.colaborador != null) {
                controladorVista.inicializarInformacion(this.colaborador);
            } else {
                System.out.println("Advertencia: El colaborador es null. No se pasará información.");
            }

        
        // Obtener el Stage actual
        Stage stage = (Stage) btnEditar.getScene().getWindow(); // Suponiendo que el botón tiene el ID registrarPaqueteButton
        
        // Establecer la nueva escena
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); // Mostrar la nueva pantalla
        
    } catch (IOException e) {
        e.printStackTrace(); // Manejo de excepciones si ocurre un error al cargar el FXML
    }
}

@FXML
private void abrirFormularioPaquete(ActionEvent event) {
    // Obtener el paquete seleccionado de la tabla
    Paquete paqueteSeleccionado = tvPaquete.getSelectionModel().getSelectedItem();

    if (paqueteSeleccionado != null) {
        try {
            // Cargar el FXML del formulario
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPaqueteRegistro.fxml"));
            Parent root = loader.load();
            FXMLPaqueteRegistroController controlador = loader.getController();

        // Pasar el colaborador al formulario
        if (this.colaborador != null) {
            controlador.inicializarInformacion(this.colaborador);
        } else {
            System.out.println("Advertencia: El colaborador es null. No se pasará información.");
        }

            // Obtener el controlador del formulario

            // Pasar la información del paquete seleccionado al formulario
            controlador.PaqueteEdicion(paqueteSeleccionado);

            // Crear una nueva ventana para el formulario
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Editar Paquete");
            stage.show();

            // Aquí cambiamos el nombre de la variable en setOnHiding para evitar el conflicto
            stage.setOnHiding(e -> cargarPaquetes()); // Recargar la lista de paquetes cuando se cierre el formulario
            inicializarBusqueda();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir el formulario: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    } else {
        // Mostrar advertencia si no hay paquete seleccionado
        mostrarAlerta("Advertencia", "Por favor selecciona un paquete de la tabla.", Alert.AlertType.WARNING);
    }
}
    
    // Método para mostrar alertas
  private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
    Platform.runLater(() -> {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    });
}

    public void inicializarInformacion(Colaborador colaborador) {
        if (colaborador == null) {
            System.err.println("Error: El colaborador es null.");
            etiquetaColaborador.setText("Bienvenido, Usuario");
            return;
        }

        this.colaborador = colaborador;

        String nombre = colaborador.getNombre() != null ? colaborador.getNombre() : "N/A";
        String apellidoPaterno = colaborador.getApellidoPaterno() != null ? colaborador.getApellidoPaterno() : "";
        etiquetaColaborador.setText(nombre + " " + apellidoPaterno);
    }
    @FXML
private void eliminarPaquete(ActionEvent evento) {
    try {
        // Obtener el paquete seleccionado de la tabla
        Paquete paqueteSeleccionado = tvPaquete.getSelectionModel().getSelectedItem();

        if (paqueteSeleccionado != null) {
            // Crear la alerta de confirmación
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Estás seguro de que deseas eliminar este paquete?");
            confirmacion.setContentText("Esta acción no se puede deshacer.");

            // Mostrar los botones "Sí" y "No"
            Optional<ButtonType> resultado = confirmacion.showAndWait();

            // Si el usuario confirma la eliminación
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                // Obtener los IDs de envio y paquete desde el paquete seleccionado
                int idEnvio = paqueteSeleccionado.getIdEnvio();
                int idPaquete = paqueteSeleccionado.getIdPaquete();

                // Llamar al método eliminarPaquete con los IDs correspondientes
                Mensaje mensaje = PaqueteDAO.eliminarPaquete(idEnvio, idPaquete);

                // Validar la respuesta del servidor
                if (mensaje != null) {
                    if (!mensaje.getError()) {
                        // Mostrar mensaje de éxito
                        Platform.runLater(() -> {
                            mostrarAlerta("Éxito", mensaje.getMensaje(), Alert.AlertType.INFORMATION);
                            cargarPaquetes(); // Recargar la tabla después de eliminar el paquete
                                        inicializarBusqueda();

                        });
                    } else {
                        // Mostrar mensaje de error
                        Platform.runLater(() -> mostrarAlerta("Error", mensaje.getMensaje(), Alert.AlertType.ERROR));
                    }
                } else {
                    // Manejar caso donde la respuesta es nula
                    Platform.runLater(() -> mostrarAlerta("Error", "No se recibió respuesta del servidor.", Alert.AlertType.ERROR));
                }
            }
        } else {
            // Si no hay un paquete seleccionado, mostrar advertencia
            Platform.runLater(() -> mostrarAlerta("Advertencia", "Por favor selecciona un paquete para eliminar.", Alert.AlertType.WARNING));
        }
    } catch (Exception e) {
        // Manejar cualquier excepción inesperada
        Platform.runLater(() -> mostrarAlerta("Error", "Ocurrió un error inesperado: " + e.getMessage(), Alert.AlertType.ERROR));
        e.printStackTrace();
    }
}

 @FXML
private void cambiarPantallaRegistro(ActionEvent event) {
     try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPaqueteRegistro.fxml"));
        Parent root = loader.load();
        FXMLPaqueteRegistroController controlador = loader.getController();
        
        if (this.colaborador != null) {
            controlador.inicializarInformacion(this.colaborador);
        } else {
            System.out.println("Advertencia: El colaborador es null. No se pasará información.");
        }

        // Abrir una nueva ventana para agregar un paquete
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Agregar Paquete");
        
        // Agregar un evento para recargar los paquetes cuando se cierre la ventana
        stage.setOnHiding(e -> cargarPaquetes());
                    inicializarBusqueda();

        
        stage.showAndWait();
    } catch (IOException e) {
        e.printStackTrace();
        mostrarAlertaSimple("Error", "No se pudo abrir el formulario para agregar el paquete.", Alert.AlertType.ERROR);
    }
}
   
}