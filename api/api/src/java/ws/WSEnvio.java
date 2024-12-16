/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import dominio.ImpEnvio;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.Envio;
import pojo.Mensaje;


@Path("envio")
public class WSEnvio {
     public WSEnvio(){
        
    }
    
    @Path("registro-envio")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarEnvio(String gsonEnvio){
        try {
            Gson gson = new Gson();
            Envio envio = gson.fromJson(gsonEnvio, Envio.class);
            System.out.println(envio);
           if (envio.getNumeroGuia() != null && !envio.getNumeroGuia().isEmpty() &&
            envio.getCosto() != null && !envio.getCosto().isEmpty() &&
            envio.getIdCliente() != null &&
            envio.getCalleOrigen() != null && !envio.getCalleOrigen().isEmpty() &&
            envio.getNumeroOrigen() != null && !envio.getNumeroOrigen().isEmpty() &&
            envio.getCodigoPostalOrigen()!= null && !envio.getCodigoPostalOrigen().isEmpty() &&
            envio.getEstadoOrigen() != null && !envio.getEstadoOrigen().isEmpty() &&
            envio.getColoniaOrigen() != null && !envio.getColoniaOrigen().isEmpty() &&
            envio.getCiudadOrigen()!= null && !envio.getCiudadOrigen().isEmpty() &&
            envio.getCalleDestino() != null && !envio.getCalleDestino().isEmpty() &&
            envio.getNumeroDestino() != null && !envio.getNumeroDestino().isEmpty() &&
            envio.getCodigoPostalDestino()!= null && !envio.getCodigoPostalDestino().isEmpty() &&
            envio.getEstadoDestino() != null && !envio.getEstadoDestino().isEmpty() &&
            envio.getColoniaDestino() != null && !envio.getColoniaDestino().isEmpty() &&
            envio.getCiudadDestino()!= null && !envio.getCiudadDestino().isEmpty()){
                return ImpEnvio.registrarEnvio(envio);
            }else{
                return new Mensaje(true, "error");
            }
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }
    
    @Path("actualizar-envio")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje actulizarEnvio(String gsonEnvio){
        try {
            Gson gson = new Gson();
            Envio envio = gson.fromJson(gsonEnvio, Envio.class);
            if(envio.getNumeroGuia()!= null && !envio.getNumeroGuia().isEmpty()){
                return ImpEnvio.actualizarEnvio(envio);
            }else{
                return new Mensaje(true, "no se pudo actualziar el pedido");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw  new BadRequestException();
        }
    }
    
  /* @Path("consultar-envio")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public List<Envio> consultarEnvio(@FormParam("numeroGuia") String numeroGuia){
        return ImpEnvio.buscarEnvio(numeroGuia);
    }
    
    @Path("asignar-conductor")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje asignarConductor(String gsonEnvio){
        try {
            Gson gson = new Gson();
            Envio envio = gson.fromJson(gsonEnvio, Envio.class);
            if(envio.getNumeroGuia()!= null && !envio.getNumeroGuia().isEmpty()){
                return ImpEnvio.actualizarEnvio(envio);
            }else{
                return new Mensaje(true, "no se pudo actualziar el pedido");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw  new BadRequestException();
        }
    }
    
    @Path("actualizar-estado")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje actualzarEstado(String gsonEstado){
        try {
            Gson gson = new Gson();
            Envio envio = gson.fromJson(gsonEstado, Envio.class);
            if(envio.getIdEnvio() != null && envio.getIdEstatus()!= null){
                return ImpEnvio.actualizarEstado(envio);
            }else{
                return new Mensaje(true, "no se pudo actualziar el estado del envio");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw  new BadRequestException();
        }
    } */


}
