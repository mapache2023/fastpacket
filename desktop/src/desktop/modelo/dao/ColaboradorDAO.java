package desktop.modelo.dao;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import desktop.modelo.ConexionWs;
import desktop.modelo.RespuestaHTTP;
import desktop.modelo.pojo.Colaborador;
import desktop.modelo.pojo.Mensaje;
import desktop.modelo.pojo.Rol;
import desktop.modelo.pojo.Unidad;
import desktop.utilidades.Constantes;
import java.lang.reflect.Type;

/**
 * Clase encargada de las operaciones relacionadas con los colaboradores.
 */
public class ColaboradorDAO {

    /**
     * Obtiene todos los colaboradores.
     * @return Un HashMap con el estado de la operación y los colaboradores obtenidos.
     */
    public static HashMap<String, Object> obtenerColaboradores() {
        HashMap<String, Object> respuestaServicio = new LinkedHashMap<>();
        List<Colaborador> colaboradores = null;

        // URL para obtener los colaboradores
        String url = Constantes.URI_WS + "colaborador/obtenerColaboradores";
        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            // Deserializar la respuesta JSON a una lista de colaboradores
            Gson gson = new Gson();
            Type tipoListaColaboradores = new TypeToken<List<Colaborador>>(){}.getType();
            colaboradores = gson.fromJson(respuesta.getContenido(), tipoListaColaboradores);

            // Añadir la lista de colaboradores a la respuesta
            respuestaServicio.put("error", false);
            respuestaServicio.put("colaboradores", colaboradores);
        }

