/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import dominio.ImpEnvio;
import dominio.ImpEnviosEspeciales;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pojo.Envio;
import pojo.Estatus;
import pojo.Historial;
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
            if(envio.getIdEnvio()!= null){
                return ImpEnvio.actualizarEnvio(envio);
            }else{
                return new Mensaje(true, "no se pudo actualziar el pedido");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw  new BadRequestException();
        }
    }
    
    
    @Path("consultar-envio/{numeroGuia}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Envio> consultarEnvio(@PathParam("numeroGuia") String numeroGuia){
        return ImpEnvio.buscarEnvio(numeroGuia);
    } 
    
    @Path("asignar-conductor")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje asignarConductor(String gsonEnvio){
        try {
            Gson gson = new Gson();
            Envio envio = gson.fromJson(gsonEnvio, Envio.class);
            if(envio.getIdColaborador()!= null){
                return ImpEnvio.asignarConductor(envio);
            }else{
                return new Mensaje(true, "no se pudo asignar el conductor");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw  new BadRequestException();
        }
    }
    
    @Path("obtener-envios")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Envio> obtenerEnvio(){
        return ImpEnvio.obtenerEnvios();
    } 
    
 @POST
 @Path("actualizar-estado")
@Consumes(MediaType.APPLICATION_JSON)
public Mensaje cambios(String json){
    Mensaje msj;
        if(json == null || json.isEmpty()){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Gson gson = new Gson();
        Historial historia= gson.fromJson(json, Historial.class);
            msj = ImpEnviosEspeciales.registrarCambio(historia);
    return msj;
}
 @Path("obtener-estados")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Estatus> obtenerEstados(){
        return ImpEnvio.obtenerEstados();
    } 

}
