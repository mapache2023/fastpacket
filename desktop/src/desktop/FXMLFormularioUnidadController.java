package desktop;

import desktop.interfaz.INotificacionCambio;
import desktop.modelo.dao.UnidadDAO;
import desktop.modelo.pojo.*;
import desktop.utilidades.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author berna
 */
public class FXMLFormularioUnidadController implements Initializable {

    public Button btnGuardar;
    public Button btnEditar;
    public Colaborador colaborador;
    @FXML
    public Label etiquetaColaborador;
    private ObservableList<Tipo> tipos;
    
    private Unidad unidad;
    private INotificacionCambio observador;

    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfAño;
    @FXML
    private ComboBox<Tipo> cbTipo;
    @FXML
    private TextField tfVin;
    @FXML
    private TextField tfNII;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cargarTiposUnidades();
        tfAño.textProperty().addListener((observable, oldValue, newValue)->{
            String año = tfAño.getText();
            String vin = tfVin.getText();

            // Si el año y el VIN tienen los valores necesarios, generamos el NII
            if (año.length() >=4 && esNumeroEntero(año) && vin.length() >= 4) {
                String numeroIdentificacion = año + vin.substring(0, 4);
                tfNII.setText(numeroIdentificacion);
            } else {
                tfNII.clear(); // Limpiar el campo si no se pueden generar los valores
            }
        } );