        return respuestaServicio;
    }

    /**
     * Registra un nuevo colaborador.
     * @param colaboradorNuevo El colaborador a registrar.
     * @return El mensaje de respuesta con el estado de la operación.
     */
    public static Mensaje registrarColaborador(Colaborador colaboradorNuevo) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URI_WS + "colaborador/registrar";
        Gson gson = new Gson();
        String parametros = gson.toJson(colaboradorNuevo);

        // Enviar solicitud para registrar al colaborador
        RespuestaHTTP respuesta = ConexionWs.peticionPOSTjson(url, parametros);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("Error al registrar el colaborador. Inténtelo más tarde.");
        }

        return mensaje;
    }

    /**
     * Edita la información de un colaborador.
     * @param colaboradorEdicion El colaborador con los datos modificados.
     * @return El mensaje de respuesta con el estado de la operación.
     */
    public static Mensaje editarColaborador(Colaborador colaboradorEdicion) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URI_WS + "colaborador/editar";
        Gson gson = new Gson();
        String parametros = gson.toJson(colaboradorEdicion);

        // Enviar solicitud para editar al colaborador
        RespuestaHTTP respuesta = ConexionWs.peticionPUTjson(url, parametros);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("Error al editar la información del colaborador. Inténtelo más tarde.");
        }

        return mensaje;
    }

    /**
     * Elimina un colaborador.
     * @param idColaborador El ID del colaborador a eliminar.
     * @return El mensaje de respuesta con el estado de la operación.
     */
    public static Mensaje eliminarColaborador(int idColaborador) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URI_WS + "colaborador/eliminar";
        String parametros = String.format("idColaborador=%s", idColaborador);

        // Enviar solicitud para eliminar al colaborador
        RespuestaHTTP respuesta = ConexionWs.peticionDELETE(url, parametros);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("Error al eliminar el colaborador. Inténtelo más tarde.");
        }

        return mensaje;
    }

    /**
     * Subir una fotografía de un colaborador.
     * @param idColaborador El ID del colaborador.
     * @param fotografia La fotografía en formato de bytes.
     * @return El mensaje de respuesta con el estado de la operación.
     */
    public static Mensaje subirFotografiaColaborador(int idColaborador, byte[] fotografia) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URI_WS + "colaborador/subirFoto/" + idColaborador;

        // Enviar solicitud para subir la fotografía
        RespuestaHTTP respuesta = ConexionWs.peticionPUTImagen(url, fotografia);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("Error al subir la fotografía del colaborador.");
        }

        return mensaje;
    }

    /**
     * Obtener la fotografía de un colaborador.
     * @param idColaborador El ID del colaborador.
     * @return El colaborador con su fotografía.
     */
    public static Colaborador obtenerFotografiaColaborador(int idColaborador) {
        Colaborador colaborador = null;
        String url = Constantes.URI_WS + "colaborador/obtenerFoto/" + idColaborador;

        // Enviar solicitud para obtener la fotografía del colaborador
        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            colaborador = gson.fromJson(respuesta.getContenido(), Colaborador.class);
        }

        return colaborador;
    }

    /**
     * Asignar una unidad a un colaborador.
     * @param colaborador El colaborador al que se le asignará la unidad.
     * @return El mensaje de respuesta con el estado de la operación.
     */
    public static Mensaje asignarUnidadColaborador(Colaborador colaborador) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URI_WS + "colaborador/asignarUnidad";
        Gson gson = new Gson();
        String parametros = gson.toJson(colaborador);

        // Enviar solicitud para asignar la unidad
        RespuestaHTTP respuesta = ConexionWs.peticionPUTjson(url, parametros);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("Error al asignar la unidad al colaborador.");
        }

        return mensaje;
    }

    /**
     * Cambiar la unidad de un colaborador.
     * @param colaborador El colaborador con la nueva unidad.
     * @return El mensaje de respuesta con el estado de la operación.
     */
    public static Mensaje cambiarUnidadColaborador(Colaborador colaborador) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URI_WS + "colaborador/cambiarUnidad";
        Gson gson = new Gson();
        String parametros = gson.toJson(colaborador);

        // Enviar solicitud para cambiar la unidad
        RespuestaHTTP respuesta = ConexionWs.peticionPUTjson(url, parametros);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje(respuesta.getContenido());
        }

        return mensaje;
    }

    /**
     * Desasignar la unidad de un colaborador.
     * @param colaborador El colaborador al que se le desasignará la unidad.
     * @return El mensaje de respuesta con el estado de la operación.
     */
    public static Mensaje desasignarUnidadColaborador(Colaborador colaborador) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URI_WS + "colaborador/desasignarUnidad";
        Gson gson = new Gson();
        String parametros = gson.toJson(colaborador);

        // Enviar solicitud para desasignar la unidad
        RespuestaHTTP respuesta = ConexionWs.peticionPUTjson(url, parametros);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("Error al desasignar la unidad del colaborador.");
        }

        return mensaje;
    }

    /**
     * Buscar un colaborador por su número de personal.
     * @param numeroPersonal El número de personal del colaborador.
     * @return El colaborador encontrado.
     */
    public static Colaborador buscarPorNumeroPersonal(String numeroPersonal) {
        Colaborador colaborador = null;
        String url = Constantes.URI_WS + "colaborador/buscarPorNumeroPersonal/" + numeroPersonal;

        // Enviar solicitud para obtener el colaborador por su número de personal
        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            colaborador = gson.fromJson(respuesta.getContenido(), Colaborador.class);
        }

        return colaborador;
    }

    /**
     * Buscar colaboradores por su rol.
     * @param rol El rol de los colaboradores a buscar.
     * @return Una lista de colaboradores con el rol especificado.
     */
    public static List<Colaborador> buscarPorRol(String rol) {
        List<Colaborador> colaboradores = null;
        String url = "";

        // Si el rol contiene espacios, lo formateamos correctamente
        if (rol.contains(" ")) {
            String rolFormateado = rol.replace(" ", "%20");
            url = Constantes.URI_WS + "colaborador/buscarPorRol/" + rolFormateado;
        } else {
            url = Constantes.URI_WS + "colaborador/buscarPorRol/" + rol;
        }

        // Enviar solicitud para obtener colaboradores por rol
        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Colaborador>>(){}.getType();
            colaboradores = gson.fromJson(respuesta.getContenido(), tipoLista);
        }

        return colaboradores;
    }

    /**
     * Buscar colaboradores por su nombre.
     * @param nombre El nombre del colaborador a buscar.
     * @return Una lista de colaboradores con el nombre especificado.
     */
    public static List<Colaborador> buscarPorNombre(String nombre) {
        List<Colaborador> colaboradores = null;
        String url;
        // Si el rol contiene espacios, lo formateamos correctamente
        if (nombre.contains(" ")) {
            String nombreFormateado = nombre.replace(" ", "%20");
            url = Constantes.URI_WS + "colaborador/buscarPorRol/" + nombreFormateado;
        } else {
            url = Constantes.URI_WS + "colaborador/buscarPorNombre/" + nombre;
        }


        // Enviar solicitud para obtener colaboradores por nombre
        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Colaborador>>(){}.getType();
            colaboradores = gson.fromJson(respuesta.getContenido(), tipoLista);
        }

        return colaboradores;
    }

    /**
     * Obtener todos los roles disponibles.
     * @return Una lista de roles.
     */
    public static List<Rol> roles() {
        List<Rol> roles = new ArrayList<>();
        String url = Constantes.URI_WS + "colaborador/roles";
        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Type tipoListaRoles = new TypeToken<List<Rol>>(){}.getType();
            Gson gson = new Gson();
            roles = gson.fromJson(respuesta.getContenido(), tipoListaRoles);
        }

        return roles;
    }

    /**
     * Obtener las unidades activas disponibles.
     * @return Una lista de unidades activas.
     */
    public static List<Unidad> obtenerUnidadesActivas() {
        List<Unidad> unidades = new ArrayList<>();
        String url = Constantes.URI_WS + "colaborador/unidadesActivas";
        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Type tipoListaUnidades = new TypeToken<List<Unidad>>(){}.getType();
            Gson gson = new Gson();
            unidades = gson.fromJson(respuesta.getContenido(), tipoListaUnidades);
        }

        return unidades;
    }
}
