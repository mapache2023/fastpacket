package desktop;

import desktop.interfaz.INotificacionCambio;
import desktop.utilidades.Utilidades;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import desktop.modelo.dao.ColaboradorDAO;
import desktop.modelo.pojo.Colaborador;
import desktop.modelo.pojo.Mensaje;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static desktop.utilidades.Utilidades.mostrarAlertaConfirmacion;

public class FXMLAdminCololaboradorController implements Initializable, INotificacionCambio {

    private Colaborador colaborador;

    // Componentes de la interfaz
    @FXML private TableColumn<?, ?> tcNumeroPersonal;
    @FXML private TableColumn<?, ?> tcRol;
    @FXML private TableColumn<?, ?> tcCorreo;
    @FXML private ComboBox<String> cbBusqueda;
    @FXML private TableColumn<?, ?> tcNombre;
    @FXML private TableColumn<?, ?> tcNulic;
    @FXML private TextField tfBusquda;
    @FXML private TableColumn<?, ?> tcCurp;
    @FXML private TableColumn<?, ?> tcUnidad;
    @FXML private TableColumn<?, ?> tcApellidoP;
    @FXML private Label lColaborador;
    @FXML private TableColumn<?, ?> tcApellidoM;
    @FXML private TableView<Colaborador> tvColaborador;

    private ObservableList<Colaborador> listaColaboradores;

    // Configuración de las columnas de la tabla
    private void configurarTabla() {
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcApellidoP.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        tcApellidoM.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        tcCurp.setCellValueFactory(new PropertyValueFactory<>("curp"));
        tcNumeroPersonal.setCellValueFactory(new PropertyValueFactory<>("numeroPersonal"));
        tcRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        tcUnidad.setCellValueFactory(new PropertyValueFactory<>("unidad"));
        tcNulic.setCellValueFactory(new PropertyValueFactory<>("numeroLicencia"));
        tcCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
    }

    // Funcion para buscar colaboradores
    @FXML
    void buscar(ActionEvent evento) {
        String textoBusqueda = tfBusquda.getText();
        String criterioBusqueda = cbBusqueda.getValue();

        // Limpiar los resultados anteriores
        listaColaboradores = FXCollections.observableArrayList();

        if (textoBusqueda.isEmpty()) {
            // Si no hay texto, mostrar todos los colaboradores
            llenarTabla();
        } else {
            // Realizar la búsqueda según el criterio seleccionado
            if (criterioBusqueda != null) {
                switch (criterioBusqueda) {
                    case "Número Personal":
                        Colaborador colaboradorPorNumero = ColaboradorDAO.buscarPorNumeroPersonal(textoBusqueda);
                        if (colaboradorPorNumero != null) {
                            listaColaboradores.clear();
                            listaColaboradores.add(colaboradorPorNumero);
                            tvColaborador.setItems(listaColaboradores);
                        }
                        break;
                    case "Nombre":
                        listaColaboradores.clear();
                        listaColaboradores.addAll(ColaboradorDAO.buscarPorNombre(textoBusqueda));
                        tvColaborador.setItems(listaColaboradores);
                        break;
                    case "Rol":
                        listaColaboradores.clear();
                        listaColaboradores.addAll(ColaboradorDAO.buscarPorRol(textoBusqueda));
                        tvColaborador.setItems(listaColaboradores);
                        break;
                }
            }
        }

        // Actualizar la TableView con los resultados de la búsqueda
        tvColaborador.setItems(listaColaboradores);
    }

    // Funcion para agregar un nuevo colaborador
    @FXML
    void agregar(ActionEvent evento) {
        abrirFormulario(null);  // Pasamos null para crear un nuevo colaborador
    }

    // Funcion para editar un colaborador seleccionado
    @FXML
    void editar(ActionEvent evento) {
        Colaborador colaboradorSeleccionado = tvColaborador.getSelectionModel().getSelectedItem();
        if (colaboradorSeleccionado != null) {
            abrirFormulario(colaboradorSeleccionado);  // Pasamos el colaborador para editarlo
        } else {
            Utilidades.mostrarAlertaSimple("Colaborador", "Por favor selecciona un colaborador", Alert.AlertType.WARNING);
        }
    }

