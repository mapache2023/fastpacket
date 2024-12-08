package desktop;

import desktop.modelo.pojo.Colaborador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLInicioController {

    // Instancia del colaborador actual (para mostrar datos en la interfaz)
    private Colaborador colaborador;

    // Etiqueta para mostrar el nombre del colaborador
    @FXML
    private Label etiquetaColaborador;

    // Botón para cerrar sesión
    @FXML
    private Button botonCerrarSesion;

    /**
     * Funcion que se ejecuta al presionar el botón de cerrar sesión.
     * Cierra la ventana actual y redirige a la pantalla de inicio de sesión.
     */
    @FXML
    void cerrarSesion(ActionEvent evento) {
        // Obtener la ventana actual y cerrarla
        Stage ventanaActual = (Stage) botonCerrarSesion.getScene().getWindow();
        ventanaActual.close();

        // Redirigir a la pantalla de inicio de sesión
        try {
            // Cargar el archivo FXML de inicio de sesión
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLLogin.fxml"));
            Parent raiz = cargador.load();
            Scene escena = new Scene(raiz);
            Stage ventanaLogin = new Stage();
            ventanaLogin.setScene(escena);
            ventanaLogin.setTitle("Inicio de sesión");
            ventanaLogin.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Funcion que se ejecuta al presionar el botón para ir a la vista de colaboradores.
     * Carga la ventana de administración de colaboradores.
     */
    @FXML
    void irAcolab(ActionEvent evento) {
        try {
            // Cargar la vista de administración de colaboradores
            FXMLLoader cargadorVista = new FXMLLoader(getClass().getResource("FXMLAdminCololaborador.fxml"));
            Parent vista = cargadorVista.load();

            // Obtener el controlador de la vista cargada y pasarle el colaborador
            FXMLAdminCololaboradorController controladorVista = cargadorVista.getController();
            controladorVista.inicializarInformacion(this.colaborador);  // Pasar el colaborador a la vista

            // Crear la escena y la ventana para la administración de colaboradores
            Scene escenaAdmin = new Scene(vista);
            Stage ventanaAdmin = new Stage();
            ventanaAdmin.setScene(escenaAdmin);
            ventanaAdmin.setTitle("Colaboradores");
            ventanaAdmin.initModality(Modality.APPLICATION_MODAL);  // Abrir la ventana de manera modal
            ventanaAdmin.showAndWait();  // Mostrar la ventana y esperar a que se cierre

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Funcion que inicializa la información del colaborador en la interfaz.
     * @param colaborador El colaborador que se mostrará en la interfaz.
     */
    public void inicializarInformacion(Colaborador colaborador) {
        this.colaborador = colaborador;
        // Mostrar el nombre completo del colaborador en la interfaz
        etiquetaColaborador.setText("Bienvenido, " + colaborador.getNombre() + " " + colaborador.getApellidoPaterno());
    }
}
