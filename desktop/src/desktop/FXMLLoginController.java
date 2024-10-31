package desktop;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import desktop.modelo.dao.LoginDAO;
import desktop.modelo.pojo.LoginColaborador;
import desktop.utilidades.Utilidades;
import javafx.event.ActionEvent;
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
        lbErrorPersonal.setText("");
        lbErrorPassword.setText("");
        boolean isValido = true;
        if(numPersonal.isEmpty()){
            lbErrorPersonal.setText("El número de personal es obligatorio");
            isValido = false;
        }
        if(password.isEmpty()){
            lbErrorPassword.setText("La contraseña es obligatoria");
            isValido = false;
        }
        if(isValido){
            verificarCredenciales(numPersonal, password);
        }
    }

     private void verificarCredenciales(String numPersonal, String password){
        LoginColaborador respuesta = 
                LoginDAO.iniciaSesion(numPersonal, password);
        
        if(respuesta.getError() == false){
            Utilidades.mostrarAlertaSimple("Credenciales correctas", 
                    respuesta.getMensaje(), Alert.AlertType.INFORMATION);
        }else{
            Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), 
                    Alert.AlertType.ERROR);
        }
    }

}
