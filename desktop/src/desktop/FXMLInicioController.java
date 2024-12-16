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
import javafx.scene.control.Alert;

public class FXMLInicioController {

    private Colaborador colaborador;

    @FXML
    private Label etiquetaColaborador;

    @FXML
    private Button botonCerrarSesion;

    @FXML
    void cerrarSesion(ActionEvent evento) {
        Stage ventanaActual = (Stage) botonCerrarSesion.getScene().getWindow();
        ventanaActual.close();

        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLLogin.fxml"));
            Parent raiz = cargador.load();
            Scene escena = new Scene(raiz);
            Stage ventanaLogin = new Stage();
            ventanaLogin.setScene(escena);
            ventanaLogin.setTitle("Inicio de sesión");
            ventanaLogin.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la pantalla de inicio de sesión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void irAcolab(ActionEvent evento) {
        try {
            FXMLLoader cargadorVista = new FXMLLoader(getClass().getResource("FXMLAdminCololaborador.fxml"));
            Parent vista = cargadorVista.load();

            FXMLAdminCololaboradorController controladorVista = cargadorVista.getController();
            if (this.colaborador != null) {
                controladorVista.inicializarInformacion(this.colaborador);
            } else {
                System.out.println("Advertencia: El colaborador es null. No se pasará información.");
            }

            Scene escenaAdmin = new Scene(vista);
            Stage ventanaAdmin = new Stage();
            ventanaAdmin.setScene(escenaAdmin);
            ventanaAdmin.setTitle("Colaboradores");
            ventanaAdmin.initModality(Modality.APPLICATION_MODAL);
            ventanaAdmin.showAndWait();

        } catch (IOException e) {
            System.err.println("Error al cargar la vista de colaboradores: " + e.getMessage());
            e.printStackTrace();
        }
    }

  public void inicializarInformacion(Colaborador colaborador) {
    if (colaborador == null) {
        etiquetaColaborador.setText("Bienvenido, Usuario");
        return;
    }

    this.colaborador = colaborador;

    // Actualizar la etiqueta con el nombre del colaborador
    etiquetaColaborador.setText(formatearNombreColaborador(colaborador));
}

// Método auxiliar para formatear el nombre
private String formatearNombreColaborador(Colaborador colaborador) {
    String nombre = colaborador.getNombre() != null ? colaborador.getNombre() : "N/A";
    String apellidoPaterno = colaborador.getApellidoPaterno() != null ? colaborador.getApellidoPaterno() : "";
    return "Bienvenido, " + nombre + " " + apellidoPaterno;
}

    @FXML
void irACliente(ActionEvent evento) {
     try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLListaCliente.fxml"));
        Parent root = loader.load();
        FXMLListaClienteController controladorVista = loader.getController();
            if (this.colaborador != null) {
                controladorVista.inicializarInformacion(this.colaborador);
            } else {
                System.out.println("Advertencia: El colaborador es null. No se pasará información.");
            }


        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Lista de Clientes");
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        mostrarAlerta("Error", "No se pudo abrir la ventana de Lista de Clientes.", Alert.AlertType.ERROR);
    }
}
// Método auxiliar para mostrar alertas en caso de error
private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
    Alert alerta = new Alert(tipo);
    alerta.setTitle(titulo);
    alerta.setContentText(contenido);
    alerta.showAndWait();
}
  
@FXML
void irAPaquete(ActionEvent evento) {
    try {
        // Cargar la nueva vista
        FXMLLoader cargadorVista = new FXMLLoader(getClass().getResource("FXMLListaPaquete.fxml"));
        Parent vista = cargadorVista.load();
        

        // Obtener el controlador de la nueva vista (opcional si necesitas pasar datos)
       FXMLListaPaqueteController controladorVista = cargadorVista.getController();
            if (this.colaborador != null) {
                controladorVista.inicializarInformacion(this.colaborador);
            } else {
                System.out.println("Advertencia: El colaborador es null. No se pasará información.");
            }
        // Configurar la nueva escena
        Scene escenaPaquete = new Scene(vista);
        Stage ventanaPaquete = new Stage();
        ventanaPaquete.setScene(escenaPaquete);
        ventanaPaquete.setTitle("Registro de Paquete");
        ventanaPaquete.initModality(Modality.APPLICATION_MODAL); // Para que sea modal
        ventanaPaquete.showAndWait();

    } catch (IOException e) {
        System.err.println("Error al cargar la vista de registro de paquete: " + e.getMessage());
        e.printStackTrace();
    }
}
    @FXML
    private void irEnvios(ActionEvent event) {
        try {
            FXMLLoader cargadorVista = new FXMLLoader(getClass().getResource("FXMLAdminEnvio.fxml"));
            Parent vista = cargadorVista.load();

            // Obtener el controlador de la vista cargada y pasarle el colaborador
            FXMLAdminEnvioController controladorVista = cargadorVista.getController();
            controladorVista.inicializarInformacion(this.colaborador);

            // Crear la escena y la ventana para la administración de colaboradores
            Scene escenaAdmin = new Scene(vista);
            Stage ventanaAdmin = new Stage();
            ventanaAdmin.setScene(escenaAdmin);
            ventanaAdmin.setTitle("Envios");
            ventanaAdmin.initModality(Modality.APPLICATION_MODAL);  // Abrir la ventana de manera modal
            ventanaAdmin.showAndWait();  // Mostrar la ventana y esperar a que se cierre
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void irUnidades(ActionEvent event) {
        try {
            FXMLLoader cargadorVista = new FXMLLoader(getClass().getResource("FXMLAdminUnidad.fxml"));
            Parent vista = cargadorVista.load();

            // Obtener el controlador de la vista cargada y pasarle el colaborador
            //     FXMLAdminUnidadController controladorVista = cargadorVista.getController();
            //     controladorVista.inicializarInformacion(this.unidad);

            // Crear la escena y la ventana para la administración de colaboradores
            Scene escenaAdmin = new Scene(vista);
            Stage ventanaAdmin = new Stage();
            ventanaAdmin.setScene(escenaAdmin);
            ventanaAdmin.setTitle("Unidad");
            ventanaAdmin.initModality(Modality.APPLICATION_MODAL);  // Abrir la ventana de manera modal
            ventanaAdmin.showAndWait();  // Mostrar la ventana y esperar a que se cierre
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

