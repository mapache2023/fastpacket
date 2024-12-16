
package desktop.modelo.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import desktop.modelo.ConexionWs;
import desktop.modelo.RespuestaHTTP;
import desktop.modelo.pojo.Mensaje;
import desktop.modelo.pojo.Paquete;
import desktop.utilidades.Constantes;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import static java.net.URLEncoder.encode;
import java.util.ArrayList;
import java.util.List;

public class PaqueteDAO {
 
    /**
     * Obtiene todos los paquetes.
     * @return Una lista de paquetes obtenidos.
     */
public static List<Paquete> obtenerPaquetes() {
    List<Paquete> paquetes = new ArrayList<>();
    
    // URL para obtener los paquetes
    String url = Constantes.URI_WS + "paquetes/obtenerPaquetes";
    RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

    // Verificar el código de respuesta y depurar
    System.out.println("Código de respuesta: " + respuesta.getCodigoRespuesta());
    System.out.println("Contenido de la respuesta: " + respuesta.getContenido());

    if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
        // Deserializar la respuesta JSON directamente a una lista de Paquetes
        Gson gson = new Gson();
        Type tipoListaPaquetes = new TypeToken<List<Paquete>>(){}.getType();
        paquetes = gson.fromJson(respuesta.getContenido(), tipoListaPaquetes);
    } else {
        System.out.println("Error al obtener paquetes: Código de respuesta no es OK.");
    }

    return paquetes;
}

    /**
     * Registra un nuevo paquete.
     * @param paquete El paquete a registrar.
     * @return El mensaje de respuesta con el estado de la operación.
     */
    public static Mensaje registrarPaquete(Paquete paqueteNuevo) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URI_WS + "paquetes/registroPaquete";
        Gson gson = new Gson();
        String parametros = gson.toJson(paqueteNuevo);

        // Enviar solicitud para registrar al cliente
        RespuestaHTTP respuesta = ConexionWs.peticionPOSTjson(url, parametros);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("Error al registrar el paquete. Inténtelo más tarde.");
        }

        return mensaje;
    }

    /**
     * Edita la información de un cliente.
     * @param edicionEdicion El cliente con los datos modificados.
     * @return El mensaje de respuesta con el estado de la operación.
     */
   public static Mensaje actualizarPaquete(Paquete paquete) {
    Mensaje mensaje = new Mensaje();
    String url = Constantes.URI_WS + "paquetes/actualizarPaquete";
    Gson gson = new Gson();
    String parametros = gson.toJson(paquete); // Corregir el parámetro aquí

    // Enviar solicitud para editar el paquete
    RespuestaHTTP respuesta = ConexionWs.peticionPUTjson(url, parametros);

    if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
        mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
    } else {
        mensaje.setError(true);
        mensaje.setMensaje("Error al actualizar la información del paquete. Inténtelo más tarde.");
    }

    return mensaje;
}

    /**
     * Elimina un cliente.
     * @param idCliente El ID del cliente a eliminar.
     * @return El mensaje de respuesta con el estado de la operación.
     */
public static Mensaje eliminarPaquete(int idEnvio, int idPaquete) {
    Mensaje mensaje = new Mensaje();
    String url = Constantes.URI_WS + "paquetes/eliminarPaquete/" + idPaquete + "/" + idEnvio;

    // Construcción de los parámetros en caso de que sean necesarios para la solicitud DELETE
    String parametros = ""; // En este caso, no se requieren parámetros adicionales para la URL

    // Enviar solicitud para eliminar el paquete
    RespuestaHTTP respuesta = ConexionWs.peticionDELETE(url, parametros);

    if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
        Gson gson = new Gson();
        mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
    } else {
        mensaje.setError(true);
        mensaje.setMensaje("Error al eliminar el paquete. Inténtelo más tarde.");
    }

    return mensaje;
}


    // Método para buscar paquete por descripcion 
public static List<Paquete> consultarPaquetePorDescripcion(String descripcion) {
    List<Paquete> paquetes = null;

    // Convertir descripcion a String y añadir a la URL
    String url = Constantes.URI_WS + "paquetes/consultarPaquetePorDescripcion/" + descripcion;

    // Enviar solicitud para obtener paquetes por descripcion
    RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

    if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
        Gson gson = new Gson();
        Type tipoListaPaquetes = new TypeToken<List<Paquete>>() {}.getType();
        paquetes = gson.fromJson(respuesta.getContenido(), tipoListaPaquetes);
    }

    return paquetes;
}


}



