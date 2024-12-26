/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop;

import desktop.interfaz.INotificacionCambio;
import desktop.modelo.dao.ColaboradorDAO;
import desktop.modelo.dao.UnidadDAO;
import desktop.modelo.pojo.Colaborador;
import desktop.modelo.pojo.Mensaje;
import desktop.modelo.pojo.Tipo;
import desktop.modelo.pojo.Unidad;
import desktop.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static desktop.utilidades.Utilidades.mostrarAlertaConfirmacion;

/**
 * FXML Controller class
 *
 * @author berna
 */
public class FXMLAdminUnidadController implements Initializable, INotificacionCambio {
    
    private ObservableList<Unidad> unidades;
    
 //   private Unidad unidadess;
    @FXML
    private Label etiquetaColaborador;
 
    private Colaborador colaborador;
    @FXML
    private TableColumn<Unidad, String> tcIdentificacion;

    @FXML
    private TableColumn<Unidad,String> tcMarca;

    @FXML
    private TableColumn<Unidad, String> tcModelo;

    @FXML
    private TableColumn<Unidad, Tipo> tcTipo;

    @FXML
    private TableColumn<Unidad,String> tcVin;

    @FXML
    private TableColumn<Unidad, Boolean> tfActivo;

    @FXML TableColumn <Unidad,Integer> tcAño;
    @FXML
    private TableView<Unidad> tvUnidad;
    @FXML
    private TextField tfBuscar;
    @FXML
    private TableColumn<Unidad,String> tcMotivo;
    @FXML
    private ComboBox<String> cbUnidad;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
         ObservableList<String> opcionesBusqueda = FXCollections.observableArrayList(
                "Vin",
                "Marca",
                "Nii"
        );
        cbUnidad.setItems(opcionesBusqueda);
        cbUnidad.getSelectionModel().selectFirst();
    }    
    
     private void configurarTabla() {
       tcMarca.setCellValueFactory(new PropertyValueFactory<>("Marca"));
       tcModelo.setCellValueFactory(new PropertyValueFactory<>("Modelo"));
       tcAño.setCellValueFactory(new PropertyValueFactory<>("Ano"));
       tcTipo.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
       tcIdentificacion.setCellValueFactory(new PropertyValueFactory<>("numeroIdentificacion"));
       tcVin.setCellValueFactory(new PropertyValueFactory<>("vin"));
       tfActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));
         tcMotivo.setCellValueFactory(new PropertyValueFactory<>("motivo"));
    }    
     
      private void cargarInformacionTabla(){
        unidades = FXCollections.observableArrayList();
        List<Unidad> listaWS = UnidadDAO.obtenerUnidad();
        if(listaWS != null){
            unidades.addAll(listaWS);
            tvUnidad.setItems(unidades);
        }else{
            Utilidades.mostrarAlertaSimple("", "Datos no disponibles ", Alert.AlertType.ERROR);
        }
    }

    
    private void irPantallaUnidad(INotificacionCambio observador, Unidad unidad){
         try{
        Stage escenario = new Stage();
        FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLFormularioUnidad.fxml"));
        Parent vista = cargador.load();
        FXMLFormularioUnidadController controlador = cargador.getController();   
             if (unidad!=null) {
                controlador.inicializarValores(observador, unidad,this.colaborador);
             }
             else{
                 controlador.inicializarValores(observador, null,this.colaborador);
             }
        Scene escenarioFormualario = new Scene(vista);
        escenario.setScene(escenarioFormualario);
        escenario.setTitle("Pantalla unidad");
        escenario.initModality(Modality.APPLICATION_MODAL);
        escenario.showAndWait();
        }catch(IOException ex){
            Utilidades.mostrarAlertaSimple("eror", "error", Alert.AlertType.ERROR);
        }
    }
       
    private void irPantallaDarBaja(INotificacionCambio observador, Unidad unidad){
         try{
        Stage escenario = new Stage();
        FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLDarBajaUnidad.fxml"));
        Parent vista = cargador.load();
       FXMLDarBajaUnidadController controlador = cargador.getController();
      controlador.inicializarValores(observador, unidad);
        Scene escenarioFormualario = new Scene(vista);
        escenario.setScene(escenarioFormualario);
        escenario.setTitle("Pantalla dar de baja unidad");
        escenario.initModality(Modality.APPLICATION_MODAL);
        escenario.showAndWait();
        }catch(IOException ex){
            Utilidades.mostrarAlertaSimple("eror", "error", Alert.AlertType.ERROR);
        }
    }

   
    @FXML
    private void btnIrFormulario(ActionEvent event) throws IOException{
        irPantallaUnidad(this, null);
    }
    
    @FXML
    private void btnIrBaja(ActionEvent event) throws IOException{
        Unidad unidadSeleccionado = tvUnidad.getSelectionModel().getSelectedItem();
        if (unidadSeleccionado != null) {
            Optional<ButtonType> resultado = mostrarAlertaConfirmacion(
                    "Confirmar Accion",
                    "¿Estás seguro de que deseas dar de baja a esta unidad? la accion sera irreversiblle"
            );

            // Si el usuario confirma, proceder con la eliminación
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                if(unidadSeleccionado.isActivo()){
                    irPantallaDarBaja(this, unidadSeleccionado);
                }else  {
                    Utilidades.mostrarAlertaSimple("Informacion", "La unidad ya esta de baja", Alert.AlertType.INFORMATION);

                }


            } else {
                Utilidades.mostrarAlertaSimple("Cancelación", "La accion ha sido cancelada", Alert.AlertType.INFORMATION);
            }

        }
        else {
            Utilidades.mostrarAlertaSimple("Unidades", "Por favor selecciona una unidad primero", Alert.AlertType.INFORMATION);
        }
    }

    
    public void notificar(){
        unidades.clear();
        cargarInformacionTabla();
    } 

    @FXML
    private void btnBuscar(ActionEvent event) {
        String textoBusqueda = tfBuscar.getText();
        String criterioBusqueda = cbUnidad.getValue();

        unidades = FXCollections.observableArrayList();

        if (textoBusqueda.isEmpty()) {
            cargarInformacionTabla();
        } else {
            if (criterioBusqueda != null) {
                switch (criterioBusqueda) {
                    case "Vin":

                            unidades.clear();
                            unidades.addAll(UnidadDAO.buscarPorVin(textoBusqueda));
                            tvUnidad.setItems(unidades);

                        break;
                    case "Marca":
                        unidades.clear();
                        unidades.addAll(UnidadDAO.buscarPorMarca(textoBusqueda));
                        tvUnidad.setItems(unidades);
                        break;
                    case "Nii":
                        unidades.clear();
                        unidades.addAll(UnidadDAO.buscarPorNumero(textoBusqueda));
                        tvUnidad.setItems(unidades);
                        break;
                }
            }
        }
        tvUnidad.setItems(unidades);
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

    @FXML
    private void btnEditar(ActionEvent event) {
         Unidad unidadSeleccionado = tvUnidad.getSelectionModel().getSelectedItem();
        if (unidadSeleccionado != null) {
            irPantallaUnidad(this, unidadSeleccionado);
             }
         else {
            Utilidades.mostrarAlertaSimple("Unidades", "Por favor selecciona una unidad primero", Alert.AlertType.INFORMATION);
        }
    }
    
}
