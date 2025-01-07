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
    private File imagenSeleccionada; 
    private Colaborador colaborador;
    private INotificacionCambio observador; 

   
    @FXML
    private TextField tfNp;
    @FXML
    private TextField tfCorreo; 
    @FXML
    private TextField tfAm;
    @FXML
    public Label lColaborador; 
    @FXML
    private TextField tfCurp;
    @FXML
    private ComboBox<Rol> cbRol;
    
    @FXML
    private TextField tfContrasena; 
  
    @FXML
    private TextField tfNombre; 
    @FXML
    private TextField tfAp; 
    @FXML
    public Button btnAccion; 
    @FXML
    private Pane pane; 
    @FXML
    private ImageView imagenperfil;
    @FXML
    private TextField tfNulic; 
    @FXML
    private Label lNuLic;


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
                c.setNumeroLicencia(tfNulic.getText()); 
            }

            if (cbRol.getValue() != null) {
                c.setIdRol(cbRol.getValue().getIdRol()); 
            }

          
            if(colaborador == null){
                guardarColaborador(c);
            }else{
                c.setIdColaborador(colaborador.getIdColaborador());
                editarColaborador(c);
            }
        }
    }

  
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
            mostrarFotografiaSeleccionada(imagenSeleccionada);
        }
    }

   
    @FXML
    private void btnSubirFotografia(ActionEvent event) {
        if (imagenSeleccionada != null) {
            cargarFotografiaServidor(imagenSeleccionada); // Enviar la imagen al servidor
        } else {
            Utilidades.mostrarAlertaSimple("Selecciona fotografía", "Para subir una fotografía del Colaborador debes seleccionarla previamente", Alert.AlertType.WARNING);
        }
    }

    
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

  
    private void mostrarFotografiaSeleccionada(File img) {
        try {
            BufferedImage buffer = ImageIO.read(img);
            Image image = SwingFXUtils.toFXImage(buffer, null);
            imagenperfil.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 
    private void obtenerImagenServicio() {
        Colaborador ColaboradorFoto = ColaboradorDAO.obtenerFotografiaColaborador(colaborador.getIdColaborador());

        if (ColaboradorFoto != null && ColaboradorFoto.getFotografiaBase64() != null && !ColaboradorFoto.getFotografiaBase64().isEmpty()) {
            mostrarFotografiaServidor(ColaboradorFoto.getFotografiaBase64());
        }
    }

    private void mostrarFotografiaServidor(String imgBase64) {
        byte[] foto = Base64.getDecoder().decode(imgBase64.replaceAll("\\n", ""));
        Image image = new Image(new ByteArrayInputStream(foto));
        imagenperfil.setImage(image);
    }

    
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

  
    private void editarColaborador(Colaborador colaborador) {
        if(validador()){
            Mensaje msj = ColaboradorDAO.editarColaborador(colaborador);
        if (!msj.getError()) {
            Utilidades.mostrarAlertaSimple("Colaborador editado", msj.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notificar();
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error al editar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
        }
        
    }

    
    private void cerrarVentana() {
        Stage escenario = (Stage) tfNombre.getScene().getWindow();
        escenario.close();
    }

    
    private void cargarRoles() {
        ObservableList<Rol> roles = FXCollections.observableArrayList();
        List<Rol> info = ColaboradorDAO.roles();
        roles.addAll(info);
        cbRol.setItems(roles);
    }

   
private Boolean validador() {
  
    if (tfCurp.getText().length() > 18) {
        Utilidades.mostrarAlertaSimple("Error", "El CURP excede el número de caracteres.", Alert.AlertType.ERROR);
        return false;
    }
    if (tfCurp.getText().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Error", "El CURP es obligatorio.", Alert.AlertType.ERROR);
        return false;
    }
    

    if (tfNombre.getText().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Error", "El nombre es obligatorio.", Alert.AlertType.ERROR);
        return false;
    }
    
    if (tfAp.getText().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Error", "El apellido paterno es obligatorio.", Alert.AlertType.ERROR);
        return false;
    }
    
    if (tfAm.getText().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Error", "El apellido materno es obligatorio.", Alert.AlertType.ERROR);
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


    if (cbRol.getValue() == null) {
        Utilidades.mostrarAlertaSimple("Error", "Debe seleccionar un rol.", Alert.AlertType.ERROR);
        return false;
    }


    if (cbRol.getValue() != null && cbRol.getValue().getNombre().equals("Conductor")) {
        if (tfNulic.getText().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Error", "El número de licencia es obligatorio para los conductores.", Alert.AlertType.ERROR);
            return false;
        }
    }

    return true;
}



private boolean validarCorreo(String correo) {
    String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    return correo.matches(regex);
}


 
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
