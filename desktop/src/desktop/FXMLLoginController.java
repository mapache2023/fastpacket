package desktop;

import desktop.modelo.pojo.Colaborador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import desktop.modelo.dao.LoginDAO;
import desktop.modelo.pojo.LoginColaborador;
import desktop.utilidades.Utilidades;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLLoginController {

    @FXML
    private TextField tfNumeroPersonal;

    @FXML
    private PasswordField tfContrasena;

    @FXML
    private Label lbErrorPersonal;

    @FXML
    private Label lbErrorPassword;


    @FXML
    void btnLogin(ActionEvent event) {

        String numPersonal = tfNumeroPersonal.getText();
        String password = tfContrasena.getText();
        lbErrorPersonal.setText("");  // Limpiar mensajes de error
        lbErrorPassword.setText("");

        boolean isValido = true;

        // Validar los campos de entrada
        if(numPersonal.isEmpty()){
            lbErrorPersonal.setText("El número de personal es obligatorio");
            isValido = false;
        }
        if(password.isEmpty()){
            lbErrorPassword.setText("La contraseña es obligatoria");
            isValido = false;
        }

        // Si los campos son válidos, intentar verificar las credenciales
        if(isValido){
            verificarCredenciales(numPersonal, password);
        }
    }

    private void verificarCredenciales(String numPersonal, String password){
        // Llamada al DAO para verificar las credenciales
        LoginColaborador respuesta = LoginDAO.iniciaSesion(numPersonal, password);

        if(!respuesta.getError()){
            // Si no hay error, comprobar si el colaborador es un administrador
            if(respuesta.getColaborador().getIdRol()== 1){
                Utilidades.mostrarAlertaSimple("Credenciales correctas",
                        respuesta.getMensaje(), Alert.AlertType.INFORMATION);
                irPantallaPrincipal(respuesta.getColaborador());
            } else {
                Utilidades.mostrarAlertaSimple("Error",
                        "Actualmente solo administradores pueden ingresar al sistema.",
                        Alert.AlertType.ERROR);
            }
        } else {
            // Si las credenciales son incorrectas, mostrar mensaje de error
            Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }


    private void irPantallaPrincipal(Colaborador colaborador){
        Stage stageActual = (Stage) tfNumeroPersonal.getScene().getWindow();
        try {
            // Cargar la vista de la pantalla principal
            FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLInicio.fxml"));
            Parent vista = loadMain.load();

            // Obtener el controlador de la pantalla principal y pasarle la información del colaborador
            FXMLInicioController controladorHome = loadMain.getController();
            controladorHome.inicializarInformacion(colaborador);

            // Cambiar la escena para mostrar la pantalla principal
            Scene escena = new Scene(vista);
            stageActual.setScene(escena);
            stageActual.setTitle("Home");
            stageActual.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
