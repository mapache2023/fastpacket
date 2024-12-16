/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop;

import desktop.interfaz.INotificacionCambio;
import desktop.modelo.dao.UnidadDAO;
import desktop.modelo.pojo.Unidad;
import desktop.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author berna
 */
public class FXMLAdminUnidadController implements Initializable, INotificacionCambio {
    
    private ObservableList<Unidad> unidades;
    
 //   private Unidad unidadess;

    @FXML
    private TableColumn<?, ?> tcMarca;
    @FXML
    private TableColumn<?, ?> tcModelo;
    @FXML
    private TableColumn<?, ?> tcAño;
    @FXML
    private TableColumn<?, ?> tcTipo;
    @FXML
    private TableColumn<?, ?> tcIdentificacion;
    @FXML
    private TableView<Unidad> tvUnidad;
    @FXML
    private TextField tfBuscar;
    @FXML
    private ComboBox<String> cbUnidad;
    @FXML
    private TableColumn<?, ?> tcVin;

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
       tcMarca.setCellValueFactory(new PropertyValueFactory("Marca"));
       tcModelo.setCellValueFactory(new PropertyValueFactory("Modelo"));
       tcAño.setCellValueFactory(new PropertyValueFactory("Ano"));
       tcTipo.setCellValueFactory(new PropertyValueFactory("Tipo"));
       tcIdentificacion.setCellValueFactory(new PropertyValueFactory("numeroIdentificacion"));
       tcVin.setCellValueFactory(new PropertyValueFactory("vin"));
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
       // controlador.inicializarValores(observador, unidad);
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
      //  controlador.inicializarValores(observador, unidad);
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
        irPantallaDarBaja(this, null);
    }
    
    public void notificar(){
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
                        Unidad unidadPorVin = UnidadDAO.buscarPorVin(textoBusqueda);
                        if (unidadPorVin != null) {
                            unidades.clear();
                            unidades.add(unidadPorVin);
                            tvUnidad.setItems(unidades);
                        }
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
    
}
