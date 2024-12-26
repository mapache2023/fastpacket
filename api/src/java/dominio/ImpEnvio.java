/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.LinkedHashMap;
import java.util.List;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Envio;
import pojo.Estatus;
import pojo.Historial;
import pojo.Mensaje;


public class ImpEnvio {
     public static Mensaje registrarEnvio(Envio envio){
        Mensaje msj = new Mensaje();
        SqlSession conexion = MyBatisUtil.obtenerConexion();
        if(conexion != null){
            try {
                Envio envioExistente = conexion.selectOne("envio.consultar",envio.getNumeroGuia());
                 if (envioExistente != null) {
                    msj.setMensaje("Error: El Numero de guia ingresado ya se encuentra registrado.");
                   msj.setError(Boolean.TRUE);
                    return msj;
                }
                int filasRegistro = conexion.insert("envio.registrar", envio);
                conexion.commit();
                if(filasRegistro > 0){
                    msj.setError(false);
                    msj.setMensaje("envio registrado: "+envio.getNumeroGuia()+" con extito");
                }else{
                    msj.setError(true);
                    msj.setMensaje("No se pudo registrar el envio");
                }
            } catch (Exception e) {
                msj.setError(true);
                msj.setMensaje(e.getMessage());
            }
        }else{
            msj.setError(true);
            msj.setMensaje("El servicio no esta disponible");
        }
        return msj;
    }
    
    public static Mensaje actualizarEnvio(Envio envio){
        Mensaje msj = new Mensaje();
        SqlSession conexion = MyBatisUtil.obtenerConexion();
        if(conexion != null){
            try {
                int filasActualizadas = conexion.update("envio.actualizar", envio);
                conexion.commit();
                if(filasActualizadas > 0){
                    msj.setError(false);
                    msj.setMensaje("envio actualizado "+envio.getNumeroGuia());
                }else{
                    msj.setError(true);
                    msj.setMensaje("No se pudo actualizar el envio");
                }
            } catch (Exception e) {
                msj.setError(true);
                msj.setMensaje(e.getMessage());
            }
        }
        return msj;
    }
    
  public static List<Envio> buscarEnvio(String numeroGuia){
        List<Envio> envio = null; 
        SqlSession conexion = MyBatisUtil.obtenerConexion();
        
        if(conexion != null){
            try {
            envio = conexion.selectList("envio.consultar", numeroGuia);
                
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return envio;   
    } 
    
    public static Mensaje asignarConductor(Envio envio){
        Mensaje msj = new Mensaje();
        SqlSession conexion = MyBatisUtil.obtenerConexion();
        if(conexion != null){
            try {
                int filasActualizadas = conexion.update("envio.asignar-conductor", envio);
                conexion.commit();
                if(filasActualizadas > 0){
                    msj.setError(false);
                    msj.setMensaje("envio actualizado ");
                }else{
                    msj.setError(true);
                    msj.setMensaje("No se pudo actualizar el envio");
                }
            } catch (Exception e) {
                msj.setError(true);
                msj.setMensaje(e.getMessage());
            }
        }
        return msj;
    }
    
     public static List<Envio> obtenerEnvios(){
        List<Envio> envios = null;
        
        SqlSession conexion = MyBatisUtil.obtenerConexion();
        
        if(conexion != null){
            try{
                envios = conexion.selectList("envio.obtenerEnvios");
            }catch(Exception e){
                e.getMessage();
            }
        }
        
        return envios;
    }
    
     public static Mensaje actualizarEstado(Historial historial){
        Mensaje msj = new Mensaje();
        msj.setError(true);
        LinkedHashMap<String, Object> parametros = new LinkedHashMap<>();
        parametros.put("idEstado", historial.getIdEstado());
        parametros.put("idEnvio", historial.getIdEnvio());
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try {
                 int filasAfectadas = conexionBD.update("agregarHistorial", historial);
                int filasAfectadas2 = conexionBD.update("actualizarEstadoEnvio", parametros);
                conexionBD.commit();
                if(filasAfectadas > 0){
                    msj.setError(false);
                    msj.setMensaje("Cambio del colaborador guardado correctamente.");
                }else{
                    msj.setMensaje("Lo sentimos hubo un error al intentar guardar el "
                            + "cambio, por favor inténtelo más tarde.");
                }
            } catch (Exception e) {
                msj.setMensaje("Error: "+e.getMessage());
            } finally{
                conexionBD.close();
            }
        }else{
            msj.setMensaje("Error de conexión, por el momento no se puede registrar el "
                    + "cambio del colaborador.");
        }
        return msj;
    } 

    public static List<Estatus> obtenerEstados() {
        List<Estatus> estatuses = null;
        
        SqlSession conexion = MyBatisUtil.obtenerConexion();
        
        if(conexion != null){
            try{
                estatuses = conexion.selectList("envio.obtenerEstatus");
            }catch(Exception e){
                e.getMessage();
            }
        }
        
        return estatuses;
    }

}
