package desktop;

import desktop.modelo.dao.ClienteDAO;
import desktop.modelo.pojo.Cliente;
import desktop.modelo.pojo.Colaborador;
import desktop.modelo.pojo.Mensaje;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLListaClienteController implements Initializable {

    private ObservableList<Cliente> listaClientes;

    @FXML
    private TableColumn<Cliente, String> tcNombre;
    @FXML
    private TableColumn<Cliente, String> tcApellidoP;
    @FXML
    private TableColumn<Cliente, String> tcApellidoM;
    @FXML
    private TableColumn<Cliente, String> tcCorreo;
    @FXML
    private TableColumn<Cliente, String> tcTelefono;
    @FXML
    private TableColumn<Cliente, String> tcNumero;
    @FXML
    private TableColumn<Cliente, String> tcColonia;
    @FXML
    private TableColumn<Cliente, String> tcCodigoP;

    @FXML
    private TextField tfBusqueda;  // Campo de texto para la búsqueda
    @FXML
    private TableView<Cliente> tvCliente;
    @FXML
    private Label etiquetaColaborador;
    private Colaborador colaborador;

   private void inicializarBusqueda(){
        if(listaClientes != null){
            FilteredList<Cliente> filtroCliente = new FilteredList<>(listaClientes, p -> true); //Filtro, se le manda un observable list y un predicado, el cual determina qué se hará dependiedo si este es true o false
            tfBusqueda.textProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroCliente.setPredicate(busqueda ->{
                        //Si no hay nada en el tf (es vacío), mete todo los valores
                        if(newValue == null || newValue.isEmpty()){
                            return true;                        
                        } 
                        String lowerFilter = newValue.toLowerCase();
                        //-- Reglas de filtrado --\\
                        //Por nombre
                        if(busqueda.getNombre().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        //Por userName
                        if(busqueda.getTelefono().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        //correo
                        if(busqueda.getCorreo().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        return false;
                    
                    });
                }
            });//Modifica las porpiedades de un TF, para saber qué se escribe caracter por caracter y poder hacer algo con respecto a esto
            SortedList<Cliente> clientesOrdenados = new SortedList(filtroCliente);
            clientesOrdenados.comparatorProperty().bind(tvCliente.comparatorProperty());
            tvCliente.setItems(clientesOrdenados);
        }
    }

    // Método para agregar un nuevo cliente
    void agregar(ActionEvent evento) {
        abrirFormulario(null);  // Crear un nuevo cliente
    }

    // Método para editar un cliente seleccionado
    void editar(ActionEvent evento) {
        Cliente clienteSeleccionado = tvCliente.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            abrirFormulario(clienteSeleccionado);  // Editar el cliente seleccionado
        } else {
            mostrarAlertaSimple("Advertencia", "Por favor selecciona un cliente para editar.", Alert.AlertType.WARNING);
        }
    }

    // Método para eliminar un cliente seleccionado
@FXML
private void eliminarCliente(ActionEvent evento) {
    // Obtener el cliente seleccionado de la tabla
    Cliente clienteSeleccionado = tvCliente.getSelectionModel().getSelectedItem();

    if (clienteSeleccionado != null) {
        // Crear la alerta de confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estás seguro de que deseas eliminar a este cliente?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");

        // Mostrar los botones "Sí" y "No"
        Optional<ButtonType> resultado = confirmacion.showAndWait();

        // Si el usuario confirma la eliminación
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // Llamar al método eliminarCliente de tu DAO pasando el idCliente
            Mensaje mensaje = ClienteDAO.eliminarCliente(clienteSeleccionado.getIdCliente());

            // Si la eliminación fue exitosa
            if (mensaje != null && !mensaje.getError()) {
                mostrarAlertaSimple("Éxito", mensaje.getMensaje(), Alert.AlertType.INFORMATION);
                // Recargar la tabla después de eliminar el cliente
                llenarTabla(); // Asegúrate de que este método recargue la lista de clientes
                inicializarBusqueda();
            } else {
                // Si ocurrió un error al eliminar
                mostrarAlertaSimple("Error", mensaje.getMensaje(), Alert.AlertType.ERROR);
            }
        }
    } else {
        // Si no hay un cliente seleccionado, mostrar advertencia
        mostrarAlertaSimple("Advertencia", "Por favor selecciona un cliente para eliminar.", Alert.AlertType.WARNING);
    }
}


    // Método para llenar la tabla con los clientes
    private void llenarTabla() {
        List<Cliente> clientes = ClienteDAO.obtenerClientes(); // Llama al método en tu DAO
        if (clientes != null) {
            listaClientes = FXCollections.observableArrayList(clientes);
            tvCliente.setItems(listaClientes);
        } else {
            mostrarAlertaSimple("Error", "No se pudieron cargar los clientes. Inténtelo más tarde.", Alert.AlertType.ERROR);
        }
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
    // Método para abrir el formulario de cliente para agregar o editar
   private void abrirFormulario(Cliente cliente) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLClienteRegistro.fxml"));
        Parent root = loader.load();
        FXMLClienteRegistroController controlador = loader.getController();

        // Pasar el colaborador al formulario
        if (this.colaborador != null) {
            controlador.inicializarInformacion(this.colaborador);
        } else {
            System.out.println("Advertencia: El colaborador es null. No se pasará información.");
        }

        // Pasar el cliente para edición si corresponde
        if (cliente != null) {
            controlador.setClienteEdicion(cliente);
        }

        // Configurar la nueva escena
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(cliente != null ? "Editar Cliente" : "Agregar Cliente");
        stage.initModality(Modality.APPLICATION_MODAL);

        // Mostrar y esperar el cierre de la ventana
        stage.showAndWait();

        // Recargar la tabla al cerrar el formulario
        llenarTabla();
        inicializarBusqueda();

    } catch (IOException e) {
        System.err.println("Error al cargar el formulario de cliente: " + e.getMessage());
        e.printStackTrace();
    }
}


    // Método para mostrar una alerta simple
   private void mostrarAlertaSimple(String titulo, String contenido, Alert.AlertType tipo) {
    Alert alerta = new Alert(tipo);
    alerta.setTitle(titulo);
    alerta.setContentText(contenido);
    alerta.showAndWait();
}

    // Método de inicialización del controlador
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //llenarTabla();  // Llenar la tabla al inicio
        configurarTabla();
        llenarTabla();
        inicializarBusqueda();
        
    }

    // Configuración de la tabla
    private void configurarTabla() {
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcApellidoP.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        tcApellidoM.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        tcCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tcColonia.setCellValueFactory(new PropertyValueFactory<>("colonia"));
        tcCodigoP.setCellValueFactory(new PropertyValueFactory<>("codigoPostal"));
    }
    @FXML
private void editarCliente(ActionEvent evento) {
    Cliente clienteSeleccionado = tvCliente.getSelectionModel().getSelectedItem();
    if (clienteSeleccionado != null) {
        abrirFormulario(clienteSeleccionado); // Llama a abrirFormulario para pasar el cliente seleccionado
    } else {
        mostrarAlertaSimple("Advertencia", "Por favor selecciona un cliente para editar.", Alert.AlertType.WARNING);
    }
}

@FXML
void irAClienteRegistro(ActionEvent evento) {
    abrirFormulario(null);
}

}