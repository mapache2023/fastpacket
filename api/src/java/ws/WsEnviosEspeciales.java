/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import dominio.ImpEnviosEspeciales;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import pojo.Envio;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import pojo.EnviosApp;
import pojo.Historial;
import pojo.Paquete;

/**
 * REST Web Service
 *
 * @author hp
 */
@Path("enviosEspeciales")
public class WsEnviosEspeciales {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WsEnviosEspeciales
     */
    public WsEnviosEspeciales() {
    }
@GET
@Path("web/{numeroGuia}")
@Produces(MediaType.APPLICATION_JSON)
public List<Envio> enviosNumeroGuia(@QueryParam("numeroGuia") String numeroGuia){
        List<Envio> envios = new ArrayList<>();
        if(numeroGuia != null && !numeroGuia.isEmpty()){
            envios = ImpEnviosEspeciales.obtenerEnviosGuia(numeroGuia);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return envios;
}
@GET
@Path("paquetes/{idEnvio}")
@Produces(MediaType.APPLICATION_JSON)
public List<Paquete> paquetesEnvio(@QueryParam("idEnvio") String idEnvio){
        List<Paquete> paquetes = new ArrayList<>();
        if(idEnvio != null && !idEnvio.isEmpty()){
            paquetes = ImpEnviosEspeciales.obtenerPaquetesEnvio(idEnvio);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return paquetes;
}
@GET
@Path("paquetes/{idEnvio}")
@Produces(MediaType.APPLICATION_JSON)
public List<Historial> historialEnvio(@QueryParam("idEnvio") String idEnvio){
        List<Historial> cambios = new ArrayList<>();
        if(idEnvio != null && !idEnvio.isEmpty()){
            cambios = ImpEnviosEspeciales.obtenerCambios(idEnvio);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return cambios;
}
@GET
@Path("app/{idColaborador}")
@Produces(MediaType.APPLICATION_JSON)
public List<EnviosApp> enviosColaboradors(@QueryParam("idColaborador") String idColaborador){
        List<EnviosApp> envios = new ArrayList<>();
        if(idColaborador != null && !idColaborador.isEmpty()){
            envios = ImpEnviosEspeciales.obtenerEnvioColaborador(idColaborador);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return envios;
}

}
