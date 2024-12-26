/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import dominio.ImpColaborador;
import dominio.ImpUnidad;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import pojo.Colaborador;
import pojo.Mensaje;
import pojo.Tipo;
import pojo.Unidad;


@Path("unidad")
public class WSUnidad {
     
    public WSUnidad(){
        
    }
    
    @Path("registro")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje registrarUnidad(String jsonUnidad){
        try {
            Gson gson = new Gson();
            Unidad unidad = gson.fromJson(jsonUnidad, Unidad.class);
            if(unidad.getMarca() != null && !unidad.getMarca().isEmpty() && unidad.getModelo() != null && !unidad.getModelo().isEmpty()
                    && unidad.getAno() != null && unidad.getVin() != null && !unidad.getVin().isEmpty() && unidad.getIdTipo()!= null
                    && unidad.getNumeroIdentificacion() != null && !unidad.getNumeroIdentificacion().isEmpty() 
                    ){
                return ImpUnidad.registrarUnidad(unidad);
            }else{
                return new Mensaje(true, "error");
            }
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }
    
    
    @Path("editar")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje editarUnidad(String gsonUnidad){
        try {
            Gson gson = new Gson();
            Unidad unidadJson  = gson.fromJson(gsonUnidad, Unidad.class);
            if(unidadJson.getIdUnidad()!= null){
                return ImpUnidad.editarUnidad(unidadJson);
            }else{
                return new Mensaje(true, "lo sentimos ocurrio un error, intentelo mas tarde");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException();
        }
    }
    
    @Path("buscar-vin/{vin}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Unidad buscarUnidad(@PathParam("vin") String vin){
        if(vin != null){
            return ImpUnidad.buscarUnidad(vin);
        }
        throw new BadRequestException();
    }
    
    @Path("buscar-marca/{marca}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Unidad> buscarUnidadMarca(@PathParam("marca") String marca){
        if(marca != null){
            return ImpUnidad.buscarUnidadMarca(marca);
        }
        throw new BadRequestException();
    }
    
    @Path("buscar-numero/{numeroIdentificacion}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Unidad buscarUnidadNumero(@PathParam("numeroIdentificacion") String numeroIdentificacion){
        if(numeroIdentificacion != null){
            return ImpUnidad.buscarUnidadNumero(numeroIdentificacion);
        }
        throw new BadRequestException();
    }
    
    @Path("baja")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje bajaUnidad(@FormParam("idUnidad") Integer idUnidad, @FormParam("motivo") String motivo){
        if(idUnidad != null && idUnidad>0){
            ImpColaborador.desasignarUnidad(idUnidad);
            return ImpUnidad.darDeBaja(idUnidad, motivo);
        }
        throw new BadRequestException();
    } 
    
    @Path("obtener-unidades")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Unidad> obtenerUnidad(){
        return ImpUnidad.obtenerUnidades();
    }
    
    @Path("obtener-tipos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tipo> obtenerTipoUnidad(){
        return ImpUnidad.obtenerTipoUnidades();
    }
    
      
    @Path("obtenerConductores")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> obtenerConductores(){
        return ImpUnidad.obtenerCoductor();
    }
}
