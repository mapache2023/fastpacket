/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import dominio.ImpUnidad;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.Mensaje;
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
                    && unidad.getNumeroIdentificacion() != null && !unidad.getNumeroIdentificacion().isEmpty() && unidad.getMotivo() != null && !unidad.getMotivo().isEmpty()
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
    
    @Path("buscar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Unidad buscarUnidad(@FormParam("vin") String vin, @FormParam("marca") String marca, @FormParam("numeroIdentificacion") String numeroIdentificacion){
        if(vin != null && marca != null && !marca.isEmpty() && numeroIdentificacion != null && !numeroIdentificacion.isEmpty()){
            return ImpUnidad.buscarUnidad(vin, marca, numeroIdentificacion);
        }
        throw new BadRequestException();
    }
    
    @Path("baja")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Unidad bajaUnidad(@FormParam("motivo") String motivo){
        if(motivo != null && !motivo.isEmpty()){
            return ImpUnidad.darDeBaja(motivo);
        }
        throw new BadRequestException();
    }

}