    // Funcion para eliminar un colaborador
    @FXML
    void eliminar(ActionEvent evento) {
        Colaborador colaboradorSeleccionado = tvColaborador.getSelectionModel().getSelectedItem();

        if (colaboradorSeleccionado != null) {
            // Mostrar un mensaje de confirmación antes de eliminar
            Optional<ButtonType> resultado = mostrarAlertaConfirmacion(
                    "Confirmar eliminación",
                    "¿Estás seguro de que deseas eliminar a este colaborador?"
            );

            // Si el usuario confirma, proceder con la eliminación
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                Mensaje mensaje = ColaboradorDAO.eliminarColaborador(colaboradorSeleccionado.getIdColaborador());
                if (!mensaje.getError()) {
                    listaColaboradores.clear();
                    llenarTabla();
                    Utilidades.mostrarAlertaSimple("Éxito", mensaje.getMensaje(), Alert.AlertType.CONFIRMATION);
                } else {
                    Utilidades.mostrarAlertaSimple("Error", mensaje.getMensaje(), Alert.AlertType.ERROR);
                }
            } else {
                Utilidades.mostrarAlertaSimple("Cancelación", "La eliminación ha sido cancelada", Alert.AlertType.INFORMATION);
            }
        } else {
            Utilidades.mostrarAlertaSimple("Colaborador", "Por favor selecciona un colaborador para eliminar", Alert.AlertType.WARNING);
        }
    }

    // Funcion para cambiar la unidad asignada a un colaborador
    @FXML
    void cambiarUnidad(ActionEvent evento) {
        Colaborador colaboradorSeleccionado = tvColaborador.getSelectionModel().getSelectedItem();
        if (colaboradorSeleccionado != null) {
            if (colaboradorSeleccionado.getIdRol() == 3) {  // Solo conductores pueden ser asignados a unidades
                abrirFormularioAsignacion(colaboradorSeleccionado);
            } else {
                Utilidades.mostrarAlertaSimple("Unidades", "Solo los conductores pueden ser asignados a las unidades", Alert.AlertType.WARNING);
            }
        } else {
            Utilidades.mostrarAlertaSimple("Unidades", "Por favor selecciona un colaborador", Alert.AlertType.WARNING);
        }
    }

    // Funcion para asignar una unidad a un colaborador
    private void abrirFormularioAsignacion(Colaborador colaborador) {
        try {
            // Cargar la ventana de asignación de unidad
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AsignarUnidad.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasar el colaborador seleccionado
            AsignarUnidad controladorUnidad = loader.getController();
            controladorUnidad.inicializarInformacion(colaborador, this);

            // Mostrar la ventana en una nueva escena
            Scene escena = new Scene(root);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Formulario de Asignación de Unidad");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Funcion para desasignar una unidad de un colaborador
    @FXML
    void desasignarUnidad(ActionEvent evento) {
        Colaborador colaboradorSeleccionado = tvColaborador.getSelectionModel().getSelectedItem();
        if (colaboradorSeleccionado != null) {
            if (colaboradorSeleccionado.getIdUnidad() == null) {
                Utilidades.mostrarAlertaSimple("Unidades", "El colaborador no tiene unidad asignada", Alert.AlertType.INFORMATION);
            } else {
                Mensaje mensaje = ColaboradorDAO.desasignarUnidadColaborador(colaboradorSeleccionado);
                llenarTabla();
                Utilidades.mostrarAlertaSimple("Unidades", mensaje.getMensaje(), Alert.AlertType.INFORMATION);
            }
        } else {
            Utilidades.mostrarAlertaSimple("Unidades", "Por favor selecciona un colaborador primero", Alert.AlertType.INFORMATION);
        }
    }

    @Override
    public void notificar() {
        listaColaboradores.clear();
        llenarTabla();
    }

    // Funcion para llenar la tabla con los colaboradores
    private void llenarTabla() {
        HashMap<String, Object> respuesta = ColaboradorDAO.obtenerColaboradores();
        if (!(boolean) respuesta.get("error")) {
            List<Colaborador> lista = (List<Colaborador>) respuesta.get("colaboradores");
            listaColaboradores = FXCollections.observableArrayList();
            listaColaboradores.addAll(lista);
            tvColaborador.setItems(listaColaboradores);
        } else {
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    // Funcion para abrir el formulario de colaborador (agregar o editar)
    private void abrirFormulario(Colaborador colaborador) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioColaborador.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del formulario
            FXMLFormularioColaboradorController controladorFormulario = loader.getController();
            if (colaborador != null) {
                controladorFormulario.lColaborador.setText("Editar Colaborador");
                controladorFormulario.btnAccion.setText("Editar");
                controladorFormulario.inicializaInformacion(colaborador, this);
            } else {
                controladorFormulario.lColaborador.setText("Agregar Colaborador");
                controladorFormulario.btnAccion.setText("Agregar");
                controladorFormulario.inicializaInformacion(null, this);
            }

            // Mostrar el formulario en una nueva ventana
            Scene escena = new Scene(root);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Formulario Colaborador");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Inicializa la información del colaborador en la interfaz
    public void inicializarInformacion(Colaborador colaborador) {
        this.colaborador = colaborador;
        lColaborador.setText(colaborador.getNombre() + " " + colaborador.getApellidoPaterno() + " " + colaborador.getApellidoMaterno());
        llenarTabla();
    }

    // Funcion de inicialización al cargar la vista
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTabla();
        ObservableList<String> opcionesBusqueda = FXCollections.observableArrayList(
                "Nombre",
                "Rol",
                "Número Personal"
        );
        cbBusqueda.setItems(opcionesBusqueda);
        cbBusqueda.getSelectionModel().selectFirst();
    }
}
