package desktop.modelo.dao;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import desktop.modelo.ConexionWs;
import desktop.modelo.RespuestaHTTP;
import desktop.modelo.pojo.Cliente;
import desktop.modelo.pojo.Mensaje;
import desktop.utilidades.Constantes;
import java.lang.reflect.Type;
import static java.net.URLEncoder.encode;

/**
 * Clase encargada de las operaciones relacionadas con los clientes.
 */
public class ClienteDAO {

    /**
     * Obtiene todos los clientes.
     * @return Una lista de clientes obtenidos.
     */
   public static List<Cliente> obtenerClientes() {
    List<Cliente> clientes = new ArrayList<>();
    
    // URL para obtener los clientes
    String url = Constantes.URI_WS + "clientes/obtenerClientes";
    RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

    // Verificar el código de respuesta y depurar
    System.out.println("Código de respuesta: " + respuesta.getCodigoRespuesta());
    System.out.println("Contenido de la respuesta: " + respuesta.getContenido());

    if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
        // Deserializar la respuesta JSON directamente a una lista de clientes
        Gson gson = new Gson();
        Type tipoListaClientes = new TypeToken<List<Cliente>>(){}.getType();
        clientes = gson.fromJson(respuesta.getContenido(), tipoListaClientes);
    } else {
        System.out.println("Error al obtener clientes: Código de respuesta no es OK.");
    }

    return clientes;
}

    /**
     * Registra un nuevo cliente.
     * @param clienteNuevo El cliente a registrar.
     * @return El mensaje de respuesta con el estado de la operación.
     */
    public static Mensaje registrarCliente(Cliente clienteNuevo) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URI_WS + "clientes/registro";
        Gson gson = new Gson();
        String parametros = gson.toJson(clienteNuevo);

        // Enviar solicitud para registrar al cliente
        RespuestaHTTP respuesta = ConexionWs.peticionPOSTjson(url, parametros);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("Error al registrar el cliente. Inténtelo más tarde.");
        }

        return mensaje;
    }

    /**
     * Edita la información de un cliente.
     * @param clienteEdicion El cliente con los datos modificados.
     * @return El mensaje de respuesta con el estado de la operación.
     */
    public static Mensaje editarCliente(Cliente clienteEdicion) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URI_WS + "clientes/editar";
        Gson gson = new Gson();
        String parametros = gson.toJson(clienteEdicion);

        // Enviar solicitud para editar al cliente
        RespuestaHTTP respuesta = ConexionWs.peticionPUTjson(url, parametros);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("Error al editar la información del cliente. Inténtelo más tarde.");
        }

        return mensaje;
    }

    /**
     * Elimina un cliente.
     * @param idCliente El ID del cliente a eliminar.
     * @return El mensaje de respuesta con el estado de la operación.
     */
    public static Mensaje eliminarCliente(int idCliente) {
    Mensaje mensaje = new Mensaje();
    String url = Constantes.URI_WS + "clientes/eliminar/" + idCliente; // Asegúrate de que el ID esté en la URL

    // Enviar solicitud para eliminar al cliente
    RespuestaHTTP respuesta = ConexionWs.peticionDELETE(url, url);  // Asegúrate de que no se necesiten parámetros adicionales

    if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
        Gson gson = new Gson();
        mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
    } else {
        mensaje.setError(true);
        mensaje.setMensaje("Error al eliminar el cliente. Inténtelo más tarde.");
    }

    return mensaje;
}

 


    // Método para buscar cliente por nombre
    public static List<Cliente> buscarPorNombre(String nombre) {
        List<Cliente> clientes = null;
        String url = Constantes.URI_WS + "cliente/buscarPorNombre/" + encode(nombre);

        // Enviar solicitud para obtener clientes por nombre
        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoListaClientes = new TypeToken<List<Cliente>>(){}.getType();
            clientes = gson.fromJson(respuesta.getContenido(), tipoListaClientes);
        }

        return clientes;
    }

    // Método para buscar cliente por correo
    public static List<Cliente> buscarPorCorreo(String correo) {
        List<Cliente> clientes = null;
        String url = Constantes.URI_WS + "cliente/buscarPorCorreo/" + encode(correo);

        // Enviar solicitud para obtener clientes por correo
        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoListaClientes = new TypeToken<List<Cliente>>(){}.getType();
            clientes = gson.fromJson(respuesta.getContenido(), tipoListaClientes);
        }

        return clientes;
    }

    // Método para buscar cliente por teléfono
    public static List<Cliente> buscarPorTelefono(String telefono) {
        List<Cliente> clientes = null;
        String url = Constantes.URI_WS + "cliente/buscarPorTelefono/" + encode(telefono);

        // Enviar solicitud para obtener clientes por teléfono
        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoListaClientes = new TypeToken<List<Cliente>>(){}.getType();
            clientes = gson.fromJson(respuesta.getContenido(), tipoListaClientes);
        }

        return clientes;
    }
}
