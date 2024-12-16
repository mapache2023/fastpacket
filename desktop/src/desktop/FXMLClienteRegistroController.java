package desktop;

import com.google.gson.Gson;
import desktop.modelo.ConexionWs;
import desktop.modelo.RespuestaHTTP;
import desktop.modelo.dao.ClienteDAO;
import desktop.modelo.pojo.Cliente;
import desktop.modelo.pojo.Colaborador;
import desktop.modelo.pojo.Mensaje;
import desktop.utilidades.Constantes;
import desktop.utilidades.Utilidades;
import java.net.HttpURLConnection;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FXMLClienteRegistroController implements Initializable {
    

    private Cliente clienteEdicion; // Cliente que estamos editando (si está en modo edición)
    private boolean modoEdicion = false; // Determina si estamos en modo edición o creación
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfColonia;
    @FXML
    private TextField tfCp;
    @FXML
    private Button btn_guardar;
    @FXML
    private TextField tfApellidoPaterno;
 @FXML
    private Text etiquetaColaborador;

    private Colaborador colaborador;
    private Cliente clienteGuardado; // Cliente agregado o editado

public Cliente getClienteGuardado() {
    return clienteGuardado;
}


@FXML
private void clickGuardarCliente(ActionEvent event) {
    System.out.println("Iniciando método clickGuardarCliente");

    // Obtener los valores de los campos
    String nombre = tfNombre.getText();
    System.out.println("Nombre ingresado: " + nombre);
    String apellidoPaterno = tfApellidoPaterno.getText();
    String apellidoMaterno = tfApellidoMaterno.getText();
    String calle = tfCalle.getText();
    String numero = tfNumero.getText();
    String codigoPostal = tfCp.getText();
    String colonia = tfColonia.getText();
    String telefono = tfTelefono.getText();
    String correo = tfEmail.getText();

    // Crear el objeto Cliente con los valores obtenidos
    Cliente cliente = new Cliente();
    cliente.setNombre(nombre);
    cliente.setApellidoPaterno(apellidoPaterno);
    cliente.setApellidoMaterno(apellidoMaterno);
    cliente.setCalle(calle);
    cliente.setNumero(numero);
    cliente.setCodigoPostal(codigoPostal);
    cliente.setColonia(colonia);
    cliente.setTelefono(telefono);
    cliente.setCorreo(correo);

    // Validar campos
    System.out.println("Validando campos...");
    if (sonCamposValidosCliente(cliente)) {
        System.out.println("Campos válidos. Procesando...");
        Mensaje mensaje = new Mensaje();
        
        if (!modoEdicion) {
            System.out.println("Guardando nuevo cliente...");

            // Llamar al método para registrar el cliente
            mensaje = ClienteDAO.registrarCliente(cliente);

            if (!mensaje.getError()) {
                clienteGuardado = cliente; // Asignar el cliente recién registrado
                // Si el registro fue exitoso, mostrar un mensaje de éxito
                Utilidades.mostrarAlertaSimple("Cliente registrado", 
                    "La información del cliente " + cliente.getNombre() + " se registró correctamente", 
                    Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                // Si hubo un error al registrar, mostrar el mensaje de error
                Utilidades.mostrarAlertaSimple("Error al guardar", mensaje.getMensaje(), Alert.AlertType.ERROR);
            }
        } else {
            System.out.println("Editando cliente existente...");
            cliente.setIdCliente(clienteEdicion.getIdCliente());

            // Llamar al método para editar el cliente
            mensaje = ClienteDAO.editarCliente(cliente);

            if (!mensaje.getError()) {
                // Si la edición fue exitosa, mostrar un mensaje de éxito
                Utilidades.mostrarAlertaSimple("Cliente editado", 
                    "La información del cliente " + cliente.getNombre() + " se editó correctamente", 
                    Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                // Si hubo un error al editar, mostrar el mensaje de error
                Utilidades.mostrarAlertaSimple("Error al editar", mensaje.getMensaje(), Alert.AlertType.ERROR);
            }
        }
    } else {
        System.out.println("Campos no válidos.");
        Utilidades.mostrarAlertaSimple("Error de validación", "Algunos campos tienen valores inválidos.", Alert.AlertType.ERROR);
    }
}



    private boolean sonCamposValidosCliente(Cliente cliente) {
        // Verificar que los campos no estén vacíos
        if (cliente.getNombre().isEmpty() || cliente.getApellidoPaterno().isEmpty() || 
            cliente.getApellidoMaterno().isEmpty() || cliente.getCalle().isEmpty() ||
            cliente.getNumero().isEmpty() || cliente.getCodigoPostal().isEmpty() || 
            cliente.getColonia().isEmpty() || cliente.getTelefono().isEmpty() || 
            cliente.getCorreo().isEmpty()) {

            Utilidades.mostrarAlertaSimple("Campos vacíos", "Por favor, llena todos los campos.", Alert.AlertType.WARNING);
            return false;
        }

        // Verificar que el correo tenga un formato válido
        if (!cliente.getCorreo().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            Utilidades.mostrarAlertaSimple("Correo inválido", "El formato del correo no es válido.", Alert.AlertType.WARNING);
            return false;
        }

        // Verificar que el teléfono tenga 10 dígitos
        if (!cliente.getTelefono().matches("\\d{10}")) {
            Utilidades.mostrarAlertaSimple("Teléfono inválido", "El teléfono debe tener 10 dígitos.", Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }

   private void guardarDatosCliente(Cliente cliente) {
    // Usar ClienteDAO para registrar el cliente
    Mensaje msj = ClienteDAO.registrarCliente(cliente);
    if (!msj.getError()) {
        // Si el registro fue exitoso, mostrar un mensaje de éxito
        Utilidades.mostrarAlertaSimple("Cliente registrado", 
            "La información del cliente " + cliente.getNombre() + " se registró correctamente", 
            Alert.AlertType.INFORMATION);
        cerrarVentana();
    } else {
        // Si hubo un error al registrar, mostrar el mensaje de error
        Utilidades.mostrarAlertaSimple("Error al guardar", msj.getMensaje(), Alert.AlertType.ERROR);
    }
}


   private void editarDatosCliente(Cliente cliente) {
    // Usar ClienteDAO para modificar los datos del cliente
    Mensaje msj = ClienteDAO.editarCliente(clienteEdicion);
    if (!msj.getError()) {
        // Si la actualización fue exitosa, mostrar un mensaje de éxito
        Utilidades.mostrarAlertaSimple("Cliente actualizado", 
            "La información del cliente " + cliente.getNombre() + " ha sido actualizada correctamente", 
            Alert.AlertType.INFORMATION);
        cerrarVentana();
    } else {
        // Si hubo un error al modificar, mostrar el mensaje de error
        Utilidades.mostrarAlertaSimple("Error al modificar", msj.getMensaje(), Alert.AlertType.ERROR);
    }
}

    private void cerrarVentana() {
        // Obtener la ventana (stage) actual y cerrarla
        Stage stage = (Stage) btn_guardar.getScene().getWindow();
        stage.close();
    }

    // Método para inicializar el controlador, puedes usarlo para configurar el modo de edición o cargar el cliente a editar
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Aquí puedes inicializar clienteEdicion y modoEdicion si estás en modo edición
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
        etiquetaColaborador.setText( nombre + " " + apellidoPaterno);
    }

    // Método para configurar el cliente a editar y activar el modo de edición
    public void setClienteEdicion(Cliente cliente) {
        this.clienteEdicion = cliente;
        this.modoEdicion = true;

        // Pre-llenar los campos con los datos del cliente a editar
        tfNombre.setText(cliente.getNombre());
        tfApellidoPaterno.setText(cliente.getApellidoPaterno());
        tfApellidoMaterno.setText(cliente.getApellidoMaterno());
        tfCalle.setText(cliente.getCalle());
        tfNumero.setText(cliente.getNumero());
        tfCp.setText(cliente.getCodigoPostal());
        tfTelefono.setText(cliente.getTelefono());
        tfEmail.setText(cliente.getCorreo());
        tfColonia.setText(cliente.getColonia());
    }

}