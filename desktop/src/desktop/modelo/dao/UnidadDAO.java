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
import desktop.modelo.pojo.Colaborador;
import desktop.modelo.pojo.Mensaje;
import desktop.modelo.pojo.Tipo;
import desktop.modelo.pojo.Unidad;
import desktop.utilidades.Constantes;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

/**
 *
 * @author berna
 */
public class UnidadDAO {
      public static List<Unidad> obtenerUnidad (){
        List<Unidad> unidades = null;
        String url = Constantes.URI_WS+"unidad/obtener-unidades";
        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);
        try{
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK);{
                 Gson gson = new Gson();
                 Type tipoLista = new TypeToken<List<Unidad>>(){}.getType();
                 unidades = gson.fromJson(respuesta.getContenido(), tipoLista);
            }
       }catch(Exception e){
           
       }
        return unidades;
    }   
      
      
    public static List<Tipo> obtenerTipoUnidad(){
        List<Tipo> tipos = null;
        String url = Constantes.URI_WS+"unidad/obtener-tipos";
        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);
        try{
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK);{
                 Gson gson = new Gson();
                 Type tipoLista = new TypeToken<List<Tipo>>(){}.getType();
                 tipos = gson.fromJson(respuesta.getContenido(), tipoLista);
              //   tipos = gson.fromJson(respuesta.getContenido(), tipoLista);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    return tipos;
    }
    
    public static Mensaje registarUnidad(Unidad unidad){
        Mensaje msj = new Mensaje();
        String url = Constantes.URI_WS+"unidad/registro";
        Gson gson = new Gson();
        try{
            String paramentros = gson.toJson(unidad); 
            RespuestaHTTP respuesta = ConexionWs.peticionPOST(url, paramentros);
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                msj.setError(true);
                msj.setMensaje(respuesta.getContenido());
            }
        }catch(Exception e){
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        }
        return msj;
    }
    
    public static Mensaje eliminarUnidad(int idUnidad){
        Mensaje msj = new Mensaje();
        String url = Constantes.URI_WS+"unidad/";
        Gson gson = new Gson();
        try{
            String parametros = gson.toJson(idUnidad);
            RespuestaHTTP respuesta = ConexionWs.peticionDELETE(url, parametros);
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                msj.setError(true);
                msj.setMensaje(respuesta.getContenido());
            }
        }catch(Exception e){
            msj.setError(true);
            msj.setMensaje(e.getMessage());
        } 
        return msj;
    }
    
    public static Unidad buscarPorVin(String vin) {
        Unidad unidad = null;
        String url = Constantes.URI_WS + "unidad/buscar-vin/" + vin;

        // Enviar solicitud para obtener el colaborador por su número de personal
        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            unidad = gson.fromJson(respuesta.getContenido(), Unidad.class);
        }

        return unidad;
    }
     
    public static Unidad buscarPorMarca(String marca) {
        Unidad unidad = null;
        String url = Constantes.URI_WS + "unidad/buscar-marca/" + marca;

        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            unidad = gson.fromJson(respuesta.getContenido(), Unidad.class);
        }

        return unidad;
    }
     
    public static Unidad buscarPorNumero(String numero) {
        Unidad unidad = null;
        String url = Constantes.URI_WS + "unidad/buscar-numero/" + numero;

        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            unidad = gson.fromJson(respuesta.getContenido(), Unidad.class);
        }

        return unidad;
    }
     
     
    public static List<Colaborador> obtenerTipoColaborador(){
        List<Colaborador> conductores = null;
        String url = Constantes.URI_WS+"unidad/obtenerConductores";
        RespuestaHTTP respuesta = ConexionWs.peticionGET(url);
        try{
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK);{
                 Gson gson = new Gson();
                 Type tipoLista = new TypeToken<List<Colaborador>>(){}.getType();
                 conductores = gson.fromJson(respuesta.getContenido(), tipoLista);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    return conductores;
    }
    

}
