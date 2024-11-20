
package ws;

import com.google.gson.Gson;
import dominio.ImpCliente;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import pojo.Cliente;
import pojo.Mensaje;


 @Path("clientes")
public class WSCliente {
      // Obtener todos los clientes registrados
    @Context
    private UriInfo context;
    
    public WSCliente(){
        
    }
@Path("registro")
@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Mensaje registrarCliente(String jsonCliente) {
    try {
        Gson gson = new Gson();
        Cliente cliente = gson.fromJson(jsonCliente, Cliente.class);

        // Validación de los campos requeridos
        if (cliente.getNombre() != null && !cliente.getNombre().isEmpty() &&
            cliente.getApellidoPaterno() != null && !cliente.getApellidoPaterno().isEmpty() &&
            cliente.getCorreo() != null && !cliente.getCorreo().isEmpty()) {

            // Llamada al método para registrar el cliente
            Mensaje mensaje = ImpCliente.registrarCliente(cliente); // Utiliza tu implementación directa
            return mensaje;

        } else {
            // Devuelve un mensaje de error por campos faltantes o incorrectos
            return new Mensaje(true, "Nombre, apellido paterno y/o correo faltantes o incorrectos");
        }
    } catch (Exception e) {
        e.printStackTrace();
        // Retorna un mensaje genérico en caso de error
        return new Mensaje(true, "Ocurrió un error al procesar la solicitud.");
    }
}
@Path("editar")
@PUT
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Mensaje editarCliente(String jsonCliente) {
    try {
        System.out.println("JSON Recibido: " + jsonCliente); // Para depuración
        Gson gson = new Gson();
        Cliente datosActualizados = gson.fromJson(jsonCliente, Cliente.class);

        if (datosActualizados.getIdCliente() == null) {
            return new Mensaje(true, "ID de cliente faltante o incorrecto");
        }

        // Convertir el ID de cliente a Integer
        Integer idCliente = datosActualizados.getIdCliente(); // Asegúrate de que getIdCliente() sea de tipo Integer

        // Busca el cliente existente por su ID
        Cliente clienteExistente = ImpCliente.buscarCliente(idCliente);

        if (clienteExistente == null) {
            return new Mensaje(true, "Cliente no encontrado");
        }

        // Actualiza solo los datos relevantes
        clienteExistente.setNombre(datosActualizados.getNombre());
        clienteExistente.setApellidoPaterno(datosActualizados.getApellidoPaterno());
        clienteExistente.setApellidoMaterno(datosActualizados.getApellidoMaterno());
        clienteExistente.setDireccion(datosActualizados.getDireccion());
        clienteExistente.setTelefono(datosActualizados.getTelefono());
        clienteExistente.setCorreo(datosActualizados.getCorreo());

        // Guarda los cambios en la base de datos
        boolean actualizado = ImpCliente.guardarCambios(clienteExistente);

        if (actualizado) {
            return new Mensaje(false, "Cliente actualizado correctamente");
        } else {
            return new Mensaje(true, "Error al guardar los cambios del cliente");
        }

    } catch (Exception e) {
        e.printStackTrace();
        return new Mensaje(true, "Error al procesar la solicitud: " + e.getMessage());
    }
}
@DELETE
@Path("eliminar/{idCliente}")  // Incluir el parámetro {idCliente} en la ruta
@Produces(MediaType.APPLICATION_JSON)
public Mensaje eliminarCliente(@PathParam("idCliente") Integer idCliente) {
    try {
        // Llama a la clase de implementación para eliminar el cliente
        Mensaje mensaje = ImpCliente.eliminarCliente(idCliente);

        // Retorna el mensaje de error si hubo un problema
        if (mensaje.getError()) {
            return mensaje; 
        }
        
        // Si la eliminación es exitosa, se retorna el mensaje con éxito
        return new Mensaje(false, "Cliente eliminado exitosamente.");
    } catch (Exception e) {
        e.printStackTrace();
        // En caso de error, retornamos un mensaje de error
        return new Mensaje(true, "Error al eliminar el cliente: " + e.getMessage());
    }
}

@Path("buscar/{idCliente}")
@GET
@Produces(MediaType.APPLICATION_JSON)
public Mensaje buscarCliente(@PathParam("idCliente") Integer idCliente) {
    try {
        // Verificar que el ID del cliente es válido
        if (idCliente == null) {
            return new Mensaje(true, "ID de cliente faltante o incorrecto");
        }

        // Llama al método buscarCliente que ya tienes en ImpCliente
        Cliente clienteEncontrado = ImpCliente.buscarCliente(idCliente);

        if (clienteEncontrado == null) {
            return new Mensaje(true, "Cliente no encontrado");
        }

        // Retorna el cliente encontrado
        return new Mensaje(false, "Cliente encontrado correctamente");

    } catch (Exception e) {
        e.printStackTrace();
        return new Mensaje(true, "Error al procesar la solicitud: " + e.getMessage());
    }
}


 }

    