        tfVin.textProperty().addListener((observable, oldValue, newValue)->{
            String año = tfAño.getText();
            String vin = tfVin.getText();

            // Si el año y el VIN tienen los valores necesarios, generamos el NII
            if (año.length() >=4 && esNumeroEntero(año) && vin.length() >= 4) {
                String numeroIdentificacion = año + vin.substring(0, 4);
                tfNII.setText(numeroIdentificacion);
            } else {
                tfNII.clear(); // Limpiar el campo si no se pueden generar los valores
            }
        });

    }    

    @FXML
    private void irEditar(ActionEvent event) {
    String marca = tfMarca.getText();
        String modelo = tfModelo.getText();
        
        // Convertimos el año de texto a un número entero
        Integer año = null;
        try {
            año = Integer.parseInt(tfAño.getText());
        } catch (NumberFormatException e) {
            // Si no se puede convertir, se mostrará un mensaje de error más tarde
            Utilidades.mostrarAlertaSimple("Error", "El año debe ser un número válido.", Alert.AlertType.ERROR);
            return;
        }

        String vin = tfVin.getText();
        String numeroIdentificion = tfNII.getText();
        
        // Obtener el tipo seleccionado
        Tipo tipoSeleccionado = cbTipo.getSelectionModel().getSelectedItem();
        if (tipoSeleccionado == null) {
            Utilidades.mostrarAlertaSimple("Error", "Debe seleccionar un tipo de unidad.", Alert.AlertType.ERROR);
            return;
        }
        int idTipo = tipoSeleccionado.getIdTipo();
      
        // Crear la unidad con los datos ingresados
        Unidad unidad = new Unidad();
        unidad.setMarca(marca);
        unidad.setModelo(modelo);
        unidad.setAno(año);
        unidad.setVin(vin);
        unidad.setNumeroIdentificacion(numeroIdentificion);        
        unidad.setIdTipo(idTipo);
        unidad.setIdUnidad(this.unidad.getIdUnidad());
        // Verificar que los campos son válidos antes de guardar
        if (sonCamposValidos()) {
            EditarDatosUnidad(unidad);
        } else {
            Utilidades.mostrarAlertaSimple("Error", "Por favor, complete todos los campos obligatorios.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void irGuardar(ActionEvent event) {
        String marca = tfMarca.getText();
        String modelo = tfModelo.getText();
        
        // Convertimos el año de texto a un número entero
        Integer año = null;
        try {
            año = Integer.parseInt(tfAño.getText());
        } catch (NumberFormatException e) {
            // Si no se puede convertir, se mostrará un mensaje de error más tarde
            Utilidades.mostrarAlertaSimple("Error", "El año debe ser un número válido.", Alert.AlertType.ERROR);
            return;
        }

        String vin = tfVin.getText();
        String numeroIdentificion = tfNII.getText();
        
        // Obtener el tipo seleccionado
        Tipo tipoSeleccionado = cbTipo.getSelectionModel().getSelectedItem();
        if (tipoSeleccionado == null) {
            Utilidades.mostrarAlertaSimple("Error", "Debe seleccionar un tipo de unidad.", Alert.AlertType.ERROR);
            return;
        }
        int idTipo = tipoSeleccionado.getIdTipo();
      
        if (tfVin.getText().length() > 17) {
              Utilidades.mostrarAlertaSimple("Error", "El VIN debe exede los  caracteres.", Alert.AlertType.ERROR);
              return;
        }
        // Crear la unidad con los datos ingresados
        Unidad unidad = new Unidad();
        unidad.setMarca(marca);
        unidad.setModelo(modelo);
        unidad.setAno(año);
        unidad.setVin(vin);
        unidad.setNumeroIdentificacion(numeroIdentificion);        
        unidad.setIdTipo(idTipo);

        // Verificar que los campos son válidos antes de guardar
        if (sonCamposValidos()) {
            guardarDatosUnidad(unidad);
        } else {
            Utilidades.mostrarAlertaSimple("Error", "Campos vacios o VIN Y año muy corto.", Alert.AlertType.ERROR);
        }
    }
    
    private void cargarTiposUnidades() {
        tipos = FXCollections.observableArrayList();
        List<Tipo> listaWS = UnidadDAO.obtenerTipoUnidad();
        if (listaWS != null) {
            tipos.addAll(listaWS);
            cbTipo.setItems(tipos);
        }
    }

    private boolean sonCamposValidos() {
        // Validación básica de los campos
        return !tfMarca.getText().isEmpty() &&
               !tfModelo.getText().isEmpty() &&
               !tfAño.getText().isEmpty() &&
               !tfVin.getText().isEmpty() &&
               !tfNII.getText().isEmpty() &&
               cbTipo.getSelectionModel().getSelectedItem() != null &&
                tfAño.getText().length() >4;
    }
    
    private void guardarDatosUnidad(Unidad unidad) {
        Mensaje msj = UnidadDAO.registarUnidad(unidad);
        if (!msj.getError()) {
            Utilidades.mostrarAlertaSimple("Unidad registrada", "La información de la unidad " + unidad.getVin() + " fue guardada correctamente.", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificar();
        } else {
            Utilidades.mostrarAlertaSimple("Error al guardar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }
       
    private void cerrarVentana() {
        Stage escenario = (Stage) tfVin.getScene().getWindow();
        escenario.close(); 
    }

    void inicializarValores(INotificacionCambio observador, Unidad unidad,Colaborador colaborador) {
        this.observador = observador;
        this.unidad = unidad;
        if (unidad != null) {
            llenarFormulario();
            tfVin.setDisable(true);
            btnGuardar.setVisible(false);
            btnEditar.setVisible(true);
        }
        else {
            btnEditar.setVisible(false);
            btnGuardar.setVisible(true);
        }
        this.colaborador = colaborador;

        String nombre = colaborador.getNombre() != null ? colaborador.getNombre() : "N/A";
        String apellidoPaterno = colaborador.getApellidoPaterno() != null ? colaborador.getApellidoPaterno() : "";
        etiquetaColaborador.setText( nombre + " " + apellidoPaterno);

    }

    private void EditarDatosUnidad(Unidad unidad) {
     Mensaje msj = UnidadDAO.editarUnidad(unidad);
        if (!msj.getError()) {
            Utilidades.mostrarAlertaSimple("Unidad registrada", "La información de la unidad " + unidad.getVin() + " fue guardada correctamente.", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificar();
        } else {
            Utilidades.mostrarAlertaSimple("Error al guardar", msj.getMensaje(), Alert.AlertType.ERROR);
        }}

    private void llenarFormulario() {
       tfAño.setText(this.unidad.getAno().toString());
       tfMarca.setText(this.unidad.getMarca());
       tfModelo.setText(this.unidad.getModelo());
       tfNII.setText(this.unidad.getNumeroIdentificacion());
       tfVin.setText(this.unidad.getVin());
        if (unidad.getIdTipo() != 0) {
            for (Tipo tipo : cbTipo.getItems()) {
                if (tipo.getIdTipo() == unidad.getIdTipo()) {
                    cbTipo.setValue(tipo);
                    break;
                }
            }
        }
    }

    private Boolean esNumeroEntero(String ano) {
        try {
            Integer.parseInt(tfAño.getText());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
