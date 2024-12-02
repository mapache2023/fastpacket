/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import dominio.ImpColaborador;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pojo.Colaborador;
import pojo.Mensaje;

/**
 * REST Web Service
 *
 * @author hp
 */
@Path("colaborador")
public class WsColaborador {

    @Context
    private UriInfo context;

    @GET
    @Path("obtenerColaboradores")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> obtenerColaborador() {
        List<Colaborador> colaborador = new ArrayList<>();

        colaborador = ImpColaborador.obtenerColaboradores();

        return colaborador;
    }

    @POST
    @Path("registrar")
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje registrarColaborador(String json) {
        Mensaje msj = new Mensaje();

        if (json == null || json.isEmpty()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Gson gson = new Gson();
        Colaborador colaborador = gson.fromJson(json, Colaborador.class);
        msj = ImpColaborador.registrarColaborador(colaborador);
        return msj;
    }

    @PUT
    @Path("editar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje editarColaborador(String json) {
        if (json == null || json.isEmpty()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Gson gson = new Gson();
        Colaborador colaborador = gson.fromJson(json, Colaborador.class);
        return ImpColaborador.editarColaborador(colaborador);
    }

    @Path("subirFoto/{idColaborador}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje subirFotoColaborador(@PathParam("idColaborador") Integer idColaborador,
            byte[] foto) {
        Mensaje msj = new Mensaje();
        if (idColaborador > 0 && foto != null) {
            msj = ImpColaborador.registrarFotoColaborador(idColaborador, foto);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return msj;
    }

    @Path("obtenerFoto/{idColaborador}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Colaborador obtenerFotoColaborador(@PathParam("idColaborador") Integer idColaborador) {
        Colaborador colaborador = null;
        if (idColaborador != null && idColaborador > 0) {
            colaborador = ImpColaborador.obtenerFotografiaColaborador(idColaborador);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return colaborador;
    }


    // Desasignar unidad de un colaborador
    @Path("desasignarUnidad")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje desasignarUnidad(String json) {
        Mensaje msj = new Mensaje();
      if (json == null || json.isEmpty()) {
    throw new WebApplicationException("El JSON recibido es vacío o nulo.", Response.Status.BAD_REQUEST);
}


        Gson gson = new Gson();
        Colaborador request = gson.fromJson(json, Colaborador.class);

        // Desasignar la unidad del colaborador
        msj = ImpColaborador.desasignarUnidad(request.getIdColaborador());

        return msj;
    }

    @DELETE
    @Path("eliminar")
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje eliminarColaborador(@FormParam("idColaborador") String IdColaborador) {
        Mensaje msj = null;
        if (IdColaborador != null && !IdColaborador.isEmpty()) {
            msj = ImpColaborador.eliminar(IdColaborador);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return msj;
    }

    // Nueva ruta: Buscar colaboradores por nombre
    @GET
    @Path("buscarPorNombre/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> buscarPorNombre(@PathParam("nombre") String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return ImpColaborador.buscarPorNombre(nombre);
    }

    // Nueva ruta: Buscar colaborador por número de personal
    @GET
    @Path("buscarPorNumeroPersonal/{numeroPersonal}")
    @Produces(MediaType.APPLICATION_JSON)
    public Colaborador buscarPorNumeroPersonal(@PathParam("numeroPersonal") String numeroPersonal) {
        if (numeroPersonal == null || numeroPersonal.isEmpty()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return ImpColaborador.buscarPorNumeroPersonal(numeroPersonal);
    }

    // Nueva ruta: Buscar colaboradores por rol
    @GET
    @Path("buscarPorRol/{rol}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> buscarPorRol(@PathParam("rol") String rol) {
        if (rol == null || rol.isEmpty()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return ImpColaborador.buscarPorRol(rol);
    }
}
