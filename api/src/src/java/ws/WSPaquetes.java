
package ws;


import com.google.gson.Gson;
import dominio.ImpPaquete;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import pojo.Paquete;

 @Path("paquetes")  
public class WSPaquetes {
    
    @Context
    private UriInfo context;
    
    public WSPaquetes(){
        
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
    
}
