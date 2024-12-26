package desktop;

import desktop.interfaz.INotificacionCambio;
import desktop.modelo.dao.ColaboradorDAO;
import desktop.modelo.pojo.Colaborador;
import desktop.modelo.pojo.Mensaje;
import desktop.modelo.pojo.Rol;
import desktop.utilidades.Utilidades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLFormularioColaboradorController implements Initializable {
    private File imagenSeleccionada; // Variable para la imagen seleccionada
    private Colaborador colaborador; // El colaborador que estamos editando o registrando
    private INotificacionCambio observador; // Notificador para actualizar la vista principal

    // Definición de campos de entrada en la interfaz gráfica
    @FXML
    private TextField tfNp; // Número personal
    @FXML
    private TextField tfCorreo; // Correo electrónico
    @FXML
    private TextField tfAm; // Apellido Materno
    @FXML
    public Label lColaborador; // Etiqueta para mostrar el nombre del colaborador
    @FXML
    private TextField tfCurp; // CURP
    @FXML
    private ComboBox<Rol> cbRol; // ComboBox para seleccionar el rol del colaborador
    @FXML
    private TextField tfContrasena; // Contraseña
    @FXML
    private TextField tfNombre; // Nombre del colaborador
    @FXML
    private TextField tfAp; // Apellido Paterno
    @FXML
    public Button btnAccion; // Botón de acción para guardar o editar
    @FXML
    private Pane pane; // Panel que contiene los campos de información del colaborador
    @FXML
    private ImageView imagenperfil; // Imagen de perfil del colaborador
    @FXML
    private TextField tfNulic; // Número de licencia
    @FXML
    private Label lNuLic; // Etiqueta de "Número de licencia"

    /**
     * Funcion que guarda o edita la información del colaborador.
     * Se ejecuta cuando el usuario hace clic en el botón "Guardar".
     * @param event El evento de clic en el botón
     */
    @FXML
    void guardar(ActionEvent event) {
        if(validador()){  // Verificamos si los campos son válidos
            Colaborador c = new Colaborador();
            c.setNombre(tfNombre.getText());
            c.setApellidoPaterno(tfAp.getText());
            c.setApellidoMaterno(tfAm.getText());
            c.setCorreo(tfCorreo.getText());
            c.setCurp(tfCurp.getText());
            c.setContrasena(tfContrasena.getText());
            c.setNumeroPersonal(tfNp.getText());

            if(!tfNulic.getText().isEmpty()){
                c.setNumeroLicencia(tfNulic.getText());  // Asignar número de licencia si existe
            }

            if (cbRol.getValue() != null) {
                c.setIdRol(cbRol.getValue().getIdRol()); // Solo se guarda el ID del rol
            }

            // Si colaborador es null, estamos registrando uno nuevo, de lo contrario, estamos editando
            if(colaborador == null){
                guardarColaborador(c);
            }else{
                c.setIdColaborador(colaborador.getIdColaborador());
                editarColaborador(c);
            }
        }
    }

    /**
     * Funcion que permite seleccionar una imagen de perfil.
     * @param event El evento de clic en el botón de "Seleccionar Foto"
     */
    @FXML
    private void btnSeleccionarFoto(ActionEvent event) {
        FileChooser dialogoSeleccionImg = new FileChooser();
        dialogoSeleccionImg.setTitle("Selecciona una imagen");

        // Configurar el filtro para archivos de imagen
        FileChooser.ExtensionFilter filtroImg = new FileChooser.ExtensionFilter("Archivos de imagen (*.png, *jpg, *jpeg)", "*.PNG", "*.JPG", "*.JPEG");
        dialogoSeleccionImg.getExtensionFilters().add(filtroImg);

        Stage stageActual = (Stage) tfNombre.getScene().getWindow();
        imagenSeleccionada = dialogoSeleccionImg.showOpenDialog(stageActual);

        if (imagenSeleccionada != null) {
            mostrarFotografiaSeleccionada(imagenSeleccionada); // Mostrar la imagen seleccionada
        }
    }

    /**
     * Funcion que sube la imagen de perfil al servidor.
     * @param event El evento de clic en el botón de "Subir Foto"
     */
    @FXML
    private void btnSubirFotografia(ActionEvent event) {
        if (imagenSeleccionada != null) {
            cargarFotografiaServidor(imagenSeleccionada); // Enviar la imagen al servidor
        } else {
            Utilidades.mostrarAlertaSimple("Selecciona fotografía", "Para subir una fotografía del Colaborador debes seleccionarla previamente", Alert.AlertType.WARNING);
        }
    }

    /**
     * Funcion para cargar la fotografía del colaborador al servidor.
     * @param imagen La imagen seleccionada
     */
    private void cargarFotografiaServidor(File imagen) {
        try {
            byte[] imgBytes = Files.readAllBytes(imagen.toPath()); // Leer los bytes de la imagen
            Mensaje msj = ColaboradorDAO.subirFotografiaColaborador(colaborador.getIdColaborador(), imgBytes);

            if (!msj.getError()) {
                Utilidades.mostrarAlertaSimple("Fotografía enviada", msj.getMensaje(), Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlertaSimple("Error", msj.getMensaje(), Alert.AlertType.ERROR);
            }
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error", "Error: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Funcion que muestra la imagen seleccionada en el ImageView.
     * @param img La imagen seleccionada
     */
    private void mostrarFotografiaSeleccionada(File img) {
        try {
            BufferedImage buffer = ImageIO.read(img);
            Image image = SwingFXUtils.toFXImage(buffer, null);
            imagenperfil.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Funcion para obtener la fotografía desde el servidor y mostrarla.
     */
    private void obtenerImagenServicio() {
        Colaborador ColaboradorFoto = ColaboradorDAO.obtenerFotografiaColaborador(colaborador.getIdColaborador());

        if (ColaboradorFoto != null && ColaboradorFoto.getFotografiaBase64() != null && !ColaboradorFoto.getFotografiaBase64().isEmpty()) {
            mostrarFotografiaServidor(ColaboradorFoto.getFotografiaBase64());
        }
    }

    /**
     * Funcion para mostrar la fotografía del servidor (en formato Base64).
     * @param imgBase64 La imagen en formato Base64
     */
    private void mostrarFotografiaServidor(String imgBase64) {
        byte[] foto = Base64.getDecoder().decode(imgBase64.replaceAll("\\n", ""));
        Image image = new Image(new ByteArrayInputStream(foto));
        imagenperfil.setImage(image);
    }

    /**
     * Funcion para cargar los datos del colaborador en la interfaz gráfica.
     */
    private void cargarDatos() {
        tfNombre.setText(colaborador.getNombre());
        tfAp.setText(colaborador.getApellidoPaterno());
        tfAm.setText(colaborador.getApellidoMaterno());
        tfCorreo.setText(colaborador.getCorreo());
        tfCurp.setText(colaborador.getCurp());
        tfContrasena.setText(colaborador.getContrasena());
        tfNp.setText(colaborador.getNumeroPersonal());
        if(colaborador.getNumeroLicencia() != null){
            tfNulic.setText(colaborador.getNumeroLicencia());
        }

        // Mostrar el rol del colaborador si está asignado
        if (colaborador.getIdRol() != 0) {
            for (Rol rol : cbRol.getItems()) {
                if (rol.getIdRol() == colaborador.getIdRol()) {
                    cbRol.setValue(rol);
                    break;
                }
            }
        }

        pane.setVisible(true);
        obtenerImagenServicio();
    }

    /**
     * Funcion que inicializa la información del colaborador (para editar) o configura la vista para registrar uno nuevo.
     * @param colaborador El colaborador que se va a editar o registrar
     * @param observador El objeto que notificará a la vista principal
     */
    public void inicializaInformacion(Colaborador colaborador, INotificacionCambio observador) {
        this.colaborador = colaborador;
        this.observador = observador;
        cargarRoles();
        tfNulic.setDisable(true);
        if (colaborador != null) {
            cargarDatos();
            cbRol.setDisable(true);
            tfNp.setDisable(true);
        } else {
            pane.setVisible(false);
        }
    }

    /**
     * Funcion que guarda un nuevo colaborador en la base de datos.
     * @param colaborador El colaborador a registrar
     */
    private void guardarColaborador(Colaborador colaborador) {
        Mensaje msj = ColaboradorDAO.registrarColaborador(colaborador);
        if (!msj.getError()) {
            Utilidades.mostrarAlertaSimple("Colaborador registrado", msj.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notificar();
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Funcion que edita la información de un colaborador existente.
     * @param colaborador El colaborador a editar
     */
    private void editarColaborador(Colaborador colaborador) {
        Mensaje msj = ColaboradorDAO.editarColaborador(colaborador);
        if (!msj.getError()) {
            Utilidades.mostrarAlertaSimple("Colaborador editado", msj.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notificar();
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error al editar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Funcion para cerrar la ventana actual.
     */
    private void cerrarVentana() {
        Stage escenario = (Stage) tfNombre.getScene().getWindow();
        escenario.close();
    }

    /**
     * Funcion que carga los roles desde la base de datos y los muestra en el ComboBox.
     */
    private void cargarRoles() {
        ObservableList<Rol> roles = FXCollections.observableArrayList();
        List<Rol> info = ColaboradorDAO.roles();
        roles.addAll(info);
        cbRol.setItems(roles);
    }

    /**
     * Funcion que valida los campos antes de guardar.
     * Verifica si el número de licencia es obligatorio para los conductores.
     * @return true si la validación pasa, false en caso contrario
     */
  private Boolean validador() {
    // Verificar que todos los campos obligatorios estén llenos
    if (tfNombre.getText().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Error", "El nombre es obligatorio.", Alert.AlertType.ERROR);
        return false;
    }
    
    if (tfAp.getText().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Error", "El apellido paterno es obligatorio.", Alert.AlertType.ERROR);
        return false;
    }
    
    if (tfCorreo.getText().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Error", "El correo electrónico es obligatorio.", Alert.AlertType.ERROR);
        return false;
    }

    if (!validarCorreo(tfCorreo.getText())) {
        Utilidades.mostrarAlertaSimple("Error", "El correo electrónico no tiene un formato válido.", Alert.AlertType.ERROR);
        return false;
    }
    
    if (tfNp.getText().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Error", "El número de personal es obligatorio.", Alert.AlertType.ERROR);
        return false;
    }
    
    if (tfContrasena.getText().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Error", "La contraseña es obligatoria.", Alert.AlertType.ERROR);
        return false;
    }

    // Si el rol es "Conductor", verificar que el número de licencia esté presente
    if (cbRol.getValue() != null && cbRol.getValue().getNombre().equals("Conductor")) {
        if (tfNulic.getText().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Error", "El número de licencia es obligatorio para los conductores.", Alert.AlertType.ERROR);
            return false;
        }
    }

    if(tfAm.getText().isEmpty()){
        Utilidades.mostrarAlertaSimple("Error","El Apellido es obligatorio", Alert.AlertType.ERROR);
        return false;
    }
    if(tfCurp.getText().isEmpty()){
        Utilidades.mostrarAlertaSimple("Error","El curp es obligatorio", Alert.AlertType.ERROR);
        return false;
    }
    return true;
}

/**
 * Método auxiliar para validar el formato del correo electrónico.
 * @param correo El correo a validar
 * @return true si el correo tiene un formato válido, false en caso contrario
 */
private boolean validarCorreo(String correo) {
    String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    return correo.matches(regex);
}


    /**
     * Funcion de inicialización para configurar el ComboBox de roles.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfNulic.setDisable(false);
                    lNuLic.setDisable(false); // Mostrar el campo "Número de Licencia"
        // Listener para mostrar u ocultar el campo de "Número de Licencia" dependiendo del rol seleccionado
        cbRol.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.getNombre().equals("Conductor")) {
                    tfNulic.setDisable(false);
                    lNuLic.setDisable(false); // Mostrar el campo "Número de Licencia"
                } else {
                    tfNulic.setDisable(true);
                    lNuLic.setDisable(true); // Ocultar el campo "Número de Licencia"
                }
            }
        });
    }
}
