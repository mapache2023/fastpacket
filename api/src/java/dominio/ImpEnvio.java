/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.List;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Envio;
import pojo.Mensaje;


public class ImpEnvio {
     public static Mensaje registrarEnvio(Envio envio){
        Mensaje msj = new Mensaje();
        SqlSession conexion = MyBatisUtil.obtenerConexion();
        if(conexion != null){
            try {
                int filasRegistro = conexion.insert("envio.registrar", envio);
                conexion.commit();
                if(filasRegistro > 0){
                    msj.setError(false);
                    msj.setMensaje("envio registrado: "+envio.getNumeroGuia()+"con extito");
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
    
  /*public static List<Envio> buscarEnvio(String numeroGuia){
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
    
     public static Mensaje actualizarEstado(Envio envio){
        Mensaje msj = new Mensaje();
        SqlSession conexion = MyBatisUtil.obtenerConexion();
        if(conexion != null){
            try {
                int filasActualizadas = conexion.update("envio.actualizar-estatus", envio);
                conexion.commit();
                if(filasActualizadas > 0){
                    msj.setError(false);
                    msj.setMensaje("envio actualizado "+envio.getIdEnvio());
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
    } */

}
