/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import dominio.ImpCliente;
import dominio.ImpPaquete;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import pojo.Cliente;
import pojo.Mensaje;
import pojo.Paquete;

 @Path("paquetes")  
public class WSPaquete {
    
    @Context
    private UriInfo context;
    
    public WSPaquete(){
        
    }
@GET
@Path("obtenerPaquetes")
@Produces(MediaType.APPLICATION_JSON)
public List<Paquete> obtenerPaquetes() {
    List<Paquete> paquetes = new ArrayList<>();

    paquetes = ImpPaquete.obtenerPaquetes();

    return paquetes;
}

@Path("registroPaquete")
@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public pojo.Mensaje registrarPaquete(String jsonPaquete) {
    try {
        Gson gson = new Gson();
        Paquete paquete = gson.fromJson(jsonPaquete, Paquete.class);

        // Validación de los campos requeridos
        if (paquete.getDescripcion() != null && !paquete.getDescripcion().isEmpty() &&
            paquete.getPeso() != null && !paquete.getPeso().isEmpty() &&
            paquete.getIdEnvio() != null) {

            // Llamada al método para registrar el paquete
            pojo.Mensaje mensaje = ImpPaquete.registrarPaquete(paquete); // Utiliza tu implementación directa
            return mensaje;

        } else {
            // Devuelve un mensaje de error por campos faltantes o incorrectos
            return new pojo.Mensaje(true, "Descripción, peso y/o ID de envío faltantes o incorrectos", null);
        }
    } catch (Exception e) {
        e.printStackTrace();
        // Retorna un mensaje genérico en caso de error
        return new pojo.Mensaje(true, "Ocurrió un error al procesar la solicitud.", null);
    }
}


@Path("actualizarPaquete")
@PUT
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Mensaje actualizarPaquete(String jsonPaquete) {
    try {
        Gson gson = new Gson();
        Paquete datosActualizados = gson.fromJson(jsonPaquete, Paquete.class);

        // Validación del ID del paquete (obligatorio para actualizar)
        if (datosActualizados.getIdPaquete() == null) {
            return new Mensaje(true, "ID del paquete faltante o incorrecto", null);
        }

        // Busca el paquete existente por su ID
        Paquete paqueteExistente = ImpPaquete.buscarPaquete(datosActualizados.getIdPaquete());
        if (paqueteExistente == null) {
            return new Mensaje(true, "Paquete no encontrado", null);
        }

        // Actualiza solo los detalles permitidos (excepto el envío al que pertenece)
        paqueteExistente.setDescripcion(datosActualizados.getDescripcion());
        paqueteExistente.setPeso(datosActualizados.getPeso());
        paqueteExistente.setAlto(datosActualizados.getAlto());
        paqueteExistente.setAncho(datosActualizados.getAncho());
        paqueteExistente.setProfundidad(datosActualizados.getProfundidad());

        // Guarda los cambios en la base de datos
        boolean actualizado = ImpPaquete.guardarCambios(paqueteExistente);

        if (actualizado) {
            // Convertir el objeto paqueteExistente a JSON para incluirlo en la respuesta como un string
            Gson gsonResponse = new Gson();
            String paqueteJson = gsonResponse.toJson(paqueteExistente);

            return new Mensaje(false, "Paquete actualizado correctamente", paqueteJson);
        } else {
            return new Mensaje(true, "Error al guardar los cambios del paquete", null);
        }

    } catch (Exception e) {
        e.printStackTrace();
        return new Mensaje(true, "Error al procesar la solicitud: " + e.getMessage(), null);
    }
}

@Path("consultarPaquetePorEnvio/{idEnvio}")
@GET
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Mensaje consultarPaquetePorEnvio(@PathParam("idEnvio") Integer idEnvio) {
    try {
        // Validación del ID de envío
        if (idEnvio == null) {
            return new Mensaje(true, "ID del envío faltante o incorrecto", null);
        }

        // Consulta los paquetes relacionados con el ID de envío
        List<Paquete> paquetes = ImpPaquete.buscarPaquetesPorEnvio(idEnvio);

        if (paquetes == null || paquetes.isEmpty()) {
            return new Mensaje(true, "No se encontraron paquetes para el envío especificado", null);
        }

        // Usar Gson para convertir la lista de Paquetes a JSON
        Gson gson = new Gson();
        String paquetesJson = gson.toJson(paquetes);

        // Crear un nuevo Mensaje con los paquetes en formato JSON
        return new Mensaje(false, "Paquetes encontrados", paquetesJson);

    } catch (Exception e) {
        e.printStackTrace();
        return new Mensaje(true, "Error al procesar la solicitud: " + e.getMessage(), null);
    }
}
@Path("eliminarPaquete/{idPaquete}/{idEnvio}")
@DELETE
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Mensaje eliminarPaquete(@PathParam("idPaquete") Integer idPaquete, @PathParam("idEnvio") Integer idEnvio) {
    try {
        // Validación de los parámetros
        if (idPaquete == null || idEnvio == null) {
            return new Mensaje(true, "ID de paquete o ID de envío faltantes o incorrectos", null);
        }

        // Eliminar el paquete de la base de datos utilizando el método de la clase ImpPaquete
        Mensaje respuesta = ImpPaquete.eliminarPaquete(idPaquete, idEnvio);

        // Retornar la respuesta del método eliminarPaquete
        return respuesta;

    } catch (Exception e) {
        e.printStackTrace();
        return new Mensaje(true, "Error al procesar la solicitud: " + e.getMessage(), null);
    }
}
@Path("consultarPaquetePorGuia/{numeroGuia}")
@GET
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Mensaje consultarPaquetePorGuia(@PathParam("numeroGuia") String numeroGuia) {
    try {
        // Validación del número de guía
        if (numeroGuia == null || numeroGuia.isEmpty()) {
            return new Mensaje(true, "Número de guía faltante o incorrecto", null);
        }

        // Consulta los paquetes relacionados con el número de guía
        List<Paquete> paquetes = ImpPaquete.buscarPaquetesPorGuia(numeroGuia);

        if (paquetes == null || paquetes.isEmpty()) {
            return new Mensaje(true, "No se encontraron paquetes para el número de guía especificado", null);
        }

        // Usar Gson para convertir la lista de Paquetes a JSON
        Gson gson = new Gson();
        String paquetesJson = gson.toJson(paquetes);

        // Crear un nuevo Mensaje con los paquetes en formato JSON
        return new Mensaje(false, "Paquetes encontrados", paquetesJson);

    } catch (Exception e) {
        e.printStackTrace();
        return new Mensaje(true, "Error al procesar la solicitud: " + e.getMessage(), null);
    }
}

    
}
