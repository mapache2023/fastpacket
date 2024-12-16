/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop;

import desktop.interfaz.INotificacionCambio;
import desktop.modelo.dao.UnidadDAO;
import desktop.modelo.pojo.Mensaje;
import desktop.modelo.pojo.Tipo;
import desktop.modelo.pojo.Unidad;
import desktop.utilidades.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author berna
 */
public class FXMLFormularioUnidadController implements Initializable {
    
    private ObservableList<Tipo> tipos;
    
    private Unidad unidad;
    private INotificacionCambio observador;

    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfAño;
  
    private TextField tfIdentificacion;
    @FXML
    private ComboBox<Tipo> cbTipo;
    @FXML
    private TextField tfVin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTiposUnidades();
    }    

    @FXML
    private void irEditar(ActionEvent event) {
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
        String marca = tfMarca.getText();
        String modelo = tfModelo.getText();
        Integer año = tfAño.getLength();
        String vin = tfVin.getText();
        String numeroIdentificion = tfIdentificacion.getText();
        int idTipo = cbTipo.getSelectionModel().getSelectedItem().getIdTipo();
      
        
        Unidad unidad = new Unidad();
        unidad.setMarca(marca);
        unidad.setModelo(modelo);
        unidad.setAno(año);
        unidad.setVin(vin);
        unidad.setNumeroIdentificacion(numeroIdentificion);        
        unidad.setIdTipo(idTipo);
        if(sonCamposValidos()){
            guardarDatosUnidad(unidad);
        }else{
       //     colaborador.setIdColaborador(colaboradorEdicion.getIdColaborador());
       //     editarDatosColaborador(colaborador);
        } 
    }
    
     private void cargarTiposUnidades(){
        tipos = FXCollections.observableArrayList();
        List<Tipo> listaWS = UnidadDAO.obtenerTipoUnidad();
        if(listaWS != null){
            tipos.addAll(listaWS);
            cbTipo.setItems(tipos);
        }
    }
     
     private boolean sonCamposValidos(){
        Mensaje msj = UnidadDAO.registarUnidad(unidad);
        
        String marca = tfMarca.getText();
        String modelo = tfModelo.getText();
        Integer año = tfAño.getLength();
        String vin = tfVin.getText();
        String Identificacion = tfIdentificacion.getText();
     //   String cbTipo = cbTipo.getText();
        return true;
    }
     
       private void guardarDatosUnidad(Unidad unidad){
        Mensaje msj = UnidadDAO.registarUnidad(unidad);
        if(!msj.getError()){
            Utilidades.mostrarAlertaSimple("Colaborador registrado", "La informacion del colaborador"+unidad.getVin()+"correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificar();
        }else{
            Utilidades.mostrarAlertaSimple("Error al guardar", msj.getMensaje(), Alert.AlertType.NONE);
        }
    }
       
         private void cerrarVentana(){
        Stage escenario = (Stage) tfVin.getScene().getWindow();
        escenario.close(); 
       
      // ((Stage) tfPersonal.getScene().getWindow()).close();
    }
    

    void inicializarValores(INotificacionCambio observador, Unidad unidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
