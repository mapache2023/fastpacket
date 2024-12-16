/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
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
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import pojo.Cliente;
import pojo.EnviosApp;
import pojo.Historial;
import pojo.Mensaje;
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
public EnviosApp enviosNumeroGuia(@PathParam("numeroGuia") String numeroGuia){
          if (numeroGuia == null || numeroGuia.isEmpty()) {
        throw new WebApplicationException("Número de guía no proporcionado", Response.Status.BAD_REQUEST);
    }

    EnviosApp envios = ImpEnviosEspeciales.obtenerEnviosGuia(numeroGuia);
    if (envios == null) {
        throw new WebApplicationException("Envío no encontrado", Response.Status.NO_CONTENT);
    }

    return envios;
}
@GET
@Path("app/conductor/{idColaborador}")
@Produces(MediaType.APPLICATION_JSON)
public List<EnviosApp> enviosColaboradors(@PathParam("idColaborador") String idColaborador){
        List<EnviosApp> envios = new ArrayList<>();
        if(idColaborador != null && !idColaborador.isEmpty()){
            envios = ImpEnviosEspeciales.obtenerEnvioColaborador(idColaborador);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return envios;
}

@GET
@Path("app/cliente/{idCliente}")
@Produces(MediaType.APPLICATION_JSON)
public Cliente enviosCliente(@PathParam("idCliente") String idCliente){
        Cliente cliente = new Cliente();
        if(idCliente != null && !idCliente.isEmpty()){
           cliente =    ImpEnviosEspeciales.obtenerCliente(idCliente);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return cliente;
}
@POST
@Path("app/cambios")
@Consumes(MediaType.APPLICATION_JSON)
public Mensaje cambios(String json){
    Mensaje msj = new Mensaje();
    
        if(json == null || json.isEmpty()){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Gson gson = new Gson();
        Historial historia= gson.fromJson(json, Historial.class);
            msj = ImpEnviosEspeciales.registrarCambio(historia);
    return msj;
}
}