/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.modelo.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import desktop.modelo.ConexionWs;
import desktop.modelo.RespuestaHTTP;
import desktop.modelo.pojo.Envio;
import desktop.modelo.pojo.Historial;
import desktop.modelo.pojo.Mensaje;
import desktop.modelo.pojo.Unidad;
import desktop.utilidades.Constantes;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author berna
 */
public class EnviosDAO {
    public static List<Envio> obtenerEnvio(){
        List<Envio> envios = null;
        String url = Constantes.URI_WS+"envio/obtener-envios";
        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);
        try{
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK);{
                 Gson gson = new Gson();
                 Type tipoLista = new TypeToken<List<Envio>>(){}.getType();
                 envios = gson.fromJson(respuesta.getContenido(), tipoLista);
            }
       }catch(Exception e){
           
       }
        return envios;

    }
    
    public static  List<Envio> buscarPorNumero(String numeroGuia) {
        List<Envio> envios = null;
        String url = Constantes.URI_WS + "envio/consultar-envio/" + numeroGuia;

        // Enviar solicitud para obtener el colaborador por su número de personal
        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
             Type tipoLista = new TypeToken<List<Envio>>(){}.getType();
            envios = gson.fromJson(respuesta.getContenido(), tipoLista);
        }

        return envios;
    }
    
    
     public static Mensaje asignarColaborador(Envio envio) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URI_WS + "envio/asignar-conductor";
        Gson gson = new Gson();
        String parametros = gson.toJson(envio);

        // Enviar solicitud para asignar la unidad
        RespuestaHTTP respuesta = ConexionWs.peticionPUTjson(url, parametros);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("Error al asignar conductor al envio.");
        }

        return mensaje;
    }
  
    public static Mensaje hacerCambios(Integer idColaborador,Integer idEnvio,Integer idEstatus,String cometario){
        Mensaje msj = new Mensaje();
        Historial historial = new Historial();
        historial.setidColaborador(idColaborador);
        historial.setIdEnvio(idEnvio);
        historial.setIdEstado(idEstatus);
        historial.setComentario(cometario);
        String url = Constantes.URI_WS + "envio/actualizar-estado";
        Gson gson = new Gson();
        String parametros = gson.toJson(historial);
        RespuestaHTTP respuesta = ConexionWs.peticionPOSTjson(url, parametros);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {

            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            msj.setError(true);
            msj.setMensaje("Error al asignar cambios de colaborador.");
        }
        return msj;

    }

    public static Mensaje editarEnvio(Envio envio) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URI_WS + "envio/actualizar-envio";
        Gson gson = new Gson();
        String parametros = gson.toJson(envio);

        // Enviar solicitud para registrar al colaborador
        RespuestaHTTP respuesta = ConexionWs.peticionPUTjson(url, parametros);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("Error al editar el envio. Inténtelo más tarde.");
        }

        return mensaje;
    }

    public static Mensaje registrarEnvio(Envio envio) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URI_WS + "envio/registro-envio";
        Gson gson = new Gson();
        String parametros = gson.toJson(envio);

        // Enviar solicitud para editar al colaborador
        RespuestaHTTP respuesta = ConexionWs.peticionPOSTjson(url, parametros);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("Error al registrar la información del envio. Inténtelo más tarde.");
        }

        return mensaje;
    }
     public static List<Envio> obtenerEnvios() {
        List<Envio> envios = new ArrayList<>();
        String url = Constantes.URI_WS + "envio/obtener-envios";
        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Type tipoListaEnvios = new TypeToken<List<Envio>>(){}.getType();
            Gson gson = new Gson();
            envios = gson.fromJson(respuesta.getContenido(), tipoListaEnvios);
        }

        return envios;
    }

}
